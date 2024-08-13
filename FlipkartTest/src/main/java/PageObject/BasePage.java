package PageObject;

import Utils.DriverUtils;
import org.openqa.selenium.WebDriver;

public class BasePage {

    DriverUtils driverUtils;
    public BasePage(WebDriver driver) {
        driverUtils = new DriverUtils(driver);
    }
}
