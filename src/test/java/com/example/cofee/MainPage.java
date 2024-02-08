package com.example.cofee;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;


public class MainPage {
    public SelenideElement searchCoffee = $$x("//*[@id=\"catalog\"]/div/div[3]/div[1]/a").first();
    public SelenideElement voronkaCoffee = $$x("//span[text()='Воронка']").first();
}
