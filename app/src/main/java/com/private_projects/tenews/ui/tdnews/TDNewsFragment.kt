package com.private_projects.tenews.ui.tdnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.R
import com.private_projects.tenews.databinding.FragmentTDNewsBinding
import com.private_projects.tenews.domain.RefreshFragmentContract
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.utils.ConnectionStatus
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class TDNewsFragment : RefreshFragmentContract() {
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
    private val connectionRetry: AppCompatImageButton by lazy {
        requireActivity().findViewById(R.id.connection_retry)
    }
    private val connectionState: ConnectionStatus by lazy {
        ConnectionStatus()
    }
    private val parentActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private var _binding: FragmentTDNewsBinding? = null
    private val binding: FragmentTDNewsBinding by lazy {
        _binding!!
    }

    companion object {
        private const val SCOPE_ID = "tdnews_scope_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTDNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
        itemClickListener()
        showProgress()
        getNews()
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
            binding.tdnewsRv.scrollToPosition(0)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun refresh() {
        getNews()
    }
}