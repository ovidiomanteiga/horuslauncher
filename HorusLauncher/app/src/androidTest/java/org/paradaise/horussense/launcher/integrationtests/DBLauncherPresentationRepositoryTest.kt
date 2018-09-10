
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
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationResult
import org.paradaise.horussense.launcher.domain.stats.LauncherPresentationVO
import org.paradaise.horussense.launcher.infrastructure.stats.DBLauncherPresentationRepository
import org.paradaise.horussense.launcher.infrastructure.database.LocalDatabase
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class DBLauncherPresentationRepositoryTest {

	// region Properties

	private lateinit var repository: DBLauncherPresentationRepository

	private lateinit var db: LocalDatabase

	@Mock
	private lateinit var presentationVO: LauncherPresentationVO

	// endregion
	// region Setup

	@Before
	fun setup() {
		MockitoAnnotations.initMocks(this)
		val context = InstrumentationRegistry.getTargetContext()
		this.db = Room.inMemoryDatabaseBuilder(context, LocalDatabase::class.java).build()
		this.repository = DBLauncherPresentationRepository(this.db)
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
	fun testLauncherPresentationAdded() {
		// Arrange
		val random = Random()
		val time = Calendar.getInstance().time
		val millis = random.nextLong()
		val launcherResults = LauncherPresentationResult.values()
		val resultIndex = random.nextInt(launcherResults.size)
		val result = launcherResults[resultIndex]
		`when`(this.presentationVO.actionTime).thenReturn(time)
		`when`(this.presentationVO.launchTime).thenReturn(time)
		`when`(this.presentationVO.millisecondsTakenToGetHorusList).thenReturn(millis)
		`when`(this.presentationVO.result).thenReturn(result)
		// Act
		this.repository.add(this.presentationVO)
		val presentationDTO = this.db.launcherPresentationDAO().all.first()
		// Assert
		assertThat(time, equalTo(presentationDTO.actionTime))
		assertThat(time, equalTo(presentationDTO.launchTime))
		assertThat(millis, equalTo(presentationDTO.millisecondsGettingHorusList))
		assertThat(result.name, equalTo(presentationDTO.result))
	}


	@Test
	@Throws(Exception::class)
	fun testManyActionExecutionsAdded() {
		// Arrange
		val numberOfInsertions = 10
		// Act
		(1..numberOfInsertions).forEach {
			this.repository.add(this.presentationVO)
		}
		// Assert
		val launcherPresentationCount = this.db.launcherPresentationDAO().all.size
		assertThat(launcherPresentationCount, equalTo(numberOfInsertions))
	}

	// endregion

}
