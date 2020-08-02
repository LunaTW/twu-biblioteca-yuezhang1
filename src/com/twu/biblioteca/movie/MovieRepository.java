package com.twu.biblioteca.movie;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MovieRepository {

    public static List<Movie> availableMovies = new ArrayList<>(Arrays.asList(
            new Movie("Green Book","Peter Farrelly",Year.of(2018)),
            new Movie("The Shawshank Redemption","Frank Darabont",Year.of(1994)),
            new Movie("The Godfather","Francis Ford Coppola",Year.of(1972)),
            new Movie("The Godfather: Part II","Francis Ford Coppola",Year.of(1974))
    ));

    public static List<Movie> checkedOutMovies = new ArrayList<>(Arrays.asList(
            new Movie("Back to the Future","Robert Zemeckis",Year.of(1985)),
            new Movie("1917","Sam Mendes",Year.of(2019))
    ));


    public MovieRepository(List<Movie> availableMovies) {
    }

    public static List<Movie> getAvailableMovies() {
        return availableMovies;
    }

    public List<Movie> getCheckedOutMovies() {
        return checkedOutMovies;
    }
    public void addMovie(Movie movie) {
        availableMovies.add(movie);
    }

    public static boolean checkOutMovie(String title){
        Movie MovieWouldLikeToCheckOut = availableMovies.stream().filter(movie -> movie.getTitle().equals(title))
                .findFirst().orElse(null);
        if(MovieWouldLikeToCheckOut != null){
            availableMovies.remove(MovieWouldLikeToCheckOut);
            checkedOutMovies.add(MovieWouldLikeToCheckOut);
            return true;
        }else{
            return false;
        }
    }

    public static boolean returnMovie(String title) {
        Movie MovieWouldLikeToReturn = checkedOutMovies.stream().filter(movie -> movie.getTitle().equals(title))
                .findFirst().orElse(null);
        if(MovieWouldLikeToReturn != null){
            availableMovies.add(MovieWouldLikeToReturn);
            checkedOutMovies.remove(MovieWouldLikeToReturn);
            return true;
        }else{
            return false;
        }

    }
}
