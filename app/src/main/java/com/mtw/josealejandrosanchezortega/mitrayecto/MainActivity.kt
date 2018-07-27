package com.mtw.josealejandrosanchezortega.mitrayecto

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // DECLARAMOS LA VARIABLE CON LA QUE SE CONOCERÁ EL OBJETO DEL RECYCLERVIEW
    private lateinit var rvVehiclesList: RecyclerView

    // DECLARAMOS EL ADAPTER DE DATOS PARA EL RECYCLERVIEW
    private lateinit var myViewAdapter: RecyclerView.Adapter<*>


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.favorites)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.journey)

                // CÓDIGO PARA LANZAR EL Activity DEL MAPS
                val intent = Intent(this, JourneyActivity::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.issue_report)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // MI CONTENIDO
        // BASADO EN LA REFERENCIA: https://developer.android.com/guide/topics/ui/layout/recyclerview

        var myDataset : ArrayList<Vehicle> = ArrayList()
        populateData(myDataset)

        // myViewAdapter = VehicleListAdapter(myDataset)
        myViewAdapter = VehicleListAdapter(myDataset)

        // SE CREA EL WIDGET DE RECYCLERVIEW Y SE ESTABLECEN PARÁMETROS DEL MISMO CON apply
        rvVehiclesList = findViewById<RecyclerView>(R.id.rvVehiclesList).apply {
            // FIJAMOS EL RECYCLERVIEW YA QUE LAS TARJETAS SERÁN DEL MISMO TAMAÑO
            setHasFixedSize(true)

            // SE ESTABLECE EL LAYOUTMANAGER QUE USARÁ EL RECYCLERVIEW DENTRO DE ÉL
            layoutManager = LinearLayoutManager(this@MainActivity)

            // SE ESTABLE EL ViewAdapter DEL RECYCLERVIEW
            adapter = myViewAdapter
        }
    }

    private fun populateData(myDataset: ArrayList<Vehicle>) {
        myDataset.add(Vehicle("NP200", "vehículo asignado a la coordinación de redes"))
        myDataset.add(Vehicle("hilux", "vehículo asignado al Departamento de Sistemas de Información"))
        myDataset.add(Vehicle("Ford 6", "vehículo asignado a la coordinación de conectividad"))
    }
}
