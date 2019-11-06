package com.example.githubtrending

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.databinding.ActivityMainBinding
import com.example.githubtrending.models.RepositoryModel
import com.example.githubtrending.utils.NetworkHelper
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel: MainActivityViewModel by viewModel()
    private val adapter: RecyclerViewRepositoryAdapter by inject {
        parametersOf(this)
    }
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.layoutNoInternetError.viewModel = mainActivityViewModel
        recyclerView = mainBinding.root.recyclerViewRepositories
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.hasFixedSize()

        recyclerView.adapter = adapter

        mainActivityViewModel.repositoryLiveData.observe(this, Observer {viewState->
            viewState?.let {
                handleViewState(it)
            }
        })

    }
    private fun handleViewState(viewState: ViewState<List<RepositoryModel>>) {
        when(viewState) {
            is ViewState.inProgress -> {
                handleProgress(viewState)
            }

            is ViewState.onNetworkError-> {
                handleError(viewState)
            }

            is ViewState.onSuccess-> {
                handleSuccess(viewState)
            }
        }
    }

    private fun handleSuccess(viewState: ViewState.onSuccess<List<RepositoryModel>>) {
        toggleViewVisibilities(
            recyclerViewVisibility = true,
            noInternetErrorLayoutVisibility = false,
            loaderVisibility = false
        )
        adapter.setRepositories(viewState.data)
    }

    private fun handleProgress(viewState: ViewState.inProgress<List<RepositoryModel>>) {
        toggleViewVisibilities(
            recyclerViewVisibility = false,
            noInternetErrorLayoutVisibility = false,
            loaderVisibility = true
        )
    }

    private fun handleError(viewState: ViewState.onNetworkError<List<RepositoryModel>>) {
        toggleViewVisibilities(
            recyclerViewVisibility = false,
            noInternetErrorLayoutVisibility = true,
            loaderVisibility = false
        )
    }

    private fun toggleViewVisibilities(recyclerViewVisibility: Boolean, noInternetErrorLayoutVisibility: Boolean, loaderVisibility: Boolean) {

        mainBinding.recyclerViewRepositories.visibility = if(recyclerViewVisibility) View.VISIBLE else View.GONE
        mainBinding.layoutNoInternetError.root.visibility = if(noInternetErrorLayoutVisibility) View.VISIBLE else View.GONE
        mainBinding.layoutLoading.visibility = if(loaderVisibility) View.VISIBLE else View.GONE

    }

}
