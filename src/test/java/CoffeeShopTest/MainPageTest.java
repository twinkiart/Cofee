package CoffeeShopTest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
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
    @Test
    @DisplayName("Проверка активности окна выбора города")
    public void windowVisable(){
        $(By.cssSelector("div[class=\"Nav__Item--Inline\"]")).click();
        $(By.cssSelector("div[class=\"fancybox-skin\"]")).shouldBe(visible);
    }
    @Test
    @DisplayName("Перенос товара в карзину + иконка кол-во товаров в корзине")
    public void buyStuff(){
        $(By.cssSelector("i[class=\"Nav__Icon--Catalog icon-menu\"]")).click();
        $(By.cssSelector("img[alt=\"Иконка Кофе\"]")).shouldBe(interactable).click();
        String shopURL = "https://eastbrew.com/category/coffee/";
        assertEquals(shopURL, WebDriverRunner.url(),"Переход не произашел");
        $(By.cssSelector("a[title=\"Кофе Бразилия Фелпс\"]")).scrollIntoView("{block: 'center'}").shouldBe(visible);
        $(By.cssSelector("form[data-product-id=\"1844\"]")).click();
        $(By.cssSelector("div[class=\"add2cart \"]")).shouldBe(visible);
        $(By.cssSelector("span[class=\"Product__Text--Primary\"]")).click();
        $(By.cssSelector("div[id=\"popupProductAdded\"]")).shouldBe(visible);
        $(By.cssSelector("a[class=\"Product__Added--Continue\"]")).click();
        $(By.cssSelector("a[title=\"Корзина\"]")).shouldBe(visible);
        $(By.cssSelector("span[class=\"Header__Text--ItemsInCart js-cart-items\"]")).shouldBe(visible);
    }
    @Test
    @DisplayName("Активность кнопки поддержки")
    public void hellpButton(){
        $(By.cssSelector("div[id=\"connect-widget\"]")).shouldBe(interactable).click();
        $(By.cssSelector("div[class=\"wca-modal\"]")).shouldBe(visible).shouldBe(interactable);
        $(By.cssSelector("a[class=\"wca-link wca-tgm\"]")).shouldBe(visible).click();
        String telegrammURL = "https://t.me/eastbrew";
        webdriver().shouldHave(url(telegrammURL), Duration.ofSeconds(15));
        assertEquals(telegrammURL,WebDriverRunner.url(),"переход на страницу тм не произашел");
    }
    @Test
    @DisplayName("Смена языка на стр.")
    public void language(){
        $(By.cssSelector("img[alt=\"usa\"]")).click();
        String languageURL = "https://eastbrew.com/en/";
        assertEquals(languageURL,WebDriverRunner.url(),"Язык на странице не изменился");
    }
    @Test
    @DisplayName("Переход на страницу журнала")
    public void journey(){
        $$x("//a[contains(text(),'Журнал о кофе')]").first().click();
        String journeyURL ="https://eastbrew.com/journal/";
        assertEquals(journeyURL,WebDriverRunner.url(),"Журнал не открылся");
    }

}
