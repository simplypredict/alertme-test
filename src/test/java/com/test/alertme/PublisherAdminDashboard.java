package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.UserAndPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;


public class PublisherAdminDashboard extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(PublisherAdminDashboard.class);
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
     * Click on Report-->MFA
     * Counting Alert-count of a publisher(e.g. for jenny )
     * Logout
     * Login as Publisher Admin (as per e.g. in step-3, login as jenny)
     * Checking if AlertCount in jenny is same as we have cheked in step-3
     * Checking if it is showing subscriber Count also.
     */

    @Test(priority = TestConstants.NO_1)
    public void publisherAdminDashboard() throws Exception
    {



        System.out.println("TEST: AlertCount on publisher Admin");
        logger.info("TEST : AlertCount on publisher Admin");

        browser_wait(TestConstants.WAIT_2000);

        //Login as an admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);



        //click on report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click on mfa
        WebElement alertmeReaders = doc_get("alertme_report_mfa_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReaders.click();


       browser_wait(TestConstants.WAIT_3000);

        //checking if there is no publisher available

        List<WebElement> mfaList = doc_list_get("alertme_mfa_filterList_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        int mfaSize = 0,i=0;
        mfaSize= mfaList.size();
        boolean check = false;

        for(i=1;i<mfaSize;i++)
        {
            if(mfaList.get(i).getText().equals("jenny"))
            {
                check = true;
                break;
            }
        }




        int sum=0;

        if(mfaSize == 0)
            Assert.fail("There is no publisher regarding your search");



        String xpathAlertCount = "html/body/div[1]/div/div[2]/div[1]/table/tbody/tr["+(i+1)+"]/td[3]";
        WebElement AlertCount = browser.findElement(By.xpath(xpathAlertCount));

        String ToCheckvalue = AlertCount.getText();

        System.out.println("ToCheckValue "+ToCheckvalue);


        //Clicking on top right corner for Logout
        WebElement logoutSetting = doc_get("alerte_topRightCorner_button_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        logoutSetting.click();
        browser_wait(TestConstants.WAIT_1000);

        //Logout
        WebElement logoutButton = doc_get("alertme_logout_button_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        logoutButton.click();
        browser_wait(TestConstants.WAIT_7000);

        browser_wait(TestConstants.WAIT_2000);

        //Login as an Publisher Admin again
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_userlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_userlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        //Counting AlertCount
        WebElement AlertCountLabel = doc_get("alertme_AlertCount_Label_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        String checkwith = AlertCountLabel.getText();
        System.out.println("Check with" + checkwith);
        browser_wait(TestConstants.WAIT_1000);

        //Checking if alertcount is same of what we have checked in Admin
        Assert.assertEquals(ToCheckvalue,checkwith,"AlertCount is not showing correct value");

        //Checking if subscriber Count is available or not.
        WebElement subscriberCount = doc_get("alertme_SubscriberCount_Label_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);

        System.out.println("Subscriber Count" + subscriberCount.getText());
        if(subscriberCount.getText() == null)
        {
            Assert.fail("No subscriber count showing");
        }

    }
}