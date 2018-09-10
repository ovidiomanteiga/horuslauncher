
package org.paradaise.horussense.launcher.unittests

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationResult.*
import org.paradaise.horussense.launcher.domain.stats.GetStatsInteractor
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationRepository
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationResult
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationVO
import org.paradaise.horussense.launcher.domain.time.Milliseconds
import java.util.*


/**
 * Tests related to getting Horus Launcher performance stats.
 *
 * See [user story #175 @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/175).
 */
@RunWith(MockitoJUnitRunner::class)
class GetStatsInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: GetStatsInteractor

	@Mock
	private lateinit var repository: LauncherPresentationRepository

	// endregion
	// region Setup

	@Before
	fun setUp() {
	}

	// endregion
	// region Tests

	@Test
	fun testNoStats() {
		// Arrange
		// Act
		this.interactor.perform()
		// Assert
		val stats = this.interactor.stats
		assertNotNull(stats)
		assertNull(stats!!.averageActionTime)
		assertNull(stats.averageTimeGettingHorusList)
		assertNull(stats.horusListAccuracy)
	}

	@Test
	fun testStatsWithJust1Presentation() {
		// Arrange
		val presentation1 = LauncherPresentationVO()
		val launchTimeCalendar = Calendar.getInstance()
		val launchTime = launchTimeCalendar.time
		launchTimeCalendar.add(Calendar.SECOND, 1)
		val actionTime = launchTimeCalendar.time
		presentation1.launchTime = launchTime
		presentation1.actionTime = actionTime
		presentation1.millisecondsTakenToGetHorusList = 444
		presentation1.result = PERFORMED_PREDICTED_ACTION
		val all = listOf(presentation1)
		Mockito.`when`(this.repository.all).thenReturn(all)
		// Act
		this.interactor.perform()
		// Assert
		val stats = this.interactor.stats
		assertNotNull(stats)
		assertEquals(1.0, stats!!.averageActionTime)
		assertEquals(444L, stats.averageTimeGettingHorusList)
		assertEquals(100.0, stats.horusListAccuracy)
	}

	@Test
	fun testStatsWithJustSomePresentations() {
		// Arrange
		val presentation1 = this.getLauncherPresentationMock(1, 100, PERFORMED_PREDICTED_ACTION)
		val presentation2 = this.getLauncherPresentationMock(3, 300, NO_ACTION_PERFORMED)
		val all = listOf(presentation1, presentation2)
		Mockito.`when`(this.repository.all).thenReturn(all)
		// Act
		this.interactor.perform()
		// Assert
		val stats = this.interactor.stats
		assertNotNull(stats)
		assertEquals(2.0, stats!!.averageActionTime)
		assertEquals(200L, stats.averageTimeGettingHorusList)
		assertEquals(50.0, stats.horusListAccuracy)
	}


	// endregion
	// region Private Methods

	private fun getLauncherPresentationMock(actionTimeDifference: Int,
	                                        millisecondsTakenToGetHorusList: Milliseconds,
	                                        result: LauncherPresentationResult):
			LauncherPresentationVO
	{
		val presentationVO = LauncherPresentationVO()
		val launchTimeCalendar = Calendar.getInstance()
		val launchTime = launchTimeCalendar.time
		launchTimeCalendar.add(Calendar.SECOND, actionTimeDifference)
		val actionTime = launchTimeCalendar.time
		presentationVO.launchTime = launchTime
		presentationVO.actionTime = actionTime
		presentationVO.millisecondsTakenToGetHorusList = millisecondsTakenToGetHorusList
		presentationVO.result = result
		return presentationVO
	}

	// endregion

}
