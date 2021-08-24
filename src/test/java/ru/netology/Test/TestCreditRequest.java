package ru.netology.Test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.Data.DBHelper.DBHelper;
import ru.netology.Data.DataHelper;
import ru.netology.Page.CreditPage.CreditPage;
import ru.netology.Page.TripPage.TripPage;

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
            val validCardInformation = DataHelper.getValidCardInformation();
            val paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditPage();
            creditPage.creditCardFullInformation(validCardInformation);
            creditPage.approved();
            assertEquals("APPROVED", new DBHelper().getCreditRequestStatus());
            assertNull(new DBHelper().getCreditId());

        }

        @Test
        void shouldSuccessWithInvalidCreditCard() {
            val invalidCardInformation = DataHelper.getInvalidCardInformation();
            val paymentPage = new TripPage();
            paymentPage.selectBuyByCreditCard();
            val creditPage = new CreditPage();
            creditPage.creditCardFullInformation(invalidCardInformation);
            assertEquals("DECLINED", new DBHelper().getCreditRequestStatus());
            assertNull(new DBHelper().getCreditId());
            creditPage.declined();
        }
    }
}
