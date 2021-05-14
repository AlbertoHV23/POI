package com.poi.camppus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_groups
import com.poi.camppus.models.tbl_subgroups
import java.util.*

class AddSubGroupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance()

    private lateinit var txt_nombreEquipo:EditText
    private lateinit var txt_nombreUsuario:EditText
    private  var id_grupo:String = ""

    var usuarios:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subgroup)
        id_grupo = intent.getStringExtra("ID").toString()
        println(id_grupo)

        auth = FirebaseAuth.getInstance()

        var btn_publicEquipo: Button = findViewById(R.id.btn_createSubGroup)
       // var btn_add: Button = findViewById(R.id.btn_addUserSubGroup)

        txt_nombreEquipo = findViewById(R.id.txt_subGroupName)
        //txt_nombreUsuario = findViewById(R.id.txt_userSubGroup)

        btn_publicEquipo.setOnClickListener(){
            insertEquipo()
            Toast.makeText(this, "Congratulations, you created your SubGroup", Toast.LENGTH_SHORT).show()
            finish()


        }

       // btn_add.setOnClickListener(){
         //   usuarios.add(txt_nombreUsuario.text.toString())
           // Toast.makeText(this, "Added to ${txt_nombreUsuario.text.toString()} to TEAM", Toast.LENGTH_SHORT).show()
            //txt_nombreUsuario.setText("")
        //}

    }

    private fun insertEquipo(){
            var GrupoId= UUID.randomUUID()
            val nombreEquipo = txt_nombreEquipo.text.toString()
            var grupo: tbl_subgroups? = null

        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo)

        userRef.get().addOnSuccessListener{
            var users = it.get("users")
            grupo = tbl_subgroups(
                    id = GrupoId.toString(),
                    name = nombreEquipo,
                    users = users as List<String>
            )

            firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(id_grupo).collection(ReferenciasFirebase.SUBGROUPS.toString()).document(grupo!!.id).set(grupo!!)


            if (usuarios !=null){
                for (item:String in usuarios){
                    firebase.collection(ReferenciasFirebase.USERS.toString()).document(item).collection(ReferenciasFirebase.SUBGROUPS.toString()).document(GrupoId.toString()).set(grupo!!)
                }
            }

        }









        }

    fun ShowAlert(title: String, msg: String) {
        val simpleDialog =  AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(R.drawable.ic_baseline_error_24)
                .setPositiveButton("Retry"){ _, _ ->
                    Toast.makeText(this, "Try again", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancel"){ _, _->
                    Toast.makeText(this, "Cancel add user", Toast.LENGTH_LONG).show()
                }.create()

        simpleDialog.show()
    }

}