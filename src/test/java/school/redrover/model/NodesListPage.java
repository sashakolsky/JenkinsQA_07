package school.redrover.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import school.redrover.model.base.BasePage;

import java.util.List;

public class NodesListPage extends BasePage {

    @FindBy(xpath = "//a[@href='new']")
    private WebElement newNodeButton;

    public NodesListPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getNodeList() {
        List<WebElement> nodesList = getDriver().findElements(By.xpath("//tr/td/a"));

        return nodesList.stream().map(WebElement::getText).toList();
    }

    public NodeCreatePage clickNewNodeButton() {
        newNodeButton.click();

        return new NodeCreatePage(getDriver());
    }

    public NodeDetailsPage clickNodeByName(String nodeName) {
        getDriver().findElement(By.xpath("//tr[@id='node_" + nodeName + "']/td/a")).click();

        return new NodeDetailsPage(getDriver());
    }
}
