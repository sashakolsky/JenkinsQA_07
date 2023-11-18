package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.runner.BaseTest;
import school.redrover.runner.ProjectUtils;
import school.redrover.runner.TestUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DashboardTest extends BaseTest {


    @Test
    public void projectRenameThroughPopupMenuTest() {
        final String PROJECT_NAME = "Some project name";
        final String URLENCODED_PROJECT_NAME = URLEncoder.encode(PROJECT_NAME, StandardCharsets.UTF_8).replace("+", "%20");
        final String NEW_PROJECT_NAME = "New project name";
        final String URLENCODED_NEW_PROJECT_NAME = URLEncoder.encode(NEW_PROJECT_NAME, StandardCharsets.UTF_8).replace("+", "%20");


        // create project
        TestUtils.createFreestyleProject(this, PROJECT_NAME);

        // show popup menu on dashboard

        String projectNameXPath = String.format("//tr[@id='job_%s']//a[contains(@href, '%s') and not(@tooltip='Schedule a Build for %s')]/span",
                PROJECT_NAME,
                URLENCODED_PROJECT_NAME,
                PROJECT_NAME);
        String popupMenuXPath = String.format("//tr[@id='job_%s']//a[contains(@href, '%s') and not(@tooltip='Schedule a Build for %s')]/button",
                PROJECT_NAME,
                URLENCODED_PROJECT_NAME,
                PROJECT_NAME);

        Actions actions = new Actions(getDriver());
        actions.moveToElement(getDriver().findElement(By.xpath(projectNameXPath)))
                .click(getDriver().findElement(By.xpath(popupMenuXPath)))
                .build()
                .perform();

        // select rename option
        getWait2().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//a[normalize-space()='Rename']")))).click();

        // enter new name
        getDriver().findElement(By.xpath("//input[@name='newName']")).clear();
        getDriver().findElement(By.xpath("//input[@name='newName']")).sendKeys(NEW_PROJECT_NAME);;

        // click rename
        getDriver().findElement(By.xpath("//button[@name='Submit']")).click();

        // verify:
        //  1. URL has new name
        Assert.assertTrue(getDriver().getCurrentUrl().contains(URLENCODED_NEW_PROJECT_NAME), getDriver().getCurrentUrl() + " url doesn't contain " + URLENCODED_NEW_PROJECT_NAME);
        //  2. Page h1 element has new name
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Project " + NEW_PROJECT_NAME);
        //  3. Breadcrumbs has new name
        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@id='breadcrumbBar']//li[last()-1]")).getText(), NEW_PROJECT_NAME);

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
