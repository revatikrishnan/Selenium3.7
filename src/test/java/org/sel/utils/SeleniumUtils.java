package org.sel.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        /*System.out.println(System.getenv("PATH")); 
       
        if(System.getenv("PATH").contains(System.getProperty("user.dir")+"/drivers/IEDriverServer.exe")){
            System.out.println("contains");
        }*/
        String browserName="chrome";
        WebDriver driver =null;
        
        if(browserName.equalsIgnoreCase("firefox")){
            //DesiredCapabilities dc=DesiredCapabilities.firefox();
            FirefoxProfile prof = new FirefoxProfile();
            prof.setPreference("browser.download.folderList", 2);
            prof.setPreference("browser.download.dir",System.getProperty("user.dir")+"\\Download");
            //,text/plain,application/json,application/pdf,application/java-archive,text/html,application/plain
            prof.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip");
            prof.setPreference("browser.helperApps.alwaysAsk.force", false);
            prof.setPreference("browser.download.manager.showWhenStarting",false);
            prof.setPreference("browser.download.downloadDir",System.getProperty("user.dir")+"\\Download");
            prof.setPreference("browser.download.defaultFolder",System.getProperty("user.dir")+"\\Download");
            
            //dc.setCapability(FirefoxDriver.PROFILE, prof);
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
            //driver=new FirefoxDriver(dc);
            FirefoxOptions options=new FirefoxOptions();
            options.setProfile(prof);
            driver=new FirefoxDriver(options);
        }
        else if(browserName.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver.exe");
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory",System.getProperty("user.dir")+"\\Download");
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--test-type");
            options.addArguments("--disable-extensions"); //to disable browser extension popup
            
            DesiredCapabilities cap = DesiredCapabilities.chrome();
            cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
            cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            cap.setCapability(ChromeOptions.CAPABILITY, options);
            driver=new ChromeDriver(options);
        }
        else{
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
            driver=new InternetExplorerDriver();
        }
        WebDriverWait wait=new WebDriverWait(driver, 60);
        /*driver.get("https://www.ranorex.com/web-testing-examples/vip/");
        
        driver.manage().window().maximize();
        
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        driver.findElement(By.cssSelector("input#Load")).click();
        driver.findElement(By.cssSelector("input#FirstName")).sendKeys("Mike");
        
        driver.findElement(By.cssSelector("input#LastName")).sendKeys("Ross");
        String gender="male";
        WebElement genderradio=driver.findElement(By.xpath("//input[@type='radio' and @value='"+gender+"']"));
        genderradio.click();
        if(genderradio.isSelected()){
            System.out.println("Radio button with gender '"+gender+"' is selected");
        }
        
        WebElement sel=driver.findElement(By.id("Category"));
        
        Select selele=new Select(sel);
        if(selele.isMultiple()){
            System.out.println("the select box is not a multi select box");
            selele.selectByVisibleText("Music");
            selele.selectByValue("Other");
            selele.selectByIndex(2);
        }else
            selele.selectByValue("Music");
       
        driver.findElement(By.id("Add")).click();
        
        System.out.println("Another user added");
        
        driver.navigate().to("http://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_fileupload_type");
        driver.switchTo().frame("iframeResult");
        File f=new File(System.getProperty("user.dir")+"/Upload/WebDriverExample.txt");
        WebElement fileupload=driver.findElement(By.cssSelector("input#myFile[type=file]"));
        fileupload.sendKeys(f.getAbsolutePath());
        takescreenshot(driver,"screenshot1.jpg");
        
        driver.switchTo().defaultContent();*/
        
        /*driver.navigate().to("https://www.google.ca");
        WebElement searchbox=driver.findElement(By.cssSelector("[name='q']"));
        System.out.println("Width and height of the serach box : width="+searchbox.getSize().getWidth()+" height="+searchbox.getSize().getHeight());
        searchbox.sendKeys("SeleniumHQ");
        searchbox.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        WebElement searchedlink=driver.findElement(By.partialLinkText("Selenium - Web Browser Automation"));
        String parentHandle=driver.getWindowHandle();
        //Actions builder= new Actions(driver);
        //builder.contextClick(searchedlink).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
        searchedlink.sendKeys(selectLinkOpeninNewTab);
        Thread.sleep(10000);
        //wait.until(ExpectedConditions.is)
        for(String handlers:driver.getWindowHandles()){
            if(!handlers.equals(parentHandle)){
                driver.switchTo().window(handlers);
                if(driver.getTitle().contains("Selenium - Web Browser Automation")){
                    System.out.println("Entered the tab with title 'Selenium - Web Browser Automation'");
                    driver.findElement(By.linkText("Download")).click();
                    takescreenshot(driver,"screenshot2.jpg");
                    Thread.sleep(10000);
                    driver.findElement(By.xpath("//td[text()='C#']//following-sibling::td/a[text()='Download']")).click();
                    System.out.println("here");
                    Thread.sleep(10000);
                    driver.close();
                    break;
                }
            }
        }
        driver.switchTo().window(parentHandle);*/
        
        driver.navigate().to("https://www.w3schools.com/js/tryit.asp?filename=tryjs_alert");
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#iframeResult")));
        driver.findElement(By.xpath("//button[.='Try it']")).click();
        
        Alert alert=driver.switchTo().alert();
        Thread.sleep(10000);
        System.out.println(alert.getText());
        alert.accept();
        
        driver.navigate().to("http://www.w3schools.com/js/tryit.asp?filename=tryjs_confirm");
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#iframeResult")));
        driver.findElement(By.xpath("//button[.='Try it']")).click();
        
        Alert alert1=driver.switchTo().alert();
        Thread.sleep(10000);
        System.out.println(alert1.getText());
        alert1.accept();
        
        
        driver.navigate().to("http://www.w3schools.com/js/tryit.asp?filename=tryjs_prompt");
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#iframeResult")));
        driver.findElement(By.xpath("//button[.='Try it']")).click();
        
        Alert alert2=driver.switchTo().alert();
        Thread.sleep(10000);
        System.out.println(alert2.getText());
        alert2.sendKeys("mike ross");
        alert2.accept();
        System.out.println(driver.findElement(By.id("demo")).getText());
        driver.quit();
        
    }

    private static void takescreenshot(WebDriver driver,String filename) throws IOException {
        File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(System.getProperty("user.dir")+"/Screenshots/"+filename));
        
        
    }

}
