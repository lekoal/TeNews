package com.private_projects.tenews.ui.ferra

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.FragmentFerraNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.utils.ConnectionStatus
import com.private_projects.tenews.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class FerraNewsFragment :
    ViewBindingFragment<FragmentFerraNewsBinding>(FragmentFerraNewsBinding::inflate) {
    private val scope by lazy {
        getKoin().getOrCreateScope<FerraNewsFragment>(SCOPE_ID)
    }
    private val adapter: FerraPagerAdapter by lazy {
        scope.get(named("ferra_adapter"))
    }
    private val viewModel: FerraViewModel by lazy {
        scope.get(named("ferra_view_model"))
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

    companion object {
        private const val SCOPE_ID = "ferra_news_scope_id"
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

    private fun initRV() {
        binding.ferraRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ferraRv.adapter = adapter
    }

    private fun getNews() {
        viewModel.getFerraNews().observe(viewLifecycleOwner) { pagingData ->
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
            binding.ferraRv.smoothScrollToPosition(0)
        }
    }
}