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

    /**
     * [AL-25]
     ## CTR report
     1)Login as an admin
     2)click report-CTR
     3)Type publisher name on Filter TextField
     4)check size of the list
     5)check the name of the publisher in all the list
     */

    @Test(priority = TestConstants.NO_1)
    public void AdminCtrFilter() throws Exception {

        System.out.println("TEST: Filetering report with correct publisher name in ctr");
        logger.info("TEST : Filetering report with correct publisher name in ctr");

        browser_wait(TestConstants.WAIT_2000);
        //Login as an admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);


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

        browser_wait(TestConstants.WAIT_7000);
        browser_wait(TestConstants.WAIT_3000);

        List<WebElement> filterList = doc_list_get("alertme_report_ctr_filterlist",browser);
        browser_wait(TestConstants.WAIT_3000);
        int size = 0;


        for(WebElement webElement: filterList){

            if(webElement.isDisplayed()){
                size++;
            }

        }

        System.out.println("Size of the List "+size);


        String s,actual,expected;



        if(size>0)
        {
            for (WebElement webElement: filterList) {
                actual=webElement.findElement(By.xpath("//td[2]")).getText();


                expected = seleniumTest_properties_assert_values_get("alertme_filter_Textfield_correct_Assert_values");

                Assert.assertEquals(actual.contains(expected),true, "Publisher name is different in the current list");


            }
        }

        else{
            Assert.fail("No list is showing");
        }


    }



}

