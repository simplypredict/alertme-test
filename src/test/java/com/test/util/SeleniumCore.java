package com.test.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;


public class SeleniumCore {
    public static final String BROWSER,BROWSER_VERSION;
    public static final String SELENIUM_LOGIN_URL;
    private static final String FIREFOX = "Firefox";
    private static final String CHROME = "Chrome";
    private static final String IE = "IE";
    private static final String SAFARI = "Safari";
    private static Properties SELENIUMTEST_PROPERTIES;
    public static final String USERNAME = " jeff103";
    public static final String AUTOMATE_KEY = "HJqxMp3D7DVyv9XfwBu2";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";
    private static Logger logger = LoggerFactory.getLogger(SeleniumCore.class);

    static {
        InputStream instream;
        instream = SeleniumCore.class
                .getClassLoader()
                .getResourceAsStream(
                        TestConstants.SELENIUM_ENVIORNMENT_FOLDERNAME
                                + File.separator
                                + TestConstants.SELENIUM_PROPERTIES_ENVIORNMENT_FILENAME
                );
        SELENIUMTEST_PROPERTIES = new Properties();
        try {
            SELENIUMTEST_PROPERTIES.load(instream);
        } catch (Exception e) {
            logger.debug("Exception in loading environment property file "
                    + TestConstants.SELENIUM_PROPERTIES_ENVIORNMENT_FILENAME, e);
        }
        BROWSER = getSpecificProperty(TestConstants.BROWSER);
        BROWSER_VERSION = getSpecificProperty(TestConstants.BROWSER_VERSION);
        SELENIUM_LOGIN_URL = getSpecificProperty(TestConstants.Url);
    }

    private static String getSpecificProperty(String property) {
        return (null != System.getProperty(property)) ? System.getProperty(property) :
                SELENIUMTEST_PROPERTIES.getProperty(property);
    }

    public static WebDriver getDriver() {
        System.out.println("Inside getdriver function");
        String runBrowser = BROWSER;
        logger.info("current browser initialized");
//        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability("browser", BROWSER);
//        caps.setCapability("browser_version", BROWSER_VERSION);
//        caps.setCapability("os", "Windows");
//        caps.setCapability("os_version", "XP");
//        caps.setCapability("browserstack.debug", "true");
        WebDriver driver = null;
//        try {
//            driver = new RemoteWebDriver(new URL(URL), caps);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

        DesiredCapabilities capabilities = null;
       ChromeOptions options = null;
        logger.info("going to check browser");
        System.out.println("going to check browser");
        if (FIREFOX.equalsIgnoreCase(runBrowser)) {
            logger.info("browser=firefox");
            System.out.println("browser=firefox");
            FirefoxProfile profile = new FirefoxProfile();
            System.out.println("initializing properties");
            profile.setPreference("browser.download.folderList", TestConstants.NO_2);
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setAssumeUntrustedCertificateIssuer(false);
            profile.setPreference("browser.download.dir", System.getProperty("user.dir"));
            profile.setPreference("plugin.state.npseleniumdetector", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                    "text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");

            System.out.println("properties initialized");

           driver = new FirefoxDriver(profile);

            System.out.println("exiting firefox block");
        } else if (CHROME.equalsIgnoreCase(runBrowser)) {
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            try {
                chromePrefs.put("profile.default_content_settings.popups", TestConstants.NO_0);
                logger.info(System.getProperty("user.dir") + " System working dir ");
                chromePrefs.put("download.default_directory", System.getProperty("user.dir"));
                chromePrefs.put("plugin.state.npseleniumdetector", 2);
                capabilities = DesiredCapabilities.chrome();
                options = new ChromeOptions();
            } catch (Exception e) {
                logger.error("SeleniumCore ChromeOption Exception : " + e);
            }
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments(Arrays.asList("--test-type", "--start-maximized"));
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            try {
                driver = new RemoteWebDriver(new URL(TestConstants.REMOTEWEBDRIVER_URL + ":" + TestConstants.CHROME_PORT), capabilities);
                System.out.println("driver initialized");
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                logger.info("Malform URL exception :" + ex);
            }
        } else if (IE.equalsIgnoreCase(runBrowser)) {
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
            capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
            capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);
            try {
                driver = new RemoteWebDriver(new URL(TestConstants.REMOTEWEBDRIVER_URL + ":" + TestConstants.IE_PORT),
                        capabilities);
            } catch (Exception ex) {
                logger.error("" + ex);
            }
        } else if (SAFARI.equalsIgnoreCase(runBrowser)) {
            if (SeleniumUtil.isWindows() || SeleniumUtil.isMac()) {
                driver = new SafariDriver();
            } else {
                driver = new FirefoxDriver();
            }
        } else {
            driver = new HtmlUnitDriver();
        }
        driver.manage().window().maximize();
        System.out.println("returning driver");
        return driver;
    }
}
