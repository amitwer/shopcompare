package shopcompare;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

public class Reporter {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Reporter.class);

    @Step
    public static void log(String s) {
        log.info(s);
    }

    /**
     * Takes screenshot if the driver is capable of taking screenshots.
     * Headless drivers will just do nothing here.
     *
     * @param driver - The webdriver from which to take the screenshot.
     * @param label  - How to name the screenshot (the prefix "Screenshot" will be added
     */
    public static void addScreenShot(WebDriver driver, String label) {
        if (driver instanceof TakesScreenshot) {
            takeScreenShort((TakesScreenshot) driver, label);
        }

    }

    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "Screenshot {label}", type = "image/png")
    private static byte[] takeScreenShort(TakesScreenshot driver, String label) {
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}
