package com.private_projects.tenews.ui.allnews

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.FragmentAllNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.utils.ConnectionStatus
import com.private_projects.tenews.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AllNewsFragment :
    ViewBindingFragment<FragmentAllNewsBinding>(FragmentAllNewsBinding::inflate) {
    private val scope by lazy {
        getKoin().getOrCreateScope<AllNewsFragment>(SCOPE_ID)
    }
    private val adapter: AllNewsPagerAdapter by lazy {
        scope.get(named("all_news_adapter"))
    }
    private val viewModel: AllNewsViewModel by lazy {
        scope.get(named("all_news_view_model"))
    }
    private val connectionErrorScreen: ConstraintLayout by lazy {
        requireActivity().findViewById(R.id.connection_error_block)
    }
    private val connectionRetry: AppCompatImageButton by lazy {
        requireActivity().findViewById(R.id.connection_retry)
    }
    private val connectionState: ConnectionStatus by lazy {
        ConnectionStatus()
    }
    private val parentActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        itemClickListener()
        showProgress()
    }

    private fun checkConnection() {
        if (!connectionState.check(requireContext())) {
            connectionErrorScreen.visibility = View.VISIBLE
            connectionRetry.setOnClickListener {
                checkConnection()
                parentActivity.onRefresh()
            }
        } else {
            connectionErrorScreen.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        checkConnection()
        getNews()
    }

    companion object {
        private const val SCOPE_ID = "all_news_scope_id"
    }

    private fun initRV() {
        binding.allNewsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.allNewsRv.adapter = adapter
    }

    private fun getNews() {
        viewModel.getAllNews().observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }

    private fun itemClickListener() {
        adapter.onItemClick = { list ->
            parentActivity.showDetails(list)
        }
    }

    private fun showProgress() {
        adapter.addLoadStateListener { state ->
            parentActivity.setProgress(state.refresh is LoadState.Loading)
            binding.allNewsRv.smoothScrollToPosition(0)
        }
    }
}