package org.sel.utils;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumFireFox {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/drivers/geckodriver.exe");
        WebDriver driver=new FirefoxDriver();
        WebDriverWait wait=new WebDriverWait(driver, 60);
        //launch google application
        driver.get("https://www.google.com");
        
        //maximize the window
        driver.manage().window().maximize();
        
        // give implicit wait between consecutive actions/events
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
        //Search for a string Guru99 using the searchbox
        WebElement searchBox=driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium");
        searchBox.submit();
        
        //store the parent window handle to switch back
        String parentHandle = driver.getWindowHandle(); // get the current window handle
        
        // get the first search link and open it in a new window using contextClick of Actions
        WebElement firstSearchLink=driver.findElement(By.xpath("((//div[@id='res']//div[@class='g']//div[@class='rc']//a))[1]"));
        
        //Actions action= new Actions(driver);
        //action.contextClick(firstSearchLink).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN); 
        firstSearchLink.sendKeys(selectLinkOpeninNewTab);
        
        // This code switched the focus to the new window using getWindowHandles function
        //Note: Window handles cannot deal with multiple tabs, they can only deal with multiple windows
        
        //option 1
        /*Set<String> windows = driver.getWindowHandles();
        String mainwin;
        String subwin;
        Iterator<String> it=windows.iterator();
        int c=0;
        while(it.hasNext()){
            if(c==0){
                mainwin=it.next();
                c++;
            }else
                subwin=it.next();
        }*/
        
        Set<String> windows = driver.getWindowHandles();
        for(String winHandle :windows){
            if(parentHandle.equalsIgnoreCase(winHandle))
                continue; 
             driver.switchTo().window(winHandle);
            
             System.out.println("First child window url: "+ driver.getCurrentUrl());
             driver.close();   // note: if there is only a single window, that will get closed but the process will still be running in background unlike quit 
        }
        
        //To come back to parent window
        driver.switchTo().window(parentHandle);
        
        // get the second search link and click it
        WebElement secondSearchLink=driver.findElement(By.xpath("(//div[@id='res']//div[@class='srg']//div[@class='rc']//a)[13]"));
        secondSearchLink.click();
        
        /*//This code is to wait for the page to load
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver wdriver) {
                    return ((JavascriptExecutor) driver).executeScript(
                        "return document.readyState"
                    ).equals("complete");
                }
            });*/
        Thread.sleep(2000);
        System.out.println("Second link clicked url : "+driver.getCurrentUrl());
        driver.findElement(By.linkText("Dietary Supplement Fact Sheets")).click();
        System.out.println("Click on a particular link on this page : "+driver.getCurrentUrl());
        
        driver.navigate().back();
        System.out.println("Back url : "+driver.getCurrentUrl());
        
        driver.navigate().forward();
        System.out.println("Forward url : "+driver.getCurrentUrl());
        
        driver.navigate().to("http://www.yahoo.com");
        System.out.println("Navigate to url : "+driver.getCurrentUrl());
        
        driver.navigate().refresh();
        System.out.println("Refresh to url : "+driver.getCurrentUrl());
        driver.quit(); // kill the entire browser running, including the background process
    }
}
