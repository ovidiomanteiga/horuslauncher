
package org.paradaise.horussense.launcher.main


import android.app.Application
import android.support.v4.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasFragmentInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerFragment
import dagger.android.support.HasSupportFragmentInjector
import org.paradaise.horussense.launcher.composition.DaggerHorusLauncherComponent
import org.paradaise.horussense.launcher.composition.HorusLauncherComponent
import org.paradaise.horussense.launcher.composition.HorusLauncherModule
import javax.inject.Inject




class HorusLauncherApplication : Application(), HasSupportFragmentInjector {

	@Inject
	lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>


	override fun onCreate() {
		super.onCreate()
		DaggerHorusLauncherComponent.builder()
				.horusLauncherModule(HorusLauncherModule(this)).build()
		//DaggerHorusLauncherComponent.create().inject(this)
	}


	override fun supportFragmentInjector(): AndroidInjector<Fragment> {
		return this.dispatchingFragmentInjector
	}

}
