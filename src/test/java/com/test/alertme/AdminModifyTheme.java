
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


public class AdminModifyTheme extends SeleniumUtil {

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
     * [AL-29]
     *Login as an admin
     1)click themes->List on dashboard
     2)press find button on select publisher
     3)check size of the list
     4)drag 1st element to the bottom of the sort order
     5)check if selected element is shifted or not
     5)click on Hide button of 1st element(Currently it is static)
     6)check if changes to unhide
     7)again click on unhide and check if changes to hide.

     */
    @Test(priority = TestConstants.NO_1)
    public void AdminModifyTheme() throws Exception {

        System.out.println("TEST: Check if he contains Selected Themes and Generated Themes");
        logger.info("TEST : Check if he contains Selected Themes and Generated Themes");


        browser_wait(TestConstants.WAIT_2000);
        //Login as an admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);

        //click on Theme
        WebElement alertmeReport = doc_get("alertme_Dashboard_Theme_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click on Theme List
        WebElement alertmeReaders = doc_get("alertme_Themes_List_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReaders.click();

        //click on find button
        WebElement publisherButton = doc_get("alertme_List_publisher_button_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        publisherButton.click();

        browser_wait(TestConstants.WAIT_3000);

        //checking list size
        List<WebElement> themeListSize = doc_list_get("alertme_ThemeList_size_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        int size = themeListSize.size();

        if(size==0)
        {
            Assert.fail("No list found");
        }

        Actions action =new Actions(browser);


        WebElement source = doc_get("alertme_source_Element_List_html_id",browser);
        String sourceText = source.getText();
        WebElement destination = doc_get("alertme_destination_Element_List_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);

        //Dragging elements
        action.dragAndDrop(source,destination).perform();

        browser_wait(TestConstants.WAIT_2000);

        String checkText = doc_get("alertme_check_Element_List_html_id",browser).getText();

        //checking if dragged
        Assert.assertEquals(checkText,sourceText,"Not able to sort");


        WebElement hideClick = doc_get("alertme_Element_button_List_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);


        //checking Hide and Unhide button
        for(int i=1;i<=2;i++) {
            browser_wait(TestConstants.WAIT_3000);
            String buttonText = hideClick.getText();

            hideClick.click();
            browser_wait(TestConstants.WAIT_2000);
            if (buttonText.equals("Hide")) {

                buttonText = hideClick.getText();

                Assert.assertEquals(buttonText, "Un Hide", "Button not clicked");
            } else if (buttonText.equals("Un Hide")) {

                buttonText = hideClick.getText();

                Assert.assertEquals(buttonText, "Hide", "Button not clicked");
            }

        }

    }
}
