package com.example.githubtrending

import androidx.lifecycle.*
import com.example.githubtrending.models.RepositoryModel
import com.example.githubtrending.models.RoomModel
import com.example.githubtrending.networkService.ApiCallResult
import com.example.githubtrending.repository.TopRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class MainActivityViewModel(private val repository: TopRepository) : ViewModel(), KoinComponent {

    private val _repositoryLiveData: MutableLiveData<ViewState<List<RepositoryModel>>> = MutableLiveData()
    private var fetchDataJob: Job? = null

    val repositoryLiveData: LiveData<ViewState<List<RepositoryModel>>>
        get() = _repositoryLiveData


    init {
        loadData()
    }

    fun loadData() {
        if(fetchDataJob?.isActive == true) {
            return
        }
        launchJob()
    }

    private fun launchJob() {
        fetchDataJob = viewModelScope.launch {
            _repositoryLiveData.postValue(ViewState.inProgress(showSpinner = true))
            val result = repository.fetchDataFromApiAndDump()
            when (result) {
                is ApiCallResult.Failure -> {
                    _repositoryLiveData.postValue(ViewState.onNetworkError(result.exception))
                }
                else -> {
                    val tempRepositoryData = repository.fetchRepositoryData()
                    if(tempRepositoryData.isNotEmpty()) {
                        _repositoryLiveData.postValue(ViewState.onSuccess(
                            tempRepositoryData.map {
                                it.toRepositoryModel()
                            }
                        ))
                    }
                }
            }
        }
    }


    private fun RoomModel.toRepositoryModel(): RepositoryModel {
        return RepositoryModel(
            author = this.author ?: DEFAULT_TEXT,
            repoName = this.repoName,
            language = this.language ?: DEFAULT_TEXT,
            languageColor = this.languageColor ?: DEFAULT_COLOR_HEX,
            description = this.description ?: DEFAULT_TEXT,
            forks = this.forks ?: DEFAULT_NUMBER,
            stars = this.stars ?: DEFAULT_NUMBER,
            repoURL = this.repoURL,
            avatarURL = this.avatarURL
        )
    }

    companion object {
        const val DEFAULT_TEXT = ""
        const val DEFAULT_NUMBER = 0
        const val DEFAULT_COLOR_HEX = "#0000ff"  //Blue colour
    }
}