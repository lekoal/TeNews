package com.private_projects.tenews.ui.tdnews

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.FragmentTDNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.utils.ConnectionStatus
import com.private_projects.tenews.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class TDNewsFragment :
    ViewBindingFragment<FragmentTDNewsBinding>(FragmentTDNewsBinding::inflate) {
    private val scope by lazy {
        getKoin().getOrCreateScope<TDNewsFragment>(SCOPE_ID)
    }
    private val adapter: TDNewsPagerAdapter by lazy {
        scope.get(named("tdnews_adapter"))
    }
    private val viewModel: TDNewsViewModel by lazy {
        scope.get(named("tdnews_view_model"))
    }
    private val connectionErrorScreen: ConstraintLayout by lazy {
        requireActivity().findViewById(R.id.connection_error_block)
    }
    private lateinit var parentActivity: MainActivity

    companion object {
        private const val SCOPE_ID = "tdnews_scope_id"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as MainActivity

        initRV()
        itemClickListener()
        showProgress()
        val connectionState = ConnectionStatus()
        if (!connectionState.check(requireContext())) {
            connectionErrorScreen.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        getNews()
    }

    private fun initRV() {
        binding.tdnewsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.tdnewsRv.adapter = adapter
    }

    private fun getNews() {
        viewModel.getTDNews().observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
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
            binding.tdnewsRv.smoothScrollToPosition(0)
        }
    }
}