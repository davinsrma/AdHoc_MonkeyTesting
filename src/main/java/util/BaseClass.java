package util;

import banner.Banner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.io.IOException;
import java.time.Duration;


public abstract class BaseClass {
    public static final int TIMEOUT = 5;
    public  static String TestBedBrowser;
    CustUtil CustUtil =new CustUtil();
    Banner banner=new Banner();
    private final String produrl = "https://www.google.com";
    public static WebDriver driver;
    public static WebDriverWait wait;
    //helps to generate the logs in the test report.


    @BeforeSuite
    public void beforeSuite() {
        System.out.println("BeforeSuite: Action Can be add here !");
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        System.out.println("BeforeTest: Action or Code can be add here\n Testcase start time stamp: " + CustUtil.getCurrentDateTimeStamp());
    }


//    @Parameters("browser")
    @BeforeClass
    public void beforeClass(@Optional("chrome") String browser) throws IOException {
        banner.bannerReader();
        System.out.println("BeforeClass: Opening browser:" + browser);
        TestBedBrowser = browser;

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("--incognito");
			options.addArguments("--start-maximized");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("safari")) {
            SafariOptions options = new SafariOptions();
            options.setCapability("safari.cleanSession", true);
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver(options);
            driver.manage().window().maximize();

        } else {
            Reporter.log("This Browser " + browser + "is not supported");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

    }



    @BeforeMethod
    public void beforeMethod() {
        System.out.println("BeforeMethod time stamp: " + CustUtil.getCurrentDateTimeStamp());
        Reporter.log("Launching URL : " + produrl);
        driver.get(produrl);
    }

    @AfterMethod
    public void tearDownMethod()  {
        System.out.println("AfterMethod: Testcase end time stamp: " + CustUtil.getCurrentDateTimeStamp());


    }


    @AfterTest
    public void tearDownTest() {
        //to write or update test information to reporter
        System.out.println("AfterTest: Codes and Action can be added here");
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        System.out.println("AfterClass: Codes and Action can be added here");
        if (driver!=null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("AfterSuite: Codes and actions can be add here");
    }
}