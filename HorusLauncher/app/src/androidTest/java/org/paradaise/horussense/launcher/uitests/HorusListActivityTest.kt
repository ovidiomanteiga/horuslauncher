
package org.paradaise.horussense.launcher.uitests

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiScrollable
import android.support.test.uiautomator.UiSelector
import android.support.v7.widget.RecyclerView
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.mockito.MockitoAnnotations
import org.paradaise.horussense.launcher.composition.MainFactory
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.domain.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.ui.HorusListActivity
import java.util.*


/**
 * UI test to check Horus list presentation.
 */
@RunWith(AndroidJUnit4::class)
class HorusListActivityTest() {

	// region Properties

	private lateinit var mDevice: UiDevice
	private lateinit var appContext: Context
	private val appPackageName: String
		get() = this.appContext.packageName

	@Mock
	private lateinit var factory: MainFactory
	@Mock
	private lateinit var interactor: GetHorusListInteractor
	@Mock
	private lateinit var action1: HorusAction
	@Mock
	private lateinit var action2: HorusAction
	@Mock
	private lateinit var action3: HorusAction

	// endregion
	// region Setup

	@get:Rule
	var mActivityRule = ActivityTestRule(HorusListActivity::class.java)


	init {
		MockitoAnnotations.initMocks(this)
		MainInjector.setFactory(this.factory)
		`when`(this.action1.name).thenReturn("Calendar")
		`when`(this.action1.url).thenReturn(UUID.randomUUID().toString())
		`when`(this.action2.name).thenReturn("Phone")
		`when`(this.action2.url).thenReturn(UUID.randomUUID().toString())
		`when`(this.action3.name).thenReturn("Facebook")
		`when`(this.action3.url).thenReturn(UUID.randomUUID().toString())
		val horusList = listOf(this.action1, this.action2, this.action3)
		doNothing().`when`(this.interactor).perform()
		`when`(this.interactor.horusList).thenReturn(horusList)
		`when`(this.factory.provideGetHorusListInteractor()).thenReturn(this.interactor)
	}


	@Before
	fun setup() {
		this.mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
		this.appContext = InstrumentationRegistry.getTargetContext()
	}


	@After
	fun tearDown() {
		MainInjector.resetFactory()
	}

	// endregion
	// region Tests

	@Test
	fun testSomePredictedApps() {
		val items = UiScrollable(UiSelector().className(RecyclerView::class.java))
		val horusItemRID = this.appPackageName + ":id/horusItem"
		val textViewRID = this.appPackageName + ":id/titleView"
		val count = items.childCount
		val item0 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 0)
		val item0Title = item0.getChild(UiSelector().resourceId(textViewRID))
		val item1 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 1)
		val item1Title = item1.getChild(UiSelector().resourceId(textViewRID))
		val item2 = items.getChildByInstance(UiSelector().resourceId(horusItemRID), 2)
		val item2Title = item2.getChild(UiSelector().resourceId(textViewRID))
		assertTrue(count > 0)
		assertEquals("Calendar", item0Title.text)
		assertEquals("Phone", item1Title.text)
		assertEquals("Facebook", item2Title.text)
	}

	// endregion

}
