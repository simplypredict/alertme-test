package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.lift.find.ImageFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.List;


public class PublisherLogo extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(PublisherLogo.class);
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


    /**[AL-27]-publisher Logo Use
     * Hit URL http://52.91.245.79/#/
     * Login as Admin
     * Click on Publisher on side menu bar
     * Obtain the list of publisher
     * Find a specific Publisher
     * Obtain the image src of the Publisher
     * Click on Edit icon for that specific publisher
     * Add logo if logo not added by adding a image src in the "logo link" textfield
     * Remove logo if logo is already added.
     * Obtain the src of image again
     * Src of new image should be what you have added in step 8
     * @throws IOException
     */
    @Test(priority = TestConstants.NO_1)
    public void publisherLogoTest() throws IOException
    {

        System.out.println("TEST: checking Admin control for publisher");
        logger.info("TEST : checking Admin control for publisher");

        browser_wait(TestConstants.WAIT_2000);

        //Login as admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));

        browser_wait(TestConstants.WAIT_3000);


        //Click on Publisher on DashBoard
        WebElement alertmepublisher =doc_get("alertme_Dashboard_Publisher_html_id",browser);
        browser_wait(TestConstants.WAIT_1000);
        alertmepublisher.click();
        browser_wait(TestConstants.WAIT_7000);

        //obtain List of Publisher
        List<WebElement> userList = doc_list_get("alertme_Publisher_email_List_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);

        System.out.println("userList size>>>"+userList.size());
        if (userList.size() == 0)
        {
            Assert.fail("No list");
        }

        //Check a specific email in the list
        int i;
        String email;
        boolean check =false;
        for(i = 0;i<userList.size();i++)
        {
            email = userList.get(i).getText();
            if( email.contains(seleniumTest_properties_assert_values_get("alertme_email_publisher_check_assert_values")))
            {
                check = true;
                break;
            }
        }


        //if found
        if(check)
        {
            i++;
            System.out.println("::::::::: "+i);
            String imageXpath = "html/body/div[1]/div/div[2]/div[1]/table/tbody/tr["+i+"]/td[4]/img";

            //obtain src of previous logo
            String imageToCheck = browser.findElement(By.xpath(imageXpath)).getAttribute("src");
            System.out.println("imageSrcBefore>>>>>>>"+imageToCheck);


            //Edit a user
            browser_wait(TestConstants.WAIT_2000);

            String edit = "html/body/div/div/div[2]/div[1]/table/tbody/tr["+i+"]/td[6]/a[1]";

            WebElement editButton = browser.findElement(By.xpath(edit));
            browser_wait(TestConstants.WAIT_2000);
            editButton.click();
            browser_wait(TestConstants.WAIT_3000);

            boolean isSelected = false;

            //Check if checkbox for "Use logo is checked or not.
            WebElement allowCheckBox = doc_get("alertme_Publisher_Edit_logo_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);

            isSelected = allowCheckBox.isSelected();

            //if checkbox is selected
            if(isSelected)
            {
                //unselect the checkbox
                allowCheckBox.click();
            }
            //if checkbox is not selected
            else
            {
                //select the checkbox
                allowCheckBox.click();

                //Add image location in logo link textfield
                WebElement logoLink = doc_get("alertme_Publisher_logoLink_textfield_html_id",browser);
                browser_wait(TestConstants.WAIT_2000);

                logoLink.sendKeys(seleniumTest_properties_assert_values_get("alertme_image_location_assert_value"));
                browser_wait(TestConstants.WAIT_2000);
            }

            //Click on submit button
            WebElement submitButton = doc_get("alertme_Dashboard_Publisher_Submit_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            submitButton.click();
            browser_wait(TestConstants.WAIT_7000);

            //obtain the src from the same email
            String imageToCheckWith = browser.findElement(By.xpath(imageXpath)).getAttribute("src");
            System.out.println("imageSrcAfter>>>>>>>>>>"+imageToCheckWith);

            if(!isSelected) {

                Assert.assertEquals(imageToCheckWith,seleniumTest_properties_assert_values_get("alertme_image_location_assert_value"),"Image is not changed accordingly");

            }
            else
            {
                Assert.assertEquals(imageToCheckWith,"http://52.91.245.79/assets/logo.png","Image is not changed");
            }

        }





    }
}