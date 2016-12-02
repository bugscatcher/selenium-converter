package ru.sberbank.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by inurrick on 01.12.2016.
 */
public class ConverterPage {

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']//button[text()='Показать']")
    private WebElement btnCalculate;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']//input[@placeholder='Сумма']")
    private WebElement txtAmount;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[1]/div[3]/div[2]")
    private WebElement ddlConverterFrom;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[1]/div[3]/div[2]//span")
    private List<WebElement> converterFromOptions;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']//input[@name='filter-datepicker-detailed']")
    private WebElement inpDate;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[1]/div[4]/div[2]")
    private WebElement ddlConverterTo;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-details-period']//button[text()='Показать']")
    private WebElement btnShowExchangeRates;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[1]/div[4]/div[2]//span")
    private List<WebElement> lstConverterToOptions;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[2]//input[@type='radio']")
    private List<WebElement> lstSource;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[3]//input[@type='radio']")
    private List<WebElement> lstDestination;

    @FindBy(how = How.XPATH, using = "//ul[@class='rates-tabs rates-right']/li[@data-view-mode='history']")
    private WebElement lnkDynamicsOfChangesInExchangeRate;

    @FindBy(how = How.XPATH, using = "//ul[@class='rates-tabs rates-right']/li[@data-view-mode='table']")
    private WebElement lnkExtendedRatesTable;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-links']//span[text()='Таблица изменения котировок']")
    private WebElement lnkTableOfQuotes;

    @FindBy(how = How.XPATH, using = "//div[@class='modal-dialog']//table/tbody/tr")
    private List<WebElement> tableOfQuotes;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-table-view-close']")
    private WebElement lnkClose;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[6]//label[2]")
    private WebElement rdoSelectDate;

    @FindBy(how = How.XPATH, using = "//div[@class='rates-aside-filter rates-container']/div[6]/div[@class='filter-datepicker input']/input")
    private WebElement inpSelectDate;

    public void clickBTNCalculate() {
        btnCalculate.click();
    }

    public void typeTXTAmount(String amount) {
//        txtAmount.clear();
        txtAmount.sendKeys(Keys.CONTROL + "a");
        txtAmount.sendKeys(Keys.DELETE);
        txtAmount.sendKeys(amount);
    }

/*    public void selectDDLConverterFrom(String currency) {
        //    class= "hidden", doesn't work
        new Select(ddlConverterFrom).selectByVisibleText(currency);
    }

    public void selectDDLConverterTo(String currency) {
        //    class= "hidden", doesn't work
        new Select(ddlConverterTo).selectByVisibleText(currency);
    }*/

    public void selectDDLConverterFrom(String currency) {
        ddlConverterFrom.click();
        selectCurrency(converterFromOptions, currency);
    }

    public void selectDDLConverterTo(String currency) {
        ddlConverterTo.click();
        selectCurrency(lstConverterToOptions, currency);
    }

    private void selectCurrency(List<WebElement> l, String currency) {
        switch (currency) {
            case "RUR":
                l.get(0).click();
                break;
            case "CHF":
                l.get(1).click();
                break;
            case "EUR":
                l.get(2).click();
                break;
            case "GBR":
                l.get(3).click();
                break;
            case "JPY":
                l.get(4).click();
                break;
            case "USD":
                l.get(5).click();
                break;
        }
    }

    public void clickLNKDynamicsOfChangesInExchangeRate() {
        lnkDynamicsOfChangesInExchangeRate.click();
    }

    public void clickLNKExtendedRatesTable() {
        lnkExtendedRatesTable.click();
    }

    public void typeINPDate(String date) {
        inpDate.clear();
        inpDate.sendKeys(date);
    }

    public void setSource(String source) {
        for (int i = 0; i < lstSource.size(); i++) {
            WebElement element = lstSource.get(i);
            if (element.getAttribute("value").equals(source)) {
                element.findElement(By.xpath("..")).click();
            }
        }
    }

    public void setDestination(String destination) {
        for (int i = 0; i < lstDestination.size(); i++) {
            WebElement element = lstDestination.get(i);
            if (element.getAttribute("value").equals(destination)) {
                element.findElement(By.xpath("..")).click();
            }
        }
    }

    public void clickBTNShowExchangeRates() {
        btnShowExchangeRates.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickLNKTableOfQuotes() {
        lnkTableOfQuotes.click();
    }

    public String getSellValue(String date) {
        String sell = null;

        Date date1 = getDateFromString(date.substring(0, date.indexOf(" ")));
        Date time1 = getTimeFromString(date.substring((date.indexOf(" ") + 1), date.length()));


        for (int i = 0; i < tableOfQuotes.size(); i++) {
            WebElement element = tableOfQuotes.get(i);
            String values = element.getText();
            String[] value = values.split(" ");
            if (value[0].length() == 10 && value[0].contains(".2016")) {
                Date date2 = getDateFromString(value[0]);
                Date time2 = getTimeFromString(value[1]);
                if (date1.after(date2) || date1.equals(date2)) {
                    if (time1.after(time2) || time1.equals(time2)) {
                        sell = value[6];
                        break;
                    }
                }
            }
        }
        return sell;
    }

    private Date getDateFromString(String s) {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        Date d = null;
        try {
            d = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    private Date getTimeFromString(String s) {
        DateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date d = null;
        try {
            d = format.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public void closeTableOfQuotes() {
        lnkClose.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void selectDate(String date) {
//        TODO
        rdoSelectDate.click();
        inpSelectDate.click();

    }
}
