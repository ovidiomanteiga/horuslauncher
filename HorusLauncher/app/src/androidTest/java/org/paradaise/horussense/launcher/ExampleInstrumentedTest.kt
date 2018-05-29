package org.paradaise.horussense.launcher

import android.arch.lifecycle.AndroidViewModel
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.Matchers.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.paradaise.horussense.launcher.ui.MainActivity



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

	@get:Rule
	var mActivityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

	@Test
	fun useAppContext() {
		// Context of the app under test.
		val appContext = InstrumentationRegistry.getTargetContext()
		assertEquals("org.paradaise.horussense.launcher", appContext.packageName)
	}

	@Test
	fun changeTab() {
		onData(allOf<AndroidViewModel>(`is`(instanceOf(AndroidViewModel::class.java))))
	}

}
