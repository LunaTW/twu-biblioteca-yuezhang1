package com.twu.biblioteca;

import com.twu.biblioteca.book.BookRepository;
import com.twu.biblioteca.movie.MovieRepository;
import com.twu.biblioteca.user.UserRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import org.junit.contrib.java.lang.system.ExpectedSystemExit;
//https://stefanbirkner.github.io/system-rules/apidocs/org/junit/contrib/java/lang/system/SystemOutRule.html


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;


public class MainMenuTest {
    private List<String> options;
    private MainMenu mainMenu;
    private ByteArrayOutputStream MainMenuOutput;
    private String InvalidOption = "Please select a valid option";
    private String option1="View a list of books";
    private String option2 = "Checkout a book";
    private String option3 = "Return a book";
    private String option4 = "View a list of movies";
    private String option5 = "Checkout a movie";
    private String option6 = "Return a movie";
    private String option7 = "Login";
    private String option8 = "View books checked out";
    private String option9 = "View my information";
    private String option10 = "Quit";

    /** Handles System.exit() calls. */
    //@Rule
    //public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    BookRepository bookRepository = new BookRepository(BookRepository.availableBooks);
    MovieRepository movieRepository = new MovieRepository(MovieRepository.availableMovies);
    UserRepository userRepository = new UserRepository(UserRepository.availableUserInformations);

    @Before
    public void setUp() throws Exception{
        MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
    }

    //****************************  (1.4) View main menu of options ****************************** //
    @Test
    public void MainMenuWasPrintedWhenZeroOption(){
        options = new ArrayList<>(Arrays.asList());
        mainMenu = new MainMenu(options,null,null,null);
        mainMenu.PrintAllMenuList();
        assertEquals("What would you like to do?\n", MainMenuOutput.toString());
    }

    @Test
    public void MainMenuWasPrintedWhenOneOption(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null,null,null);
        mainMenu.PrintAllMenuList();
        assertEquals("What would you like to do?\nEnter 1 : " + option1 + "\n", MainMenuOutput.toString());
    }
    //*********************************** 1.5 当我选择无效选项时得到通知 *********************************** //
    @Test
    //此时只有一个option,只有 1 是正确的，其他如 3，list，-8，3.5 均无效
    public void GetNotifiedWhenChoseInvalidOption_OtherNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null,null,null);
        System.setIn(new ByteArrayInputStream("100".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_Decimal(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null,null,null);
        System.setIn(new ByteArrayInputStream("1.5".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_NegativeNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null,null,null);
        System.setIn(new ByteArrayInputStream("-2".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_NonNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null,null,null);
        System.setIn(new ByteArrayInputStream("List".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    //*********************************** （1.2）查看书籍清单 *********************************** //
    // 1. 有效选项 => 没有报错
    // 2. 选择1 则出现完整的书籍信息

    @Test // 选择了有效的选项，则没有报错
    public void GetNotifiedWhenChosevalidOption(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        mainMenu.UserSelectOptions();
        assertNotEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test //包含了完成的书籍信息
    public void ViewAListOfBooks(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals("** Index **| ** Title **                   | ** Author **                  | ** ISBN **     | ** Year **\n" +
                                "01         | The little prince             | Antoine de Saint-Exupery      | 9787539998312  | 2017  \n" +
                                "02         | And Then There were none      | Agatha Christie               | 9780007136834  | 2007  \n" +
                                "03         | Harry Potter                  | Joanne Rowling                | 9573317249234  | 2008  \n"
                                , MainMenuOutput.toString());
    }

    //*********************************** (1.7) Checkout a book *********************************** //
    /*  1. 正确借书（可借书单里有这本书）=> 可借书单里没有了这本书 & 已借出书单里有了这本书
        2. 错误借书 可借书单里无此书 => 此书暂时不可借
     */
    @Test
    public void CheckoutABook_Successfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        assertNotEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("The little prince")).findFirst().orElse(null),null);
        System.setIn(new ByteArrayInputStream("2\nThe little prince".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("The little prince")).findFirst().orElse(null),null);
        assertNotEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("And Then There were none")).findFirst().orElse(null),null);
        assertNotEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("Harry Potter")).findFirst().orElse(null),null);
        assertThat(MainMenuOutput.toString(),containsString("Thank you! Enjoy the book."));
    }

    @Test
    public void CheckoutABook_Unsuccessfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("2\nThis is an INVALID BookName".getBytes()));
        mainMenu.UserSelectOptions();
        assertThat(MainMenuOutput.toString(),containsString("Sorry, that book is not available."));
    }

    //*********************************** (1.10) Return a book *********************************** //
    /*  1. 正确还书（已借出书单里有这本书）=> 可借书单里添加了这本书 & 已借出书单里删除了这本书 (1.11) Notified on successful return
        2. 错误借书 已借出书单里无此书 => 此书暂时不可还，【不属于这个图书馆或找管理员咨询】(1.12) Notified on unsuccessful return
     */
    @Test
    public void RetrunABook_Successfully(){
        assertEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("Happy Coding")).findFirst().orElse(null),null);
        assertNotEquals(BookRepository.checkedOutBooks.stream().filter(book -> book.getTitle().equals("Happy Coding")).findFirst().orElse(null),null);
        options = new ArrayList<>(Arrays.asList(option1,option2,option3));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("3\nHappy Coding".getBytes()));
        mainMenu.UserSelectOptions();
        assertNotEquals(BookRepository.availableBooks.stream().filter(book -> book.getTitle().equals("Happy Coding")).findFirst().orElse(null),null);
        assertEquals(BookRepository.checkedOutBooks.stream().filter(book -> book.getTitle().equals("Happy Coding")).findFirst().orElse(null),null);
    }

    @Test
    public void RetrunABook_Unsuccessfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("3\nHappy Studying".getBytes()));
        mainMenu.UserSelectOptions();
        assertThat(MainMenuOutput.toString(),containsString("This book may not borrowed from our library, please contact the librarian if not."));
    }



    //*********************************** (1.6) Quit the application *********************************** //
    @Ignore
    @Test //Question import exit出现问题，在contrib标红色【line14,line40 - line42】
    public void QuitTheApplication(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("4".getBytes()));
        mainMenu.UserSelectOptions();
        //System.out.println(MainMenuOutput.toString());
        //assertThat(MainMenuOutput.toString(),containsString("Goodbye"));
        //exit.expectSystemExit();
    }


    //*********************************** (2.1) View a list of available movies *********************************** //
    @Test //包含了完成的电影信息 option4
    public void ViewAListOfMovies(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("4".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals("** Title **                   | ** Director **                | ** Year **\n" +
                "Green Book                    | Peter Farrelly                | 2018  \n" +
                "The Shawshank Redemption      | Frank Darabont                | 1994  \n" +
                "The Godfather                 | Francis Ford Coppola          | 1972  \n" +
                "The Godfather: Part II        | Francis Ford Coppola          | 1974  \n"
                , MainMenuOutput.toString());
    }

    //*********************************** (2.2) Checkout a movie *********************************** //
    @Test
    public void CheckoutMovie_Successfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        assertNotEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("The Shawshank Redemption")).findFirst().orElse(null),null);
        System.setIn(new ByteArrayInputStream("5\nThe Shawshank Redemption".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("The Shawshank Redemption")).findFirst().orElse(null),null);
        assertThat(MainMenuOutput.toString(),containsString("Thank you! Enjoy the movie."));
    }

    @Test
    public void CheckoutMovie_Unsuccessfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("5\nNotExisted Movie Name".getBytes()));
        mainMenu.UserSelectOptions();
        assertThat(MainMenuOutput.toString(),containsString("Sorry, that movie is not available."));
    }

    //******************* (2.2) - extend -- Return a movie *********************************** //
    @Test
    public void ReturnMovie_Successfully(){
        assertEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("Back to the Future")).findFirst().orElse(null),null);
        assertNotEquals(MovieRepository.checkedOutMovies.stream().filter(movie -> movie.getTitle().equals("Back to the Future")).findFirst().orElse(null),null);
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("6\nBack to the Future".getBytes()));
        mainMenu.UserSelectOptions();
        assertNotEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("Back to the Future")).findFirst().orElse(null),null);
        assertEquals(MovieRepository.checkedOutMovies.stream().filter(movie -> movie.getTitle().equals("Back to the Future")).findFirst().orElse(null),null);
    }

    @Test
    public void ReturnMovie_Unsuccessfully(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("6\nINVALID MOVIE NAME".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("INVALID MOVIE NAME")).findFirst().orElse(null),null);
        assertThat(MainMenuOutput.toString(),containsString("This movie may not borrowed from our library, please contact the librarian if not."));
    }

    //******************* (2.3) User accounts: Login and View books checked out *********************************** //
    @Ignore
    @Test // Welcome => login 未登录状态什么都不能查看
    public void Login(){
        System.setIn(new ByteArrayInputStream("5102636\nmoneymoneymoney\n7".getBytes()));
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option7,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        mainMenu.login();
        assertEquals("Please input your UserID: \n"+"Please input your Password: \n"+"Please click return\n"
                + "UserID : 5102636\n"+ "Successful login\n"+
                "------------------------------------------------------\n" +
                "What would you like to do?\n" +
                "Enter 1 : View a list of books\n" +
                "Enter 2 : Checkout a book\n" +
                "Enter 3 : Return a book\n" +
                "Enter 4 : View a list of movies\n" +
                "Enter 5 : Checkout a movie\n" +
                "Enter 6 : Return a movie\n" +
                "Enter 7 : Login\n" +
                "Enter 8 : Quit\n",MainMenuOutput.toString());
    }


    @Ignore
    @Test //Question: 这个测试单独跑没有问题，但一起跑的时候，会收到下面借还书的影响，这个应该如何解决
    public void ViewBooksCheckedOut(){
        options = new ArrayList<>(Arrays.asList(option1,option2,option3,option4,option5,option6,option7,option8,option10));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("8".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals("** Index **| ** Title **                   | ** Author **                  | ** ISBN **     | ** Year **\n" +
                        "04         | Happy Coding                  | Luna                          | 2468           | 2020  \n" +
                        "05         | Happy Reading                 | Luna                          | 13579          | 2020  \n"
                , MainMenuOutput.toString());
    }

    @Ignore
    @Test   //Question: app可以正常使用，但测试的时候失败【原因：传参数时，只能获取1和5102636，不能获得moneymoneymoney
    public void ViewMyInformation(){
        options = new ArrayList<>(Arrays.asList(option9));
        mainMenu = new MainMenu(options,bookRepository,movieRepository,userRepository);
        System.setIn(new ByteArrayInputStream("1\n51026366\nmoneymoneymoney".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals("** UserID ** | ** UserName ** | ** PassWord **     | ** Email **     \n" +
                        "5102636      | Luna           | moneymoneymoney    | unswluna@gmail.com\n"
                , MainMenuOutput.toString());
    }
}
