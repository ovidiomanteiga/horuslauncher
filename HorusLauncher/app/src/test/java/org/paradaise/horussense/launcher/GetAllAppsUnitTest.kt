package org.paradaise.horussense.launcher


import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository


/**
 * Tests related to the get all apps feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16).
 */
@RunWith(MockitoJUnitRunner::class)
class GetAllAppsUnitTest {

	// region Properties

	private lateinit var interactor: GetAllActionsInteractor

	@Mock
	private lateinit var repository: AllAppsRepository

	// endregion
	// region Setup

    @Before
    fun setUp() {
        this.interactor = GetAllActionsInteractor(repository = this.repository)
    }

	// endregion
	// region Tests

    @Test
    fun noAppsAvailable() {
        this.interactor.perform()
        val allApps = this.interactor.allApps
        assertEquals(0, allApps.size)
    }


    @Test
    fun someAppsAvailable() {
	    val actions = listOf(HorusAction())
	    `when`(this.repository.get()).thenReturn(actions)
        this.interactor.perform()
        val allApps = this.interactor.allApps
        assertEquals(1, allApps.size)
    }

	// endregion

}
