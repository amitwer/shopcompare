package shopcompare;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

@Slf4j
public class Reporter {
    @Step
    public static void log(String s){
       log.info(s);
    }

    /**
     * Takes screenshot if the driver is capable of taking screenshots.
     * Headless drivers will just do nothing here.
     *
     * @param driver - The webriver from which to take the screenshot.
     * @param label  - How to name the screenshot (the prefix "Screenshot" will be added
     */
    public static void addScreenShot(WebDriver driver, String label) {
        if (driver instanceof TakesScreenshot) {
            takeScreenShort((TakesScreenshot) driver, label);
        }

    }
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    @Attachment(value = "Screenshot {label}", type = "image/png")
    private  static byte[] takeScreenShort(TakesScreenshot driver, String label) {
        return driver.getScreenshotAs(OutputType.BYTES);
    }
}
