package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;


public class FrequentAlerts extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(AdminAdPosition.class);
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

    /**[AL-22]
     * Login as an admin
     * Check if Dashboard is showing Total AlertCount for all publisher
     * Clicking on the AlertCount Icon
     * Checking if MFA contains column named as "PublisherName" and "AlertCount"
     * Checking the size of the list
     * Counting Alert-count per publisher
     * Checking if total Alert-count shown on Dashboard is equal to alertcount of each Publisher
     */

    @Test(priority = TestConstants.NO_1)
    public void FrequentAlerts() throws Exception
    {
        System.out.println("TEST: Display Total Alerts for all publisher as well as per publisher");
        logger.info("TEST : Display Total Alerts for all publisher as well as per publisher");

        browser_wait(TestConstants.WAIT_2000);
        //Login as an admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        //obtaining Total number of Alerts from all the publisher on Dashboard
        WebElement alertmeCount = doc_get("alertme_dashboard_Publisher_AlertCount_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        String totalAlerts = alertmeCount.getText();

        //Checking if No values are coming
        Assert.assertNotNull(totalAlerts,"Total number of alerts from all the publisher is not showing");

        int totalsum = Integer.parseInt(totalAlerts);

        //Clicking on AlertCount on Dashboard
        WebElement alertcountButton = doc_get("alertme_dashboard_alertcount_button_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertcountButton.click();

        browser_wait(TestConstants.WAIT_3000);

        //Checking if publisher column is available or not
        WebElement publisherName = doc_get("alertme_mfa_publishername_column_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        Assert.assertEquals(publisherName.getText() , "Publisher Name" , "Publisher name column is not present");

        //checking if AlertCount column is available or not
        WebElement alertCount = doc_get("alertme_mfa_AlertCount_column_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        Assert.assertEquals(alertCount.getText(), "Alert Count","AlertCount column is not present");

        //obtaining the list of MFA
        List<WebElement> mfaList = doc_list_get("alertme_report_mfa_List_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        int mfaSize = mfaList.size();

        String xpathPublisherName,xpathAlertCount;
        String alertCountValue,publisherNameValue;

        int sum=0;

        //checking if size is not null
        if(mfaSize>0)
        {
            for(int i=1;i<=mfaSize;i++) {

                xpathPublisherName = ".//*[@id='alertsTable']/tbody/tr["+i+"]/td[2]";

                publisherNameValue = browser.findElement(By.xpath(xpathPublisherName)).getText();
                //checking publisher name for each row
                Assert.assertNotNull(publisherNameValue,"PublisherName is not available");

                xpathAlertCount = "//*[@id='alertsTable']/tbody/tr["+i+"]/td[3]";
                alertCountValue = browser.findElement(By.xpath(xpathAlertCount)).getText();

                //checking AlertCount value according to each publisher
                Assert.assertNotNull(alertCountValue,"Alertcount is null");

                //Adding alertcount value of each publisher
                sum +=Integer.parseInt(alertCountValue);


            }
        }
        else {
            Assert.fail("No list of MFA is available");
        }

        //checking if total alertcount is showing on the dashboard
        Assert.assertEquals(totalsum,sum,"Dashboard is not showing total alert of all publisher");

    }
}