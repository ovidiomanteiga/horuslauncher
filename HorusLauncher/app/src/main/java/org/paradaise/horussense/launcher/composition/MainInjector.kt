
package org.paradaise.horussense.launcher.composition

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
		}

	}

}

// region Dependency Interfaces

interface NeedsExecuteActionInteractor {
	var executeActionInteractor: ExecuteActionInteractor
}

interface NeedsExecutePromotedActionInteractor {
	var executePromotedActionInteractor: ExecutePromotedAction
}

interface NeedsGetAllActionsInteractor {
	var getAllActionsInteractor: GetAllActionsInteractor
}


interface NeedsGetHorusListInteractor {
	var getHorusListInteractor: GetHorusListInteractor
}


interface NeedsGetPromotedActionsInteractor {
	var getPromotedActionsInteractor: GetPromotedActions
}

// endregion
