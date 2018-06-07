
package org.paradaise.horussense.launcher.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_all_actions.view.*
import kotlinx.android.synthetic.main.item_app.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.domain.AllActions
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
import org.paradaise.horussense.launcher.domain.HorusAction
import org.paradaise.horussense.launcher.infrastructure.AllAppsRepository
import org.paradaise.horussense.launcher.infrastructure.App



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
	    recyclerView.adapter = ActionsAdapter(actions, this::onActionClicked)
	    val layoutManager = recyclerView.layoutManager
	    if (layoutManager is GridLayoutManager) {
		    layoutManager.spanCount = 4
	    }
        return rootView
    }


	private fun onActionClicked(action: HorusAction) {
		if (action is App) {
			action.intent?.let {
				this.startActivity(it)
				return
			}
		}
		Toast.makeText(this.context,
				"Action clicked: " + action.name,
				Toast.LENGTH_LONG).show()
	}


	private lateinit var interactor: GetAllActionsInteractor

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
