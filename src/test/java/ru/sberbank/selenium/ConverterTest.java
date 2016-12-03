package ru.sberbank.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ConverterTest extends Basic {

    private final String amount;
    private final String converterFrom;
    private final String converterTo;
    private final String source;
    private final String destination;
    private final String date;

    public ConverterTest(String amount, String converterFrom, String converterTo, String source, String destination, String date) {
        super();
        this.amount = amount;
        this.converterFrom = converterFrom;
        this.converterTo = converterTo;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    @Parameterized.Parameters(name = "{0} {1} > {2} : {5}")
    public static Collection xmlData() throws IOException {
        File xml = new File("src\\test\\java\\ru\\sberbank\\selenium\\TestData.xml");
        return new XmlData(xml).getData();
    }

    @Test
    public void checkCalculate() {
        ConverterPage page = PageFactory.initElements(driver, ConverterPage.class);
        page.selectConverterFrom(converterFrom);
        page.typeAmount(amount);
        page.selectConverterFrom(converterFrom);

        float am = Float.parseFloat(amount);
        float buy = 1;
        float sell = 1;

        if (!(converterFrom.equals("RUR") || converterTo.equals("RUR"))) {
            page.setSource(source);
            page.setDestination(destination);
            page.clickShowExchangeRates();
            page.openTableOfQuotes();
            buy = page.getBuyValue(date);
            page.closeTableOfQuotes();
            page.selectConverterFrom(converterTo);
            page.clickShowExchangeRates();
            page.openTableOfQuotes();
            sell = page.getSellValue(date);
            page.closeTableOfQuotes();
            page.selectConverterFrom(converterFrom);
            page.selectConverterTo(converterTo);
        } else if (converterFrom.equals("RUR")) {
            page.selectConverterTo(converterTo);
            page.setSource(source);
            page.setDestination(destination);
            page.clickShowExchangeRates();
            page.openTableOfQuotes();
            sell = page.getSellValue(date);
            page.closeTableOfQuotes();
        } else if (converterTo.equals("RUR")) {
            page.selectConverterTo(converterTo);
            page.setSource(source);
            page.setDestination(destination);
            page.clickShowExchangeRates();
            page.openTableOfQuotes();
            buy = page.getBuyValue(date);
            page.closeTableOfQuotes();
        }

        page.selectDate(date);
        page.clickCalculate();

        String expectedResult = new DecimalFormat("#0.00").format((am * buy) / sell);
        String actualResult = page.getResultText();
        assertEquals(expectedResult, actualResult);
    }
}
