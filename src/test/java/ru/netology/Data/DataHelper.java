package ru.netology.Data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    static Faker faker = new Faker(new Locale("en"));
    static DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
    static String monthEndOfAction = LocalDate.now().plusMonths(3).format(format);
    static DateTimeFormatter formatYear = DateTimeFormatter.ofPattern("yy");
    static String yearEndOfAction = LocalDate.now().plusYears(2).format(formatYear);
    static String nameHolder = faker.name().fullName();
    static String codeCvc = Integer.toString(faker.number().numberBetween(100, 999));

    private DataHelper() {
    }

    public static CardInfo getValidCardInformation() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAction, yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidCardInformation() {
        return new CardInfo("4444 4444 4444 4442", monthEndOfAction, yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidCardDataIfEmptyAllFields() {
        return new CardInfo("", "", "", "", "");
    }

    public static CardInfo getInvalidCardNumberIfEmpty() {
        return new CardInfo("", monthEndOfAction, yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidCardNumberInccorectNumber() {
        return new CardInfo("4444", monthEndOfAction, yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidCardNumberIfOutOfDatabase() {
        return new CardInfo("5578334444444441",monthEndOfAction, yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidMonthIfEmpty() {
        return new CardInfo("4444 4444 4444 4441", "",  yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidNumberOfMonthIfMore12() {
        return new CardInfo("4444 4444 4444 4441", "35", yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidNumberOfMonthIfOneDigit() {
        return new CardInfo("4444 4444 4444 4441", "3", yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidNumberOfMonthIfZero() {
        return new CardInfo("4444 4444 4444 4441", "00", yearEndOfAction, nameHolder, codeCvc);
    }

    public static CardInfo getInvalidYearIfZero() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAction, "00", nameHolder, codeCvc);
    }

    public static CardInfo getInvalidYearIfInTheFarFuture() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAction, "70", nameHolder, codeCvc);
    }

    public static CardInfo getInvalidNumberOfYearIfOneDigit() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, "7", nameHolder, codeCvc);
    }

    public static CardInfo getInvalidYearIfEmpty() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, "", nameHolder, codeCvc);
    }

    public static CardInfo getInvalidYearIfBeforeCurrentYear() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, "13", nameHolder, codeCvc);
    }

    public static CardInfo getInvalidCardOwnerNameIfEmpty() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, yearEndOfAction, "", codeCvc);
    }

    public static CardInfo getInvalidCardOwnerNameIfNumericAndSpecialCharacters() {
        return new CardInfo("5578334444444441", monthEndOfAction, yearEndOfAction, "47853!$", codeCvc);
    }

    public static CardInfo getInvalidCardOwnerNameIfRussianLetters() {
        return new CardInfo("5578334444444441",  monthEndOfAction, yearEndOfAction, "Иван Петров", codeCvc);
    }

    public static CardInfo getInvalidCvcIfEmpty() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, yearEndOfAction, nameHolder, "");
    }

    public static CardInfo getInvalidCvcIfOneDigit() {
        return new CardInfo("4444 4444 4444 4441",  monthEndOfAction, yearEndOfAction, nameHolder, "5");
    }

    public static CardInfo getInvalidCvcIfTwoDigits() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAction, yearEndOfAction, nameHolder, "25");
    }

    public static CardInfo getInvalidCvvIfThreeZero() {
        return new CardInfo("4444 4444 4444 4441", monthEndOfAction, yearEndOfAction, nameHolder, "000");
    }

    @Value
    public static class CardInfo {
        private String number, month, year, holder, cvc;
    }
}
