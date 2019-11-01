package com.example.githubtrending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var githubTrendingViewModel: GithubTrendingViewModel
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        recyclerView = mainBinding.root.recyclerViewRepositories
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        val adapter = RepositoryAdapter(this)
        recyclerView.adapter = adapter

        githubTrendingViewModel = ViewModelProviders.of(this).get(GithubTrendingViewModel::class.java)
        githubTrendingViewModel.repositoriesLiveData.observe(this, Observer {
            adapter.setRepositories(it)
        })

    }

}
