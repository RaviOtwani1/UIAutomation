package UIAutomator;

import Utils.ConfigReader;
import Utils.DriverUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    ConfigReader configReader = new ConfigReader();
    WebDriver driver;
    DriverUtils driverUtils;
    public static final String USERNAME = "raviotwani_kV18Oo";
    public static final String AUTOMATE_KEY = "FNpF9NTiSxjyi4Vm9AHZ";
    public static final String BS_URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    public WebDriver initialize() {
        String browser = configReader.getDriver();
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-dev-shm-usage");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else {
            DesiredCapabilities caps = new DesiredCapabilities();
            try {
                driver = new RemoteWebDriver(new URL(BS_URL), caps);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        driverUtils = new DriverUtils(driver);
        return driver;
    }

    public void closeBrowser(){
        driverUtils.closeCurrentWindow();
    }
}
