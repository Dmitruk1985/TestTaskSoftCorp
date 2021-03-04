package tests;

import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.MainPage;
import pageObjects.SearchResults;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SearchResultsTests extends CoreTest {
    MainPage mainPage = new MainPage();
    SearchResults searchResults = new SearchResults();

    @Test
    @Description(value = "Проверяет корректное закрытие окна с результатами поиска")
    public void testCloseSearchResultsPopup() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.closeSearchResults();
        searchResults.assertSearchResultsPopupIsNotPresent();
    }

    @Test
    @Description(value = "Проверка, что количество событий, отображаемое в поле 'EVENTS FOUND' соответствует реальному количеству в списке")
    public void testNumberOfSearchResultsMatchesReality() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        sleep(1000);
        int number_of_search_results = searchResults.getNumberOfSearchResults();
        int number_of_search_results_list = searchResults.getNumberOfSearchResultsFromList();
        Assert.assertEquals(number_of_search_results, number_of_search_results_list, "Number of search results doesn't matches");
    }

    @Test
    @Description(value = "Проверка, что нажатие на кнопку 'x' очищает поле")
    public void testClearSearchResultsInput() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.clickClearButton();
        String input_value = searchResults.getSearchInputValue();
        Assert.assertEquals(input_value,"", "Search input is not clear!");
    }

    @Test
    @Description(value = "Проверка, что события с тэгом 'LIVE' не отображаются после снятия чекбокса 'Live'")
    public void testLiveResultsAreNotDisplayed() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.clickLiveCheckbox();
        int number_of_live_labels = searchResults.getNumberOfLiveLabels();
        Assert.assertEquals(number_of_live_labels, 0, "Live results are displayed");
    }

    @Test
    @Description(value = "Проверка, что события с типа 'Sports' не отображаются после снятия чекбокса 'Sports'")
    public void testSportsResultsAreNotDisplayed() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.clickSportsCheckbox();
        int number_of_live_labels = searchResults.getNumberOfLiveLabels();
        int number_of_search_results_list = searchResults.getNumberOfSearchResultsFromList();
        Assert.assertEquals(number_of_live_labels, number_of_search_results_list, "Sport results are displayed");
    }

    @Test
    @Description(value = "Проверка, что никакие события не отображаются после снятия обоих чекбоксов 'Live' и 'Sports'")
    public void testNoResultsAreDisplayed() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.clickLiveCheckbox();
        searchResults.clickSportsCheckbox();
        searchResults.assertSearchResultsAreEmpty();
    }

    @Test
    @Description(value = "Проверка, что в поле поиска по умолчанию введено значение, которое было использовано при поиске")
    public void testSearchValueMatchesValueAtSearchResults() {
        String search_value = "Belarus";
        mainPage.openMainPage();
        mainPage.makeSearch(search_value);
        String input_search_value = searchResults.getSearchInputValue();
        Assert.assertEquals(search_value, input_search_value, "Search input doesn't matches");
    }

    @Test
    @Description(value = "Проверка позитивного поиска в форме просмотра результатов (должны быть результаты)")
    public void testPositiveSearchFromSearchResults() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.makeSearch();
        searchResults.assertSearchResultsAreNotEmpty();
    }

    @Test
    @Description(value = "Проверка негативного поиска в форме просмотра результатов (не должно быть результатов)")
    public void testNegativeSearchFromSearchResults() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.makeSearch("!@#@#$%^^&*");
        searchResults.assertSearchResultsAreEmpty();
    }

    @Test
    @Description(value = "Проверка поиска с пустым полем в форме просмотра результатов (не должно быть результатов)")
    public void testEmptySearchFromSearchResults() {
        mainPage.openMainPage();
        mainPage.makeSearch();
        searchResults.makeEmptySearch();
        searchResults.assertSearchResultsAreEmpty();
    }

    @Test
    @Description(value = "Проверка, что все найденные события содержат значение, которое было использовано при поиске")
    public void testAllSearchResultsContainsSearchValue() {
        String search_value = "Belarus";
        mainPage.openMainPage();
        mainPage.makeSearch(search_value);
        List<String> descriptions = searchResults.getDescriptionOfAllSearchResults();
        searchResults.assertAllElementsHaveText(descriptions, search_value);
    }

    @Test
    @Description(value = "Проверка, что при клике на событие открывается корректная страница " +
            "(содержит значение, которое было использовано при поиске)")
    public void testNewTabWithSearchResultContainsSearchValue() {
        String search_value = "Belarus";
        mainPage.openMainPage();
        mainPage.makeSearch(search_value);
        searchResults.clickFirstSearchItem();
        searchResults.assertPageContainsKeyWord(search_value);
        closeWindow();
    }

    @Test
    @Description(value = "Проверка, что при клике на блок со ставкой в купоне отображено слово, по которому был поиск")
    public void testClickBet() {
        String search_value = "Belarus";
        mainPage.openMainPage();
        mainPage.makeSearch(search_value);
        searchResults.clickBet();
        searchResults.assertCouponBetContainsKeyWord(search_value);
    }

}
