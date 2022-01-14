package com.kareem.news_app.ui.article_details

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.kareem.news_app.R
import com.kareem.news_app.databinding.FragmentArticleDetailsBinding
import com.kareem.news_app.view_models.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import kotlin.coroutines.CoroutineContext


class ArticleDetailsFragment : Fragment() {
    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var args: ArticleDetailsFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        setupView()
    }

    private fun getArgs() {
        args = ArticleDetailsFragmentArgs.fromBundle(requireArguments())
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun setupView() {
        binding.apply {
            args.title?.let { tvArticleTitle.text = it }
            args.publishedAt?.let { tvArticlePublishedAt.text = "Published at: $it" }
            args.author?.let { tvArticleAuthor.text = "Author: $it" }
            args.description?.let { tvArticleDescription.text = "Description: $it" }
            args.content?.let { tvArticleContent.text = "Content: $it" }
            args.urlOfImage?.let { ivArticleImage.load(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}