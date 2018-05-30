
package org.paradaise.horussense.launcher


import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.paradaise.horussense.launcher.ui.MainActivity
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import org.junit.Before



/**
 * UI test to check all apps are presented on screen.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

	// region Properties

	private lateinit var mDevice: UiDevice

	// endregion
	// region Setup

	@get:Rule
	var mActivityRule = ActivityTestRule(MainActivity::class.java)


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(getInstrumentation())
	}

	// endregion
	// region Tests

	@Test
	fun useAppContext() {
		val appContext = InstrumentationRegistry.getTargetContext()
		assertEquals("org.paradaise.horussense.launcher", appContext.packageName)
	}


	@Test
	fun checkListItems() {
		val actionItems = this.mDevice.findObjects(By.res("android:id/text1"))
		assertEquals(3, actionItems?.size ?: 0)
		val expectedList = listOf("Facebook", "Google", "WhatsApp")
		val actualList = actionItems.map { it.text }
		assertEquals(expectedList, actualList)
	}

	// endregion

}
