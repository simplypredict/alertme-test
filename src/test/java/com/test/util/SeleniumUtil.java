package com.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;


public class SeleniumUtil {
    protected static WebDriver browser;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
    protected String seleniumTest_properties_Assert_Values_file = TestConstants.NULL;
    protected String seleniumTest_properties_HTML_ID_file = TestConstants.NULL;
    private Properties seleniumTest_properties_assert_values = null;
    private Properties seleniumTest_properties_html_id = null;

    private static String loginXpath = "//*[@id='page-content-wrapper']/div/div/div[2]/form/p[1]/input";
    private static  String pwdXpath = "//*[@id='page-content-wrapper']/div/div/div[2]/form/p[2]/input";
    private static String loginButtonXpath = "//*[@id='page-content-wrapper']/div/div/div[2]/form/div[4]/button";

    public static boolean isWindows() {
        return (OS.indexOf("win") >= TestConstants.NO_0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= TestConstants.NO_0);
    }

    public static void browser_wait(Long waitingTime) {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException ie) {
            logger.error("Browser_wait.", ie.getMessage());
        }
    }

    public void seleniumTest_suite_setup() throws MalformedURLException,
            IOException, InterruptedException {
        System.out.println("inside seleniumTest_suite_setup");
        seleniumTest_suite_properties_initialize();
    }

    private void seleniumTest_suite_properties_initialize() throws MalformedURLException,
            IOException, InterruptedException {

        System.out.println("inside seleniumTest_suite_properties_initialize\ngoing to call getdriver");
        browser = SeleniumCore.getDriver();
        logger.info("Loading Driver for the browser");
        logger.info("Fetching the UUID of the Device ");
        String target = SeleniumCore.SELENIUM_LOGIN_URL;
        System.out.println("Opening URL = " + target);
        browser.get(target);
        try {
            seleniumTest_properties_assert_values = new Properties();
            seleniumTest_properties_html_id = new Properties();

            seleniumTest_properties_Assert_set(seleniumTest_properties_Assert_Values_file);
            seleniumTest_properties_HTML_ID_set(seleniumTest_properties_HTML_ID_file);
        } catch (Exception e) {
            logger.debug("SeleniumUtil set Assert & HTML_ID Exception : ", e.getCause());
        }
    }

    public void seleniumTest_properties_Assert_set(String fileName) throws IOException {
        if (fileName != null) {
            if (seleniumTest_properties_assert_values == null) {
                seleniumTest_properties_assert_values = new Properties();
            }
            InputStream instream = SeleniumUtil.class.getClassLoader().getResourceAsStream(
                    TestConstants.PROPERTIES + File.separator + fileName);
            seleniumTest_properties_assert_values.load(instream);
        }
    }

    public void seleniumTest_properties_HTML_ID_set(String fileName)
            throws IOException {
        if (fileName != null) {
            if (seleniumTest_properties_html_id == null) {
                seleniumTest_properties_html_id = new Properties();
            }
            InputStream instream = SeleniumUtil.class.getClassLoader()
                    .getResourceAsStream(TestConstants.PROPERTIES + File.separator + fileName);
            seleniumTest_properties_html_id.load(instream);
        }
    }

    public WebElement doc_get(String xpath, WebDriver browser) {

        WebElement elementPath = null;
        try {
            elementPath = browser.findElement(By
                    .xpath(seleniumTest_properties_HTML_ID_get(xpath)));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return elementPath;
    }

    public String seleniumTest_properties_HTML_ID_get(String propId) {
        String value = null;
        if (seleniumTest_properties_html_id.size() > TestConstants.NO_0) {
            value = seleniumTest_properties_html_id.getProperty(propId);

        }
        return value;
    }

    public String seleniumTest_properties_assert_values_get(String key) {
        String value = TestConstants.NULL;
        if (seleniumTest_properties_assert_values.size() > TestConstants.NO_0) {
            value = seleniumTest_properties_assert_values.getProperty(key);
        }
        return value;
    }

    /**
     * This method return the list of web-elements of given xpath
     */
    public List<WebElement> doc_list_get(String xpath, WebDriver browser) {
        List<WebElement> listElementPath = null;
        try {
            listElementPath = browser.findElements(By
                    .xpath(seleniumTest_properties_HTML_ID_get(xpath)));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return listElementPath;
    }

    /**
     * This method return the list of web-elements of given xpath
     */
    public void sparkWayLogin(String userName, String password) {

        WebElement nrichLoginTextField=doc_get("nrich_login_textfield_html_id", browser);
        browser_wait(TestConstants.WAIT_2000);
        nrichLoginTextField.sendKeys(userName);

        WebElement nrichPwdTextField=doc_get("nrich_password_textfield_html_id", browser);
        browser_wait(TestConstants.WAIT_2000);
        nrichPwdTextField.sendKeys(password);

        WebElement nrichLoginButton=doc_get("nrich_login_button_html_id", browser);
        browser_wait(TestConstants.WAIT_2000);
        nrichLoginButton.click();
        browser_wait(TestConstants.WAIT_3000);
    }

    /**
     *It takes filename and downloadpath as parameter
     * compare if file name is present in downloadpath
     * @return True or False
     */
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        boolean flag = false;
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            // System.out.println(dir_contents[i].getName());
            if (dir_contents[i].getName().equals(fileName)) {

                return flag = true;
            }
        }

        return flag;
    }

    @AfterMethod
    public void nrichLogOut() {
       try {

          browser.quit();
       }
//            WebElement nrich_sidemenu_toggle = doc_get("nrich_side_menu_toggle_xpath", browser);
//            browser_wait(TestConstants.WAIT_7000);
//            Assert.assertFalse((nrich_sidemenu_toggle == null), "Side Menu Not found");
//            nrich_sidemenu_toggle.click();
//            browser_wait(TestConstants.WAIT_1000);
//            WebElement nrich_sidemenu_option = doc_get("nrich_side_ " +
//                    "menu_option_xpath",browser);
//            List<WebElement> nrich_sidemenu_list = nrich_sidemenu_option.findElements(By.tagName("li"));
//            Iterator<WebElement> side_menu_list_iterator = nrich_sidemenu_list.iterator();
//            while(side_menu_list_iterator.hasNext())
//            {   browser_wait(TestConstants.WAIT_1000);
//                WebElement nrich_list_option = side_menu_list_iterator.next();
//                String nrich_sidemenu_option_name = nrich_list_option.findElement(By.tagName("a")).findElement(By.tagName("span")).getText();
//                System.out.println(nrich_sidemenu_option_name);
//                if(nrich_sidemenu_option_name.equals(seleniumTest_properties_assert_values_get("nrich_sidemenu_logout_option_text")) && null != nrich_sidemenu_option_name)
//                {
//                    nrich_list_option.findElement(By.tagName("a")).click();
//                    browser.wait(TestConstants.WAIT_7000);
//                    browser.close();
//                    break;
//                }
//            }
//            browser_wait(TestConstants.WAIT_7000);
//            logger.info("nirchLogout");
//        }
        catch (Exception exception) {
            logger.error("" + exception);

        }

   }

    public long getCurrentTimeStamp()
    {
        return (new Date()).getTime();
    }
}
