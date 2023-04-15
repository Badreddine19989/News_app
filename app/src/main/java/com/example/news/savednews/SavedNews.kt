package com.example.news.savednews

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.database.ArticleDatabase
import com.example.news.databinding.FragmentSavedNewsBinding
import com.example.news.recyclerview.ArticleAdapter
import com.example.news.recyclerview.ArticleListener

class SavedNews : Fragment() {

    private lateinit var viewModelFactory: SavedNewsViewModelFactory
    private lateinit var viewModel: SavedNewsViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSavedNewsBinding.inflate(inflater)
        setHasOptionsMenu(true)

        //Creating a database instance
        val application = requireNotNull(this.activity).application
        val dataSource = ArticleDatabase.getInstance(application).ArticleDatabaseDao

        //Creating the SaveNews View Model and biding it
        viewModelFactory = SavedNewsViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SavedNewsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //Creating the ArticleAdapter with a click listener
        //binding the Adapter
        articleAdapter = ArticleAdapter(ArticleListener { article ->
            this.findNavController()
                .navigate(SavedNewsDirections.actionSavedNewsToNewsArticle(article))
        })
        binding.articleList.adapter = articleAdapter

        //Swipe function to delete articles
        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteArticle(articleAdapter.currentList.get(position))
                Toast.makeText(context, "Article deleted", Toast.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(simpleItemTouchCallback).apply {
            attachToRecyclerView(binding.articleList)
        }

        //Observing the articles variable and setting the results to the adapter
        viewModel.articles.observe(viewLifecycleOwner) {
            articleAdapter.submitList(it)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.delete_all_articles, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            //dalete all articles
            R.id.DellAll -> {
                viewModel.deleteAllArticles()
                Toast.makeText(context, "All articles deleted", Toast.LENGTH_SHORT).show()
            }
        }

        return super.onContextItemSelected(item)
    }
}