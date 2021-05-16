package com.poi.camppus.activities

import android.app.ProgressDialog
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
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_groups
import java.util.*

class AddGroupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val firebase  = FirebaseFirestore.getInstance()

    private lateinit var txt_nombreEquipo:EditText
    private lateinit var txt_nombreUsuario:EditText

    var usuarios:MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        auth = FirebaseAuth.getInstance()

        var btn_publicEquipo: Button = findViewById(R.id.btn_createGroup)
        var btn_add: Button = findViewById(R.id.btn_addUserGroup)

        txt_nombreEquipo = findViewById(R.id.txt_GroupName)
        txt_nombreUsuario = findViewById(R.id.txt_userGroup)

        btn_publicEquipo.setOnClickListener(){
           if (usuarios.size > 4){
               insertEquipo()
               Toast.makeText(this, "Congratulations, you created your team", Toast.LENGTH_SHORT).show()
               finish()

           }
            else{
               ShowAlert("Error", "At least 5 users are required to create a group")
           }
        }

        btn_add.setOnClickListener(){
            usuarios.add(txt_nombreUsuario.text.toString())
            Toast.makeText(this, "Added to ${txt_nombreUsuario.text.toString()} to TEAM", Toast.LENGTH_SHORT).show()
            txt_nombreUsuario.setText("")
        }

    }

    private fun insertEquipo(){
            var GrupoId= UUID.randomUUID()
            val nombreEquipo = txt_nombreEquipo.text.toString()
            var grupo: tbl_groups? = null

            grupo = tbl_groups(
                    id = GrupoId.toString(),
                    name = nombreEquipo,
                    users = usuarios
            )

            firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(GrupoId.toString()).set(grupo)

            if (usuarios !=null){
                for (item:String in usuarios){
                    var userRef =firebase.collection(ReferenciasFirebase.USERS.toString()).document(item)
                        userRef.collection(ReferenciasFirebase.TEAMS.toString()).document(GrupoId.toString()).set(grupo)
                    if (item != auth.currentUser.email){
                        val tblUsuarios: tbl_Usuarios = tbl_Usuarios("","","",item,"","https://firebasestorage.googleapis.com/v0/b/camppus-224af.appspot.com/o/logoDeafult.jpg?alt=media&token=2c018c0d-340b-4853-8fb6-7fe71f2287d7","Desconectado")
                        userRef.set(tblUsuarios)
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