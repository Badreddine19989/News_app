package com.example.news.breakingnews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentBreakingNewsBinding
import com.example.news.recyclerview.ArticleAdapter
import com.example.news.recyclerview.ArticleListener

class BreakingNews : Fragment() {
    private lateinit var viewModel: BreakingNewsViewModel
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentBreakingNewsBinding.inflate(inflater)

        //Creating and binding the BreakingNews View Model
        viewModel = ViewModelProvider(this).get(BreakingNewsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Creating the ArticleAdapter with a click listener
        //binding the Adapter
        articleAdapter = ArticleAdapter(ArticleListener { article ->
            this.findNavController()
                .navigate(BreakingNewsDirections.actionBreakingNewsToNewsArticle(article))
        })
        binding.articleList.adapter = articleAdapter

        //Observing the apiresponse variable and setting the results to the adapter
        viewModel.apiresponse.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }

        return binding.root
    }

}