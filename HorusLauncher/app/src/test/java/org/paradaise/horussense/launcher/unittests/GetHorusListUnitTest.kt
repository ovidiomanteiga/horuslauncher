
package org.paradaise.horussense.launcher.unittests

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import java.util.*
import java.util.UUID.*


/**
 * Tests related to the predict actions feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/25).
 */
@RunWith(MockitoJUnitRunner::class)
class GetHorusListUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: GetHorusListInteractor

	@Mock
	private lateinit var repository: ActionExecutionRepository
	@Mock
	private lateinit var action1: HorusAction
	@Mock
	private lateinit var action2: HorusAction
	@Mock
	private lateinit var action3: HorusAction

	// endregion
	// region Setup

	@Before
	fun setUp() {
		`when`(this.action1.url).thenReturn(randomUUID().toString())
		`when`(this.action2.url).thenReturn(randomUUID().toString())
		`when`(this.action3.url).thenReturn(randomUUID().toString())
	}

	// endregion
	// region Tests

	@Test
	fun testEmptyHorusList() {
		// Arrange
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(0, predictedActions.size)
	}


	@Test
	fun testSomePredictedActions() {
		// Arrange
		val now = Calendar.getInstance().time
		val executions = listOf(
				ActionExecution(this.action1, moment = now),
				ActionExecution(this.action2, moment = now),
				ActionExecution(this.action3, moment = now)
		)
		Mockito.`when`(this.repository.all).thenReturn(executions)
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(3, predictedActions.size)
	}


	@Test
	fun testPredictedActionsJustLastWeek() {
		// Arrange
		val now = Calendar.getInstance().time
		var momentMoreThanOneWeekAgo = Calendar.getInstance()
		momentMoreThanOneWeekAgo.add(Calendar.DATE, -10)
		val executions = listOf(
				ActionExecution(this.action1, moment = now),
				ActionExecution(this.action2, moment = momentMoreThanOneWeekAgo.time),
				ActionExecution(this.action3, moment = now)
		)
		Mockito.`when`(this.repository.all).thenReturn(executions)
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(2, predictedActions.size)
	}


	@Test
	fun testPredictedActionsOrderedByFrequency() {
		// Arrange
		val now = Calendar.getInstance().time
		val action2times = this.action2
		val action1time = this.action1
		val executions = listOf(
				ActionExecution(action2times, moment = now),
				ActionExecution(action1time, moment = now),
				ActionExecution(action2times, moment = now)
		)
		Mockito.`when`(this.repository.all).thenReturn(executions)
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(2, predictedActions.size)
		assertEquals(action2times, predictedActions[0].action)
		assertEquals(action1time, predictedActions[1].action)
	}

	// endregion

}
