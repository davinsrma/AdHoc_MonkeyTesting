package adHoc;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import util.BaseClass;
import util.CustUtil;

import java.time.Duration;
import java.util.List;

public class ClickEachElement extends BaseClass {


    @Test
    public void setClickableElements(){

        List<WebElement> clickableElements = driver.findElements(By.xpath("//a | //button"));
        // Click on each clickable element
        for (int i = 0; i < clickableElements.size(); i++) {
            // Find the element again to handle stale element reference exceptions
            clickableElements = driver.findElements(By.xpath("//a"));

            WebElement element = clickableElements.get(i);

                if(element.isDisplayed() && element.isEnabled()){
                try{
                CustUtil.highlightElement(driver, element);
                    try{
                        System.out.println("Clicked elements :"+element.getText());

                        element.click();
                            driver.navigate().back();
                         }catch (Exception e){
                    }

                }catch (Exception e){
                }
            }
        }
        driver.quit();
    }

}