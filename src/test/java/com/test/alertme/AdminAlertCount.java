package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.WebElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;


public class AdminAlertCount extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(AdminDownloadReports.class);
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
     * [AL-26]
     * ## mfa report
     * 1)Click report-->mfa
     * 2)check list size.
     * 3)check alertcount for first alert in the list
     */
    @Test(priority = TestConstants.NO_3)
    public void DownloadMFAReport() throws Exception {
        System.out.println("TEST: Checking Alertme Count");
        logger.info("TEST : Checking Alertme Count");

        //click on report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click on mfa
        WebElement alertmeReaders = doc_get("alertme_report_mfa_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReaders.click();

        List<WebElement> mfaList = doc_list_get("alertme_report_mfa_table_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        int size;
        System.out.println(size=mfaList.size());

        if(size == 0)
        {
            Assert.fail("No mfa list available");
        }

        WebElement alertName= doc_get("alertme_mfaList_alertName_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        String name = alertName.getText();
        System.out.println(name);

        WebElement alertCount = doc_get("alertme_mfaList_alertCount_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        String count =alertCount.getText();

        if(count.equals("0") || count.equals(""))
        {
            Assert.fail("Alertcount is empty");
        }






    }
}