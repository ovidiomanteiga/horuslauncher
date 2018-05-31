
package org.paradaise.horussense.launcher.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_all_actions.view.*
import org.paradaise.horussense.launcher.R



class AllActionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_all_actions, container, false)
        rootView.recyclerView.adapter = ActionsAdapter(listOf("Facebook", "Google", "WhatsApp"))
        return rootView
    }

}



// region Classes

private class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: String) = with(itemView) {
        itemView.findViewById<TextView>(android.R.id.text1).text = item
    }

}



private class ActionsAdapter(val items: List<String>) : RecyclerView.Adapter<ActionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        return ActionViewHolder(v)
    }


    override fun getItemCount(): Int {
        return this.items.count()
    }


    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

// endregion
