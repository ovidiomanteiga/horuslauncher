package org.paradaise.horussense.launcher


import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.AllActionsRepository
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.HorusAction


/**
 * Tests related to the get all apps feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16).
 */
@RunWith(MockitoJUnitRunner::class)
class GetAllActionsUnitTest {

	// region Properties

	private lateinit var interactor: GetAllActionsInteractor

	@Mock
	private lateinit var repository: AllActionsRepository

	// endregion
	// region Setup

    @Before
    fun setUp() {
        this.interactor = GetAllActionsInteractor(repository = this.repository)
    }

	// endregion
	// region Tests

    @Test
    fun noActionsAvailable() {
        this.interactor.perform()
        val allActions = this.interactor.allActions
        assertEquals(0, allActions.size)
    }


    @Test
    fun someActionsAvailable() {
	    val actions = listOf(HorusAction())
	    `when`(this.repository.get()).thenReturn(actions)
        this.interactor.perform()
        val allActions = this.interactor.allActions
        assertEquals(1, allActions.size)
    }


	@Test
	fun manyActionsAvailable() {
		val actions = listOf(
				HorusAction(name = "Facebook"),
				HorusAction(name = "Google"),
				HorusAction(name = "WhatsApp"))
		`when`(this.repository.get()).thenReturn(actions)
		this.interactor.perform()
		val allActions = this.interactor.allActions
		assertEquals(3, allActions.size)
	}


	@Test
	fun defaultAvailableActionsOrdering() {
		val facebook = HorusAction(name = "Facebook")
		val google = HorusAction(name = "Google")
		val whatsApp = HorusAction(name = "WhatsApp")
		val actions = listOf(whatsApp, google, facebook)
		`when`(this.repository.get()).thenReturn(actions)
		this.interactor.perform()
		val allActions = this.interactor.allActions
		assertEquals(allActions, listOf(facebook, google, whatsApp))
	}

	// endregion

}
