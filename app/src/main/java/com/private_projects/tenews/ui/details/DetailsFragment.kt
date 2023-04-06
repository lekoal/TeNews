package com.private_projects.tenews.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import com.private_projects.tenews.R
import com.private_projects.tenews.data.VkHelpData
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.databinding.FragmentDetailsBinding
import com.private_projects.tenews.ui.main.FERRA_NEWS_FRAGMENT
import com.private_projects.tenews.ui.main.IXBT_NEWS_FRAGMENT
import com.private_projects.tenews.ui.main.MainActivity
import com.private_projects.tenews.ui.main.TD_NEWS_FRAGMENT
import com.private_projects.tenews.utils.RssDateFormatter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class DetailsFragment : Fragment() {
    private val parentActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val scope by lazy {
        getKoin().getOrCreateScope<DetailsFragment>(SCOPE_ID)
    }
    private val viewModel: DetailsViewModel by lazy {
        scope.get(named("details_view_model"))
    }
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding by lazy {
        _binding!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getElements()
        loadCheck()
        checkStatus()
    }

    override fun onResume() {
        super.onResume()
        parentActivity.blockDetails(true)
    }

    override fun onStop() {
        parentActivity.blockDetails(false)
        parentActivity.setProgress(false)
        super.onStop()
    }

    companion object {
        private const val SCOPE_ID = "details_scope_id"
        private const val NEWS_URL = "news_url"
        private const val NEWS_ID = "news_id"
        private const val NEWS_DATE = "news_date"
        private const val NEWS_DOMAIN = "news_domain"
        fun newInstance(details: List<String>): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = Bundle()
            fragment.arguments?.putString(NEWS_URL, details[0])
            fragment.arguments?.putString(NEWS_ID, details[1])
            fragment.arguments?.putString(NEWS_DOMAIN, details[2])
            fragment.arguments?.putString(NEWS_DATE, details[3])
            return fragment
        }
    }

    private fun getElements() {
        val params = listOf(
            this.arguments?.getString(NEWS_URL).toString(),
            this.arguments?.getString(NEWS_ID).toString(),
            this.arguments?.getString(NEWS_DOMAIN).toString(),
            this.arguments?.getString(NEWS_DATE).toString()
        )
        viewModel.getElements(params)
        viewModel.resultEntity.observe(viewLifecycleOwner) {
            setViews(it)
        }
    }

    private fun loadCheck() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            parentActivity.setProgress(it)
        }
    }

    private fun checkStatus() {
        when (this.arguments?.getString(NEWS_DOMAIN)) {
            resources.getString(R.string.ixbt_news) -> status(IXBT_NEWS_FRAGMENT)
            resources.getString(R.string.ferra_news) -> status(FERRA_NEWS_FRAGMENT)
            resources.getString(R.string.td_news) -> status(TD_NEWS_FRAGMENT)
        }
    }

    private fun status(domain: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getStatus(domain).collect {
                if (it) {
                    showErrorMessage(this@DetailsFragment.arguments?.getString(NEWS_DOMAIN))
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun showErrorMessage(domain: String?) {
        Toast.makeText(requireContext(), resources.getString(R.string.details_load_error) +
                " ${domain.toString()}", Toast.LENGTH_SHORT).show()
    }

    private fun setViews(entity: NewsDetailsEntity) {
        binding.detailsFirstTitle.text = entity.header.firstTitle
        if (entity.header.secondTitle != null) {
            binding.detailsSecondTitle.visibility = View.VISIBLE
            binding.detailsSecondTitle.text = entity.header.secondTitle
        } else {
            binding.detailsSecondTitle.visibility = View.GONE
        }

        if (entity.header.ownerDomain.isEmpty()) {
            binding.detailAuthor.visibility = View.GONE
        } else {
            binding.detailAuthor.visibility = View.VISIBLE
            binding.detailAuthor.text = entity.header.ownerDomain
        }

        when (entity.header.ownerDomain) {
            VkHelpData.IXBT_DOMAIN -> {
                binding.detailsDate.text = entity.header.newsDate
            }
            else -> {
                val rssDateFormatter = RssDateFormatter()
                val date = rssDateFormatter.toDateFormat(entity.header.newsDate)
                binding.detailsDate.text = rssDateFormatter.format(date)
            }
        }

        binding.detailsContent.let { layout ->
            layout.removeAllViews()
            for (i in 0 .. receiveMaxPosition(entity)) {
                entity.imageBlocks?.forEach {
                    if (it.position == i) {
                        val imageView = AppCompatImageView(requireContext())
                        val params = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 20, 0, 0)
                        imageView.layoutParams = params
                        imageView.apply {
                            scaleType = ImageView.ScaleType.FIT_XY
                            load(it.url) {
                                placeholder(R.drawable.placeholder).scale(Scale.FILL)
                                crossfade(true)
                                crossfade(500)
                            }
                        }
                        layout.addView(imageView)
                    }
                }
                entity.textBlocks?.forEach {
                    if (it.position == i) {
                        val textView = AppCompatTextView(requireContext())
                        if (
                            it.content.contains("Фото:") ||
                            it.content.contains("Источник изображений:") ||
                            it.content.contains("Источник изображения:")
                        ) {
                            textView.textSize = 16F
                            textView.text =
                                requireActivity().getString(R.string.photo_source_text, it.content)
                        } else {
                            textView.textSize = 18F
                            textView.text = it.content
                        }
                        layout.addView(textView)
                    }
                }
            }
        }
    }

    private fun receiveMaxPosition(entity: NewsDetailsEntity): Int {
        var textMaxPosition = 0
        var imageMaxPosition = 0
        var videoMaxPosition = 0
        if (!entity.textBlocks.isNullOrEmpty()) {
            textMaxPosition = entity.textBlocks.last().position
        }
        if (!entity.imageBlocks.isNullOrEmpty()) {
            imageMaxPosition = entity.imageBlocks.last().position
        }
        if (!entity.videoBlocks.isNullOrEmpty()) {
            videoMaxPosition = entity.videoBlocks.last().position
        }
        return if (textMaxPosition > imageMaxPosition) textMaxPosition
        else if (videoMaxPosition > textMaxPosition) videoMaxPosition
        else imageMaxPosition
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}