package com.private_projects.tenews.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.tabs.TabLayoutMediator
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.ActivityMainBinding
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

const val ALL_NEWS_FRAGMENT = 0
const val IXBT_NEWS_FRAGMENT = 1
const val FERRA_NEWS_FRAGMENT = 2
const val TD_NEWS_FRAGMENT = 3

class MainActivity : AppCompatActivity() {
    private val scope by lazy {
        getKoin().getOrCreateScope<MainActivity>(SCOPE_ID)
    }
    private val viewModel: MainViewModel by lazy {
        scope.get(named("main_view_model"))
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.viewPager.adapter = MainViewPagerAdapter(this)

        attachTabs()
        checkProgress()
    }

    companion object {
        private const val SCOPE_ID = "main_scope_id"
    }

    private fun attachTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                ALL_NEWS_FRAGMENT -> tab.text = resources.getString(R.string.all_news)
                IXBT_NEWS_FRAGMENT -> tab.text = resources.getString(R.string.ixbt_news)
                FERRA_NEWS_FRAGMENT -> tab.text = resources.getString(R.string.ferra_news)
                TD_NEWS_FRAGMENT -> tab.text = resources.getString(R.string.td_news)
            }
        }.attach()
    }

    private fun checkProgress() {
        viewModel.loading.observe(this) { isLoading ->
            if (isLoading) {
                binding.blockScreen.visibility = View.VISIBLE
            } else {
                binding.blockScreen.visibility = View.GONE
            }
            binding.blockScreen.isClickable = isLoading
        }
    }

    fun setProgress(isShow: Boolean) {
        viewModel.load(isShow)
    }
}