
package org.paradaise.horussense.launcher.unittests

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.*


/**
 * Tests related to the execution of a promoted action.
 *
 * See [story @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/38).
 */
@RunWith(MockitoJUnitRunner::class)
class ExecutePromotedActionUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: ExecutePromotedAction

	@Mock
	private lateinit var action: PromotedAction

	@Mock
	private lateinit var service: PromotedActionsService

	// endregion
	// region Setup

    @Before
    fun setUp() { }

	// endregion
	// region Tests

    @Test
    fun testActionExecutedCorrectly() {
	    // Arrange
	    this.interactor.action = this.action
	    // Act
	    this.interactor.perform()
	    // Assert
        verify(this.action, times(1)).perform()
    }


	@Test
	fun testActionExecutedAndNotified() {
		// Arrange
		this.interactor.action = this.action
		// Act
		this.interactor.perform()
		// Assert
		verify(this.action, times(1)).perform()
		val argument = ArgumentCaptor.forClass(PromotedActionExecution::class.java)
		verify(this.service, times(1))
				.notifyActionExecuted(argument.capture())
		assertEquals(this.action, argument.value.action)
	}


	@Test
	fun testNullAction() {
		// Arrange
		this.interactor.action = null
		// Act
		this.interactor.perform()
		// Assert
		verify(this.action, times(0)).perform()
		verify(this.service, never()).notifyActionExecuted(any())
	}

	// endregion

}
