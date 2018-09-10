
package org.paradaise.horussense.launcher.integrationtests

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.paradaise.horussense.launcher.domain.action_execution.ActionExecutionVO
import org.paradaise.horussense.launcher.domain.all_actions.AllActionsRepository
import org.paradaise.horussense.launcher.domain.action.HorusAction
import org.paradaise.horussense.launcher.infrastructure.action_execution.DBActionExecutionRepository
import org.paradaise.horussense.launcher.infrastructure.database.LocalDatabase
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class DBActionExecutionRepositoryTest {

	// region Properties

	@Mock
	private lateinit var action: HorusAction
	@Mock
	private lateinit var execution: ActionExecutionVO
	@Mock
	private lateinit var allActionsRepository: AllActionsRepository

	private lateinit var db: LocalDatabase
	private lateinit var repository: DBActionExecutionRepository

	// endregion
	// region Setup

	@Before
	fun setup() {
		MockitoAnnotations.initMocks(this)
		val context = InstrumentationRegistry.getTargetContext()
		this.db = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
		this.repository = DBActionExecutionRepository(this.allActionsRepository, this.db)
	}


	@After
	@Throws(IOException::class)
	fun tearDown() {
		this.db.close()
	}

	// endregion
	// region Tests

	@Test
	@Throws(Exception::class)
	fun testActionExecutionAdded() {
		// Arrange
		`when`(this.action.url).thenReturn(UUID.randomUUID().toString())
		`when`(this.execution.action).thenReturn(this.action)
		`when`(this.execution.moment).thenReturn(Calendar.getInstance().time)
		// Act
		this.repository.add(this.execution)
		val actionExecutionDTO = this.db.actionExecutionDAO().all[0]
		// Assert
		assertThat(this.execution.action.url, equalTo(actionExecutionDTO.url))
		assertThat(this.execution.moment, equalTo(actionExecutionDTO.moment))
	}


	@Test
	@Throws(Exception::class)
	fun testManyActionExecutionsAdded() {
		// Arrange
		`when`(this.action.url).thenReturn(UUID.randomUUID().toString())
		`when`(this.execution.action).thenReturn(this.action)
		`when`(this.execution.moment).thenReturn(Calendar.getInstance().time)
		val numberOfExecutions = 10
		// Act
		(1..numberOfExecutions).forEach {
			this.repository.add(this.execution)
		}
		val actionExecutionCount = this.db.actionExecutionDAO().all.size
		// Assert
		assertThat(actionExecutionCount, equalTo(numberOfExecutions))
	}

	// endregion

}
