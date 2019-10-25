package com.example.githubtrending

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var githubTrendingViewModel: GithubTrendingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewRepositories)
        val adapter = RepositoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()

        githubTrendingViewModel = ViewModelProviders.of(this).get(GithubTrendingViewModel::class.java)
        githubTrendingViewModel.repositoriesLiveData.observe(this, Observer {
            adapter.setrepositories(it)
        })
    }

}
