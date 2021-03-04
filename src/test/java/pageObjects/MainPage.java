package pageObjects;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class MainPage extends CorePage {
    private final static String
            MAIN_URL = "https://www.oppabet.com",
            SEARCH_INPUT = "input[placeholder='Search...']",
            DEFAULT_SEARCH_VALUE = "Russia";

    public void openMainPage() {
        open(MAIN_URL);
    }

    public void makeSearch(String search_value) {
        $(By.cssSelector(SEARCH_INPUT)).setValue(search_value).pressEnter();
    }

    public void makeSearch() {
        makeSearch(DEFAULT_SEARCH_VALUE);
    }

    public String getDefaultSearchValue(){
        return DEFAULT_SEARCH_VALUE;
    }

}
