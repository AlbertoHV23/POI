package com.poi.camppus.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.adapters.MensajesAdapter
import com.poi.camppus.models.Encriptacion
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.LLAVES
import com.poi.camppus.models.tbl_Mensajes
import com.squareup.picasso.Picasso
import java.util.*

class MensajesActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseDatabase.getInstance() //INTANCIA DE LA BASE DE DATOS
    val firebase  = FirebaseFirestore.getInstance();


    private lateinit var _Mensaje:EditText
    private lateinit var _id:String

    private var ENCRIPTADO = false


    // Location
    private var PERMISSION_ID = 52

    // Variables required
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Camppus_login)
        setContentView(R.layout.activity_chat)


        // Location
        // Initiate the fused...providerClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        auth = FirebaseAuth.getInstance()

        var ema=  intent.getStringExtra("EMAIL")
        var uid=  intent.getStringExtra("ID")
        var ChatName=  intent.getStringExtra("CHATNAME")

        var username:TextView = findViewById(R.id.txt_UsernameMensaje)
        var img: ImageView = findViewById(R.id.imageView3)

        Picasso.get().load("https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg").into(img)

        username.text = ChatName

        val toggle:Switch = findViewById(R.id.switch1)
        toggle.setOnCheckedChangeListener { _, isChecked ->
            ENCRIPTADO = isChecked
            println(ENCRIPTADO)
        }

        _Mensaje = findViewById(R.id.txt_mensaje_main)
        if (uid != null) {
            _id = uid
        }


        val btn_send:Button = findViewById(R.id.btn_enviarmensajemain)
        val btn_sendUbication:Button = findViewById(R.id.btn_sendUbication)



        btn_sendUbication.setOnClickListener(){
            getLastLocation()
        }

        btn_send.setOnClickListener(){

            if(ENCRIPTADO){
                enviarMensajeEncriptado()

            }
            else{
                enviarMensaje()
            }


            _Mensaje.setText("")

        }


        val userRef = firebase.collection(ReferenciasFirebase.CHATS.toString()).document(_id)

        userRef.collection(ReferenciasFirebase.MESSAGES.toString()).orderBy("dob",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { document ->
                    var listChats = document.toObjects(tbl_Mensajes::class.java)
                    var rv = findViewById<RecyclerView>(R.id.rv_ListaMensajes)

                    rv.layoutManager   = LinearLayoutManager(this)
                    val adapter = MensajesAdapter(this, listChats)
                    rv.adapter = adapter



                }
        userRef.collection(ReferenciasFirebase.MESSAGES.toString()).orderBy("dob",com.google.firebase.firestore.Query.Direction.ASCENDING)
                .addSnapshotListener(){
            messages,error ->
            if (error == null){
                messages?.let { var listChats = it.toObjects(tbl_Mensajes::class.java)
                    var rv = findViewById<RecyclerView>(R.id.rv_ListaMensajes)

                    rv.layoutManager   = LinearLayoutManager(this)
                    val adapter = MensajesAdapter(this, listChats)
                    rv.adapter = adapter }
            }
        }

    }

    private fun enviarMensaje() {
        val mensaje = _Mensaje.text.toString()
        val txt_mensaje_main:EditText = findViewById(R.id.txt_mensaje_main)

        val chat = tbl_Mensajes(
                message = mensaje,
                from = auth.currentUser.email,
                encriptado = false



        )

        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(_id).collection(ReferenciasFirebase.MESSAGES.toString()).document().set(chat)
        txt_mensaje_main.setText("")
    }

    private fun enviarMensajeEncriptado() {
        val mensaje = _Mensaje.text.toString()
        val txt_mensaje_main:EditText = findViewById(R.id.txt_mensaje_main)

        var mensajeEncriptado = Encriptacion.cifar(mensaje,LLAVES.MESSAGE.toString())

        val chat = tbl_Mensajes(
                message = mensajeEncriptado,
                from = auth.currentUser.email,
                encriptado = true



        )

        firebase.collection(ReferenciasFirebase.CHATS.toString()).document(_id).collection(ReferenciasFirebase.MESSAGES.toString()).document().set(chat)
        txt_mensaje_main.setText("")
    }



    // Function that will allow us to get the last location
    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        // Check permission
        if(CheckPermission()){
            // Check the location service is enabled
            if(IsLocationEnabled()){
                // Get Location
                fusedLocationProviderClient.lastLocation.addOnCompleteListener{task ->
                    var location: Location? = task.result
                    if(location == null){

                        // if the location is null we will get the new user location
                        getNewLocation()

                    }else{

                        // location.latitude will return the latitude coordinates
                        // location.longitude will return the longitude coordinates
                        _Mensaje.setText("Hi, this is mi ubication:\nLat:" + location.latitude + " ;Long:"+ location.longitude+
                                "\nCity: " + getCityName(location.latitude,location.longitude) + ", Country: " + getCountryName(location.latitude,location.longitude) )
                    }

                }
            }else{
                Toast.makeText(this,"Please Enable your location service", Toast.LENGTH_SHORT).show()
            }
        }else{
            RequestPermission()
        }
    }

    // Location null function
    @SuppressLint("MissingPermission")
    private fun getNewLocation(){
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
                // locationCallback variable
        )
    }

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation : Location = p0.lastLocation
            // Set the new Location
            // Set the new Location
            _Mensaje.setText("Hi, this is mi ubication: :\nLat:" + lastLocation.latitude + " ;Long:"+ lastLocation.longitude+
                    "\nCity: " + getCityName(lastLocation.latitude,lastLocation.longitude) + ", Country: " + getCountryName(lastLocation.latitude,lastLocation.longitude))

        }
    }

    // Function to get city name
    private fun getCityName(lat:Double,long:Double):String{
        var CityName = ""
        var geocoder = Geocoder(this, Locale.getDefault())
        var Adress: MutableList<Address>? = geocoder.getFromLocation(lat,long,1)

        CityName = Adress?.get(0)?.locality.toString()
        return CityName
    }
    // Function to get country name
    private fun getCountryName(lat:Double,long:Double):String{
        var CountryName = ""
        var geocoder = Geocoder(this, Locale.getDefault())
        var Adress: MutableList<Address>? = geocoder.getFromLocation(lat,long,1)

        CountryName = Adress?.get(0)?.countryName.toString()
        return CountryName
    }



    // Function that will check the uses permission
    private fun CheckPermission():Boolean{
        if(
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ){
            return true
        }

        return false
    }

    // Function that will allow us to get user permission
    private fun RequestPermission(){
        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID
        )
    }

    // Function that check if the location service of the device is enabled
    private fun IsLocationEnabled():Boolean{

        var locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        // this is a buil in function that check the permission result
        // only for debugging our code
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_ID){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug:","You Have the Permission")
            }
        }
    }


}