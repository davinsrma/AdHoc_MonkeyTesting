package adHoc;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import util.BaseClass;

import java.util.List;
import java.util.Random;
public class MonkeyTesting extends BaseClass {
    private static List<WebElement> elements;
    private static WebElement ele;

        @Test
        public void monkeyTest() throws InterruptedException {
        Random random = new Random();
        int countNotClicked=0;
        int countClicked=0;
        int countElementNotClicked=1;


        elements = driver.findElements(By.xpath("//a"));
        for(int j=0;j<= elements.size();j++){
            try{
            ele= elements.get(j);
            if(ele.isEnabled() && ele.isDisplayed()){
            System.out.println(ele.getText());
                }
            }catch (Exception e){
                System.out.println(ele.getText());
                System.out.println("Exception Handled : "+countElementNotClicked++);
            }
        }

        for (int i=0 ;i<= elements.size();i++) {
            elements = driver.findElements(By.xpath("//a"));
            if (!elements.isEmpty()) {
                WebElement element = elements.get(random.nextInt(elements.size()));
                try{
                    util.CustUtil.highlightElement(driver,element);
                    Thread.sleep(1000);
                    element.click();
                    countClicked++;
                    driver.navigate().back();
                }catch (Exception e){
                   countNotClicked++;
                }
            }
            Thread.sleep(random.nextInt(1000));
        }
        System.out.println("Not clickable elements are : "+countNotClicked);
        System.out.println("Clicked Element count is : "+countClicked);
        driver.quit();
    }
}

