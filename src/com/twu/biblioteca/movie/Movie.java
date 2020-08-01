package com.twu.biblioteca.movie;

import java.time.Year;
import java.util.List;

public class Movie {
    private String title;
    private String director;
    private String starring;
    private Year year;

    public Movie(String title, String director, Year year){
        this.title = title;
        this.director = director;
        this.starring = starring;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public Year getYear(){
        return year;
    }

}


