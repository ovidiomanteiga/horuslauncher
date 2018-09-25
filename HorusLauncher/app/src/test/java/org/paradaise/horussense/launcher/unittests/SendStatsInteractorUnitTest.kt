
package org.paradaise.horussense.launcher.unittests

import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.stats.GetStatsInteractor
import org.paradaise.horussense.launcher.domain.stats.LauncherStatsVO
import org.paradaise.horussense.launcher.domain.stats.SendStatsInteractor
import org.paradaise.horussense.launcher.domain.stats.StatsService


/**
 * Tests related to sending Horus Launcher performance stats.
 *
 * See [user story #176 @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/176).
 */
@RunWith(MockitoJUnitRunner::class)
class SendStatsInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: SendStatsInteractor


	@Mock
	private lateinit var getStatsInteractor: GetStatsInteractor
	@Mock
	private lateinit var service: StatsService
	@Mock
	private lateinit var stats: LauncherStatsVO

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
		verify(this.getStatsInteractor, times(1)).perform()
		verify(this.service, never()).send(ArgumentMatchers.any())
	}

	@Test
	fun testSendStats() {
		// Arrange
		`when`(this.getStatsInteractor.stats).thenReturn(this.stats)
		// Act
		this.interactor.perform()
		// Assert
		verify(this.getStatsInteractor, times(1)).perform()
		val captor = ArgumentCaptor.forClass(LauncherStatsVO::class.java)
		verify(this.service, times(1)).send(captor.capture())
		Assert.assertEquals(this.stats, captor.value)
	}

}
