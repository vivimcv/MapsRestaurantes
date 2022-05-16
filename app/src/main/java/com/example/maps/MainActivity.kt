package com.example.maps

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity(),OnMapReadyCallback {
    private lateinit var map:GoogleMap
    companion object{
        const val REQUEST_CODE_LOCATION =0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createFrag()
    }
    private fun enableLocation(){
        //si el mapa está inicializado
        if(!::map.isInitialized)return
        if(isLocationPermissionGranted()){
            map.isMyLocationEnabled=true
        }else{
            requestLocationPermissio()
        }

    }
    private fun createFrag(){
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map= googleMap
       // createMarker()
        //acceder al gps
        enableLocation()
        findViewById<ImageView>(R.id.ivRestaurante1).setOnClickListener{
            createMarker(19.434094255760495, -99.14053460062753,"Miralto")
        }
//,
        findViewById<ImageView>(R.id.ivRestaurante2).setOnClickListener{
            createMarker(19.427478636359556, -99.16434037549813,"Goguinara")
        }


        findViewById<ImageView>(R.id.ivRestaurante3).setOnClickListener{
            createMarker(19.368457962616716,  -99.18084807031424,"El japonez")
            //,
        }
    }

    private fun createMarker(lat: Double, lon: Double, ubicacion: String){
       // val coordenadas = LatLng(19.427370,-99.167953)
    //    val marker = MarkerOptions().position(coordenadas).title("Angel de la independencia")
      //  map.addMarker(marker)
        val coordenadas = LatLng(lat,lon )
        val marker = MarkerOptions().position(coordenadas).title(ubicacion)
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,18f),
            4000,null
        )

    }
    private fun isLocationPermissionGranted()=
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
    private fun requestLocationPermissio() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION)){
        //Mostrar la ventana de permiso
    }else{
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE_LOCATION)
    }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                map.isMyLocationEnabled=true
            }
            else{
                Toast.makeText(this,"Para activar permiso, ve a ajustes y acepta los permisos",Toast.LENGTH_LONG).show()
            }
            else->{}
        }


    }

    }
//}