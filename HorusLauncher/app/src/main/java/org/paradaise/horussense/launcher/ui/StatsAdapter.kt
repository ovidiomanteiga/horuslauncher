
package org.paradaise.horussense.launcher.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_stats.view.*
import org.paradaise.horussense.launcher.R


class StatsAdapter : RecyclerView.Adapter<StatsItemViewHolder> {

	constructor(list: List<StatsItemViewModel>) : super() {
		this.list = list
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsItemViewHolder {
		val itemView = LayoutInflater.from(parent.context)
				.inflate(R.layout.item_stats, parent, false)
		return StatsItemViewHolder(itemView)
	}

	override fun getItemCount(): Int {
		return this.list.count()
	}

	override fun onBindViewHolder(holder: StatsItemViewHolder, position: Int) {
		holder.bind(this.list[position])
	}

	private val list: List<StatsItemViewModel>

}


class StatsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	fun bind(item: StatsItemViewModel) = with(itemView) {
		subtitleView.text = item.subtitle
		titleView.text = item.title
		valueView.text = item.value
	}

}
