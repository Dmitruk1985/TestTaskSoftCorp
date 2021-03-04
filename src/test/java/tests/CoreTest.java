package tests;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeMethod;

public class CoreTest {
    @BeforeMethod()
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src\\test\\java\\resources\\chromedriver.exe");
        Configuration.startMaximized=true;
    }
}
