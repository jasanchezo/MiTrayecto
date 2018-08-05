package com.mtw.josealejandrosanchezortega.mitrayecto

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.mtw.josealejandrosanchezortega.mitrayecto.api.VehiclesApiService
import com.mtw.josealejandrosanchezortega.mitrayecto.api.VehiclesModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // DECLARAMOS LA VARIABLE CON LA QUE SE CONOCERÁ EL OBJETO DEL RECYCLERVIEW
    private lateinit var rvVehiclesList: RecyclerView

    // DECLARAMOS EL ADAPTER DE DATOS PARA EL RECYCLERVIEW
    // private lateinit var myViewAdapter: RecyclerView.Adapter<*>
    private lateinit var myViewAdapter: VehicleListAdapter


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

    private var disposable: Disposable? = null

    private val vehiclesApiService by lazy {
        VehiclesApiService.create()
    }

    private fun populateData(myDataset: ArrayList<Vehicle>) {
        /*myDataset.add(Vehicle("NP200", "vehículo asignado a la coordinación de redes"))
        myDataset.add(Vehicle("hilux", "vehículo asignado al Departamento de Sistemas de Información"))
        myDataset.add(Vehicle("Ford 6", "vehículo asignado a la coordinación de conectividad"))*/


        disposable = vehiclesApiService.getVehicles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { // result -> txt_search_result.text = "${result.query.searchinfo.totalhits} result found" },
                            result ->
                                Log.i("MITRAYECTAPP", "CORRECTO: " + result.size)
                                // myDataset.add(Vehicle("NP200", "vehículo asignado a la coordinación de redes"))
                                // myDataset.add(Vehicle(result[i].smallname, result[i].description))
                                Log.i("ITEMS", result[1].smallname)
                            Log.i("ITEMS", result[1].description)
                            /* Log.i("ITEMS", result[1].smallname)
                            Log.i("ITEMS", result[2].smallname) */
                            for (i in result.indices) {
                                myDataset.add(Vehicle(result[i].smallname, result[i].description))
                            }



                                // Toast.makeText(this, "ITEMS: " + result.items.size, Toast.LENGTH_SHORT).show()
                        }
                        ,
                        {
                            error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            Log.e("MITRAYECTAPP", "INCORRECTO: " + error.message)
                        }
                )

    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    // SE INSTANCÍA HASTA QUE SE USA Y SÓLO SUCEDE UNA VEZ. SI SE VUELVE A USAR SE REGRESA EL OBJETO
    // YA INSTANCIADO
    /*private val vehiclesApiService by lazy {
        VehiclesApiService.create()
    }

    private fun populateDateFromWS() {
        // EJECUTAR LA LLAMADA A LA API. ESPECIFFICAMENTE EJECUTAR EL MÉTODO SearchBooks
        disposable = vehiclesApiService.getVehicles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result ->
                    // MOSTRAR LA LISTA DE LIBROS REGRESADOS EN LA LLAMADA
                    myViewAdapter.setItemsList(result.items)
                    // MOSTRAR MENSAJE DE DIALOGO
                    Toast.makeText(this@MainActivity, "búsqueda realizada", Toast.LENGTH_LONG).show()
                }, {
                    error ->
                    showAlert("Error", error.message!!)
                })
    }

    private var disposable : Disposable? = null

    // SE INSTANCÍA HASTA QUE SE USA Y SÓLO SUCEDE UNA VEZ. SI SE VUELVE A USAR SE REGRESA EL OBJETO
    // YA INSTANCIADO
    private val booksApiService by lazy {
        // BooksApiService.create()
        VehiclesApiService.create()
    }

    // METODO PARA CREAR UN DIALOGO DE TIPO MESSAGEBOX
    private fun showAlert(title : String, message : String) {
        AlertDialog.Builder(this@MainActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar", null)
                .create().show()
    }*/
}
