
package org.paradaise.horussense.launcher.unittests


import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.*
import org.paradaise.horussense.launcher.domain.LauncherPresentationResult.*
import java.util.*


/**
 * Tests related to the launcher presentation manager.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/70).
 */
@RunWith(MockitoJUnitRunner::class)
class LauncherPresentationManagerUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var manager: LauncherPresentationManager

	@Mock
	private lateinit var calendar: Calendar

	private lateinit var actionTime: Date
	private lateinit var launchTime: Date

	// endregion
	// region Setup

    @Before
    fun setUp() {
    }

	// endregion
	// region Tests

    @Test
    fun testStart() {
	    // Arrange
	    this.launchTime = Calendar.getInstance().time
	    `when`(this.calendar.timeInMillis).thenReturn(this.launchTime.time)
	    // Act
	    this.manager.start()
	    // Assert
	    val actual = this.manager.current
	    assertNotNull(actual)
	    assertEquals(this.launchTime, actual?.launchTime)
	    assertNull(actual?.actionTime)
	    assertNull(actual?.result)
    }

	@Test
	fun testFinish() {
		// Arrange
		this.launchTime = Calendar.getInstance().time
		`when`(this.calendar.timeInMillis).thenReturn(this.launchTime.time)
		this.manager.start()
		this.actionTime = Calendar.getInstance().time
		`when`(this.calendar.timeInMillis).thenReturn(this.actionTime.time)
		// Act
		val actual = this.manager.finish()
		// Assert
		assertNotNull(actual)
		assertEquals(this.launchTime, actual?.launchTime)
		assertEquals(this.actionTime, actual?.actionTime)
		assertEquals(NO_ACTION_PERFORMED, actual?.result)
	}

	@Test(expected = Exception::class)
	fun testFinishBeforeStart() {
		// Arrange
		// Act
		this.manager.finish()
		// Assert
	}

	@Test
	fun testActionPerformed() {
		// Arrange
		this.manager.start()
		// Act
		this.manager.notifyActionPerformed()
		// Assert
		val actual = this.manager.current
		assertNotNull(actual)
		assertEquals(PERFORMED_ACTION, actual?.result)
	}

	@Test
	fun testPredictedActionPerformed() {
		// Arrange
		this.manager.start()
		// Act
		this.manager.notifyPredictedActionPerformed()
		// Assert
		val actual = this.manager.current
		assertNotNull(actual)
		assertEquals(PERFORMED_PREDICTED_ACTION, actual?.result)
	}

	@Test
	fun testPromotedActionPerformed() {
		// Arrange
		this.manager.start()
		// Act
		this.manager.notifyPromotedActionPerformed()
		// Assert
		val actual = this.manager.current
		assertNotNull(actual)
		assertEquals(PERFORMED_PROMOTED_ACTION, actual?.result)
	}

	// endregion

}
