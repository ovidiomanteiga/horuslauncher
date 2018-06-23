
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
import kotlinx.android.synthetic.main.fragment_horus_list.*
import kotlinx.android.synthetic.main.item_horus.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.R.id.*
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsExecuteActionInteractor
import org.paradaise.horussense.launcher.composition.NeedsGetHorusListInteractor
import org.paradaise.horussense.launcher.domain.*


class HorusListFragment : Fragment(),
		NeedsGetHorusListInteractor, NeedsExecuteActionInteractor
{

	override lateinit var executeActionInteractor: ExecuteActionInteractor
	override lateinit var getHorusListInteractor: GetHorusListInteractor


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


	private fun getHorusList() {
		AsyncTask.execute {
			this.getHorusListInteractor.perform()
			this.activity?.runOnUiThread {
				val actions = this.getHorusListInteractor.horusList
				this.recyclerView.adapter = HorusListAdapter(actions, this::onItemClicked)
			}
		}
	}


	private fun onItemClicked(item: HorusListItem) {
		this.executeActionInteractor.action = item.action
		AsyncTask.execute {
			this.executeActionInteractor.perform()
		}
	}

}


private class HorusListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	fun bind(item: HorusListItem, isFirst: Boolean, listener: (HorusListItem) -> Unit) = with(itemView) {
		imageView.setImageDrawable(item.icon)
		val times = item.numberOfExecutionsLastWeek
		val subtitleRID = R.plurals.times_launched_so_far_this_week
		val subtitle = this.context.resources.getQuantityString(subtitleRID, times, times)
		subtitleView.text = subtitle
		titleView.text = item.name
		val typeface = if (isFirst) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
		subtitleView.typeface = typeface
		titleView.typeface = typeface
		setOnClickListener { listener(item) }
	}

}


private class HorusListAdapter : RecyclerView.Adapter<HorusListItemViewHolder> {

	constructor(list: HorusList, listener: (HorusListItem) -> Unit) : super() {
		this.list = list
		this.listener = listener
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorusListItemViewHolder {
		val itemView = LayoutInflater.from(parent.context)
				.inflate(R.layout.item_horus, parent, false)
		return HorusListItemViewHolder(itemView)
	}

	override fun getItemCount(): Int {
		return this.list.count()
	}

	override fun onBindViewHolder(holder: HorusListItemViewHolder, position: Int) {
		holder.bind(this.list[position], isFirst = position == 0, listener = this.listener)
	}


	private val list: HorusList
	private val listener: (HorusListItem) -> Unit

}
