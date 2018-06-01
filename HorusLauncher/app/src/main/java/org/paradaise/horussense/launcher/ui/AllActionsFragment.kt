
package org.paradaise.horussense.launcher.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_all_actions.view.*
import kotlinx.android.synthetic.main.item_app.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.domain.AllActions
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository



class AllActionsFragment : Fragment() {

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		this.interactor = GetAllActionsInteractor(AllAppsRepository(context))
		this.interactor.perform()
	}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_all_actions, container, false)
	    val recyclerView = rootView.recyclerView
	    val actions = this.interactor.allActions
	    recyclerView.adapter = ActionsAdapter(actions)
	    val layoutManager = recyclerView.layoutManager
	    if (layoutManager is GridLayoutManager) {
		    layoutManager.spanCount = 4
	    }
        return rootView
    }


	private lateinit var interactor: GetAllActionsInteractor

}



// region Classes

private class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: HorusAction) = with(itemView) {
        itemView.textView.text = item.name
	    itemView.imageView.setImageDrawable(item.icon)
    }

}



private class ActionsAdapter(val items: AllActions) : RecyclerView.Adapter<ActionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_app, parent, false)
        return ActionViewHolder(v)
    }


    override fun getItemCount(): Int {
        return this.items.count()
    }


    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(this.items[position])
    }

}

// endregion
