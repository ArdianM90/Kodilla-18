package com.kodilla.testing2.facebook;

import com.kodilla.testing2.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class FacebookTestingApp {
    public static final String XPATH_BUTTON_COOKIES = "//button[@data-cookiebanner=\"accept_button\"]";
    public static final String XPATH_BUTTON_REGISTRATION = "//a[contains(@data-testid, \"registration-form-button\")]";
    public static final String XPATH_SELECT_BIRTHDAY = "//select[@name=\"birthday_day\"]";
    public static final String XPATH_SELECT_BIRTHMONTH = "//select[@name=\"birthday_month\"]";
    public static final String XPATH_SELECT_BIRTHYEAR = "//select[@name=\"birthday_year\"]";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://www.facebook.com/");

        while (!driver.findElement(By.xpath(XPATH_BUTTON_COOKIES)).isDisplayed());
        WebElement cookiesButton = driver.findElement(By.xpath(XPATH_BUTTON_COOKIES));
        cookiesButton.click();

        while (!driver.findElement(By.xpath(XPATH_BUTTON_REGISTRATION)).isDisplayed());
        WebElement registrationButton = driver.findElement(By.xpath(XPATH_BUTTON_REGISTRATION));
        registrationButton.click();

        waitXSeconds(2);
        WebElement selectBirthDay = driver.findElement(By.xpath(XPATH_SELECT_BIRTHDAY));
        Select selectDay = new Select(selectBirthDay);
        selectDay.selectByValue("15");
        WebElement selectBirthMonth = driver.findElement(By.xpath(XPATH_SELECT_BIRTHMONTH));
        Select selectMonth = new Select(selectBirthMonth);
        selectMonth.selectByValue("5");
        WebElement selectBirthYear = driver.findElement(By.xpath(XPATH_SELECT_BIRTHYEAR));
        Select selectYear = new Select(selectBirthYear);
        selectYear.selectByValue("1990");
    }

    public static void waitXSeconds(int x) {
        try {
            Thread.sleep(x* 1000L);
        }
        catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
