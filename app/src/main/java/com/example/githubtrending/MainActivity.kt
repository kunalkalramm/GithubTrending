package com.example.githubtrending

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {

    private val githubTrendingViewModel: GithubTrendingViewModel by viewModel()
    private val adapter: RecyclerViewRepositoryAdapter by inject {
        parametersOf(this)
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        recyclerView = mainBinding.root.recyclerViewRepositories
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        recyclerView.adapter = adapter

        githubTrendingViewModel.repositoriesLiveData.observe(this, Observer {
            adapter.setRepositories(it)
        })

    }

}
