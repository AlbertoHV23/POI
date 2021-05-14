package com.poi.camppus.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.poi.camppus.R
import com.poi.camppus.activities.LoginActivity
import com.poi.camppus.adapters.PostAdapter
import com.poi.camppus.models.ReferenciasFirebase
import com.poi.camppus.models.tbl_Usuarios
import com.poi.camppus.models.tbl_posts
import com.squareup.picasso.Picasso


class PorfileFragment : Fragment() {
    private  var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebase  = FirebaseFirestore.getInstance();



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
        var img_perfil:ImageButton = root.findViewById(R.id.imageButton2)




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
                    .into(img_perfil)


            }

        var btn_logOut:Button = root.findViewById(R.id.btn_logOut)

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
        return root
    }

}