package com.twu.biblioteca;

import com.twu.biblioteca.book.Book;
import com.twu.biblioteca.book.BookRepository;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;


public class MainMenu {
    private List<String> options;
    private BookRepository bookRepository;

    public MainMenu(List<String> options,BookRepository bookRepository){
        this.options = options;
        this.bookRepository=bookRepository;
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
        //从键盘接收数据
        Scanner scanner = new Scanner(System.in);
        // 判断是否还有输入
        if(scanner.hasNext()){
            //1、以Enter为结束符,也就是说 nextLine()方法返回的是输入回车之前的所有字符。
            //2、可以获得空白。
            String InputOption = scanner.nextLine();
            //只有当选项有效的时候才进行
            if(CheckInputIsValid(InputOption)){
                String optionChoice = options.get(Integer.valueOf(InputOption)-1); //index 比本身小1
                switch (optionChoice){
                    case "List of books":
                        displayBooks();
                        break;
                }
            }
        }
    }


    public void displayBooks(){
        System.out.printf("%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n", "** Title **","|", "** Author **","|", "** ISBN **", "|","** Year **");
        for (Book book: bookRepository.getAvailableBooks()){
            System.out.printf("%-30s%-2s%-30s%-2s%-15s%-2s%-6s%n", book.getTitle(), "|",
                    book.getAuthor(), "|", book.getIsbn(), "|", book.getYear());
        }
        System.out.printf("------------------------------------------------------\n");
    }
}


