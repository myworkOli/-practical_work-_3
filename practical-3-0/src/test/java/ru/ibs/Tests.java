package ru.ibs;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Tests extends BaseClass{
    /**
     * Определение пролей элементов используемых в обоих тестах
     */

    WebElement addBtn = driver.findElement(By.xpath ("//button[text()=\"Добавить\"]"));
    WebElement addTypeExotic = driver.findElement(By.xpath ("//*[@id=\"exotic\"]"));
    WebElement saveBtn = driver.findElement(By.xpath ("//button[@id=\"save\"]"));
    Select addType = new Select(driver.findElement(By.xpath("//select[@id=\"type\"]")));
    WebElement addName = driver.findElement(By.xpath ("//*[@id=\"name\"]"));



    @ParameterizedTest
    @CsvSource({"Кивано # 1 (Новая Зеландия),Овощ ,true","Lemon (☼),Фрукт, false" })
    public void TestID_23_07_30_02(String name, String type , String exotic){


        /**
         * Проверить наличие на странице столбцов :  "Наименование","Тип", "Экзотический"
         */
        WebElement tableRow = driver.findElement(By.xpath("//*[@class= 'table']/thead/tr"));
        String tableRowText = tableRow.getText();
        Assertions.assertTrue(tableRowText.contains("Наименование"),"нет столбца - Наименование");
        Assertions.assertTrue(tableRowText.contains("Тип"),"нет столбца - Тип");
        Assertions.assertTrue(tableRowText.contains("Экзотический"),"нет столбца - Экзотический");
        /**
         *  Зафиксировать информацию с количеством строк в табличной части
         */
        List<WebElement> tablRow0 = driver.findElements(By.xpath("//*[@class= 'table']/tbody/tr"));
        int n0=tablRow0.size();

        addBtn.click();
        /**
         * Заполнить Форму добавления товара даными из CsvSource
         */
        addName.sendKeys(name);
        addType.selectByVisibleText(type);
        if (exotic=="true"){
            addTypeExotic.click();
        }
        saveBtn.click();
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /**
         *  Проверить, что количество строк в таблице стало больше на 1
         */
        List<WebElement> tablRow = driver.findElements(By.xpath("//*[@class= 'table']/tbody/tr"));
        int n1=tablRow.size();
        Assertions.assertTrue((n1-n0)==1,"Количество строк в таблице не изменилось на 1");
        /**
         * Проверить , что информация в новой строке соответствует тому что мы добавляли
         */
        List<WebElement> table = driver.findElements(By.tagName("tr"));
        String end_string = table.get(n1).getText();


        Assertions.assertAll("Проверка новой записи таблицы с тем что вводили",
                ()-> end_string.contains(name),
                ()-> end_string.contains(type),
                ()-> end_string.contains(exotic));

        System.out.println("end TestID_23_07_30_02");

    }

    @Test
    public void TestID_23_07_30_01(){
        addBtn.click();
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /**
         * Проверить наличие в форме полей: "Наименование","Тип", "Экзотический"
         */
        WebElement productAddingForm = driver.findElement(By.xpath("//*[@class=\"modal-content\"]"));
        String productAddingFormText = productAddingForm.getText();
        Assertions.assertTrue(productAddingFormText.contains("Наименование"),"нет поля - Наименование");
        Assertions.assertTrue(productAddingFormText.contains("Тип"),"нет поля - Тип");
        Assertions.assertTrue(productAddingFormText.contains("Экзотический"),"нет поля - Экзотический");
        addName.click();
        
        addName.sendKeys("Кивано");
        addName.sendKeys(Keys.TAB);
        /**
         * Скрины для последующей визуальной проверки отображения введенных данных в поля "Наименования"
         */
        File scr=productAddingForm.getScreenshotAs(OutputType.FILE);

        try {
            FileHandler.copy(scr, new File("C:\\Users\\Olga\\Desktop\\IBS УЧЕБА\\Java тест\\practical-3-0\\src\\test\\resources\\4_scr_товар.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addName.sendKeys(" (Kiwano)  (Новая Зеландия) # 1");
        addName.sendKeys(Keys.TAB);

        // скрин с измененным значением в поле ввода
        File scr1=productAddingForm.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(scr1, new File("C:\\Users\\Olga\\Desktop\\IBS УЧЕБА\\Java тест\\practical-3-0\\src\\test\\resources\\6_scr_товар_изменен.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //скрин поля ввода с введеным спецсимволом
        addName.clear();
        addName.sendKeys("Кивано (Kiwano) (Новая Зеландия) # 1☼");
        addName.sendKeys(Keys.TAB);
        File scr2=productAddingForm.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(scr2, new File("C:\\Users\\Olga\\Desktop\\IBS УЧЕБА\\Java тест\\practical-3-0\\src\\test\\resources\\7_scr_товар_изменен_спецсимвол.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
         *  поле "Тип"
         */
        WebElement addTypeList= driver.findElement(By.xpath("//select[@id=\"type\"]"));
        String addTypeListText=addTypeList.getText();
        Assertions.assertAll("Выпадающий список содержит Овощи и Фрукты",
                ()->addTypeListText.equals("Фрукт"),
                ()->addTypeListText.equals("Овощ"));

        addType.selectByVisibleText("Овощ");
        File scr3=productAddingForm.getScreenshotAs(OutputType.FILE);
        try {
            FileHandler.copy(scr3, new File("C:\\Users\\Olga\\Desktop\\IBS УЧЕБА\\Java тест\\practical-3-0\\src\\test\\resources\\9_scr_тип_Овощ.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addType.selectByVisibleText("Фрукт");
        addTypeList.sendKeys(Keys.TAB);
        File scr4=productAddingForm.getScreenshotAs(OutputType.FILE);

        try {
            FileHandler.copy(scr4, new File("C:\\Users\\Olga\\Desktop\\IBS УЧЕБА\\Java тест\\practical-3-0\\src\\test\\resources\\10_scr_тип_Фрукт.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        /**
         *  поле "Экзотический"
         */

        addTypeExotic.click();
        Assertions.assertTrue(addTypeExotic.isSelected(),"чекбокс не сменил статус на вкл.");
        addTypeExotic.click();
        Assertions.assertFalse(addTypeExotic.isSelected(),"чекбокс не сменил статус на выкл.");


        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        saveBtn.click();
		
		//проверка закрытия формы 

        Assertions.assertThrows(StaleElementReferenceException.class,() ->{productAddingForm.click();},
                "Элементы формы добавления товара доступны после закрытия формы");


        System.out.println("end TestID_23_07_30_01 ");









    }


}
