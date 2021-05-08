package com.poi.camppus.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poi.camppus.ui.groups.FragmentFiles
import com.poi.camppus.ui.groups.FragmentPost
import com.poi.camppus.ui.groups.FragmentsGroupAssigments

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return  when(position){
            0 ->{
                FragmentPost()
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