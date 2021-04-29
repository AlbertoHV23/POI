package com.poi.camppus.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.poi.camppus.R

class Porfileragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        var root =  inflater.inflate(R.layout.fragment_profile, container, false)


        return root
    }

}