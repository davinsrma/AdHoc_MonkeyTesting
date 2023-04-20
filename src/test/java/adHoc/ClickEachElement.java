package adHoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import util.BaseClass;
import util.CustUtil;
import java.util.List;

public class ClickEachElement extends BaseClass {


    @Test
    public void setClickableElements(){
        List<WebElement> clickableElements = driver.findElements(By.xpath("//a | //button"));
        for (int i = 0; i < clickableElements.size(); i++) {
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