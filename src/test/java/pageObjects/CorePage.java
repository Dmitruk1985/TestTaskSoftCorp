package pageObjects;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class CorePage {
    public String getTextOfElement(By by){
        return $(by).shouldBe(Condition.visible).getText();
    }

    public String getValueOfElement(By by){
        return $(by).shouldBe(Condition.visible).getValue();
    }

    public void assertElementHasText(By by, String text){
        String text_of_element = getTextOfElement(by);
        Assert.assertTrue(text_of_element.contains(text));
    }

    public void assertAllElementsHaveText(List<String> list, String text){
        for (String element:list) {
            Assert.assertTrue(element.contains(text));
        }

    }


}
