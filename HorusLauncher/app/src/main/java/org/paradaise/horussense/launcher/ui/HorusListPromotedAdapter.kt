
package org.paradaise.horussense.launcher.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_horus_promoted.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.domain.HorusList
import org.paradaise.horussense.launcher.domain.HorusListItem
import org.paradaise.horussense.launcher.domain.PromotedAction


class HorusListPromotedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

	constructor(list: HorusList, promotedAction: PromotedAction?,
	            listener: (HorusListItem) -> Unit) : super()
	{
		this.list = list
		this.listener = listener
		this.promotedAction = promotedAction
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		if (viewType == this.horusItemViewType) {
			val itemView = LayoutInflater.from(parent.context)
					.inflate(R.layout.item_horus, parent, false)
			return HorusListItemViewHolder(itemView)
		} else if (viewType == this.promotedItemViewType) {
			val itemView = LayoutInflater.from(parent.context)
					.inflate(R.layout.item_horus_promoted, parent, false)
			return HorusListPromotedItemViewHolder(itemView)
		}
		throw Exception()
	}

	override fun getItemViewType(position: Int): Int {
		val promotedActionPosition = this.promotedItemPosition ?: return horusItemViewType
		return if (position == promotedActionPosition) this.promotedItemViewType
				else this.horusItemViewType
	}

	override fun getItemCount(): Int {
		return this.list.count() + (if (this.promotedAction != null) 1 else 0)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (holder is HorusListPromotedItemViewHolder) {
			val promotedAction = this.promotedAction ?: return
			holder.bind(promotedAction)
		} else if (holder is HorusListItemViewHolder) {
			var itemPosition =  position
			val promotedPosition = this.promotedItemPosition
			if (promotedPosition != null && position >= promotedPosition) {
				itemPosition--
			}
			val item = this.list[itemPosition]
			holder.bind(item, isFirst = position == 0, listener = this.listener)
		}

	}

	private val list: HorusList
	private val listener: (HorusListItem) -> Unit
	private val promotedAction: PromotedAction?

	private val horusItemViewType = 0
	private val promotedItemViewType = 1
	private val promotedItemPosition: Int?
		get() = if (this.promotedAction != null) this.itemCount / 2 else null

}


class HorusListPromotedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	fun bind(item: PromotedAction) = with(itemView) {
		imageView.setImageDrawable(item.icon)
		imageView.visibility = if (item.icon != null) View.VISIBLE else View.GONE
		subtitleView.text = item.description
		titleView.text = item.name
	}

}
