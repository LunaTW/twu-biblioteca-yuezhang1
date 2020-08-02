package com.twu.biblioteca.book;

import java.time.Year;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class BookTest {
    Book book = new Book("01","The little prince","Antoine de Saint-Exupery","9787539998312", Year.of(2017));
    BookRepository bookRepository1 = new BookRepository(BookRepository.availableBooks);
    BookRepository bookRepository2 = new BookRepository(BookRepository.checkedOutBooks);

    @Test
    public void BookHasAllParts_Title_Author_Isbn_Year(){
        assertEquals(book.getindex(),"01");
        assertEquals(book.getTitle(),"The little prince");
        assertEquals(book.getAuthor(),"Antoine de Saint-Exupery");
        assertEquals(book.getIsbn(),"9787539998312");
        assertEquals(book.getYear(),Year.of(2017));
    }
}
