package com.twu.biblioteca;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.*;

//（1.1）用户能否看到欢迎界面
//【测试】1. 程序init之后，应出现欢迎界面 https://stackoverflow.com/questions/33569920/how-to-test-system-out-println-by-mocking
public class WelcomeMessageTest {
    private String WelcomeMessagecheck = "Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!\n";

    //******************************************* （1.1）用户看到欢迎消息 ******************************//
    @Test
    public void ShouldHaveAWelcomeMessage(){
        ByteArrayOutputStream OutputInformation= new ByteArrayOutputStream();
        System.setOut(new PrintStream(OutputInformation));
        WelcomeMessage.WelcomeMessageInScreen();
        assertEquals(WelcomeMessagecheck , OutputInformation.toString());
    }
}


