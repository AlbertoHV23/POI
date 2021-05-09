package com.poi.camppus.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.poi.camppus.R
import com.poi.camppus.activities.LoginActivity


class Porfileragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_profile, container, false)
        var btn_logOut:Button = root.findViewById(R.id.btn_logOut)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()

        val googleSignInClient = GoogleSignIn.getClient(root.context, gso)


        btn_logOut.setOnClickListener(){

            FirebaseAuth.getInstance().signOut()
            googleSignInClient.signOut();
            val intent = Intent(getActivity(), LoginActivity::class.java)
            startActivity(intent)



        }
        return root
    }

}