package com.mtw.josealejandrosanchezortega.mitrayecto

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vehicle_list_item.view.*

class VehicleListAdapter(private val myDataset: Array<String>) : RecyclerView.Adapter<VehicleListAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    //// class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        // MÉTODO PARA ASOCIAR DATOS CON UN view QUE PERMITA MOSTRAR DICHO DATO
        fun bind(text : String) {
            view.tvVehicle.text = text
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListAdapter.MyViewHolder {
        // SE GENERA UNA INSTANCIA DE UN inflater PARA RENDERIZAR EL activity CON SUS view's
        val layoutInflater = LayoutInflater.from(parent.context)

        // SE REGRESA UNA INSTANCIA DEL CONTENEDOR CON TODAS LAS views INSTANCIADAS HACIENDO REFERENCIA AL activity
        return MyViewHolder(layoutInflater.inflate(R.layout.vehicle_list_item, parent,false))
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //// holder.textView.text = myDataset[position]
        holder.bind(myDataset[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}