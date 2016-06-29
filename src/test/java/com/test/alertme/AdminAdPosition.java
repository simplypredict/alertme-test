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


public class AdminAdPosition extends SeleniumUtil {

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

    /**
     *[AL-28]
     * 1)Login with admin
       2)Click email Tempelate List.
       3)Click Add new button
       4)get the number of element in DropdownListlist(select add size)
       5)compare the name of the ad position in the dropdownlist.
     */

    @Test(priority = TestConstants.NO_1)
    public void DetermineAdPosition() throws Exception
    {
        System.out.println("TEST: checking size and name of ad position");
        logger.info("TEST : checking size and name of ad position");

        browser_wait(TestConstants.WAIT_2000);

        //Login as admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));

        browser_wait(TestConstants.WAIT_3000);

        //Click on EmailTempelate on DashBoard
        WebElement alertmeEmail =doc_get("alertme_Dashboard_EmailTempelate_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeEmail.click();

        //click on Add new button
        WebElement addNewButton = doc_get("alertme_EmailTempelates_AddNew_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        addNewButton.click();

        //Click on select add size
        WebElement selectAdSize = doc_get("alertme_selectAddSize_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        selectAdSize.click();

        List<WebElement> selectAdSizeList = doc_list_get("alertme_selectAddSize_List_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);

        int size=selectAdSizeList.size();
        String arr[]=new String[5];
        arr[0] = "None Selected";
        arr[1] = "Super Leaderboard(970×90)";
        arr[2] = "Half Page(300×600)";
        arr[3] = "Button 2 (120×60)";
        arr[4] = "Micro Bar(88×31)";

        for(int i=0;i<size;i++)
        {
            //Comparing obtained adposition with hardcopy ad position
            Assert.assertEquals(selectAdSizeList.get(i).getText(),arr[i],"");


        }

    }

}