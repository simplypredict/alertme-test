package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class LogoRedirect extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(LogoRedirect.class);
    private final String SELENIUM_PROPERTIES_HTML_ID_FILENAME = "AlertMe_HTML_ID.properties";
    private final String SELENIUM_PROPERTIES_ASSERT_VALUES_FILENAME = "AlertMe_Assert_Values.properties";

    @BeforeMethod
    protected void seleniumTest_suite_pre_function() throws Exception {
        try {
            System.out.println("Inside before method");
            logger.debug("Preparing Test Suite");
            seleniumTest_properties_Assert_Values_file = SELENIUM_PROPERTIES_ASSERT_VALUES_FILENAME;
            seleniumTest_properties_HTML_ID_file = SELENIUM_PROPERTIES_HTML_ID_FILENAME;
            System.out.println("going to call seleniumTest_suite_setup");
            seleniumTest_suite_setup();
            browser.get("http://52.91.245.79/#/manage_themes");


        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


/* AL-98
Hit url http://52.91.245.79/#/manage_themes
Click on logo
Check if current Url is home page or not
 */

    @Test(priority = TestConstants.NO_1)

    public void logoRedirect() throws IOException
    {


        browser_wait(TestConstants.WAIT_2000);

        WebElement logo = doc_get("alertme_manageThemes_Homepage_Logo_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        logo.click();

        browser_wait(TestConstants.WAIT_3000);

        String Url = browser.getCurrentUrl();
        System.out.println("URL " + Url);

        String homepage = seleniumTest_properties_assert_values_get("alertme_HomePage_Url_Assert_values");

        Assert.assertEquals(Url,homepage,"It has not been redirected to homepage");

    }
}
