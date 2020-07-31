package com.twu.biblioteca;

import com.twu.biblioteca.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;


public class BookRepository {


    private static List<Book> availableBooks = new ArrayList<>(Arrays.asList(
            new Book("title1","author1","isbn1", Year.of(2020)),
            new Book("title2","author2","isbn2",Year.of(2020)),
            new Book("title3","author3","isbn3",Year.of(2020))
    ));
    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    public BookRepository(List<Book> books){
        //super(books);
    }







}




