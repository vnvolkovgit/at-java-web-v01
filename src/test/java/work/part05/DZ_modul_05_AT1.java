package work.part05;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DZ_modul_05_AT1 {

    @Test
    void at1_test01_modal_window_find(){
        open("https://www.specialist.ru/");
        $x("//button[@id='cookieConsent__ok']").click();
        $x("//a[@class='top-level-menu' and contains(text(), 'Форматы обучения')]").click();
        $x("//a[contains(text(), 'Свободное обучени')]").click();
        $x("//a[@class='page-button banner-button' and contains(text(), 'Выбрать курс')]").click();
        $x("//select[@class='filter-input']").selectOptionByValue("ПРГ");
        $x("//button[@id='sendBtn']").click();
        $("body").shouldHave(text("Тестирование ПО"));
        sleep(5_000);
    }

}
