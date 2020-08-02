package com.twu.biblioteca.movie;

import org.junit.Test;
import java.time.Year;
import static org.junit.Assert.*;

public class MovieRepositoryTest {
    Movie movie = new Movie("Upcoming movie","YUE", Year.of(2023));

    @Test
    public void MovieCanBeAddedInMOVIELIST(){
        assertEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("Upcoming movie")).findFirst().orElse(null),null);
        MovieRepository.availableMovies.add(movie);
        assertNotEquals(MovieRepository.availableMovies.stream().filter(movie -> movie.getTitle().equals("Upcoming movie")).findFirst().orElse(null),null);
    }

    @Test
    public void MovieCanBeReturnMOVIELIST(){
        MovieRepository.availableMovies.add(movie);
        assertTrue(MovieRepository.getAvailableMovies().contains(movie));
    }

    @Test
    public void AvailableMovieCanBeCheckOutMovieLIST(){
        assertTrue(MovieRepository.checkOutMovie("Green Book"));
        assertFalse(MovieRepository.returnMovie("WrongMovie"));
    }

    public void AvailableMovieCanBeReturnMOVIELIST(){
        assertTrue(MovieRepository.returnMovie("1917"));
        assertFalse(MovieRepository.returnMovie("WrongMovie"));
    }
}
