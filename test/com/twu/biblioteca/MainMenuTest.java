package com.twu.biblioteca;

/*
        （1.4）点击查看主菜单：是否有操作选项（此时只有选项：书籍清单）
        （1.5）点击无效的选项时，是否报错
        【测试】【给出主菜单选项1.书籍 2，电影等等附加功能】
        1. 没有option
        2. 一个option
        3. 输入88，则报错
        ======> 合并
        1. 打印出所有option
        2。选择88，报错
 */

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {
    @Test
    public void MainMenuWasPrintedWhenZeroOption(){
        //List<String> options = new ArrayList<>();
        //MainMenu mainMenu = new MainMenu(options);
        MainMenu mainMenu = new MainMenu(new ArrayList<>());
        ByteArrayOutputStream MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
        mainMenu.PrintAllMenuList();
        assertEquals("", MainMenuOutput.toString());
    }

    private String option1="List of books";

    @Test
    public void MainMenuWasPrintedWhenOneOption(){
        List<String> options = new ArrayList<>(Arrays.asList(option1));
        MainMenu mainMenu = new MainMenu(options);

        ByteArrayOutputStream MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
        mainMenu.PrintAllMenuList();
        assertEquals("1- " + option1 + "\n", MainMenuOutput.toString());
    }
}


