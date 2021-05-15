package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_asssigments
import com.poi.camppus.models.tbl_subgroups
import java.util.*

class CreateAssigmentActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance()


    private  var id_grupo:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_assigment)

        id_grupo = intent.getStringExtra("ID").toString()

        var txt_title:EditText = findViewById(R.id.editTextTextPersonName)
        var txt_description:EditText = findViewById(R.id.editTextTextPersonName3)
        var puntuacion:EditText = findViewById(R.id.editTextNumber)
        var btn_createA:Button = findViewById(R.id.btn_create_Ass)

        btn_createA.setOnClickListener() {

            var assigmnents: tbl_asssigments = tbl_asssigments(
                UUID.randomUUID().toString(),
                txt_title.text.toString(),
                txt_description.text.toString(),
                puntuacion.text.toString(),"0"


            )
            println(assigmnents)
            insertarAssigment(assigmnents)
        }

    }

    private fun insertarAssigment(assigmet: tbl_asssigments){
        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo)
        userRef.get().addOnSuccessListener{
            var users = it.get("users")

            assigmet.users = users as List<String>
            assigmet.team = id_grupo

            firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo).collection(ReferenciasFirebase.ASSIGMENTS.toString()).document(assigmet!!.id).set(assigmet!!)


            if (users !=null){
                for (item:String in users){
                    firebase.collection(ReferenciasFirebase.USERS.toString()).document(item).collection(ReferenciasFirebase.ASSIGMENTS.toString()).document(assigmet.toString()).set(assigmet!!)
                }
            }


        }
    }
}