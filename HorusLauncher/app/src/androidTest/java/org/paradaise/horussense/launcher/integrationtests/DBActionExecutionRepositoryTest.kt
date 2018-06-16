
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
import org.paradaise.horussense.launcher.domain.ActionExecution
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.infrastructure.DBActionExecutionRepository
import org.paradaise.horussense.launcher.infrastructure.LocalDatabase
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class DBActionExecutionRepositoryTest {

	// region Properties

	@Mock
	private lateinit var action: HorusAction
	@Mock
	private lateinit var execution: ActionExecution

	private lateinit var db: LocalDatabase
	private lateinit var repository: DBActionExecutionRepository

	// endregion
	// region Setup

	@Before
	fun setup() {
		val context = InstrumentationRegistry.getTargetContext()
		this.db = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
		this.repository = DBActionExecutionRepository(this.db)
		MockitoAnnotations.initMocks(this)
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
