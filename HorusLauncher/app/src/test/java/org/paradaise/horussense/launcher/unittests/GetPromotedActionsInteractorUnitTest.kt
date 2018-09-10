
package org.paradaise.horussense.launcher.unittests

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.action.PromotedAction
import org.paradaise.horussense.launcher.domain.promoted_actions.GetPromotedActionsInteractor
import org.paradaise.horussense.launcher.domain.promoted_actions.PromotedActionsService
import org.paradaise.horussense.launcher.domain.user_profile.UserProfileVO


/**
 * Tests related to the promoted actions feature.
 *
 * See [feature #29 @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/29).
 */
@RunWith(MockitoJUnitRunner::class)
class GetPromotedActionsInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: GetPromotedActionsInteractor

	@Mock
	private lateinit var promotedAction1: PromotedAction
	@Mock
	private lateinit var promotedAction2: PromotedAction
	@Mock
	private lateinit var service: PromotedActionsService

	// endregion
	// region Setup

	@Before
	fun setUp() {
	}

	// endregion
	// region Tests

	@Test
	fun testNoPromotedActions() {
		// Arrange
		// Act
		this.interactor.perform()
		// Assert
		val promotedActions = this.interactor.actions
		assertEquals(0, promotedActions.size)
	}


	@Test
	fun testSomePromotedActions() {
		// Arrange
		val profile = Mockito.any<UserProfileVO>()
		val expectedPromotedActions = listOf(
				this.promotedAction1,
				this.promotedAction2
		)
		Mockito.`when`(this.service.getPromotedActionsFor(profile))
				.thenReturn(expectedPromotedActions)
		// Act
		this.interactor.perform()
		// Assert
		val promotedActions = this.interactor.actions
		assertEquals(expectedPromotedActions, promotedActions)
	}

	// endregion

}
