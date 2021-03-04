package pageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SearchResults extends CorePage {
    MainPage mainPage = new MainPage();
    private final String
            SEARCH_RESULTS_POPUP = "div[class='search-popup v-modal-search']",
            SEARCH_ITEM = ".search-popup-events__item",
            NUMBER_OF_SEARCH_RESULTS = "span[class*='text--accent']",
            CLOSE_BUTTON = ".search-popup__close",
            LIVE_CHECKBOX = "div[class='search-popup v-modal-search'] #checkbox_1",
            LIVE_LABEL = ".search-popup-event__live",
            SPORTS_CHECKBOX = "div[class='search-popup v-modal-search'] #checkbox_2",
            SEARCH_RESULTS_INPUT = ".search-popup__input",
            SEARCH_BUTTON = ".search-popup__button",
            CLEAR_BUTTON = ".search-popup__clear",
            MICROPHONE_BUTTON = "div[class*='microphone']",
            MICROPHONE_POPUP = "div.swal2-header",
            LEAGUES_TAB = "//button[contains(text(), 'Leagues')]",
            MATCHES_TAB = "//button[contains(text(), 'Matches')]",
            SEARCH_RESULT_LEAGUE = ".search-popup-event__league",
            SEARCH_RESULT_TEAMS = ".search-popup-event__teams",
            SEARCH_RESULT_HEADER = ".search-popup-event__header",
            SEARCH_RESULT_NEW_TAB_LINK = "//a[contains(text(), '{SEARCH_VALUE}')]",
            SEARCH_RESULT_NEW_TAB = "div[class='prevPageButCon c-events-time__wrap sports-block open']",
            BET_ITEM = ".search-popup-coefs__item",
            BET_COUPON_COUNTRY = "//div[@class='coupon__bets']//*[contains(text(), '{COUNTRY}')]",
            NO_RESULTS_MESSAGE = ".search-popup__nothing-find";

    /*Клики по элементам*/
    public void clickBet(){
        $(By.cssSelector(BET_ITEM)).click();
    }
    public void closeSearchResults() {
        $(By.cssSelector(CLOSE_BUTTON)).click();
    }

    public void clickLiveCheckbox() {
        $(By.cssSelector(LIVE_CHECKBOX)).click();
    }

    public void clickSportsCheckbox() {
        $(By.cssSelector(SPORTS_CHECKBOX)).click();
        sleep(500);
    }

    public void clickClearButton() {
        $(By.cssSelector(CLEAR_BUTTON)).click();
    }

    public void clickMicrophoneButton() {
        $(By.cssSelector(MICROPHONE_BUTTON)).click();
    }

    public void clickLeaguesTab() {
        $(By.xpath(LEAGUES_TAB)).click();
    }

    public void clickMatchesTab() {
        $(By.xpath(MATCHES_TAB)).click();
    }

    public void clickFirstSearchItem() {
        $(By.cssSelector(SEARCH_ITEM)).click();
        switchTo().window(1);
        $(By.cssSelector(SEARCH_RESULT_NEW_TAB)).waitUntil(Condition.visible, 5);
    }
    /*Клики по элементам*/

    /*Получение данных*/
    public int getNumberOfSearchResults() {
        return Integer.parseInt(getTextOfElement(By.cssSelector(NUMBER_OF_SEARCH_RESULTS)));
    }

    public int getNumberOfSearchResultsFromList() {
        return $$(By.cssSelector(SEARCH_ITEM)).size();
    }

    public int getNumberOfLiveLabels() {
        return $$(By.cssSelector(LIVE_LABEL)).size();
    }

    public String getSearchInputValue() {
        return getValueOfElement(By.cssSelector(SEARCH_RESULTS_INPUT));
    }

    public List<String> getDescriptionOfAllSearchResults(){
        $(By.cssSelector(SEARCH_RESULT_LEAGUE)).getText();
        List<String> descriptions = new ArrayList<>();
        List<SelenideElement> leagueList = $$(By.cssSelector(SEARCH_RESULT_LEAGUE));
        List<SelenideElement> teamsList = $$(By.cssSelector(SEARCH_RESULT_TEAMS));
        for (int i = 0; i < leagueList.size(); i++) {
            descriptions.add(leagueList.get(i).getText()+" "+teamsList.get(i).getText());
            leagueList.get(i).scrollIntoView(true);
        }
        return descriptions;
    }
    /*Получение данных*/

    /*Методы*/
    public void makeSearch() {
        String default_search_value = mainPage.getDefaultSearchValue();
        makeSearch(default_search_value);
    }

    public void makeSearch(String search_value) {
        $(By.cssSelector(SEARCH_RESULTS_INPUT)).setValue(search_value);
        $(By.cssSelector(SEARCH_BUTTON)).click();
        sleep(500);
    }

    public void makeEmptySearch() {
        clickClearButton();
        $(By.cssSelector(SEARCH_BUTTON)).click();
        sleep(500);
    }
    /*Методы*/


    /*Проверки*/
    public void assertNoResutsMessageIsPresent(){
        $(By.cssSelector(NO_RESULTS_MESSAGE)).should(Condition.visible);
    }

    public void assertSearchResultsPopupIsPresent() {
        $(By.cssSelector(SEARCH_RESULTS_POPUP)).should(Condition.visible);
    }

    public void assertSearchResultsPopupIsNotPresent() {
        $(By.cssSelector(SEARCH_RESULTS_POPUP)).shouldNotBe(Condition.visible);
    }

    public void assertSearchResultsAreNotEmpty() {
        $(By.cssSelector(SEARCH_RESULT_HEADER));
        int number_of_search_results = getNumberOfSearchResults();
        Assert.assertTrue(number_of_search_results > 0, "No search results found!");
    }

    public void assertSearchResultsAreEmpty() {
        int number_of_search_results = getNumberOfSearchResults();
        Assert.assertTrue(number_of_search_results == 0, "Search results are not empty, " + number_of_search_results + " results found!");
    }

    public void assertMicrophonePopupIsPresent() {
        $(By.cssSelector(MICROPHONE_POPUP)).waitUntil(Condition.visible,4);
    }

    public void assertPageContainsKeyWord(String key_word){
        String word_xpath = SEARCH_RESULT_NEW_TAB_LINK.replace("{SEARCH_VALUE}", key_word);
        $(By.xpath(word_xpath)).should(Condition.visible);
    }

    public void assertCouponBetContainsKeyWord(String key_word){
        String key_word_xpath = BET_COUPON_COUNTRY.replace("{COUNTRY}", key_word);
        $(By.xpath(key_word_xpath)).shouldBe(Condition.visible);
    }
    /*Проверки*/


}
