package ru.netology.Test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.Data.DBHelper.DBHelper;
import ru.netology.Data.DataHelper;
import ru.netology.Page.CreditPage;
import ru.netology.Page.TripPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestCreditRequest {

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
    class shouldResponseBaseData {
        @Test
        void shouldSuccessWithValidCreditCard() {
            var validCardInformation = DataHelper.getValidCardInformation();
            var paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            var creditPage = new CreditPage();
            creditPage.creditCardFullInformation(validCardInformation);
            creditPage.approved();
            assertEquals("APPROVED", new DBHelper().getCreditRequestStatus());
            assertNull(new DBHelper().getCreditId());

        }

        @Test
        void shouldSuccessWithInvalidCreditCard() {
            var invalidCardInformation = DataHelper.getInvalidCardInformation();
            var paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            var creditPage = new CreditPage();
            creditPage.creditCardFullInformation(invalidCardInformation);
            assertEquals("DECLINED", new DBHelper().getCreditRequestStatus());
            assertNull(new DBHelper().getCreditId());
            creditPage.declined();
        }
    }
}
