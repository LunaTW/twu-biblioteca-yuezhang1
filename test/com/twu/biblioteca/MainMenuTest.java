package com.twu.biblioteca;

import com.twu.biblioteca.book.Book;
import com.twu.biblioteca.book.BookRepository;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class MainMenuTest {
    private List<String> options;
    private MainMenu mainMenu;
    private ByteArrayOutputStream MainMenuOutput;
    private String InvalidOption = "Please select a valid option";
    private String option1="List of books";

    List<Book> BookList = new ArrayList<>(Arrays.asList(
            new Book("title1","author1","isbn2", Year.of(2020)),
            new Book("title2","author2","isbn2",Year.of(2020)),
            new Book("title3","author3","isbn3",Year.of(2020))
    ));
    BookRepository bookRepository = new BookRepository(BookList);

    @Before
    public void setUp() throws Exception{
        MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
    }

    //****************************  (1.4) View main menu of options ****************************** //
    @Test
    public void MainMenuWasPrintedWhenZeroOption(){
        options = new ArrayList<>(Arrays.asList());
        mainMenu = new MainMenu(options,null);
        mainMenu.PrintAllMenuList();
        assertEquals("", MainMenuOutput.toString());
    }

    @Test
    public void MainMenuWasPrintedWhenOneOption(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null);
        mainMenu.PrintAllMenuList();
        assertEquals("1- " + option1 + "\n", MainMenuOutput.toString());
    }
    //*********************************** 1.5 当我选择无效选项时得到通知 *********************************** //
    @Test
    //此时只有一个option,只有 1 是正确的，其他如 3，list，-8，3.5 均无效
    public void GetNotifiedWhenChoseInvalidOption_OtherNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null);
        System.setIn(new ByteArrayInputStream("7".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_Decimal(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null);
        System.setIn(new ByteArrayInputStream("1.5".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_NegativeNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null);
        System.setIn(new ByteArrayInputStream("-2".getBytes()));
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test
    public void GetNotifiedWhenChoseInvalidOption_NonNumber(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,null);
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
        mainMenu = new MainMenu(options,bookRepository);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        mainMenu.UserSelectOptions();
        assertNotEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }

    @Test //包含了完成的书籍信息
    public void ViewAListOfBooks(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options,bookRepository);
        System.setIn(new ByteArrayInputStream("1".getBytes()));
        mainMenu.UserSelectOptions();
        mainMenu.PrintAllMenuList();
        assertEquals("** Title **                   | ** Author **                  | ** ISBN **     | ** Year **\n" +
                                "The little prince             | Antoine de Saint-Exupery      | 9787539998312  | 2017  \n" +
                                "And Then There were none      | Agatha Christie               | 9780007136834  | 2007  \n" +
                                "Harry Potter                  | Joanne Rowling                | 9573317249234  | 2008  \n" +
                                "------------------------------------------------------\n" +
                                "1- " + option1 + "\n", MainMenuOutput.toString());
    }



}


