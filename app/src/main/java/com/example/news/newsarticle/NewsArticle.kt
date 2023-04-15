package com.example.news.newsarticle

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import com.example.news.R
import com.example.news.database.ArticleDatabase
import com.example.news.databinding.FragmentNewsArticleBinding

class NewsArticle : Fragment() {

    private lateinit var viewModelFactory: NewsArticleViewModelFactory
    private lateinit var viewModel: NewsArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewsArticleBinding.inflate(inflater)

        //Getting the article from the bundle
        val args = NewsArticleArgs.fromBundle(requireArguments())

        //Creating a database instance
        val application = requireNotNull(this.activity).application
        val dataSource = ArticleDatabase.getInstance(application).ArticleDatabaseDao

        //Creating the Article View Model and biding it
        viewModelFactory = NewsArticleViewModelFactory(dataSource, args.article)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsArticleViewModel::class.java)
        binding.viewModel = viewModel

        //Binding the webView
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(args.article.url)

        //Observe a Live Data variable to trigger a Toast event when pressing the Save Article FAB
        viewModel.showSnackbarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(context, "Article saved", Toast.LENGTH_SHORT).show()
                viewModel.doneShowingSnackbar()
            }

        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.share_menu, menu)
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            //Hide the menu item if it doesn't resolve
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            //Share Article
            R.id.share -> ShareArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    //Creating the sharing intent function
    fun getShareIntent(): Intent {
        return ShareCompat.IntentBuilder.from(requireActivity()).setText(viewModel.getArticleUrl())
            .setType("text/plain").intent
    }

    //Share Article funciton
    fun ShareArticle() {
        startActivity(getShareIntent())
    }

}