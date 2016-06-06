package com.test.alertme;

import com.test.util.SeleniumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import com.test.util.TestConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class AdminFilterReport extends SeleniumUtil {

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
//	[AL-25]
////    ## CTR report
////    1)Hit URL = http://52.91.245.79/reports/ctr .
////    2)Type correct publisher name on Filter TextField
////    3)check size of the list
//      4)check the name of the publisher name in all the list


    @Test(priority = TestConstants.NO_1)
    public void AdminCorrectPublisher() throws Exception {

        System.out.println("TEST: Filetering report with correct publisher name in ctr");
        logger.info("TEST : Filetering report with correct publisher name in ctr");

        //click on report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click on ctr
        WebElement alertmeReportCtr = doc_get("alertme_report_ctr_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReportCtr.click();

        //Type publisher name on filter Textfield
        WebElement alertmeFilter =doc_get("alertme_report_filter_Textfield_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeFilter.sendKeys(seleniumTest_properties_assert_values_get("alertme_filter_Textfield_correct_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        List<WebElement> filterList = doc_list_get("alertme_report_ctr_filterlist",browser);
        browser_wait(TestConstants.WAIT_3000);
;

        String s,x,y;

        if(filterList.size() > 0) {
            for (int i = 1; i <= filterList.size(); i++) {
                s= "//*[@id='publishersTable']/tbody/tr["+ i +"]/td[2]";
                System.out.println(x=browser.findElement(By.xpath(s)).getText());
                System.out.println(y=seleniumTest_properties_assert_values_get("alertme_filter_Textfield_correct_Assert_values"));

                if (x.equals(y)) {
                    System.out.println("Test Passed");
                } else {
                    //System.out.println("List is showing other publisher name");
                    Assert.fail("List is showing other publisher name");
                }
            }
        }
        else{
            Assert.fail("No list is showing");
        }

        browser.close();


    }


//	[AL-25]
////    ## Subscriber report
////    1)Hit URL = http://52.91.245.79/reports/pub_subcribers .
////    2)Type correct publisher name on Filter TextField
////    3)check size of the list
//      4)check the name of the publisher name in all the list

    @Test(priority = TestConstants.NO_1)
    public void AdminIncorrectPublisher() throws Exception {

        System.out.println("TEST: Filetering report with correct publisher name in subscribers");
        logger.info("TEST : Filetering report with correct publisher name in subscribers");

        //click report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click subscriber in report
        WebElement alertmeReportCtr = doc_get("alertme_report_subscriber_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReportCtr.click();

        //Type publisher name on filter Textfield
        WebElement alertmeFilter =doc_get("alertme_report_filter_Textfield_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeFilter.sendKeys(seleniumTest_properties_assert_values_get("alertme_filter_Textfield_correct_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        List<WebElement> filterList = doc_list_get("alertme_report_subscriber_filterList",browser);
        browser_wait(TestConstants.WAIT_3000);
        System.out.println(filterList.size());


        String s,x,y;

        if(filterList.size() > 0) {
            for (int i = 1; i <= filterList.size(); i++) {
                s= "//*[@id='pstTable']/tbody/tr[" +i+ "]/td[2]";
                System.out.println(x=browser.findElement(By.xpath(s)).getText());
                System.out.println(y=seleniumTest_properties_assert_values_get("alertme_filter_Textfield_correct_Assert_values"));

                if (x.equals(y)) {
                    System.out.println("Test Passed");
                } else {
                    //System.out.println("List is showing other publisher name");
                    Assert.fail("List is showing other publisher name");
                }
            }
        }
        else{
            Assert.fail("No list is showing");
        }

        browser.close();


    }
}
