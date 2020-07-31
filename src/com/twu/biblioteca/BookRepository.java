package com.twu.biblioteca;

import com.twu.biblioteca.Book;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private static List<Book> availableBooks = new ArrayList<>();
    public List<Book> getAvailableBooks() {
        return availableBooks;
    }

}




