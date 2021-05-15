package com.poi.camppus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.models.ReferenciasFirebase

class AssigmentActivity : AppCompatActivity() {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebase  = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Camppus_login)

        setContentView(R.layout.activity_assigment)

        var id_assig = intent.getStringExtra("ID_TAREA")
        var ID_GRUPO = intent.getStringExtra("ID_GRUPO")

        var btn_send:Button = findViewById(R.id.btn_calificar)





        val userRef = firebase.collection(ReferenciasFirebase.TEAMS.toString()).document(ID_GRUPO.toString())
        userRef.collection(ReferenciasFirebase.ASSIGMENTS.toString()).document(id_assig.toString()).get()
            .addOnSuccessListener{
                var id = it.get("id").toString()
                var title = it.get("title").toString()
                var description = it.get("description").toString()
                var valor = it.get("valor").toString()
                var puntos = it.get("puntos").toString()
                var users = it.get("users").toString()
                var team = it.get("team").toString()

                var txt_tittle:TextView = findViewById(R.id.view_tittle)
                var txt_description:TextView = findViewById(R.id.view_tittle2)
                var txt_score:TextView = findViewById(R.id.view_tittle3)
                txt_tittle.text = title.toString()
                txt_description.text = description.toString()
                txt_score.text = valor.toString()
            }

        btn_send.setOnClickListener(){
            userRef.collection(ReferenciasFirebase.ASSIGMENTS.toString()).document(id_assig.toString()).update("puntos","100")

        }


    }
}