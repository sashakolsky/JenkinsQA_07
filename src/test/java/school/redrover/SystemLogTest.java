package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;

import java.util.*;

public class SystemLogTest extends BaseTest {

    private final static String SYSLOG_NAME = "NewSystemLog";
    private void openSyslogPage() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        getDriver().findElement(By.xpath("//a[@href='/manage']")).click();
        js.executeScript("arguments[0].scrollIntoView();",
                getDriver().findElement(By.xpath("//a[@href='log']")));
        getDriver().findElement(By.xpath("//a[@href='log']")).click();

    }

    private boolean isDisplayed(By by) {
        try {
            return getDriver().findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testCreateCustomLogRecorder() {
        openSyslogPage();

        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        new Actions(getDriver()).moveToElement(getDriver()
                .findElement(By.cssSelector("input[checkurl='checkNewName']")))
                .click()
                .perform();
        getDriver().findElement(By.cssSelector("input[checkurl='checkNewName']")).sendKeys(SYSLOG_NAME);
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        getDriver().findElement(By.xpath("//*[@id='breadcrumbs']/li[5]/a")).click();

        Assert.assertEquals(getDriver()
                .findElement(By.xpath("//*[@id='logRecorders']/tbody/tr[2]/td[1]/a"))
                .getText(), SYSLOG_NAME);
    }

    @Test(dependsOnMethods ="testCreateCustomLogRecorder")
    public void testDeleteCustomLogRecorder() {
        openSyslogPage();

        getDriver().findElement(By.xpath("//a[@href='" + SYSLOG_NAME + "/'][1]")).click();
        getDriver().findElement(By.xpath("//button[@tooltip='More actions']")).click();
        Actions actions = new Actions(getDriver());
        actions.pause(400)
                .moveToElement(getDriver()
                .findElement(By.xpath("//a[@data-post='true']")))
                .click()
                .perform();
        getWait5().until(ExpectedConditions.alertIsPresent()).accept();

        Assert.assertFalse(isDisplayed(By.xpath("//a[@href='" + SYSLOG_NAME + "/'][1]")));
    }
}
