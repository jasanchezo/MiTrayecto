package com.mtw.josealejandrosanchezortega.mitrayecto

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vehicle_list_item.view.*

class VehicleListAdapter(private val myDataset: ArrayList<Vehicle>) : RecyclerView.Adapter<VehicleListAdapter.MyViewHolder>() {

    /**
     * CLASE PARA ENCAPSULAR LAS views DE LOS ITEMS DEL RecyclerView
     */
    class MyViewHolder(val mainView: View) : RecyclerView.ViewHolder(mainView) {
        // MÉTODO PARA ASOCIAR DATOS CON UN view QUE PERMITA MOSTRAR DICHO DATO
        fun bind(vehicle : Vehicle) {
            mainView.tvVehicleSortname.text = vehicle.sortname
            mainView.tvVehicleDescription.text = vehicle.description
        }
    }

    /**
     * MÉTODO QUE SE INVOCA PARA RENDERIZAR UN CONTENEDOR/HOLDER
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListAdapter.MyViewHolder {
        // SE GENERA UNA INSTANCIA DE UN inflater PARA RENDERIZAR EL activity CON SUS view
        val layoutInflater = LayoutInflater.from(parent.context)

        // SE REGRESA UNA INSTANCIA DEL CONTENEDOR CON TODAS LAS views INSTANCIADAS HACIENDO REFERENCIA AL activity
        return MyViewHolder(layoutInflater.inflate(R.layout.vehicle_list_item, parent,false))
    }

    /**
     * MÉTODO SOBRECARGADO QUE ASOCIA CADA DATO CON SU RESPECTIVO mainView. IMPORTANTE QUE EN LA CLASE
     * HOLDER SE CUENTE CON EL MÉTODO bind
     */
    override fun onBindViewHolder(myHolder: MyViewHolder, position: Int) {
        myHolder.bind(myDataset[position])
    }

    /**
     * MÉTODO SOBRECARGADO QUE REGRESA LA CANTIDAD DE DATOS INDEPENDIENTE DE LOS views
     */
    override fun getItemCount() = myDataset.size
}