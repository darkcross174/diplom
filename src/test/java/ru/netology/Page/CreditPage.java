package ru.netology.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.Data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {
    private final SelenideElement heading = $$(".heading").find(exactText("Кредит по данным карты"));
    private final SelenideElement cardNumber = $(".input [placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $(".input [placeholder='08']");
    private final SelenideElement year = $(".input [placeholder='22']");
    private final SelenideElement fieldCardOwner = $$(".input__top").find(text("Владелец")).parent();
    private final SelenideElement cardOwner = fieldCardOwner.$(".input__control");
    private final SelenideElement cvc = $(".input [placeholder='999']");
    private final SelenideElement proceedButton = $(".form-field button");
    private final SelenideElement approvedNotification = $(".notification_status_ok");
    private final SelenideElement declinedNotification = $(".notification_status_error");
    private final SelenideElement improperFormat = $(byText("Неверный формат"));
    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement invalidExpiredDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expiredDatePass = $(byText("Истёк срок действия карты"));
    private final ElementsCollection resultLinks = $$(".input__top");

    public CreditPage() {
        heading.shouldBe(Condition.visible);
    }

    public void creditCardFullInformation(DataHelper.CardInfo info) {
        cardNumber.setValue(info.getNumber());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        cardOwner.setValue(info.getHolder());
        cvc.setValue(info.getCvc());
        proceedButton.click();
    }

    public void shouldBeApprovedLoanApplication() {
        approvedNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldBeRejectedLoanApplication() {
        declinedNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldValueFieldNumberCard() {
        var fieldNumberCard = resultLinks.find(text("Номер карты")).parent();
        fieldNumberCard.shouldHave(text("Неверный формат"));
    }

    public void shouldValueFieldMonth() {
        var fieldNumberCard = resultLinks.find(text("Месяц")).parent();
        fieldNumberCard.shouldHave(text("Неверный формат"));

    }

    public void shouldValueFieldYear() {
        var fieldNumberCard = resultLinks.find(text("Год")).parent();
        fieldNumberCard.shouldHave(text("Неверный формат"));
    }

    public void shouldValueFieldCodCVC() {
        var fieldNumberCard = resultLinks.find(text("CVC/CVV")).parent();
        fieldNumberCard.shouldHave(text("Неверный формат"));
    }

    public void shouldValueFieldHolder() {
        var fieldNumberCard = resultLinks.find(text("Владелец")).parent();
        fieldNumberCard.shouldHave(text("Поле обязательно для заполнения"));
    }

    public void shouldImproperFormatNotification() {
        improperFormat.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldEmptyFieldNotification() {

        emptyField.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldInvalidExpiredDateNotification() {
        invalidExpiredDate.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void shouldExpiredDatePassNotification() {
        expiredDatePass.shouldBe(Condition.visible);
    }
    public void shouldInvalidCardDataIfEmptyAllFieldsAndAfterFullInformationCard(){
        var invalidCardInformation = DataHelper.getInvalidCardDataIfEmptyAllFields();
        var paymentPage = new TripPage();
        paymentPage.selectBuyByCreditCard();
        var creditPage = new CreditPage();
        creditPage.creditCardFullInformation(invalidCardInformation);
        creditPage.shouldEmptyFieldNotification();
        creditPage.shouldImproperFormatNotification();
        creditPage.shouldValueFieldCodCVC();
        creditPage.shouldValueFieldHolder();
        creditPage.shouldValueFieldMonth();
        creditPage.shouldValueFieldYear();
        creditPage.shouldValueFieldNumberCard();
        var validCardInformation = DataHelper.getInvalidCardNumberIfOutOfDatabase();
        creditPage.creditCardFullInformation(validCardInformation);

    }

}
