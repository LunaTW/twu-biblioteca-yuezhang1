package com.twu.biblioteca;

import com.twu.biblioteca.book.Book;
import com.twu.biblioteca.book.BookRepository;
import com.twu.biblioteca.movie.Movie;
import com.twu.biblioteca.movie.MovieRepository;
import com.twu.biblioteca.user.User;
import com.twu.biblioteca.user.UserRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MainMenu {
    private List<String> options;
    private BookRepository bookRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private Scanner scanner;
    private String bookName;
    private String movieName;
    private String UserID;
    private String password;
    private String UserSelectedOption;

    public MainMenu(List<String> options,BookRepository bookRepository,MovieRepository movieRepository,UserRepository userRepository){
        this.options = options;
        this.bookRepository=bookRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public void login(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input your UserID: ");
        UserID = scanner.nextLine();
        System.out.println("Please input your Password: ");
        password = scanner.nextLine();
        //System.out.println("Please click return");
        //UserSelectedOption = scanner.nextLine();
        if(UserID == null  ){
            System.out.println("UserID cannot be empty!");
            login();
        }
        if(password == null  ){
            System.out.println("UserID cannot be empty!");
            login();
        }

        if (UserRepository.areValidCredentials(UserID,password)){
            System.out.println("UserID : "+ UserID+"\n"+
                    "Successful login\n------------------------------------------------------");
            PrintAllMenuList();
            UserSelectOptions(); //Question hide => test
        }else{
            System.out.println("Sorry that's not a valid ID/PASSWORD");
            login();
        }
    }

    public void PrintAllMenuList(){
        System.out.println("What would you like to do?");
        IntStream.range(0, options.size()).forEach(i -> System.out.println("Enter "+ (i + 1) + " : " + options.get(i)));//^^^
    }

    //检查输入项是否有效
    //1. 有输入 2. 是个数字 3. 数字在选项的list中
    private boolean CheckInputIsValid(String option){
        int InputNumber;
        try {
            InputNumber = Integer.valueOf(option);
        }
        catch(NumberFormatException e){
            System.out.println("Please select a valid option");
            return false;
        }
        if (InputNumber <= 0 || InputNumber > options.size()){
            System.out.println("Please select a valid option");
            return false;
        }
        return true;
    }

    public void UserSelectOptions(){
        Scanner scanner = new Scanner(System.in);
        UserSelectedOption = scanner.nextLine();
        if (UserSelectedOption != null) {
            String InputOption = UserSelectedOption;
            if(CheckInputIsValid(InputOption)) {
                String optionChoice = options.get(Integer.valueOf(InputOption) - 1); //index 比本身小1
                //System.out.println(optionChoice);
                switch (optionChoice) {
                    case "View a list of books":
                        displayBooks();
                        break;
                    case "Checkout a book":
                        System.out.println("Which book would you like to checkout?[Please input BOOK NAME]");
                        bookName = scanner.nextLine();
                        checkOutBook(bookName);
                        break;
                    case "Return a book":
                        System.out.println("Which book would you like to Return?[Please input BOOK NAME]");
                        bookName = scanner.nextLine();
                        returnBook(bookName);
                        break;
                    case "View books checked out":
                        displayCheckOutBook();
                        break;
                    case "View a list of movies":
                        displayMovies();
                        break;
                    case "Checkout a movie":
                        System.out.println("Which movie would you like to checkout?[Please input MOVIE NAME]");
                        movieName = scanner.nextLine();
                        checkOutMovie(movieName);
                        break;
                    case "Return a movie":
                        System.out.println("Which movie would you like to Return?[Please input MOVIE NAME]");
                        movieName = scanner.nextLine();
                        returnMovie(movieName);
                        break;
                    case "Quit":
                        System.out.println("Goodbye!");
                        System.exit(0);
                    case "View my information":
                        String userID;
                        System.out.println("Please confirm your userID: ");
                        userID = scanner.nextLine();
                        System.out.println("Please confirm your Password: ");
                        password = scanner.nextLine();
                        if(UserRepository.areValidCredentials(UserID,password)){
                            ViewMyInformation(userID);
                        }else{
                            System.out.println("Sorry that's not a valid ID/PASSWORD");
                            login();
                        }
                }
                //System.out.println("------------------------------------------------------");
                //PrintAllMenuList();
                //UserSelectOptions(); //May Question: 添加这一行，程序可以一直循环，但测试无法通过
            }
        }
    }

    private void displayBooks(){
        System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n","** Index **","|","** Title **","|", "** Author **","|", "** ISBN **", "|","** Year **");
        for (Book book: bookRepository.getAvailableBooks()){
            System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n", book.getindex(),"|",book.getTitle(), "|",
                    book.getAuthor(), "|", book.getIsbn(), "|", book.getYear());
        }
    }
    private void displayCheckOutBook(){
        System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n","** Index **","|","** Title **","|", "** Author **","|", "** ISBN **", "|","** Year **");
        for (Book book: bookRepository.getCheckedOutBooks()){
            System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n", book.getindex(),"|",book.getTitle(), "|",
                    book.getAuthor(), "|", book.getIsbn(), "|", book.getYear());
        }
    }
    public void displayMovies(){
        System.out.printf("%-30s%-2s%-30s%-2s%-6s%n","** Title **","|", "** Director **","|","** Year **");
        for(Movie movie:movieRepository.getAvailableMovies()){
            System.out.printf("%-30s%-2s%-30s%-2s%-6s%n", movie.getTitle(),"|",movie.getDirector(),"|",movie.getYear());
        }
    }

    private void checkOutBook(String bookName) {
        String input;
        input = bookName;
        System.out.println(bookRepository.checkOutBook(input)? "Thank you! Enjoy the book." : "Sorry, that book is not available.");
    }
    private void checkOutMovie(String movieName){
        String input = movieName;
        System.out.println(movieRepository.checkOutMovie(input)? "Thank you! Enjoy the movie." : "Sorry, that movie is not available.");
    }

    private void returnBook(String bookName){
        String input = bookName;
        System.out.println(bookRepository.returnBook(input)? "Thanks for your return, have a good day!" : "This book may not borrowed from our library, please contact the librarian if not.");
    }
    private void returnMovie(String movieName){
        String input = movieName;
        System.out.println(movieRepository.returnMovie(input)? "Thanks for your return, have a good day" : "This movie may not borrowed from our library, please contact the librarian if not.");
    }

    private void ViewMyInformation(String userID) {
        System.out.printf("%-13s%-2s%-15s%-2s%-19s%-2s%-16s%n","** UserID **","|","** UserName **","|", "** PassWord **","|", "** Email **");
        User USER = UserRepository.getUser(userID);
        System.out.printf("%-13s%-2s%-15s%-2s%-19s%-2s%-16s%n", USER.getUserID(),"|",USER.getUserName(), "|",
                USER.getPassword(), "|", USER.getEmail());
    }
}


