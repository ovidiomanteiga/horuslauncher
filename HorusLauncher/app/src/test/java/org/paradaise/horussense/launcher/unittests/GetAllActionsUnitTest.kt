
package org.paradaise.horussense.launcher.unittests


import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.*


/**
 * Tests related to the get all apps feature.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/16).
 */
@RunWith(MockitoJUnitRunner::class)
class GetAllActionsUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: GetAllActionsInteractor

	private lateinit var facebookAction: HorusAction
	private lateinit var googleAction: HorusAction
	private lateinit var someActions: AllActions
	private lateinit var whatsAppAction: HorusAction

	@Mock
	private lateinit var repository: AllActionsRepository

	// endregion
	// region Setup

    @Before
    fun setUp() {
	    this.facebookAction = HorusAction(name = "Facebook")
	    this.googleAction = HorusAction(name = "Google")
	    this.whatsAppAction = HorusAction(name = "WhatsApp")
	    this.someActions = listOf(this.facebookAction, this.googleAction, this.whatsAppAction)
    }

	// endregion
	// region Tests

    @Test
    fun testNoActionsAvailable() {
        this.interactor.perform()
        val allActions = this.interactor.allActions
        assertEquals(0, allActions.size)
    }


    @Test
    fun testSomeActionsAvailable() {
	    val actions = listOf(HorusAction())
	    `when`(this.repository.get()).thenReturn(actions)
        this.interactor.perform()
        val allActions = this.interactor.allActions
        assertEquals(1, allActions.size)
    }


	@Test
	fun testManyActionsAvailable() {
		val actions = this.someActions
		`when`(this.repository.get()).thenReturn(actions)
		this.interactor.perform()
		val allActions = this.interactor.allActions
		assertEquals(actions.size, allActions.size)
	}


	@Test
	fun testDefaultAvailableActionsOrdering() {
		val actions = listOf(this.whatsAppAction, this.googleAction, this.facebookAction)
		`when`(this.repository.get()).thenReturn(actions)
		this.interactor.perform()
		val allActions = this.interactor.allActions
		assertEquals(this.someActions, allActions)
	}


	@Test
	fun testGetPagedAvailableActions() {
		val actions = this.someActions
		`when`(this.repository.get()).thenReturn(actions)
		this.interactor.paging = Paging(startIndex = 0, maxItems = 2)
		this.interactor.perform()
		val allActions = this.interactor.allActions
		assertEquals(actions.take(2), allActions)
	}

	// endregion

}
