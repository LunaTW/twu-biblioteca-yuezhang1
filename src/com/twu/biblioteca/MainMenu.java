package com.twu.biblioteca;

import java.util.List;
import java.util.stream.IntStream;

public class MainMenu {
    private List<String> options;

    public MainMenu(List<String> options){
        this.options = options;
    }

    public void PrintAllMenuList(){
        IntStream.range(0, options.size()).forEach(i -> System.out.println(i + 1 + "- " + options.get(i)));//^^^
    }
}



