
package org.paradaise.horussense.launcher.ui


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_all_actions.view.*
import org.paradaise.horussense.launcher.R
import org.paradaise.horussense.launcher.domain.AllActions
import org.paradaise.horussense.launcher.domain.GetAllActionsInteractor
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
        rootView.recyclerView.adapter = ActionsAdapter(this.interactor.allActions)
        return rootView
    }


	private lateinit var interactor: GetAllActionsInteractor

}



// region Classes

private class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: String) = with(itemView) {
        itemView.findViewById<TextView>(android.R.id.text1).text = item
    }

}



private class ActionsAdapter(val items: AllActions) : RecyclerView.Adapter<ActionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ActionViewHolder(v)
    }


    override fun getItemCount(): Int {
        return this.items.count()
    }


    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(this.items[position].name ?: "?")
    }

}

// endregion
