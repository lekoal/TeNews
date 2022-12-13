package com.private_projects.tenews.ui.ixbt

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.databinding.FragmentIxbtNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
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
    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentActivity = requireActivity() as MainActivity

        initRV()
        getNews()
        itemClickListener()
        showProgress()
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
            Toast.makeText(requireContext(), list[1], Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgress() {
        adapter.addLoadStateListener { state ->
            parentActivity.setProgress(state.refresh is LoadState.Loading)
        }
    }
}