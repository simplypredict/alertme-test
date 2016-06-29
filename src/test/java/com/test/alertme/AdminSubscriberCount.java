package com.test.alertme;

import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class AdminSubscriberCount extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(AdminRemoveTheme.class);
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
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * [AL-24]
     ## Subscriber report
     1)Login as an admin
     2)Click on reports on dashboard
     3)Click on subscriber
     4)Type publisher name of filter
     5)check the size of the list
     6)check subscriber count.
     */
    @Test(priority = TestConstants.NO_2)
    public void AdminSubscriberFilter() throws Exception {

        System.out.println("TEST: Filtering report with correct publisher name in subscribers");
        logger.info("TEST : Filetering report with correct publisher name in subscribers");


        browser_wait(TestConstants.WAIT_2000);
        //Login as an admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);


        //click report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click subscriber in report
        WebElement alertmeReportCtr = doc_get("alertme_report_subscriber_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReportCtr.click();

        //Type publisher name on filter Textfield
        WebElement alertmeFilter =doc_get("alertme_report_subscriber_Textfield_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeFilter.sendKeys(seleniumTest_properties_assert_values_get("alertme_subscriber_Textfield_correct_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        //obtain the size of list
        List<WebElement> filterList = doc_list_get("alertme_report_subscriber_filterList",browser);
        browser_wait(TestConstants.WAIT_3000);
        System.out.println("Size of list:"+filterList.size());


        String xpathPublisherName,xpathSubscriberCount,actual,expected;

        //checking size
        if(filterList.size() > 0) {
            for (int i=1; i <=filterList.size(); i++) {

                //xpath of publisher name
                xpathPublisherName= "//*[@id='pstTable']/tbody/tr[" +i+ "]/td[2]";
                actual=browser.findElement(By.xpath(xpathPublisherName)).getText();
                expected=seleniumTest_properties_assert_values_get("alertme_subscriber_theme_Assert_values");
                System.out.println(actual.contains(expected));

                //compare filter search
                Assert.assertEquals(actual.contains(expected),true, "");

                if(actual.equals(expected)) {

                    //xpath for subscription count column
                    xpathSubscriberCount = "//*[@id='pstTable']/tbody/tr[" + i + "]/td[4]";

                    //shows subscriber count
                    if(browser.findElement(By.xpath(xpathSubscriberCount)).getText() == null)
                    {
                     Assert.fail("no count is showing");
                    }
                    break;
                }

            }
        }
        else{
            Assert.fail("No list is showing");
        }

    }
}