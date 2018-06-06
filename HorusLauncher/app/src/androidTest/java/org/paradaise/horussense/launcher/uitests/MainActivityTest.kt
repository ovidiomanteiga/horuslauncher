
package org.paradaise.horussense.launcher.uitests


import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.By
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import android.support.v7.widget.RecyclerView
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.paradaise.horussense.launcher.ui.MainActivity



/**
 * UI test to check all apps are presented on screen.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

	// region Properties

	private lateinit var mDevice: UiDevice

	private lateinit var appContext: Context

	private val appPackageName: String
		get() = this.appContext.packageName

	// endregion
	// region Setup

	@get:Rule
	var mActivityRule = ActivityTestRule(MainActivity::class.java)


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(getInstrumentation())
		this.appContext = InstrumentationRegistry.getTargetContext()
	}

	// endregion
	// region Tests

	@Test
	fun useAppContext() {
		assertEquals("org.paradaise.horussense.launcher", this.appPackageName)
	}


	@Test
	fun checkAppCount() {
		val resourceId = this.appPackageName + ":id/textView"
		val actionItems =  this.mDevice.findObjects(By.res(resourceId))
		val count = actionItems?.size ?: 0
		assertTrue(count == 20)
	}


	@Test
	fun checkCertainAppsArePresent() {
		val resourceId = this.appPackageName + ":id/textView"
		val someExpectedActions = listOf("Calculator", "Calendar", "Camera",
				"Chrome", "Clock", "Contacts", "Maps", "Phone", "YouTube")
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		someExpectedActions.forEach {
			val item = items.getChildByText(UiSelector().resourceId(resourceId), it)
			assertNotNull(it, item)
		}
	}


	@Test
	fun checkAppsOrdering() {
		val resourceId = this.appPackageName + ":id/textView"
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		var instance = 0
		var apps = ArrayList<String>()
		while (true) {
			try {
				val app = items.getChildByInstance(UiSelector().resourceId(resourceId), instance)
				apps.add(app.text)
				instance++
			} catch (e: Exception) {
				break
			}
		}
		assertEquals(apps, apps.sorted())
	}

	// endregion

}
