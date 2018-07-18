
package org.paradaise.horussense.launcher.unittests


import org.junit.Assert.*
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
	private lateinit var domainFactory: DomainFactory

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
		`when`(this.domainFactory.provideLauncherPresentationManager())
				.thenReturn(this.manager)
		DomainFactory.current = this.domainFactory
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
	    verify(this.manager, times(1))?.start()
    }

	@Test
	fun testDeviceLocking() {
		// Arrange
		this.interactor.locking = true
		// Act
		this.interactor.perform()
		// Assert
		verify(this.manager, times(1))?.finish()
	}

	// endregion

}
