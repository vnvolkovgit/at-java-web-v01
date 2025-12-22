package work.part05;

import org.junit.jupiter.api.Test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Alert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class DZ_modul_05_AT2 {

    @BeforeEach
    void setUP(){ // перед каждым тестом заполняю поля, чтобы не дублировать код, а дальше перебиваю значени на нужны для теста
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        $x("//input[@name='age']").setValue("20");
        $x("//input[@name='date']").setValue(getDateSession(1));
        $x("//input[@name='session' and @value='5']").click(); //можно выбрать двумя способами, кликнуть
        $x("//input[@name='film']").selectRadio("king"); //а можно установить принудительно у конкретного радиобутона.
    }


    @Test
    void test01_pozitiv(){
        $x("//input[@type='submit']").click();
        $x("//div").shouldHave(text("рублей."));
    }

    @Test
    void test02_Empty_age(){
        $x("//input[@name='age']").setValue("");
        $x("//input[@type='submit']").click();
        $x("//div").shouldHave(text("надо указать возраст"));
    }

    @ParameterizedTest()
    @CsvFileSource(resources = "limit_age.csv", numLinesToSkip = 1) // если используется несколько параметров
    void test03_Limit_age(String strFilm, String strAge){
        $x("//input[@name='film']").selectRadio(strFilm);
        $x("//input[@name='age']").setValue(strAge);

        $x("//input[@type='submit']").click();

        if (strFilm.equals("back") && Integer.parseInt(strAge)<12)
            $x("//div").shouldHave(text("только с 12 лет"));
        else if ((strFilm.equals("crime") ||
                strFilm.equals("killers") ||
                strFilm.equals("tango"))
                && Integer.parseInt(strAge)<18)
            $x("//div").shouldHave(text("только с 18 лет"));
        else $x("//div").shouldHave(text("рублей."));

    }


    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "99", "100"})
    void test02_age(String strAge){
        $x("//input[@name='age']").setValue(strAge);
        $x("//input[@type='submit']").click();

        if(Integer.parseInt(strAge)>=0 && Integer.parseInt(strAge)<100)
            $x("//div").shouldHave(text("рублей."));
        else
            $x("//div").shouldHave(text("нажмите кнопку для расчёта"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "1", "7", "8"})
    void test02_Date(String countDay){
        $x("//input[@name='date']").setValue(getDateSession(Integer.parseInt(countDay)));
        $x("//input[@type='submit']").click();

        if(Integer.parseInt(countDay)>0 && Integer.parseInt(countDay)<8)
            $x("//div").shouldHave(text("рублей."));
        else
            $x("//div").shouldHave(text("нажмите кнопку для расчёта"));
    }


    private String getDateSession(int param){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.DAY_OF_MONTH, param);
        return dateFormat.format(currentDate.getTime());

    }

}
