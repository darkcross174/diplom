package ru.netology.Test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.Data.DBHelper;
import ru.netology.Data.DataHelper;
import ru.netology.Page.TripPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestPaymentCardFieldMonth {


    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080/");
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

        @Test
        public void shouldInvalidMonthIfEmpty() {
            var incorrectCardInfo = DataHelper.getInvalidMonthIfEmpty();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldMonth();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfMore12() {
            var incorrectCardInfo = DataHelper.getInvalidNumberOfMonthIfMore12();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldInvalidExpiredDateNotification();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfOneDigit() {
            var incorrectCardInfo = DataHelper.getInvalidNumberOfMonthIfOneDigit();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldMonth();

        }

        @Test
        public void shouldInvalidNumberOfMonthIfZero() {
            var incorrectCardInfo = DataHelper.getInvalidNumberOfMonthIfZero();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldDeclined();
            paymentPage.shouldValueFieldMonth();
        }

    //Проверяем неверные подписи строчек после правильного заполнения
    @Test
    public void shouldInvalidCardDataIfEmptyAllFieldsAndAfterFullInformationCard() {
        var incorrectCardInfo = DataHelper.getInvalidCardDataIfEmptyAllFields();
        var paymentPage = new TripPage().selectBuyByDebitCard();
        paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
        paymentPage.shouldEmptyFieldNotification();
        paymentPage.shouldImproperFormatNotification();
        paymentPage.shouldValueFieldCodCVC();
        paymentPage.shouldValueFieldYear();
        paymentPage.shouldValueFieldMonth();
        paymentPage.shouldValueFieldNumberCard();
        paymentPage.shouldValueFieldHolder();
        var invalidCardInfo = DataHelper.getInvalidCardNumberIfOutOfDatabase();
        paymentPage.fillCardInformationForSelectedWay(invalidCardInfo);
        paymentPage.shouldDeclined();
        paymentPage.shouldValueFieldCodCVC();
        paymentPage.shouldValueFieldHolder();
        paymentPage.shouldValueFieldNumberCard();
        final SelenideElement declinedNotification = $(".notification_status_error");
        declinedNotification.click();
        paymentPage.shouldApproved();
    }
}
