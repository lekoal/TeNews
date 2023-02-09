package com.private_projects.tenews.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import coil.load
import coil.size.Scale
import com.private_projects.tenews.R
import com.private_projects.tenews.data.details.NewsDetailsEntity
import com.private_projects.tenews.databinding.FragmentDetailsBinding
import com.private_projects.tenews.ui.main.MainActivity
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

    private fun setViews(entity: NewsDetailsEntity) {
        binding.detailsFirstTitle.text = entity.header.firstTitle
        binding.detailsSecondTitle.text = entity.header.secondTitle
        binding.detailAuthor.text = entity.header.ownerDomain
        binding.detailsDate.text = entity.header.newsDate

        val textSize = entity.textBlocks?.size ?: 0
        val imageSize = entity.imageBlocks?.size ?: 0
        val videoSize = entity.videoBlocks?.size ?: 0
        val totalSize = textSize + imageSize + videoSize
        binding.detailsContent.let { layout ->
            layout.removeAllViews()
            for (i in 0 until totalSize) {
                entity.imageBlocks?.forEach {
                    if (it.position == i) {
                        val imageView = AppCompatImageView(requireContext())
                        imageView.layoutParams = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
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
                        textView.textSize = 16F
                        if (
                            it.content.contains("Фото:") ||
                            it.content.contains("Источник изображений:") ||
                            it.content.contains("Источник изображения:")
                        ) {
                            textView.text =
                                requireActivity().getString(R.string.photo_source_text, it.content)
                        } else {
                            textView.text = it.content
                        }
                        layout.addView(textView)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}