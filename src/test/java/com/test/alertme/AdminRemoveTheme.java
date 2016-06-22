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


public class AdminRemoveTheme extends SeleniumUtil {

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
     * [AL-72] steps:
     1). There is two section showing Selected and Removed.
     2). Get the no of element in Selected section and removed section.
     3). Pick one item from selected and drop it into removed.
     4). Check again the no of element in selected and removed section.
     There should be one item less in selected section and one should be increased in Removed section.
     */
    @Test(priority = TestConstants.NO_1)
    public void AdminRemoveTheme() throws Exception {
        System.out.println("TEST: Removing themes from the list");
        logger.info("TEST : Removing themes from the list");

        //Click on Entity on DashBoard
        WebElement alertmeEntity = doc_get("alertme_Dashboard_Entity_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);
        alertmeEntity.click();

        //click on List
        WebElement alertmeEntityList = doc_get("alertme_Entity_List_html_id",browser);
        browser_wait(TestConstants.WAIT_2000);
        alertmeEntityList.click();

        browser_wait(TestConstants.WAIT_3000);
        List<WebElement> selectedList = doc_list_get("alertme_List_selectedlist_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);

        int selectListBefore=selectedList.size();

        if(selectListBefore == 0)
            {
                Assert.fail("NO element in seleced list");
            }

        String s;
        WebElement source ;

        List<WebElement> removedList;
        removedList = doc_list_get("alertme_List_removedlist_html_id",browser);
        browser_wait(TestConstants.WAIT_3000);

        int removeSizeBefore =removedList.size();

        int size=0;
        WebElement destinaton;

        Actions action =new Actions(browser);

        for(int i=0;i<2;i++)
            {

            //Selected source element
                s = "//*[@id='page-wrapper']/div[4]/div[1]/ul/li["+(i+1)+"]";
                source=browser.findElement(By.xpath(s));
                browser_wait(TestConstants.WAIT_2000);
            //destination element
                destinaton=doc_get("alertme_List_destination_html_id",browser);
                browser_wait(TestConstants.WAIT_3000);

            //dragged to selected list to removed list
                action.dragAndDrop(source,destinaton).perform();
                browser_wait(TestConstants.WAIT_2000);

        }

            removedList =doc_list_get("alertme_List_removedlist_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);
            size=removedList.size();

            selectedList = doc_list_get("alertme_List_selectedlist_html_id",browser);
            browser_wait(TestConstants.WAIT_2000);

        //checked size after removing item
         int selectListAfter=selectedList.size();

         Assert.assertEquals((selectListBefore-selectListAfter),(size-removeSizeBefore),"Element Not dragged");

    }


}
