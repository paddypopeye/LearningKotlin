 package com.example.androidpokemongooglemaps

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.FileObserver.ACCESS
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()
        loadPokemon()
    }

    var ACCESSLOCATION = 123
    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat
                    .checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESSLOCATION)
                return
            }
        }

        getUserLocation()
    }

    fun getUserLocation() {
        Toast.makeText(this, "User location access is on ", Toast.LENGTH_LONG).show()
        var myLocation = myLocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        checkPermission()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 3f, myLocation)
        var myThread = myThread()
        myThread.start()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            ACCESSLOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserLocation()
                } else {
                    Toast.makeText(this, "We cannot access to your location", Toast.LENGTH_LONG).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
    }

    var location: Location? = null

    //Get user location
    inner class myLocationListener : LocationListener {

        constructor() {
            location = Location("Start")
            location!!.longitude = 0.0
            location!!.latitude = 0.0
        }

        override fun onLocationChanged(p0: Location?) {
            location = p0

        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(provider: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
    var oldLocation: Location? = null
    inner class myThread : Thread {
        constructor() : super(){
            oldLocation = Location("Start")
            oldLocation!!.longitude = 0.0
            oldLocation!!.latitude = 0.0
        }

        override fun run() {
            while (true) {
                try {

                    if(oldLocation!!.distanceTo(location)==0f){
                        continue
                    }
                    oldLocation = location
                    runOnUiThread {
                        // Add a marker in Sydney and move the camera
                        mMap!!.clear()
                        //show me
                        val meeee = LatLng(location!!.latitude, location!!.longitude)
                            mMap!!.addMarker(
                            MarkerOptions()
                                .position(meeee)
                                .title("Me")
                                .snippet(" here is my loction")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mario))
                        )
                        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(meeee, 20f))

                        for (i in 0..listOfPokemons.size - 1) {
                            var newPokemon = listOfPokemons[i]
                            if (newPokemon.isCaught == false) {
                                val pokeeee = LatLng(newPokemon.location!!.latitude, newPokemon.location!!.longitude)
                                mMap!!.addMarker(
                                    MarkerOptions()
                                        .position(pokeeee)
                                        .title(newPokemon.name!!)
                                        .snippet(newPokemon.desc!! + "Power: " + newPokemon.power!!)
                                        .icon(BitmapDescriptorFactory.fromResource(newPokemon.image!!)))


                                if(location!!.distanceTo(newPokemon.location)< 2){
                                    newPokemon.isCaught=true
                                    listOfPokemons[i] = newPokemon
                                    playerPower += newPokemon.power!!
                                    Toast.makeText(applicationContext, "You caught a new pokemon your power is now.." + playerPower, Toast.LENGTH_LONG).show()
                                    }



                            }
                        }
                    }


                    Thread.sleep(500)

                    } catch (ex: Exception) {}
                }
            }
        }

        var playerPower = 0.0
        var listOfPokemons = ArrayList<Pokemon>()
        fun loadPokemon() {
            listOfPokemons.add(Pokemon("Pokemon One", "Description one", R.drawable.pikachu, 55.0, 37.30, -122.0))
            listOfPokemons.add(Pokemon("Pokemon Two", "Description two", R.drawable.pokemon1, 20.0, 33.23, -120.0))
            listOfPokemons.add(Pokemon("Pokemon Three", "Description three", R.drawable.pokemon2, 15.0, 35.16, -118.0))
        }
}

