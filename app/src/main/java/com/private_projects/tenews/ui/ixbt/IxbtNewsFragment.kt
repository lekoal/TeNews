package com.private_projects.tenews.ui.ixbt

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.FragmentIxbtNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.utils.ConnectionStatus
import com.private_projects.tenews.utils.ViewBindingFragment
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class IxbtNewsFragment :
    ViewBindingFragment<FragmentIxbtNewsBinding>(FragmentIxbtNewsBinding::inflate) {
    private val scope by lazy {
        getKoin().getOrCreateScope<IxbtNewsFragment>(SCOPE_ID)
    }
    private val adapter: IxbtPagerAdapter by lazy {
        scope.get(named("ixbt_adapter"))
    }
    private val viewModel: IxbtViewModel by lazy {
        scope.get(named("ixbt_view_model"))
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
    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as MainActivity

        initRV()
        itemClickListener()
        showProgress()
    }

    private fun checkConnection() {
        if (!connectionState.check(requireContext())) {
            connectionErrorScreen.visibility = View.VISIBLE
            connectionRetry.setOnClickListener {
                checkConnection()
            }
        } else {
            connectionErrorScreen.visibility = View.GONE
            parentActivity.onRefresh()
        }
    }

    override fun onResume() {
        super.onResume()
        checkConnection()
        getNews()
    }

    companion object {
        private const val SCOPE_ID = "ixbt_news_scope_id"
    }

    private fun initRV() {
        binding.ixbtRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ixbtRv.adapter = adapter
    }

    private fun getNews() {
        viewModel.getIxbtNews().observe(viewLifecycleOwner) { pagingData ->
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
            binding.ixbtRv.smoothScrollToPosition(0)
        }
    }
}