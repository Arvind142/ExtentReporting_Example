import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Slf4j
public class TestNG_Runner {
    // extent reporting varibales
    ExtentSparkReporter extentSparkReporter;
    ExtentReports extentReports;
    ExtentTest extentTest;
    @BeforeClass
    public void beforeClass(){
        log.info("@BeforeClass method called");
        // path where you want to store test report
        extentSparkReporter = new ExtentSparkReporter("test-output/extent/report.html");
        extentReports = new ExtentReports();
        //attaching reporter
        extentReports.attachReporter(extentSparkReporter);
    }

    /***
     * Below test method will log most basic details in extent report
     */
    @Test
    public void testMethod_Basic(){
        extentTest = extentReports.createTest("testMethod_Basic");
        extentTest.log(Status.PASS,"Pass log");
    }
    /***
     * Below test method will have user details in extent report
     */
    @Test
    public void testMethod_User(){
        extentTest = extentReports.createTest("testMethod_User");
        extentTest.assignAuthor("Arvind");
        extentTest.log(Status.PASS,"Pass log");
    }

    /***
     * Below test method will have category details in extent report
     */
    @Test
    public void testMethod_Category(){
        extentTest = extentReports.createTest("testMethod_Category");
        extentTest.assignCategory("category_Case");
        extentTest.log(Status.PASS,"Pass log");
    }

    /***
     * Below test method will have device details in extent report
     */
    @Test
    public void testMethod_Device(){
        extentTest = extentReports.createTest("testMethod_Device");
        extentTest.assignDevice("Win-10");
        extentTest.log(Status.PASS,"Pass log");
    }

    /***
     * Below test method will have user as well as category details in extent report
     */
    @Test
    public void testMethod_User_Category(){
        extentTest = extentReports.createTest("testMethod_User_Category");
        extentTest.assignAuthor("Arvind");
        extentTest.assignCategory("category_Case");
        extentTest.log(Status.PASS,"Pass log");
    }

    /***
     * Below test method will have user, category & device details in extent report
     */
    @Test
    public void testMethod_User_Category_Device(){
        extentTest = extentReports.createTest("testMethod_User_Category_Device");
        extentTest.assignAuthor("Arvind");
        extentTest.assignCategory("category_Case");
        extentTest.assignDevice("Win-10");
        extentTest.log(Status.PASS,"Pass log");
    }

    /**
     *  Test method which will add screenshot to your extent test report - default way
     */
    @Test
    public void testMethod_withAttachment(){
        extentTest = extentReports.createTest("testMethod_withAttachment");
        extentTest.log(Status.PASS,"Pass log");
        WebDriver driver = null;
        try{
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            driver.get("https://www.google.com");
            extentTest.log(Status.PASS,"page title : "+driver.getTitle());

            extentTest.addScreenCaptureFromPath(takeScreenshot(driver),"main Page");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(driver!=null){
                driver.quit();
            }
        }
    }

    @AfterClass
    public void afterClass(){
        log.info("@AfterClass method called");
        // flush - for report creation
        extentReports.flush();
    }

    /***
     * takeScreenshot Method
     */

    public String takeScreenshot(WebDriver driver) throws IOException {
        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File ssFolder=new File("test-output/screenshots/");
        if(!ssFolder.exists()){
            ssFolder.mkdirs();
        }
        File destFile = new File(ssFolder.getAbsolutePath()+"ss");
        FileUtils.copyFile(SrcFile, destFile);
        return destFile.getAbsolutePath();
    }

}
