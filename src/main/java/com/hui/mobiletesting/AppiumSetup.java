package com.hui.mobiletesting;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class AppiumSetup {
	private static final String URL = "http://127.0.0.1:4723/wd/hub";
	String platformVersion;
	String udid;

	public AppiumSetup(String platformVersion, String udid) {
		this.platformVersion = platformVersion;
		this.udid = udid;

	}

	public WebDriver getConnectFactory() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", udid);
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("appPackage", "com.tencent.mm");
		capabilities.setCapability("appActivity", "com.tencent.mm.ui.LauncherUI");
		capabilities.setCapability("app", "C:\\my\\soft\\weixin_1420.apk");//安装apk
		if (udid != null) {
			capabilities.setCapability("udid", udid);
		}
		capabilities.setCapability("noReset", true); // 不要在会话前重置应用状态
		capabilities.setCapability("fullReset", false); // Android是否删除应用，IOS是否删除整个模拟器目录
		capabilities.setCapability("sessionOverride", true);
		// 输入配置
		capabilities.setCapability("unicodeKeyboard", true); // 是否启动Unicode输入法
		capabilities.setCapability("resetKeyboard", false); // 结束后是否切换回默认输入法
		// H5驱动关键，不配置webview会被识别成com.tencent.mm的webview，不是com.tencent.mm:tools的
		ChromeOptions op = new ChromeOptions();
		op.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
		capabilities.setCapability(ChromeOptions.CAPABILITY, op);

		AndroidDriver<?> driver = new AndroidDriver(new URL(URL), capabilities);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

}
