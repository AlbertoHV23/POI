package com.poi.camppus.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.poi.camppus.R
import com.poi.camppus.activities.LoginActivity
import com.poi.camppus.activities.SendmessagesActivity
import com.squareup.picasso.Picasso

class Porfileragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_profile, container, false)
        var btn_logOut:Button = root.findViewById(R.id.btn_logOut)

        btn_logOut.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            startActivity(intent)



        }
        return root
    }

}