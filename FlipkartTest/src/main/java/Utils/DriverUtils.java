package Utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class DriverUtils {

    protected WebDriver driver;
    public WebDriverWait wait;
    //Seconds value to take from property file
    private final Integer seconds = 11;
    private static final Logger log = LoggerFactory.getLogger(DriverUtils.class);

    public DriverUtils(WebDriver driver) {
        this.driver = driver;
    }

    public DriverUtils openPage(String url) {
        driver.get(url);
        maximizeWindow();
        return this;
    }

    public DriverUtils maximizeWindow() {
        driver.manage().window().maximize();
        return this;
    }

    public DriverUtils closeCurrentWindow() {
        try {
            driver.close();
        } catch (Exception e) {
            log.error("Not able to close window/tab due to error: "+e.getMessage());
        }
        return this;
    }

    /**
     * Find element without any wait
     *
     * @param by locator of the element
     * @return element
     */
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    public WebElement waitForElementToBeVisible(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElementToBeVisible(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementToClickable(WebElement element) {
            return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForElementToClickable(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    public WebElement waitForElementToBePresent(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Wait for multiple elements to be present
     *
     * @param by locator of list of elements
     * @return list of elements
     */
    public List<WebElement> waitForElementsToBePresent(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public boolean waitForNumberOfWindowsToBe(int numberOfWindows) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
    }

    public DriverUtils clickOnElement(WebElement element) {
        if(element == null) {
            log.error("Element is null. Cannot click on null element");
        }
        else if(waitForElementToClickable(element) != null) {
            element.click();
        }
        else {
            log.error("Element is not clickable even after waiting for "+seconds+" seconds");
        }
        return this;
    }

    /**
     * Find an element and click on it
     *
     * @param by Locator of an element
     */
    public DriverUtils clickOnElement(By by) {
        WebElement element = waitForElementToClickable(by);
        if(element != null) {
            element.click();
        }
        else {
            log.error("Element is not found or clickable even after waiting for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils enterText(WebElement element, String text) {
        if(element == null) {
            log.error("Element is null. Cannot enter text into null element");
        }
        else if(waitForElementToBeVisible(element) != null) {
            element.clear();
            element.sendKeys(text);
        }
        else {
            log.error("Element is not visible even after "+seconds+" seconds");
        }
        return this;
    }

    /**
     * Find element and enter text into it
     *
     * @param by   locator of an element
     * @param text text to be entered
     */
    public DriverUtils enterText(By by, String text) {
        WebElement element = waitForElementToBeVisible(by);
        if(element != null) {
            element.clear();
            element.sendKeys(text);
        }
        else {
            log.error("Element is not visible even after "+seconds+" seconds");
        }
        return this;
    }

    public String getText(WebElement element) {
        if(waitForElementToBeVisible(element) != null) {
            return element.getText();
        }
        else {
            log.error("Element is not visible after "+seconds+" seconds");
        }
        return null;
    }

    public String getText(By by) {
        WebElement element = waitForElementToBeVisible(by);
        if(element != null) {
            return element.getText();
        }
        else {
            log.error("Element is not visible after "+seconds+" seconds");
        }
        return null;
    }

    public boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    //Note: Below select methods works only for select element. It won't work on div or li elements
    public List<WebElement> getAllOptionsOfDropDown(By by) {
        WebElement selectElement = waitForElementToBeVisible(by);
        Select select = new Select(selectElement);
       // log.info(String.valueOf(select.getOptions().size()));   //added
        return select.getOptions();
    }

    public List<WebElement> getAllSelectedOptionsOfDropDown(By by) {
        WebElement selectElement = waitForElementToBeVisible(by);
        Select select = new Select(selectElement);
        System.out.println(select.getAllSelectedOptions().size());   //added
        return select.getAllSelectedOptions();
    }

    public WebElement getFirstSelectedOptionOfDropDown(By by) {
        WebElement selectElement = waitForElementToBeVisible(by);
        Select select = new Select(selectElement);
        System.out.println(select.getFirstSelectedOption().getText());   //added
        return select.getFirstSelectedOption();
    }

    public DriverUtils selectOptionByVisibleText(By by, String text) {
        WebElement selectElement = waitForElementToBeVisible(by);
        if (selectElement != null) {
            Select select = new Select(selectElement);
            select.selectByVisibleText(text);
        } else {
            log.error("Select element is not visible after "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils selectOptionByValue(By by, String value) {
        WebElement selectElement = waitForElementToBeVisible(by);
        if(selectElement != null) {
            Select select = new Select(selectElement);
            select.selectByValue(value);
        } else {
            log.error("Select element is not visible after "+seconds+" seconds");
        }
        return this;
    }

    public Select getSelectElement(By by) {
        WebElement selectElement = waitForElementToBeVisible(by);
        if(selectElement != null) {
            return new Select(selectElement);
        } else {
            log.error("Select element is not visible after "+seconds+" seconds");
        }
        return null;
    }
    public DriverUtils selectOptionByIndex(By by, int index) {
        WebElement selectElement = waitForElementToBeVisible(by);
        if (selectElement != null) {
            Select select = new Select(selectElement);
            select.selectByIndex(index);
        } else {
            log.error("Select element is not visible after "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils deselectByValue(By by, String value) {
        WebElement selectElement = waitForElementToBeVisible(by);
        if (selectElement != null) {
            Select select = new Select(selectElement);
            select.deselectByValue(value);
        } else {
            log.error("Select element is not visible after "+seconds+" seconds");
        }
        return this;
    }
//file upload or download

    public DriverUtils fileUpload(By by, String filePath) {
        WebElement uploadElement = waitForElementToBeVisible(by);
        uploadElement.sendKeys(filePath);
        return this;
    }

    public ExpectedCondition<Boolean> isFileDownloaded(String path) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                File f = new File(path);
                return f.exists();
            }
            @Override
            public String toString() {
                return String.format("file to be present within the time specified");
            }
        };
    }
    public Boolean deleteFile(String path) {
        File f = new File(path);
        return f.delete();
    }
    public DriverUtils fileDownload(By by, String path) {
        WebElement downloadElement = waitForElementToBeVisible(by);
        downloadElement.click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(seconds));
        wait.until(isFileDownloaded(path));
        return this;
    }
    //Below are navigation method wrappers
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public DriverUtils navigateForward() {
        driver.navigate().forward();
        return this;
    }

    public DriverUtils navigateBack() {
        driver.navigate().back();
        return this;
    }

    public DriverUtils refreshPage() {
        driver.navigate().refresh();
        return this;
    }

    public DriverUtils acceptAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.alertIsPresent());
        if (alert != null) {
            alert.accept();
        } else {
            log.error("Alert box is not present");
        }
        return this;
    }

    public DriverUtils dismissAlert() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.alertIsPresent());
        if (alert != null) {
            alert.dismiss();
        } else {
            log.error("Alert box is not present");
        }
        return this;
    }

    public String getAlertMessage() {
        Alert alert = new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.alertIsPresent());
        try {
            return alert.getText();
        } catch (Exception e) {
            log.error("Alert is null or not present");
        }
        return null;
    }

    // Wrappers of common actions methods. However, other combinations are also possible with actions object
    public Actions getActionsObject() {
        return new Actions(driver);
    }

    public DriverUtils clickAndHold(WebElement element) {
        if(waitForElementToClickable(element) != null) {
            getActionsObject()
                    .clickAndHold(element)
                    .perform();
        }
        else {
            log.error("Element is not clickable for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils clickAndRelease(WebElement element) {
        if(waitForElementToClickable(element) != null) {
            getActionsObject()
                    .click(element)
                    .perform();
        }
        else {
            log.error("Element is not clickable for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils rightClick(WebElement element) {
        if(waitForElementToClickable(element) != null) {
            getActionsObject()
                    .contextClick(element)
                    .perform();
        }
        else {
            log.error("Element is not clickable for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils doubleClick(WebElement element) {
        if(waitForElementToClickable(element) != null) {
            getActionsObject()
                    .doubleClick(element)
                    .perform();
        }
        else {
            log.error("Element is not clickable for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils moveToElement(WebElement element) {
        if(waitForElementToBeVisible(element) != null) {
            getActionsObject()
                    .moveToElement(element)
                    .perform();
        }
        else {
            log.error("Element is not visible for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils moveToElementAndClick(WebElement element) {
        if(waitForElementToBeVisible(element) != null) {
            getActionsObject()
                    .moveToElement(element)
                    .click(element)
                    .build()
                    .perform();
        }
        else {
            log.error("Element is not visible for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils moveToElement(WebElement element, int xOffSet, int yOffSet) {
        if(waitForElementToBeVisible(element) != null) {
            getActionsObject()
                    .moveToElement(element, xOffSet, yOffSet)
                    .perform();
        }
        else {
            log.error("Element is not visible for "+seconds+" seconds");
        }
        return this;
    }

    public DriverUtils moveByOffset(int xOffSet, int yOffSet) {
        getActionsObject()
                .moveByOffset(xOffSet, yOffSet)
                .perform();
        return this;
    }

    public DriverUtils dragAndDrop(WebElement draggable, WebElement droppable) {
        if(waitForElementToBeVisible(draggable) != null && waitForElementToBeVisible(droppable) != null) {
            getActionsObject()
                    .dragAndDrop(draggable, droppable)
                    .perform();
        }
        else {
            log.error("Either draggable or droppable web element is not visible");
        }
        return this;
    }

    //Wrappers to window and cookie management
    public DriverUtils addCookie(Cookie cookie) {
        driver.manage().addCookie(cookie);
        return this;
    }

    public Cookie getCookieNamed(String cookieName) {
        return driver.manage().getCookieNamed(cookieName);
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public DriverUtils deleteCookie(String cookieName) {
        driver.manage().deleteCookieNamed(cookieName);
        return this;
    }

    public DriverUtils deleteCookie(Cookie cookie) {
        driver.manage().deleteCookie(cookie);
        return this;
    }

    public DriverUtils deleteAllCookies() {
        driver.manage().deleteAllCookies();
        return this;
    }

    /**
     * Switch to new window or tab
     *
     * @return this
     */
    public DriverUtils switchToNewWindow() {
        String originalWindow = driver.getWindowHandle();
        if(driver.getWindowHandles().size() > 1) {
            for(String windowHandle : driver.getWindowHandles()) {
                if(!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
        }
        return this;
    }

    public DriverUtils createNewTabAndSwitch() {
        driver.switchTo().newWindow(WindowType.TAB);
        return this;
    }

    public DriverUtils createNewWindowAndSwitch() {
        driver.switchTo().newWindow(WindowType.WINDOW);
        return this;
    }

    public String getOriginalWindow() {
        return driver.getWindowHandle();
    }

    public JavascriptExecutor getJavaScriptExecutor() {
        return (JavascriptExecutor)driver;
    }

    /**
     * This method to be used only if clickOnElement doesn't work
     *
     * @return this
     */
    public DriverUtils clickWithJavaScriptExecutor(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        if(element != null) {
            js.executeScript("arguments[0].click();", element);
        }
        else {
            log.error("Element can't be null");
        }
        return this;
    }

    public DriverUtils scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        if(element != null) {
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        }
        else {
            log.error("Element can't be null");
        }
        return this;
    }

    protected String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }

}
