
package org.paradaise.horussense.launcher.unittests


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import org.paradaise.horussense.launcher.domain.*


/**
 * Tests related to the device locking domain interactor.
 *
 * See [feature @ VSTS](https://lateaint.visualstudio.com/HorusSense/_workitems/edit/70).
 */
@RunWith(MockitoJUnitRunner::class)
class DeviceLockingInteractorUnitTest {

	// region Properties

	@InjectMocks
	private lateinit var interactor: DeviceLockingInteractor

	@Mock
	private lateinit var manager: LauncherPresentationManager

	@Mock
	private lateinit var presentation: LauncherPresentationVO

	@Mock
	private lateinit var repository: LauncherPresentationRepository

	// endregion
	// region Setup

    @Before
    fun setUp() {
	    `when`(this.manager.current).thenReturn(this.presentation)
    }

	// endregion
	// region Tests

    @Test
    fun testDeviceUnlocking() {
	    // Arrange
	    this.interactor.locking = false
	    // Act
	    this.interactor.perform()
	    // Assert
	    val manager = this.interactor.launcherPresentationManager
	    verify(manager, times(1))?.start()
    }

	@Test
	fun testDeviceLocking() {
		// Arrange
		this.interactor.locking = true
		// Act
		this.interactor.perform()
		// Assert
		val manager = this.interactor.launcherPresentationManager
		verify(manager, times(1))?.finish()
		val repository = this.interactor.repository
		val argument = ArgumentCaptor.forClass(LauncherPresentationVO::class.java)
		verify(repository, times(1)).add(argument.capture())
		assertNotNull(manager?.current)
		assertEquals(manager?.current, argument.value)
	}

	// endregion

}
