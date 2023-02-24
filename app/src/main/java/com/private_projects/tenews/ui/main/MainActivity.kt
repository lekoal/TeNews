package com.private_projects.tenews.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.ActivityMainBinding
import com.private_projects.tenews.ui.details.DetailsFragment
import com.private_projects.tenews.utils.ConnectionStatus
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
    private val connectionErrorScreen: ConstraintLayout by lazy {
        findViewById(R.id.connection_error_block)
    }
    private val connectionRetry: AppCompatImageButton by lazy {
        findViewById(R.id.connection_retry)
    }
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewPagerAdapter: MainViewPagerAdapter by lazy {
        MainViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.viewPager.adapter = viewPagerAdapter

        attachTabs()
        checkProgress()
        waitOnRefresh()
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

    fun showDetails(params: List<String>) {
        val connectionState = ConnectionStatus()
        if (!connectionState.check(this)) {
            connectionErrorScreen.visibility = View.VISIBLE
            connectionRetry.setOnClickListener {
                onRefresh()
                showDetails(params)
            }
        } else {
            connectionErrorScreen.visibility = View.GONE
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.details_show, R.anim.details_hide)
                .replace(binding.detailsContainer.id, DetailsFragment.newInstance(params))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun waitOnRefresh() {
        binding.refreshFab.setOnClickListener {
            onRefresh()
        }
    }

    fun onRefresh() {
        when (binding.tabLayout.selectedTabPosition) {
            ALL_NEWS_FRAGMENT -> {
                fragmentRefresh(ALL_NEWS_FRAGMENT)
            }
            IXBT_NEWS_FRAGMENT -> {
                fragmentRefresh(IXBT_NEWS_FRAGMENT)
            }
            FERRA_NEWS_FRAGMENT -> {
                fragmentRefresh(FERRA_NEWS_FRAGMENT)
            }
            TD_NEWS_FRAGMENT -> {
                fragmentRefresh(TD_NEWS_FRAGMENT)
            }
        }
    }

    private fun fragmentRefresh(fragment: Int) {
        viewPagerAdapter.fragments[fragment].refresh()
    }

    fun blockDetails(isBlock: Boolean) {
        if (isBlock) {
            binding.detailsBlockScreen.visibility = View.VISIBLE
            binding.detailsBlockScreen.isClickable = true
            binding.detailsBlockScreen.setOnClickListener {
                supportFragmentManager.popBackStack()
            }
        } else {
            binding.detailsBlockScreen.visibility = View.GONE
            binding.detailsBlockScreen.isClickable = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}