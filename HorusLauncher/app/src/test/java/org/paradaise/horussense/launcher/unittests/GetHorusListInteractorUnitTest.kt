
package org.paradaise.horussense.launcher.unittests

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionRepository
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionVO
import org.paradaise.horussense.launcher.domain.factory.DomainFactory
import org.paradaise.horussense.launcher.domain.horus_list.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationManager
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import java.util.*
import java.util.UUID.*


/**
 * Tests related to the predict actions feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/25).
 */
@RunWith(MockitoJUnitRunner::class)
class GetHorusListInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: GetHorusListInteractor

	@Mock
	private lateinit var actionExecutionRepository: ActionExecutionRepository
	@Mock
	private lateinit var launcherPresentationRepository: LauncherPresentationRepository
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
				ActionExecutionVO(this.action1, moment = now),
				ActionExecutionVO(this.action2, moment = now),
				ActionExecutionVO(this.action3, moment = now)
		)
		Mockito.`when`(this.actionExecutionRepository.all).thenReturn(executions)
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
		val momentMoreThanOneWeekAgo = Calendar.getInstance()
		momentMoreThanOneWeekAgo.add(Calendar.DATE, -10)
		val executions = listOf(
				ActionExecutionVO(this.action1, moment = now),
				ActionExecutionVO(this.action2, moment = momentMoreThanOneWeekAgo.time),
				ActionExecutionVO(this.action3, moment = now)
		)
		Mockito.`when`(this.actionExecutionRepository.all).thenReturn(executions)
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
				ActionExecutionVO(action2times, moment = now),
				ActionExecutionVO(action1time, moment = now),
				ActionExecutionVO(action2times, moment = now)
		)
		Mockito.`when`(this.actionExecutionRepository.all).thenReturn(executions)
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(2, predictedActions.size)
		assertEquals(action2times, predictedActions[0].action)
		assertEquals(action1time, predictedActions[1].action)
	}


	@Test
	fun testPredictedActionsOrderedByFrequencyAndLastExecution() {
		// Arrange
		val now = Calendar.getInstance().time
		val yesterdayCalendar = Calendar.getInstance()
		yesterdayCalendar.add(Calendar.DATE, -1)
		val yesterday = yesterdayCalendar.time
		val actionTwice = this.action2
		val actionOnceYesterday = this.action1
		val actionOnceNow = this.action3
		val executions = listOf(
				ActionExecutionVO(actionTwice, moment = now),
				ActionExecutionVO(actionOnceYesterday, moment = yesterday),
				ActionExecutionVO(actionTwice, moment = now),
				ActionExecutionVO(actionOnceNow, moment = now)
		)
		Mockito.`when`(this.actionExecutionRepository.all).thenReturn(executions)
		// Act
		this.interactor.perform()
		// Assert
		val predictedActions = this.interactor.horusList
		assertEquals(3, predictedActions.size)
		assertEquals(actionTwice, predictedActions[0].action)
		assertEquals(actionOnceNow, predictedActions[1].action)
		assertEquals(actionOnceYesterday, predictedActions[2].action)
	}


	@Test
	fun testMeasureTimeTakenGettingHorusList() {
		// Arrange
		val factory = mock(DomainFactory::class.java)
		val manager = mock(LauncherPresentationManager::class.java)
		`when`(factory.provideLauncherPresentationManager()).thenReturn(manager)
		DomainFactory.current = factory
		// Act
		this.interactor.perform()
		// Assert
		verify(manager, times(1))
				.notifyMillisecondsTakenToGetHorusList(ArgumentMatchers.anyLong())
	}

	// endregion

}
