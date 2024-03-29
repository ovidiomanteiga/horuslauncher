
package org.paradaise.horussense.launcher.unittests


import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionVO
import org.paradaise.horussense.launcher.domain.action_execution.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository


/**
 * Tests related to the execute action feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/33).
 */
@RunWith(MockitoJUnitRunner::class)
class ExecuteActionInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: ExecuteActionInteractor

	@Mock
	private lateinit var action: HorusAction

	@Mock
	private lateinit var repository: ActionExecutionRepository

	@Mock
	private lateinit var launcherPresentationRepository: LauncherPresentationRepository

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
		val argument = ArgumentCaptor.forClass(ActionExecutionVO::class.java)
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
