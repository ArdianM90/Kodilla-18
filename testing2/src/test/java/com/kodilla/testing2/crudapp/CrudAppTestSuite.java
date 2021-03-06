package com.kodilla.testing2.crudapp;

import com.kodilla.testing2.config.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class CrudAppTestSuite {
    private static final String BASE_URL = "https://ardianm90.github.io/";
    private WebDriver driver;
    private Random rnd;

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
        rnd = new Random();
    }

    @After
    public void cleanUpAfterTest() {
        driver.close();
    }

    private String createCrudAppTestTask() throws InterruptedException {
        final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
        final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]/fieldset[2]/textarea";
        final String XPATH_TASK_BUTTON = "//form[contains(@action, \"createTask\")]/fieldset[3]/button";
        String taskName = "Task number "+rnd.nextInt(100000);
        String taskContent = taskName+" task content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement addButton = driver.findElement(By.xpath(XPATH_TASK_BUTTON));
        addButton.click();
        Thread.sleep(2000);

        return taskName;
    }

    private void sendTestTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        while (!driver.findElement(By.xpath("//select[1]")).isDisplayed());

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(5000);
    }

    private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
        final String TRELLO_EMAIL = "amienkowski%40gmail.com";
        final String TRELLO_URL = "https://id.atlassian.com/login?email="+TRELLO_EMAIL;
        boolean result = false;
        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driverTrello.get(TRELLO_URL);

        WebElement loginButton = driverTrello.findElement(By.id("login-submit"));
        loginButton.click();

        while (!driverTrello.findElement(By.id("password")).isDisplayed());

        driverTrello.findElement(By.id("password")).sendKeys("12345678");
        WebElement passwordButton = driverTrello.findElement(By.id("login-submit"));
        passwordButton.submit();

        final String TRELLO_BUTTON_XPATH = "//div[contains(@data-testid, \"home-page-content\")]//a/button[1]";
        Thread.sleep(10000);
        while (!driverTrello.findElement(By.xpath(TRELLO_BUTTON_XPATH)).isDisplayed());
        Thread.sleep(2000);
        WebElement trelloButton = driverTrello.findElement(By.xpath(TRELLO_BUTTON_XPATH));
        trelloButton.click();

        Thread.sleep(4000);

        driverTrello.findElements(By.xpath("//a[@class=\"board-tile\"]")).stream()
                .filter(aHref -> aHref.findElements(By.xpath(".//div[@title=\"Kodilla Application\"]")).size() > 0)
                .forEach(WebElement::click);

        Thread.sleep(4000);

        result = driverTrello.findElements(By.xpath("//span")).stream()
                .anyMatch(theSpan -> theSpan.getText().equals(taskName));

        driverTrello.close();

        return result;
    }

    private void deleteTaskFromCrudApp(String taskName) throws InterruptedException {
        driver.switchTo().alert().accept();

        driver.navigate().refresh();
        Thread.sleep(5000);

        int count = (int) driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream().count();
        System.out.println("PRZED: "+count);

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    System.out.println("Znalazłem button");
                    WebElement deleteButton = theForm.findElement(By.xpath("//button[text()=\"Delete\"]"));
                    deleteButton.click();
                });

        driver.navigate().refresh();
        Thread.sleep(2000);
        count = (int) driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream().count();
        System.out.println("PO: "+count);
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTestTaskToTrello(taskName);
        boolean result = checkTaskExistsInTrello(taskName);
        deleteTaskFromCrudApp(taskName);
        assertTrue(result);
    }
}
