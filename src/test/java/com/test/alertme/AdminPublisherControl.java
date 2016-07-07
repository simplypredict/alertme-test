package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.Alert;
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


public class AdminPublisherControl extends SeleniumUtil {

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

    @Test(priority = TestConstants.NO_1)
    public void adminpublishercontrol() throws IOException
    {

        String email = "test"+(int)((Math.random())*1000)+"@example.com";

        System.out.println(email);

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
        browser_wait(TestConstants.WAIT_2000);

        //Click on Add button on Publisher
        WebElement addPublisher =doc_get("alertme_Dashboard_AddPublisher_html_id",browser);
        browser_wait(TestConstants.WAIT_1000);
        addPublisher.click();
        browser_wait(TestConstants.WAIT_2000);

        //Add email
        WebElement addEmail =doc_get("alertme_Dashboard_AddPublisher_Email_html_id",browser);
        Assert.assertNotNull(addEmail.isDisplayed());
        browser_wait(TestConstants.WAIT_2000);
        addEmail.sendKeys(email);
        browser_wait(TestConstants.WAIT_2000);

        //Add name
        WebElement addName =doc_get("alertme_Dashboard_AddPublisher_Name_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        addName.sendKeys(seleniumTest_properties_assert_values_get("alertme_publisher_name_assert_value"));
        browser_wait(TestConstants.WAIT_2000);

        //Add Password
        WebElement addPassword =doc_get("alertme_Dashboard_AddPublisher_Password_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        addPassword.sendKeys(seleniumTest_properties_assert_values_get("alertme_publisher_password_assert_value"));
        browser_wait(TestConstants.WAIT_2000);

        //Re type password
        WebElement rePassword =doc_get("alertme_Dashboard_AddPublisher_Re_Password_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        rePassword.sendKeys(seleniumTest_properties_assert_values_get("alertme_publisher_password_assert_value"));
        browser_wait(TestConstants.WAIT_2000);


        //Click on submit button
        WebElement submitButton = doc_get("alertme_Dashboard_AddPublisher_Submit_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        submitButton.click();
        browser_wait(TestConstants.WAIT_7000);

        //check whether the joint name is in the list
        List<WebElement> userList = doc_list_get("alertme_Dashboard_AddPublisher_List_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        Boolean check = false;
        System.out.println("+++++++++++++"+userList.size()+"+++++++++++");
        int i;
        for(i = 0;i<userList.size();i++)
        {
            String s = userList.get(i).getText();
            if( s.contains(email) && s.contains(seleniumTest_properties_assert_values_get("alertme_publisher_name_assert_value")))
            {
                check = true;
                break;
            }
        }
        System.out.println(userList.get(i).getText());
        if (check == false)
        {
            Assert.fail("no user created");
        }

        //Edit a user
        browser_wait(TestConstants.WAIT_2000);
        i++;
        String edit = "html/body/div/div/div[2]/div[1]/table/tbody/tr["+i+"]/td[6]/a[1]";
        System.out.println(edit);
        WebElement editButton = browser.findElement(By.xpath(edit));
        browser_wait(TestConstants.WAIT_2000);
        editButton.click();
        browser_wait(TestConstants.WAIT_2000);
        WebElement checkBox1 = doc_get("alertme_Dashboard_AddPublisher_CheckBox1_html_id",browser);
        checkBox1.click();
        browser_wait(TestConstants.WAIT_2000);
        WebElement checkBox2 = doc_get("alertme_Dashboard_AddPublisher_CheckBox2_html_id",browser);
        checkBox2.click();
        browser_wait(TestConstants.WAIT_2000);
        WebElement submitButtonEditPage = doc_get("alertme_Dashboard_AddPublisher_SubmitEditPage_html_id",browser);
        submitButtonEditPage.click();
        browser_wait(TestConstants.WAIT_2000);

        //Delete a user
        browser_wait(TestConstants.WAIT_7000);
        int before = userList.size();
        String del = "html/body/div/div/div[2]/div[1]/table/tbody/tr["+i+"]/td[6]/a[2]";
        System.out.println(del);
        WebElement deleteButton = browser.findElement(By.xpath(del));
        deleteButton.click();
        browser_wait(TestConstants.WAIT_2000);

        Alert alert = browser.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();

        browser_wait(TestConstants.WAIT_7000);
        int after = userList.size();
        System.out.println(before+"*************"+after);
        if(after != before)
            Assert.fail("user not deleted");

    }


}