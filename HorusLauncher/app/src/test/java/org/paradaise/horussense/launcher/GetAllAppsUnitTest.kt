package org.paradaise.horussense.launcher


import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner


/**
 * Tests related to the get all apps feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16).
 */
@RunWith(MockitoJUnitRunner::class)
class GetAllAppsUnitTest {

	// region Properties

	private lateinit var interactor: GetAllAppsInteractor

	@Mock
	private lateinit var service: AllAppsService

	// endregion
	// region Setup

    @Before
    fun setUp() {
        this.interactor = GetAllAppsInteractor(service = this.service)
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
	    `when`(this.service.get()).thenReturn(listOf("Google"))
        this.interactor.perform()
        val allApps = this.interactor.allApps
        assertEquals(1, allApps.size)
    }

	// endregion

}
