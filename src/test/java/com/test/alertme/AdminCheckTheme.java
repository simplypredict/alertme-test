package com.test.alertme;
import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;


public class AdminCheckTheme extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(com.test.alertme.AdminDownloadReports.class);
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

    public static String downloadPath = System.getProperty("user.dir");


    /**
     * [AL-20]
     * ## readers theme report
     * 1)Login as an admin
     * 2)Click report->readers theme .
     * 3)check size of the list
     * 4)pick 1st Reader in the List.
     * 5)Check if he contains "Selected Themes" and "Generated Themes".
     */
    @Test(priority = TestConstants.NO_1)
    public void CheckTheme() throws Exception {

        System.out.println("TEST: Downloading Readers's theme report and checking its file extension");
        logger.info("TEST : Downloading Reader's theme report and checking its file extension");


        browser_wait(TestConstants.WAIT_2000);

        //Login as admin
        sparkWayLogin(seleniumTest_properties_assert_values_get("alertme_adminlogin_textfield_Assert_values"),seleniumTest_properties_assert_values_get("alertme_adminlogin_password_Assert_values"));
        browser_wait(TestConstants.WAIT_3000);




        //click on report
        WebElement alertmeReport = doc_get("alertme_Dashboard_report_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReport.click();

        //click on reader
        WebElement alertmeReaders = doc_get("alertme_report_reader_html_id", browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeReaders.click();

        browser_wait(TestConstants.WAIT_7000);

        //Check the list of the reader theme
        List<WebElement> readerList = doc_list_get("alertme_report_List_html_id" , browser);
        browser_wait(TestConstants.WAIT_3000);
        int size = readerList.size();

        if(size == 0)
        {
            Assert.fail("No list found");
        }


        List<WebElement> themeList = doc_list_get("alertme_report_Theme_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        int themesize = themeList.size();
        System.out.println("ThemeSize is "+themesize);

        String themeXpath;
        String isSelected = "NO";
        String isGenerated = "NO";

        for(int i=0;i<themesize;i++)
        {
            themeXpath = "//*[@id='topicsTable']/tbody/tr/td[5]/p["+(i+1)+"]";
            String themeName = browser.findElement(By.xpath(themeXpath)).getText();

            if(themeName.indexOf("Selected") != -1)
            {

                isSelected = "Yes";
            }
            else if(themeName.indexOf("Generated") != -1)
            {

                isGenerated = "Yes";
            }
            else {
                Assert.fail("Neither selected nor generated");
            }

        }
        Assert.assertEquals(isSelected,"Yes","NO selected theme available");
        //Assert.assertEquals(isGenerated,"Yes","NO Generated theme available");


    }
}