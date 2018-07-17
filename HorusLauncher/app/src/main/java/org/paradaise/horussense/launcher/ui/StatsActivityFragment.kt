
package org.paradaise.horussense.launcher.ui

import android.content.Context
import android.os.AsyncTask
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stats.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsGetStatsInteractor
import org.paradaise.horussense.launcher.domain.GetStatsInteractor
import org.paradaise.horussense.launcher.domain.LauncherStatsVO


class StatsActivityFragment : Fragment(), NeedsGetStatsInteractor {

	// region Internal Properties

	override lateinit var getStatsInteractor: GetStatsInteractor

	// endregion
	// region Lifecycle

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		MainInjector.inject(this)
	}

	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_stats, container, false)
	}


	override fun onStart() {
		super.onStart()
		AsyncTask.execute {
			this.getStatsInteractor.perform()
			this.activity?.runOnUiThread {
				this.showStats()
			}
		}
	}

	// endregion
	// region Private Methods

	private fun showStats() {
		val stats = this.getStatsInteractor.stats ?: return
		val statsVM1 = this.getStatsHorusListAccuracyViewModel(stats)
		val statsVM2 = this.getStatsAverageActionTimeViewModel(stats)
		val statsVM3 = this.getStatsAverageHorusListTimeViewModel(stats)
		val statsVMs = listOf(statsVM1, statsVM2, statsVM3)
		this.recyclerView.adapter = StatsAdapter(statsVMs)
	}


	private fun getStatsAverageHorusListTimeViewModel(stats: LauncherStatsVO): StatsItemViewModel {
		val averageTimeGettingHorusListText =
				stats.averageTimeGettingHorusList?.toString() ?: "?"
		return StatsItemViewModel(
				getString(R.string.stats_average_time_getting_horus_list_subtitle),
				getString(R.string.stats_average_time_getting_horus_list_title),
				averageTimeGettingHorusListText)
	}


	private fun getStatsAverageActionTimeViewModel(stats: LauncherStatsVO): StatsItemViewModel {
		val averageActionTimeText = stats.averageActionTime?.let {
			"%.1f".format(it)
		} ?: "?"
		return StatsItemViewModel(
				getString(R.string.stats_average_action_time_subtitle),
				getString(R.string.stats_average_action_time_title),
				averageActionTimeText)
	}


	private fun getStatsHorusListAccuracyViewModel(stats: LauncherStatsVO): StatsItemViewModel {
		val horusListAccuracyText = stats.horusListAccuracy?.let {
			"%.2f".format(it)
		} ?: "?"
		return StatsItemViewModel(
				getString(R.string.stats_horus_list_accuracy_subtitle),
				getString(R.string.stats_horus_list_accuracy_title),
				horusListAccuracyText)
	}

	// endregion

}

// region Internal Classes

class StatsItemViewModel(
	val subtitle: String?,
	val title: String?,
	val value: String?)

// endregion
