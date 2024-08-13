package UIAutomator;

import PageObject.HomePage;
import PageObject.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
/*
    This is a Demo test created for an e-commerce website
*/

public class DemoNewTest extends BaseTest {

    public HomePage homePage;
    public SearchPage searchPage;
    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = initialize();
    }

    @Test
    public void testFlipkart() throws IOException {

        homePage = new HomePage(driver);
        searchPage = new SearchPage(driver);
        boolean b = homePage.goToHomePage()
                .enterTextInSearchString("Samsung Galaxy S10")
                .selectFromSearchResult( "in Mobiles");

        /*if (b) {
            b = homePage.enterTextInSearchString("Samsung Galaxy S10")
                    .selectFromSearchResult( "in Mobiles");
        }*/

        List<WebElement> name = searchPage.clickOnFirstFilter("SAMSUNG")
                .clickOnSecondFilter("Plus (FAssured)")
                .sortBy("Price -- High to Low")
                .fetchingNameOfProduct();
        List<WebElement> price = searchPage.fetchingPriceOfProduct();
        List<WebElement> link = searchPage.fetchingLinkOfProduct();

        for (int i = 0; i < name.size(); i++) {
            System.out.println(name.get(i).getText());
            System.out.println(price.get(i).getText());
            System.out.println(link.get(i).getAttribute("href"));
            if(i!=0) {
                String val = price.get(i-1).getText().replace(",","");
                int greater = Integer.parseInt(val.substring(1,val.length()));
                val = price.get(i).getText().replace(",","");
                int less = Integer.parseInt(val.substring(1,val.length()));
                Assert.assertTrue( greater >= less , "Sorting is incorrect greater price is " + greater+" while lesser is "+ less);
            }
        }
        searchPage.verifyTextPresent("Showing 1 – ");
        //driverUtils.closeCurrentWindow();
    }

    @AfterClass
    public void cleanUp() {
        closeBrowser();
    }

}
