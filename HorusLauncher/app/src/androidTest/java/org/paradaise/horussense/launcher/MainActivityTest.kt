
package org.paradaise.horussense.launcher


import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.paradaise.horussense.launcher.ui.MainActivity
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.*
import android.support.v7.widget.RecyclerView
import org.junit.Assert.*
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
		val resourceId = "android:id/text1"
		val actionItems =  this.mDevice.findObjects(By.res(resourceId))
		val count = actionItems?.size ?: 0
		assertTrue(count > 10)
		val someExpectedActions = listOf("Calculator", "Calendar", "Camera",
				"Chrome", "Clock", "Contacts")
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		someExpectedActions.forEach {
			val item = items.getChildByText(UiSelector().resourceId(resourceId), it)
			assertNotNull(it, item)
		}
	}

	// endregion

}
