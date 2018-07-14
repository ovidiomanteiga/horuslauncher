
package org.paradaise.horussense.launcher.ui

import android.content.Context
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.horus_list.*
import kotlinx.android.synthetic.main.item_horus.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsExecuteActionInteractor
import org.paradaise.horussense.launcher.composition.NeedsGetHorusListInteractor
import org.paradaise.horussense.launcher.domain.*


class HorusListFragment : Fragment(),
		NeedsGetHorusListInteractor, NeedsExecuteActionInteractor
{
	// region Public Properties

	override lateinit var executeActionInteractor: ExecuteActionInteractor
	override lateinit var getHorusListInteractor: GetHorusListInteractor

	// endregion
	// region Lifecycle

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		MainInjector.inject(this)
	}


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment_horus_list, container, false)
	}


	override fun onStart() {
		super.onStart()
		this.getHorusList()
	}

	// endregion
	// region Private Properties

	private var horusList: HorusList? = null
	private val listener: HorusListFragmentListener?
		get() = this.activity as? HorusListFragmentListener
	private val nullOrEmptyHorusList: Boolean
		get() = this.horusList?.isEmpty() ?: true

	// endregion
	// region Private Methods

	private fun getHorusList() {
		AsyncTask.execute {
			this.getHorusListInteractor.perform()
			this.activity?.runOnUiThread {
				this.horusList = this.getHorusListInteractor.horusList
				this.showHorusList()
			}
		}
	}


	private fun showHorusList() {
		val noList = this.nullOrEmptyHorusList
		this.emptyView.visibility = if (noList) View.VISIBLE else View.GONE
		if (noList) {
			this.onEmptyHorusList()
			return
		}
		val horusList = this.horusList ?: return
		val adapter = HorusListAdapter(horusList, this::onItemClicked)
		this.recyclerView.adapter = adapter
	}


	private fun onEmptyHorusList() {
		this.listener?.onEmptyHorusList()
	}


	private fun onItemClicked(item: HorusListItem) {
		val predictedItem = item as? PredictedHorusListItem ?: return
		this.executeActionInteractor.action = predictedItem.horusAction
		AsyncTask.execute {
			this.executeActionInteractor.perform()
		}
	}

	// endregion

}


// region Public Interfaces

interface HorusListFragmentListener {
	fun onEmptyHorusList()
}

// endregion
