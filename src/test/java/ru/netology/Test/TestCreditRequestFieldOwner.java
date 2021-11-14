package ru.netology.Test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.Data.DBHelper;
import ru.netology.Data.DataHelper;
import ru.netology.Page.CreditPage;
import ru.netology.Page.TripPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCreditRequestFieldOwner {

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
        public void shouldInvalidCardOwnerNameIfEmpty() {
            var invalidCardNumber = DataHelper.getInvalidCardOwnerNameIfEmpty();
            var paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            var creditPage = new CreditPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
            var invalidCardNumber = DataHelper.getInvalidCardOwnerNameIfNumericAndSpecialCharacters();
            var paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            var creditPage = new CreditPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfRussianLetters() {
            var invalidCardNumber = DataHelper.getInvalidCardOwnerNameIfRussianLetters();
            var paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            var creditPage = new CreditPage();
            creditPage.creditCardFullInformation(invalidCardNumber);
            creditPage.shouldValueFieldHolder();
        }


        //Проверяем неверные подписи строчек после правильного заполнения
    @Test
    public void shouldInvalidCardDataIfEmptyAllFieldsAndAfterFullInformationCard() {
        var paymentPage = new TripPage();
        paymentPage.selectBuyByCreditCard();
        var creditPage = new CreditPage();
        creditPage.shouldInvalidCardDataIfEmptyAllFieldsAndAfterFullInformationCard();
        creditPage.shouldBeRejectedLoanApplication();
        creditPage.shouldValueFieldCodCVC();
        creditPage.shouldValueFieldHolder();
        creditPage.shouldValueFieldNumberCard();
        final SelenideElement declinedNotification = $(".notification_status_error");
        declinedNotification.click();
        creditPage.shouldBeApprovedLoanApplication();
    }
}

