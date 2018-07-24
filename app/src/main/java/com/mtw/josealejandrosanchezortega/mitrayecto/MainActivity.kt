package com.mtw.josealejandrosanchezortega.mitrayecto

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
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    // DECLARAMOS EL LAYOUT MANAGER QUE SE USARÁ DENTRO DEL RECYCLERVIEW
    private lateinit var rvLayoutManager: RecyclerView.LayoutManager


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.favorites)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.journey)
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

        rvLayoutManager = LinearLayoutManager(this)

        var myDataset = arrayOf("HOLA 0", "HOLA 1")

        viewAdapter = VehicleListAdapter(myDataset)


        rvVehiclesList = findViewById<RecyclerView>(R.id.rvVehiclesList).apply {
            // FIJAMOS EL RECYCLERVIEW YA QUE LAS TARJETAS SERÁN DEL MISMO TAMAÑO
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = rvLayoutManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
    }
}
