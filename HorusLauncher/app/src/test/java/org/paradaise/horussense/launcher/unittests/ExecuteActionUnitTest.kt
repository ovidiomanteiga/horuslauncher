
package org.paradaise.horussense.launcher.unittests


import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.HorusAction


/**
 * Tests related to the execute action feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16).
 */
@RunWith(MockitoJUnitRunner::class)
class ExecuteActionUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: ExecuteActionInteractor

	@Mock
	private lateinit var action: HorusAction

	@Mock
	private lateinit var repository: ActionExecutionRepository

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
	fun testActionExecutedAndLogged() {
		// Arrange
		this.interactor.action = this.action
		// Act
		this.interactor.perform()
		// Assert
		verify(this.action, times(1)).perform()
		val argument = ArgumentCaptor.forClass(ActionExecution::class.java)
		verify(this.repository, times(1)).add(argument.capture())
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
		verify(this.repository, times(0)).add(Matchers.any())
	}


	// endregion

}
