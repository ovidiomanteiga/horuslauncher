
package org.paradaise.horussense.launcher.composition

import android.content.BroadcastReceiver
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import org.paradaise.horussense.launcher.domain.*


class MainInjector {

	companion object {

		fun inject(target: AppCompatActivity) {
			this.factory.context = target
			this.injectCommon(target)
		}

		fun inject(target: Fragment) {
			this.factory.context = target.requireContext()
			this.injectCommon(target)
		}

		fun inject(target: BroadcastReceiver, context: Context) {
			this.factory.context = context
			this.injectCommon(target)
		}

		fun resetFactory() {
			this.factory = DefaultMainFactory()
		}

		fun setFactory(factory: MainFactory){
			this.factory = factory
		}

		private var factory: MainFactory = DefaultMainFactory()

		private fun injectCommon(target: Any) {
			val factory = this.factory
			(target as? NeedsGetHorusListInteractor)?.getHorusListInteractor =
					factory.provideGetHorusListInteractor()
			(target as? NeedsGetAllActionsInteractor)?.getAllActionsInteractor =
					factory.provideGetAllActionsInteractor()
			(target as? NeedsExecuteActionInteractor)?.executeActionInteractor =
					factory.provideExecuteActionInteractor()
			(target as? NeedsExecutePromotedActionInteractor)?.executePromotedActionInteractor =
					factory.provideExecutePromotedActionInteractor()
			(target as? NeedsGetPromotedActionsInteractor)?.getPromotedActionsInteractor =
					factory.provideGetPromotedActionsInteractor()
			(target as? NeedsDeviceLockingInteractor)?.deviceLockingInteractor =
					factory.provideDeviceLockingInteractor()
			(target as? NeedsGetStatsInteractor)?.getStatsInteractor =
					factory.provideGetStatsInteractor()
			(target as? NeedsSendStatsInteractor)?.sendStatsInteractor =
					factory.provideSendStatsInteractor()
		}

	}

}

// region Dependency Interfaces

interface NeedsExecuteActionInteractor {
	var executeActionInteractor: ExecuteActionInteractor
}

interface NeedsExecutePromotedActionInteractor {
	var executePromotedActionInteractor: ExecutePromotedActionInteractor
}

interface NeedsGetAllActionsInteractor {
	var getAllActionsInteractor: GetAllActionsInteractor
}

interface NeedsGetHorusListInteractor {
	var getHorusListInteractor: GetHorusListInteractor
}

interface NeedsGetPromotedActionsInteractor {
	var getPromotedActionsInteractor: GetPromotedActionsInteractor
}

interface NeedsGetStatsInteractor {
	var getStatsInteractor: GetStatsInteractor
}

interface NeedsDeviceLockingInteractor {
	var deviceLockingInteractor: DeviceLockingInteractor
}

interface NeedsSendStatsInteractor {
	var sendStatsInteractor: SendStatsInteractor
}

// endregion
