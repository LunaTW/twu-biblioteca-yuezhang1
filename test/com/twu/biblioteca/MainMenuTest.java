package com.twu.biblioteca;

/*
        （1.4）点击查看主菜单：是否有操作选项（此时只有选项：书籍清单）
        （1.5）点击无效的选项时，是否报错
        【测试】【给出主菜单选项1.书籍 2，电影等等附加功能】
        1. 输入1，则进入书籍清单界面
        2. 输入88，则报错
 */

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class MainMenuTest {
    private String OneOption="1- List of books\n";
    @Test
    public void MainMenuWasPrintedWhenOneOption(){
        ByteArrayOutputStream MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
        MainMenu.MainMenu();
        assertEquals(OneOption, MainMenuOutput.toString());
    }
}


