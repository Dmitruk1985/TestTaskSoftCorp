package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pageObjects.MainPage;
import pageObjects.SearchResults;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SearchTests extends CoreTest{
    MainPage mainPage = new MainPage();
    SearchResults searchResults = new SearchResults();
@Test
@Description(value = "Проверка позитивного поиска (должны быть отображены результаты)")
    public void testPositiveSearch(){
    mainPage.openMainPage();
    mainPage.makeSearch();
    searchResults.assertSearchResultsPopupIsPresent();
    searchResults.assertSearchResultsAreNotEmpty();
}

    @Test
    @Description(value = "Проверка негативного поиска (не должно быть результатов)")
    public void testNegativeSearch(){
        mainPage.openMainPage();
        mainPage.makeSearch("*??:%!@#$%:?*");
        searchResults.assertSearchResultsPopupIsPresent();
        searchResults.assertNoResutsMessageIsPresent();
        searchResults.assertSearchResultsAreEmpty();
    }

    @Test
    @Description(value = "Проверка поиска с пустым значением (не должно быть результатов)")
    public void testEmptySearch(){
        mainPage.openMainPage();
        mainPage.makeSearch("");
        searchResults.assertSearchResultsPopupIsPresent();
        searchResults.assertNoResutsMessageIsPresent();
        searchResults.assertSearchResultsAreEmpty();
    }



}
