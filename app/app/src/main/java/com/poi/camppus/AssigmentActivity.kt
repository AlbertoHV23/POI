package com.poi.camppus

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.poi.camppus.models.ReferenciasFirebase

class AssigmentActivity : AppCompatActivity() {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebase  = FirebaseFirestore.getInstance();
    private val fileResult = 1
    var urlImagen = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Camppus_login)

        setContentView(R.layout.activity_assigment)

        var id_assig = intent.getStringExtra("ID_TAREA")
        var ID_GRUPO = intent.getStringExtra("ID_GRUPO")

        var btn_send:Button = findViewById(R.id.btn_calificar)
        var btn_add:Button = findViewById(R.id.btn_addFile)





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
            userRef.collection(ReferenciasFirebase.ASSIGMENTS.toString()).document(id_assig.toString()).update("trabajo",urlImagen)
            finish()
        }

        btn_add.setOnClickListener(){
        filemanager()

        }


    }

    private fun filemanager(){
        //ABRE LA VENTA DEL FILENAMAGER PARA SELECCIONAR LA IMAGEN
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2 ){
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        }
        intent.type = "*/*"
        startActivityForResult(intent,fileResult)
    }

    //trae el elemento seleccionado del file manager
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {

                val clipData = data.clipData

                if (clipData != null){
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        uri?.let { fileUpload(it) }
                    }
                }else {
                    val uri = data.data
                    uri?.let { fileUpload(it) }
                }

            }
        }
    }

    private fun fileUpload(mUri: Uri) {
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Files")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                urlImagen =java.lang.String.valueOf(uri)

            }
        }.addOnFailureListener {
        }
    }
}