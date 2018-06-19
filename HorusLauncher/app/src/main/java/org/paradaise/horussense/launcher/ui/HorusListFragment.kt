
package org.paradaise.horussense.launcher.ui

import android.content.Context
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
import org.paradaise.horussense.launcher.composition.MainInjector
import org.paradaise.horussense.launcher.composition.NeedsGetHorusListInteractor
import org.paradaise.horussense.launcher.domain.GetHorusListInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.domain.HorusList


class HorusListFragment : Fragment(), NeedsGetHorusListInteractor {

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
				this.recyclerView.adapter = HorusListAdapter(actions)
			}
		}
	}


	override lateinit var getHorusListInteractor: GetHorusListInteractor

}


private class HorusListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	fun bind(item: HorusAction) = with(itemView) {
		itemView.imageView.setImageDrawable(item.icon)
		itemView.titleView.text = item.name
		itemView.subtitleView.text = "<Launched 34 times so far this week>"
	}

}


private class HorusListAdapter(val list: HorusList) : RecyclerView.Adapter<HorusListItemViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorusListItemViewHolder {
		val itemView = LayoutInflater.from(parent.context)
				.inflate(R.layout.item_horus, parent, false)
		return HorusListItemViewHolder(itemView)
	}

	override fun getItemCount(): Int {
		return this.list.count()
	}

	override fun onBindViewHolder(holder: HorusListItemViewHolder, position: Int) {
		holder.bind(this.list[position])
	}

}
