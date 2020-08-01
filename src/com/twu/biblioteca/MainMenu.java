package com.twu.biblioteca;

import com.twu.biblioteca.book.Book;
import com.twu.biblioteca.book.BookRepository;
import com.twu.biblioteca.movie.Movie;
import com.twu.biblioteca.movie.MovieRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class MainMenu {
    private List<String> options;
    private BookRepository bookRepository;
    private MovieRepository movieRepository;
    private Scanner scanner;
    String bookName;
    String movieName;

    public MainMenu(List<String> options,BookRepository bookRepository,MovieRepository movieRepository){
        this.options = options;
        this.bookRepository=bookRepository;
        this.movieRepository = movieRepository;
    }

    public void PrintAllMenuList(){
        IntStream.range(0, options.size()).forEach(i -> System.out.println(i + 1 + "- " + options.get(i)));//^^^
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

    //如何使用scanner https://www.runoob.com/java/java-scanner-class.html
    public void UserSelectOptions(){
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext()){
            String InputOption = scanner.nextLine();
            if(CheckInputIsValid(InputOption)){
                String optionChoice = options.get(Integer.valueOf(InputOption)-1); //index 比本身小1
                //System.out.println(optionChoice);

                switch (optionChoice){
                    case "List of books":
                        //System.out.println("List of books");
                        displayBooks();
                        break;
                    case "Checkout a book":
                        //System.out.println("Checkout a book");
                        bookName = scanner.nextLine();
                        checkOutBook(bookName);
                        break;
                    case "Return a book":
                        //System.out.println("Return a book");
                        bookName = scanner.nextLine();
                        returnBook(bookName);
                        break;
                    case "List of movies":
                        displayMovies();
                        break;
                    case "Checkout a movie":
                        movieName = scanner.nextLine();
                        checkOutMovie(movieName);
                        break;
                    case "Quit":
                        System.out.println("Goodbye!");
                        System.exit(0);
                }
            }
        }
    }


    public void displayBooks(){
        System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n","** Index **","|","** Title **","|", "** Author **","|", "** ISBN **", "|","** Year **");
        for (Book book: bookRepository.getAvailableBooks()){
            System.out.printf("%-11s%-2s%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n", book.getindex(),"|",book.getTitle(), "|",
                    book.getAuthor(), "|", book.getIsbn(), "|", book.getYear());
        }
        System.out.printf("------------------------------------------------------\n");
        System.out.printf("1- List of books");
    }

    public void displayMovies(){
        System.out.printf("%-30s%-2s%-30s%-2s%-6s%n","** Title **","|", "** Director **","|","** Year **");
        for(Movie movie:movieRepository.getAvailableMovies()){
            System.out.printf("%-30s%-2s%-30s%-2s%-6s%n", movie.getTitle(),"|",movie.getDirector(),"|",movie.getYear());
        }
        System.out.printf("------------------------------------------------------\n");
        System.out.printf("4- List of movies");

    }

    private void checkOutBook(String bookName) {
        String input;
        System.out.println("Which book would you like to checkout?[Please input BOOK NAME]");
        input = bookName;
        System.out.println(bookRepository.checkOutBook(input)? "Thank you! Enjoy the book." : "Sorry, that book is not available.");
    }

    private void returnBook(String bookName){
        String input = bookName;
        System.out.println("Which book would you like to Return?[Please input BOOK NAME]");
        System.out.println(bookRepository.returnBook(input)? "Thanks for your return, have a good day!" : "This book may not borrowed from our library, please contact the librarian if not.");
    }

    private void checkOutMovie(String movieName){
        String input = movieName;
        System.out.println("Which movie would you like to checkout?[Please input MOVIE NAME]");
        System.out.println(movieRepository.checkOutMovie(input)? "Thank you! Enjoy the movie." : "Sorry, that movie is not available.");
    }
}
