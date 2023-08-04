package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.delivery.data.DataGenerator.generateCity;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        //var validUser = DataGenerator.Registration.generateUser("ru"); // методы утилитного класса вызываются по имени класса. Обращаемся к классу, затем к методу
        int daysToAddForFirstMeeting = 4;
        String firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        int daysToAddForSecondMeeting = 7;
        String secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id = city] input").setValue(DataGenerator.generateCity("ru"));// передаем сюда локаль, то есть в методе прописано string locale. а в тесте уже пишем, какая именно
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $("[data-test-id = date] input").setValue(firstMeetingDate);
        $("[data-test-id = name] input").setValue(DataGenerator.generateName("ru"));
        $("[data-test-id = phone] input").setValue(DataGenerator.generatePhone("ru"));
        ////$("[data-test-id = city] input").setValue(validUser.getCity());
        //$("[data-test-id = name] input").setValue(validUser.getName());
        //$("[data-test-id = phone] input").setValue(validUser.getPhone());
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = success-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id = date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        $("[data-test-id = date] input").setValue(secondMeetingDate);
        $(".button").click();
        $("[data-test-id = replan-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id = replan-notification] button").click();
        $("[data-test-id = success-notification] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }




}
