package com.poi.camppus.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poi.camppus.ui.groups.FragmentFiles
import com.poi.camppus.ui.groups.FragmentPost
import com.poi.camppus.ui.groups.FragmentsGroupAssigments

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle,IdGrupo:String):
    FragmentStateAdapter(fragmentManager,lifecycle) {
    var id = IdGrupo

    override fun getItemCount(): Int {

        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0 ->{
                FragmentPost(id)

            }

            1 ->{
                FragmentFiles()
            }

            2 ->{
                FragmentsGroupAssigments()
            }

            else -> {
                Fragment()
            }
        }
    }
}