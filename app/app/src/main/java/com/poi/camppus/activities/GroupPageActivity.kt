package com.poi.camppus.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.poi.camppus.R
import com.poi.camppus.adapters.ViewPagerAdapter

class GroupPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Camppus_login)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_page)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.viewpager)

        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        viewPager.adapter = adapter

        var NOMBRE=  intent.getStringExtra("NAMETEAM")
        var txt_name:TextView = findViewById(R.id.txt_GroupName3)

        txt_name.text = NOMBRE

        TabLayoutMediator(tabLayout,viewPager){
            tab,position->
            when(position){
                0 ->{
                    tab.text = "Posts"
                }
                1 ->{
                    tab.text = "Files"
                }
                2 ->{
                    tab.text = "Assigments"
                }
            }
        }.attach()

    }
}