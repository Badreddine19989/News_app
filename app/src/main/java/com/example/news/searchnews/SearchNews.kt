package com.example.news.searchnews

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.news.databinding.FragmentSearchNewsBinding
import com.example.news.recyclerview.ArticleAdapter
import com.example.news.recyclerview.ArticleListener

class SearchNews : Fragment() {
    private lateinit var viewModel: SearchNewsViewModel
    private lateinit var articleAdapter: ArticleAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchNewsBinding.inflate(inflater)

        //Creating and binding the SearchNews View Model
        viewModel = ViewModelProvider(this).get(SearchNewsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        //Creating the ArticleAdapter with a click listener
        //binding the Adapter
        articleAdapter = ArticleAdapter(ArticleListener { article ->
            this.findNavController()
                .navigate(SearchNewsDirections.actionSearchNewsToNewsArticle(article))
        })
        binding.articleList.adapter = articleAdapter

        //Observing the apiresponse variable and setting the results to the adapter
        viewModel.apiresponse.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }

        //Binding the searchBar and setting the search function
        //search articles
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchedNewsArticle(binding.searchBar.query.toString())
                return false
            }

        })
        return binding.root
    }
}