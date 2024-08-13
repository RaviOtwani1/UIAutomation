package PageObject;

import Utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class SearchPage extends BasePage{

    private static String filterSelectText = "//span[text()='Filters']//ancestor::section//div[text()='%s']";
    private static String sortByText = "//div[text()='%s']";
    private static By firstFilter = By.xpath("//div[text()='SAMSUNG']/parent::label");
    private static By secondFilter = By.cssSelector("label:has(img.SwtzWS)");
    private static By waitForSortByElementSelect =  By.cssSelector("div._0H7xSG");
    private static By fetchNameOfSearchResult =  By.cssSelector("div.KzDlHZ");
    private static By fetchPriceOfSearchResult =  By.cssSelector("div.Nx9bqj");
    private static By fetchLinkOfSearchResult =  By.cssSelector("a.CGtC98");

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public SearchPage verifyFilterSelection(String text){
        String filterSelection = String.format(filterSelectText, text);
        driverUtils.waitForElementToBeVisible(By.xpath(filterSelection));
        return this;
    }

    public SearchPage clickOnFirstFilter(String filter) {
        driverUtils.clickOnElement(firstFilter);
        verifyFilterSelection(filter);
        return this;
    }

    public SearchPage clickOnSecondFilter(String filter) {
        driverUtils.clickOnElement(secondFilter);
        verifyFilterSelection(filter);
        return this;
    }

    public SearchPage sortBy(String sort) {
        String sortingValue = String.format(sortByText, sort);
        driverUtils.clickOnElement(By.xpath(sortingValue));
        driverUtils.waitForElementToBeVisible(waitForSortByElementSelect);
        driverUtils.clickOnElement(By.xpath(sortingValue));
        return this;
    }

    public List<WebElement> fetchingNameOfProduct() {
        return driverUtils.findElements(fetchNameOfSearchResult);
    }

    public List<WebElement> fetchingPriceOfProduct() {
        return driverUtils.findElements(fetchPriceOfSearchResult);
    }

    public List<WebElement> fetchingLinkOfProduct() {
        return driverUtils.findElements(fetchLinkOfSearchResult);
    }

    public void printResult(List<WebElement> we) {
        for (int i = 0 ; i< we.size(); i++) {
            //driverUtils.scrollToElement(we.get(i));
            System.out.println("Name " + we.get(i).getText());
        }
    }

    public void printResultHref(List<WebElement> we) {
        for (int i = 0 ; i< we.size(); i++) {
            //driverUtils.scrollToElement(we.get(i));
            System.out.println("Name " + we.get(i).getAttribute("href"));
        }
    }

    public void verifyTextPresent(String text) {
        int size = driverUtils.findElements(By.xpath("//span[contains(text(),'"+text+"')]")).size();
        Assert.assertEquals(size,1 , "Actual size should be 1");
    }
}
