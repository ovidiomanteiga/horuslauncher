
package org.paradaise.horussense.launcher.ui.all_actions


import android.content.Context
import android.os.AsyncTask
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
import org.paradaise.horussense.launcher.composition.*
import org.paradaise.horussense.launcher.domain.all_actions.AllActions
import org.paradaise.horussense.launcher.domain.action_execution.ExecuteActionInteractor
import org.paradaise.horussense.launcher.domain.all_actions.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.action.HorusAction



class AllActionsFragment : Fragment(),
		NeedsGetAllActionsInteractor, NeedsExecuteActionInteractor
{

	override lateinit var executeActionInteractor: ExecuteActionInteractor
	override lateinit var getAllActionsInteractor: GetAllActionsInteractor


	override fun onAttach(context: Context?) {
		super.onAttach(context)
		MainInjector.inject(this)
	}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_all_actions, container, false)
	    val recyclerView = rootView.recyclerView
	    this.getAllActionsInteractor.perform()
	    val actions = this.getAllActionsInteractor.allActions
	    recyclerView.adapter = ActionsAdapter(actions, this::onActionClicked)
	    val layoutManager = recyclerView.layoutManager
	    if (layoutManager is GridLayoutManager) {
		    layoutManager.spanCount = 4
	    }
        return rootView
    }


	private fun onActionClicked(action: HorusAction) {
		this.executeActionInteractor.fromHorusList = false
		this.executeActionInteractor.action = action
		AsyncTask.execute {
			this.executeActionInteractor.perform()
		}
	}

}



// region Classes

private class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: HorusAction, listener: (HorusAction) -> Unit) = with(itemView) {
        itemView.textView.text = item.name
	    itemView.imageView.setImageDrawable(item.icon)
	    itemView.setOnClickListener { listener(item) }
    }

}



private class ActionsAdapter() : RecyclerView.Adapter<ActionViewHolder>() {

	constructor(items: AllActions, listener: (HorusAction) -> Unit) : this() {
		this.items = items
		this.listener = listener
	}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_app, parent, false)
        return ActionViewHolder(v)
    }


    override fun getItemCount(): Int {
        return this.items.count()
    }


    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(this.items[position], this.listener)
    }


	private lateinit var items: AllActions
	private lateinit var listener: (HorusAction) -> Unit

}

// endregion
