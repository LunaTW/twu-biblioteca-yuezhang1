package com.twu.biblioteca.book;

import com.twu.biblioteca.book.Book;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRepository {

    private static List<Book> availableBooks = new ArrayList<>(Arrays.asList(
            new Book("The little prince","Antoine de Saint-Exupery","9787539998312", Year.of(2017)),
            new Book("And Then There were none","Agatha Christie","9780007136834",Year.of(2007)),
            new Book("Harry Potter","Joanne Rowling","9573317249234",Year.of(2008))
    ));
    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

    public BookRepository(List<Book> books){
    }
}




