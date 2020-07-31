package com.twu.biblioteca;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
/*      （1.4）点击查看主菜单：是否有操作选项（此时只有选项：书籍清单）
        （1.5）点击无效的选项时，是否报错
        【测试】【给出主菜单选项1.书籍 2，电影等等附加功能】
        1. 没有option
        2. 一个option
        3. 输入88，则报错 */

public class MainMenuTest {
    private List<String> options;
    private MainMenu mainMenu;
    private ByteArrayOutputStream MainMenuOutput;
    private String InvalidOption = "Please select a valid option";
    private String option1="List of books";

    @Before
    public void setUp() throws Exception{
        MainMenuOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(MainMenuOutput));
    }

    @Test
    public void MainMenuWasPrintedWhenZeroOption(){
        options = new ArrayList<>(Arrays.asList());
        mainMenu = new MainMenu(options);
        mainMenu.PrintAllMenuList();
        assertEquals("", MainMenuOutput.toString());
    }

    @Test
    public void MainMenuWasPrintedWhenOneOption(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options);
        mainMenu.PrintAllMenuList();
        assertEquals("1- " + option1 + "\n", MainMenuOutput.toString());
    }

    @Test
    //此时只有一个option
    public void GetNotifiedWhenChoseInvalidOption(){
        options = new ArrayList<>(Arrays.asList(option1));
        mainMenu = new MainMenu(options);
        System.setIn(new ByteArrayInputStream("3".getBytes()));
        //mainMenu.PrintAllMenuList();
        mainMenu.UserSelectOptions();
        assertEquals(InvalidOption + "\n", MainMenuOutput.toString());
    }
}


