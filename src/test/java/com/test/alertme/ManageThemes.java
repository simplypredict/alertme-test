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

import java.io.IOException;
import java.util.List;


public class ManageThemes extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(ManageThemes.class);
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

    @Test(priority = TestConstants.NO_1)
    public void manageThemesWithEmail() throws IOException
    {

        browser_wait(TestConstants.WAIT_3000);

        WebElement emailTextField = doc_get("alertme_manageThemes_email_phone_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        emailTextField.sendKeys(seleniumTest_properties_assert_values_get("alertme_managethemes_email_Assert_value"));
        browser_wait(TestConstants.WAIT_1000);


        WebElement password = doc_get("alertme_manageThemes_password_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        password.sendKeys(seleniumTest_properties_assert_values_get("alertme_managethemes_password_Assert_value"));
        browser_wait(TestConstants.WAIT_1000);

        WebElement submitButton = doc_get("alertme_managethemes_submit_button_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        submitButton.click();
        browser_wait(TestConstants.WAIT_7000);

        List<WebElement>  themeList = doc_list_get("alertme_managethemes_ListofThemes_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);

        int sizeOfThemes = themeList.size();
        System.out.println("Size Of Themes "+sizeOfThemes);

        if(sizeOfThemes > 0)
        {
            WebElement themeCheckBox = doc_get("alertme_managethemes_Theme_checkbox_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            themeCheckBox.click();
            browser_wait(TestConstants.WAIT_2000);

            WebElement updateButton = doc_get("alertme_managethemes_Update_button_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            updateButton.click();
            browser_wait(TestConstants.WAIT_2000);

        }
        else
        {
            Assert.fail("There is no theme subscribed");
        }

        browser_wait(TestConstants.WAIT_7000);
        browser_wait(TestConstants.WAIT_3000);


        themeList = doc_list_get("alertme_managethemes_ListofThemes_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        int checkSize = themeList.size();

        System.out.println("CheckSize "+checkSize);

        Assert.assertEquals(checkSize,sizeOfThemes-1,"Error! Theme has not been updated");


    }






    @Test(priority = TestConstants.NO_2)
    public void manageThemesWithPhone() throws IOException
    {

        browser_wait(TestConstants.WAIT_3000);

        WebElement emailTextField = doc_get("alertme_manageThemes_email_phone_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        emailTextField.sendKeys(seleniumTest_properties_assert_values_get("alertme_managethemes_Phone_Assert_value"));
        browser_wait(TestConstants.WAIT_1000);


        WebElement password = doc_get("alertme_manageThemes_password_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        password.sendKeys(seleniumTest_properties_assert_values_get("alertme_managethemes_password_Assert_value"));
        browser_wait(TestConstants.WAIT_1000);

        WebElement submitButton = doc_get("alertme_managethemes_submit_button_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        submitButton.click();
        browser_wait(TestConstants.WAIT_7000);

        List<WebElement>  themeList = doc_list_get("alertme_managethemes_ListofThemes_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);

        int sizeOfThemes = themeList.size();
        System.out.println("Size Of Themes "+sizeOfThemes);

        if(sizeOfThemes > 0)
        {
            WebElement themeCheckBox = doc_get("alertme_managethemes_Theme_checkbox_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            themeCheckBox.click();
            browser_wait(TestConstants.WAIT_2000);

            WebElement updateButton = doc_get("alertme_managethemes_Update_button_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            updateButton.click();
            browser_wait(TestConstants.WAIT_2000);

        }
        else
        {
            Assert.fail("There is no theme subscribed");
        }

        browser_wait(TestConstants.WAIT_7000);
        browser_wait(TestConstants.WAIT_3000);


        themeList = doc_list_get("alertme_managethemes_ListofThemes_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        int checkSize = themeList.size();

        System.out.println("CheckSize "+checkSize);

        Assert.assertEquals(checkSize,sizeOfThemes-1,"Error! Theme has not been updated");


    }


}