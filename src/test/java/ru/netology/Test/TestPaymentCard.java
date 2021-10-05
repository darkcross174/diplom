package ru.netology.Test;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;

import org.junit.jupiter.api.*;
import ru.netology.Data.DBHelper.DBHelper;
import ru.netology.Data.DataHelper;
import ru.netology.Page.TripPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestPaymentCard {


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

    @Nested
    class shouldResponseBasaData {
        @Test
        void shouldSuccessWithValidDebitCard() {
            var validCardInformation = DataHelper.getValidCardInformation();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(validCardInformation);
            paymentPage.shouldApproved();
            assertEquals("APPROVED", new DBHelper().getPaymentStatus());
            assertEquals(4500000, new DBHelper().getPaymentAmount());
            assertNull(new DBHelper().getCreditId());

        }

        @Test
        void shouldSuccesWithInvalidDebitCard() {
            var invalidCardInformation = DataHelper.getInvalidCardInformation();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(invalidCardInformation);
            paymentPage.shouldApproved();
            assertEquals("DECLINED", new DBHelper().getPaymentStatus());
            assertNull(new DBHelper().getCreditId());

        }
    }

    @Nested
    class shouldInvalidCardNumber {
        @Test
        void shouldGetNotificationEmptyFields() {
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
        }

        @Test
        public void shouldFailurePaymentIfEmptyNumberCard() {
            var incorrectCardInfo = DataHelper.getInvalidCardNumberIfEmpty();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardMiniNumber() {
            var incorrectCardInfo = DataHelper.getInvalidCardNumberInccorectNumber();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldNumberCard();
        }

        @Test
        public void shouldIncorrectFieldNumberCardOtherNumber() {
            var incorrectCardInfo = DataHelper.getInvalidCardNumberIfOutOfDatabase();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldDeclined();
        }
    }

    @Nested
    class shouldInvalidCardFieldMonth {
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
    }

    @Nested
    class shouldInvalidCardFieldYear {
        @Test
        public void shouldInvalidYearIfZero() {
            var incorrectCardInfo = DataHelper.getInvalidYearIfZero();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldExpiredDatePassNotification();
        }

        @Test
        public void shouldInvalidYearIfInTheFarFuture() {
            var incorrectCardInfo = DataHelper.getInvalidYearIfInTheFarFuture();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldInvalidExpiredDateNotification();

        }

        @Test
        public void shouldInvalidNumberOfYearIfOneDigit() {
            var incorrectCardInfo = DataHelper.getInvalidNumberOfYearIfOneDigit();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldYear();

        }

        @Test
        public void shouldInvalidYearIfEmpty() {
            var incorrectCardInfo = DataHelper.getInvalidYearIfEmpty();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldYear();
        }

        @Test
        public void shouldInvalidYearIfBeforeCurrentYear() {
            var incorrectCardInfo = DataHelper.getInvalidYearIfBeforeCurrentYear();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldExpiredDatePassNotification();

        }
    }

    @Nested
    class shouldInvalidCardFieldOwner {
        @Test
        public void shouldInvalidCardOwnerNameIfEmpty() {
            var incorrectCardInfo = DataHelper.getInvalidCardOwnerNameIfEmpty();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
            var incorrectCardInfo = DataHelper.getInvalidCardOwnerNameIfNumericAndSpecialCharacters();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }

        @Test
        public void shouldInvalidCardOwnerNameIfRussianLetters() {
            var incorrectCardInfo = DataHelper.getInvalidCardOwnerNameIfRussianLetters();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldHolder();
        }
    }

    @Nested
    class shouldInvalidCardFieldCodeCVC {
        @Test
        public void shouldInvalidCvcIfEmpty() {
            var incorrectCardInfo = DataHelper.getInvalidCvcIfEmpty();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvcIfOneDigit() {
            var incorrectCardInfo = DataHelper.getInvalidCvcIfOneDigit();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvcIfTwoDigits() {
            var incorrectCardInfo = DataHelper.getInvalidCvcIfTwoDigits();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldValueFieldCodCVC();
        }

        @Test
        public void shouldInvalidCvvIfThreeZero() {
            var incorrectCardInfo = DataHelper.getInvalidCvvIfThreeZero();
            var paymentPage = new TripPage().selectBuyByDebitCard();
            paymentPage.fillCardInformationForSelectedWay(incorrectCardInfo);
            paymentPage.shouldDeclined();
            paymentPage.shouldValueFieldCodCVC();
        }
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
