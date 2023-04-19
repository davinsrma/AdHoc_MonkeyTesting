package util;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.*;

import static util.BaseClass.driver;

public class CustUtil {

    protected static JavascriptExecutor jse= (JavascriptExecutor) driver;

    public static int GetRandomInteger() {
        Random r1 = new Random();
        return r1.nextInt(1000);
    }

    public static void zoomControl(int zoomSize){
        jse = (JavascriptExecutor) driver;
        jse.executeScript("document.body.style.zoom='"+zoomSize+"%'");
    }

    public static void highlightElement(WebDriver driver,WebElement element) {
        jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    public static void clearDirectory(String Dir) throws NullPointerException {
        File file = new File(Dir);
        String[] myFiles;

        try {
            if (file.isDirectory()) {
                myFiles = file.list();
                //			for (int i = 0; i < myFiles.length; i++) {
                assert myFiles != null;
                for (String name : myFiles) {
                    File myFile = new File(file, name);
                    //noinspection ResultOfMethodCallIgnored
                    myFile.delete();
                }
            } else if (file.isFile()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public static void DeleteDirectoryRecursively(String DirPath) throws IOException {
//		String DirPath = "D:\\test";
        Path directory = Paths.get(DirPath);
        System.out.println("Deleting folder: " + directory);

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.delete(file); // this will work because it's always a File
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir); // this will work because Files in the directory are already deleted
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void getAllCookies(WebDriver driver, String sFileNameWithPath) {
//		File file = new File("d:\\a\\Cookies1.data");
        File file = new File(sFileNameWithPath);
        try {
            // Delete old file if exists
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            FileWriter fileWrite = new FileWriter(file);
            BufferedWriter Bwrite = new BufferedWriter(fileWrite);
            // loop for getting the cookie information

            // loop for getting the cookie information
            for (Cookie ck : driver.manage().getCookies()) {
                Bwrite.write((ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
                        + ck.getExpiry() + ";" + ck.isSecure()));
                Bwrite.newLine();
            }
            Bwrite.close();
            fileWrite.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void uploadFileUsingRobot(String sFilename) throws AWTException, InterruptedException {
        Thread.sleep(1000);
        StringSelection ss = new StringSelection(sFilename);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
    }


    public static void waitForElementToDisapper(WebDriver driver, By Byele) throws InterruptedException {
        int Cnt = 0;
//        By btest = By.xpath("//div[contains(text(),'Loading...')]");
        while (driver.findElements(Byele).size() > 0 && Cnt <= 60) {
            //noinspection BusyWait
            Thread.sleep(1000);
            Cnt++;
            System.out.println("waiting for bouncer to dissappear. Elapsed time in sec: " + Cnt);
        }
//		System.out.println("");
    }

    public static boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private static boolean acceptNextAlert = true;

    public static String closeAlertAndGetItsText(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public static boolean VerifyTextContainsOnPage(WebDriver driver, String textToVerify) {
        driver.getPageSource();
        return driver.getPageSource().contains(textToVerify);
    }


    public static boolean verifyElementNotDisplayed(WebDriver driver, WebElement ele) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean bstatus = verifyElementNotDisplayed(ele);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return bstatus;
    }

    public static boolean verifyElementNotDisplayed(WebElement ele) {
        try {
            return (!ele.isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public static String getCurrentDateTimeStamp() {
        Date objDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd_hh_mm_ss");
        objDate = new Date();
        // Current System Date and time is assigned to objDate
        return (sdf.format(objDate));
    }

    public static String getCurrentDate() {
        Date objDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MMM_dd");
        objDate = new Date();
        // Current System Date and time is assigned to objDate
        return (sdf.format(objDate));
    }




    public static void click(WebDriverWait wait, WebElement ele) {
        try {
            wait.until(ExpectedConditions.visibilityOf(ele));
            wait.until(ExpectedConditions.elementToBeClickable(ele));
            ele.click();
        } catch (Exception e) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(e);
        }
    }





    public static void mouseHovering(WebElement element){
        Actions actions=new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public static String getVisibleText(WebElement element){
        String text=element.getText();
        return text;
    }

    public static void enterValue(WebElement element, String value){
        if(element.isDisplayed() && element.isEnabled()){
            element.clear();
            element.sendKeys(value);
        }
    }

    public static String getPageTitle(){
        String title= driver.getTitle();
        return title;
    }

    public static void dragAndDrop(WebElement source, WebElement destination){
        Actions actions=new Actions(driver);
        actions.dragAndDrop(source,destination).perform();
    }
    public static void pressTab(){
        Actions actions=new Actions(driver);
        actions.sendKeys(Keys.TAB).perform();

    }

    public static void pressEnter(){
        Actions actions=new Actions(driver);
        actions.sendKeys(Keys.ENTER).perform();
    }
// Similar we can write code for select by index, select by value also
    public static void selectDropDown(WebElement dropdDown, String text){
        Select select=new Select(dropdDown);
        select.selectByVisibleText(text);
    }

    public static void acceptAlert(){
       driver.switchTo().alert().accept();
    }

    public static void dismissAlert(){
        driver.switchTo().alert().dismiss();
    }

    public static String getAlertMsg(){
        String text=driver.switchTo().alert().getText();
        return text;
    }

    public static void sendValueToAlert(String value){
        driver.switchTo().alert().sendKeys(value);
    }

    public static void switchFrame(WebElement frameName){
        driver.switchTo().frame(frameName);
    }
    public static void switchDefaultFrame(){
        driver.switchTo().defaultContent();
    }
}