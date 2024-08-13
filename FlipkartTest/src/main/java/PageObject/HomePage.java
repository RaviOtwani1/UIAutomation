package PageObject;

import Utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

    private static final By searchTextBox = By.cssSelector("[name=q]");
    private static final By noResultText = By.xpath("//div[text()='Sorry, no results found!']");
    String selectSearchText = "//div[text()='%s']";

    ConfigReader configReader = new ConfigReader();
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage goToHomePage(){
        driverUtils.openPage(configReader.getHomeUrl());
        driverUtils.maximizeWindow();
        return this;
    }

    public HomePage enterTextInSearchString(String searchText){
        driverUtils.clickOnElement(searchTextBox);
        driverUtils.enterText(searchTextBox, searchText);
        return this;
    }

    public boolean selectFromSearchResult(String secondarySearchText){
        String select = String.format(selectSearchText, secondarySearchText);
        driverUtils.clickOnElement(By.xpath(select));
        if(driverUtils.findElements(noResultText).size()>=1) {
            return false;
        } else {
            return true;
        }
    }

}
