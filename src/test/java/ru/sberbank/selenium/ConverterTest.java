package ru.sberbank.selenium;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by inurrick on 01.12.2016.
 */
@RunWith(Parameterized.class)
public class ConverterTest extends Basic {

    private String amount;
    private String converterFrom;
    private String converterTo;
    private String source;
    private String destination;
    private String date;

    public ConverterTest(String amount, String converterFrom, String converterTo, String source, String destination, String date) {
        super();
        this.amount = amount;
        this.converterFrom = converterFrom;
        this.converterTo = converterTo;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    @Parameterized.Parameters
    public static Collection xmlData() throws IOException {
        File xml = new File("src\\test\\java\\ru\\sberbank\\selenium\\TestData.xml");
        return new XmlData(xml).getData();
    }

    @Test
    public void first() {
        ConverterPage page = PageFactory.initElements(driver, ConverterPage.class);
        page.selectDDLConverterFrom(converterFrom);
        page.typeTXTAmount(amount);
        page.selectDDLConverterFrom(converterFrom);
        page.selectDDLConverterTo(converterTo);
        page.setSource(source);
        page.setDestination(destination);
        page.clickBTNShowExchangeRates();
        page.clickLNKTableOfQuotes();
        String sell = page.getSellValue(date);
        page.closeTableOfQuotes();
        page.selectDate(date);
        page.clickBTNCalculate();
    }
}
