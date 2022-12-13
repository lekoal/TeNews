package com.private_projects.tenews.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.private_projects.tenews.R

const val ALL_NEWS_FRAGMENT = 0
const val IXBT_NEWS_FRAGMENT = 1
const val FERRA_NEWS_FRAGMENT = 2
const val TD_NEWS_FRAGMENT = 3

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}