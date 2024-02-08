package com.example.cofee;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverConditions.url;
import static org.junit.jupiter.api.Assertions.*;

import static com.codeborne.selenide.Selenide.*;

public class MainPageTest {
    MainPage mainPage = new MainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.pageLoadStrategy = "normal";
        AllureSelenide allureSelenide = new AllureSelenide()
                .screenshots(true)
                .savePageSource(false);
        SelenideLogger.addListener("allure", allureSelenide);
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--remote-allow-origins=*");
        open("https://eastbrew.com/");
    }

    @Test
    @DisplayName("Проверка интерактивности кнопи Воронка")
    public void search() {
        mainPage.searchCoffee.scrollIntoView("{block: 'center'}").click();
        String curURL = "https://eastbrew.com/category/coffee/";
        assertEquals(curURL, WebDriverRunner.url(),"URL после клика на searchCoffee не соответствует ожидаемому");
        mainPage.voronkaCoffee.click();
        mainPage.voronkaCoffee.shouldBe(interactable);
    }
    @Test
    @DisplayName("Вход в личный кабинет")
    public void logIm(){
        open("https://eastbrew.com/login/");
        $(By.name("login")).sendKeys("test@mail.com");
        $(By.name("password")).sendKeys("Uzd6N%BPuxE");
        $(By.cssSelector("input[type=\"submit\"]")).click();
        String loginURL = "https://eastbrew.com/my/orders/";
        webdriver().shouldHave(url(loginURL), Duration.ofSeconds(15));
        assertEquals(loginURL,WebDriverRunner.url(),"Переход в личный кабинет не прошел");

    }
    @Test
    @DisplayName("Проверка строки поиска")
    public void searchKenya(){
        $(By.cssSelector("input[name='query']")).sendKeys("Кения");
        $(By.cssSelector("button[class=\"Search__Submit -Alternative \"]")).click();
        String surchURL = "https://eastbrew.com/search/?query=%D0%9A%D0%B5%D0%BD%D0%B8%D1%8F&sort=stock&order=desc";
        assertEquals(surchURL, WebDriverRunner.url(),"Переход не произашел");
        $(By.cssSelector("h1[class=\"Title__Main\"]")).shouldBe(visible);
        $(By.cssSelector("form[data-product-id=\"158\"]")).shouldBe(interactable);
    }
    @Test
    @DisplayName("Проверка работы таймера на главной странице")
    public void timerTest(){
        $("#seconds").shouldHave(text("59"));
        Selenide.sleep(30000);
        $("#seconds").shouldHave(text("29"));
    }

}
