package com.poi.camppus.ui.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.poi.camppus.R
import com.poi.camppus.activities.LoginActivity
import com.poi.camppus.adapters.ListaUsuariosAdapter
import com.poi.camppus.adapters.PostAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_posts
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*


class PorfileFragment : Fragment() {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebase  = FirebaseFirestore.getInstance();
    private val database = Firebase.database
    private val fileResult = 1
    var urlImagen = ""
    lateinit var img_perfil:ImageButton

    lateinit var Usuario: tbl_Usuarios

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_profile, container, false)

        var names :String = ""
        var surname:String= ""
        var emails :String= ""
        var password:String= ""
        var imagen:String= ""

        var txt_perfilName:EditText = root.findViewById(R.id.txt_perfilName)
        var txt_perfilSurname:EditText = root.findViewById(R.id.txt_perfilSurname)
        var txt_perfilEmail:EditText = root.findViewById(R.id.txt_perfilEmail)
        var txt_perfilPassword:EditText = root.findViewById(R.id.txt_perfilPassword)
        img_perfil = root.findViewById(R.id.imageButton2)




        val userRef = firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email)
        userRef.get()
            .addOnSuccessListener{
                names = it.get("names").toString()
                surname = it.get("surname").toString()
                emails = it.get("emails").toString()
                password = it.get("password").toString()
                imagen = it.get("image").toString()

                txt_perfilName.setText(names)
                txt_perfilSurname.setText(surname)
                txt_perfilEmail.setText(emails)
                txt_perfilPassword.setText(password)
                Picasso.get()
                    .load(imagen)
                        .resize(300, 300)
                        .centerCrop()
                        .into(img_perfil)


            }


        userRef.addSnapshotListener(){
            messages,error ->
            if (error == null){
                messages?.let {
                    names = it.get("names").toString()
                    surname = it.get("surname").toString()
                    emails = it.get("emails").toString()
                    password = it.get("password").toString()
                    imagen = it.get("image").toString()

                    txt_perfilName.setText(names)
                    txt_perfilSurname.setText(surname)
                    txt_perfilEmail.setText(emails)
                    txt_perfilPassword.setText(password)
                    Picasso.get()
                            .load(imagen)
                            .resize(300, 300)
                            .centerCrop()
                            .into(img_perfil)

                }
            }
        }

        var btn_logOut:Button = root.findViewById(R.id.btn_logOut)
        var btn_save:Button = root.findViewById(R.id.btn_save)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

        val googleSignInClient = GoogleSignIn.getClient(root.context, gso)



        var useremail = auth.currentUser.email





        btn_logOut.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut();
            println(useremail)

            firebase.collection(ReferenciasFirebase.USERS.toString()).document(useremail).update("estado","Desconectado")
            val intent = Intent(getActivity(), LoginActivity::class.java)
            startActivity(intent)

        }

        img_perfil.setOnClickListener() {
            filemanager()


        }
        btn_save.setOnClickListener() {
            println("url: $urlImagen")

        }


        return root
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
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("User")
        val path =mUri.lastPathSegment.toString()
        val fileName: StorageReference = folder.child(path.substring(path.lastIndexOf('/')+1))

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->
                urlImagen =java.lang.String.valueOf(uri)
                firebase.collection(ReferenciasFirebase.USERS.toString()).document(auth.currentUser.email).update("image",urlImagen)

            }
        }.addOnFailureListener {
        }
    }

}