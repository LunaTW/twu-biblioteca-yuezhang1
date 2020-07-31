package com.twu.biblioteca;

import java.time.Year;

public class Book {
    private String author;
    private String isbn;
    private String title;
    private Year year;

    public Book(String title, String author, String isbn, Year year) {
        this.setTitle(title);
        this.author = author;
        this.isbn = isbn;
        this.setYear(year);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Year getYear() {
        return year;
    }
    public String getIsbn() {
        return isbn;
    }
}