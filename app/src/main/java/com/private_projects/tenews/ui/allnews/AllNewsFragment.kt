package com.private_projects.tenews.ui.allnews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.private_projects.tenews.databinding.FragmentAllNewsBinding
import com.private_projects.tenews.ui.main.MainActivity
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
        private const val SCOPE_ID = "all_news_scope_id"
    }

    private fun initRV() {
        binding.allNewsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.allNewsRv.adapter = adapter
    }

    private fun getNews() {
        viewModel.getNews().observe(viewLifecycleOwner) { pagingData ->
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