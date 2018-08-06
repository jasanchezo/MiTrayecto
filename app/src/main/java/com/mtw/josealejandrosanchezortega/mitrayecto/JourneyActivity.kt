package com.mtw.josealejandrosanchezortega.mitrayecto

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_journey.*

class JourneyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    // TODO (2) SOLICITAR PERMISOS EN TIEMPO DE EJECUCION
    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1

        private val REQUEST_CHECK_SETTINGS = 1
    }

    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_journey)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // TODO 3.2 INSTANCIA LA CLASE DE LA GEOFERENCIACION
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        tvbtnGetGeoLocation.setOnClickListener {
            // textView.text = "TRAER LOCALIZACIÓN"
            if (checkPermission()) {
                Log.i("GPSAPP", "ACCESO CONCEDIDO")
                getLastLocation()
            } else {
                Log.i("GPSAPP", "ACCESO DENEGADO")
            }
        }
    }

    /**
     * FUNCION PARA VERIFICAR PERMISOS. DEVUELVE TRUE SI EL USUARIO OTORGÓ PERMISOS
     */
    private fun checkPermission() : Boolean {
        // EN ESTE CASO USAREMOS ACCESS_FINE_LOCATION, PERO HAY VARIOS ESQUEMAS DE PERMISOS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            // EN CASO DE NO TENER EL PERMISO, ENTONCES SE ABRE EL DIALOGO Y EL DATO DE RESPUESTA DEL
            // DIALOGO SE ALMACENA EN LA VARIABLE REQUEST_LOCATION_PERMISSION
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            return false
        }
    }

    private fun getLastLocation() {
        try {
            fusedLocationClient.lastLocation
                    .addOnSuccessListener {
                        location : Location? ->
                        Log.i("GPSAPP", "OnSuccessListener lastLocation")
                        onLocationChanged(location)
                    }
                    .addOnFailureListener {
                        Log.i("GPSAPP", "OnFailureListener lastLocation")
                        Toast.makeText(this@JourneyActivity, "Error en la lectura del GPS", Toast.LENGTH_LONG).show()
                    }
        } catch (e : SecurityException) {
            Toast.makeText(this@JourneyActivity, "SecEx: " + e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onLocationChanged(location : Location?) {
        if (location != null) {
            // SE USA UN RECURSO DE STRINGS PERO FORMATEADO/ENMARCARADO Y SE SUSTITUYE CON LOS VALORES SIGUIENTES
            // textview_location.text = getString(R.string.location_text, location?.latitude.toString(), location?.longitude.toString(), location?.altitude.toString())
            textView.text = "Latitud: " + location?.latitude + " Longitud: " + location?.longitude + " Altitud: " + location?.altitude
        } else {
            textView.text = "No se recuperó la ubicación"
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // related task you need to do.
                    // OBTENER LA GEOLOCALIZACION
                    getLastLocation()
                } else {
                    // EN CASO DE NO OTORGAR PERMISO ENTONCES DESPLEGAR UN MENSAJE Y TAMBIEN SE
                    // PUEDEN DESHABILITAR LAS FUNCIONALIDADES
                    Toast.makeText(this, "Acceso al GPS denegado", Toast.LENGTH_SHORT).show()
                }
                return
            }
            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
