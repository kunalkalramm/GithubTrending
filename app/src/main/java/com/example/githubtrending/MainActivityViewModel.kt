package com.example.githubtrending

import android.graphics.Color
import android.graphics.fonts.FontFamily
import android.graphics.fonts.FontStyle
import android.net.Network
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.*
import com.example.githubtrending.models.RepositoryModel
import com.example.githubtrending.models.RoomModel
import com.example.githubtrending.networkService.ApiCallResult
import com.example.githubtrending.repository.TopRepository
import com.example.githubtrending.utils.NetworkHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel(private val repository: TopRepository) : ViewModel(), KoinComponent, NetworkHelper.NetworkListener {

    private val _repositoryLiveData: MutableLiveData<ViewState<List<RepositoryModel>>> = MutableLiveData()
    private val networkHelper: NetworkHelper by inject()
    private var fetchDataJob: Job? = null

    val repositoryLiveData: LiveData<ViewState<List<RepositoryModel>>>
        get() = _repositoryLiveData


    init {
        networkHelper.registerNetworkListener(this)
        loadData()
    }

    fun loadData() {
        if(fetchDataJob?.isActive == true) {
            return
        }
        launchJob()
    }

    override fun onAvailability(isAvailable: Boolean, network: Network?) {
        if(_repositoryLiveData.value is ViewState.onNetworkError && isAvailable) {
            loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkHelper.deregisterNetworkListener(this)
    }

    private fun launchJob() {
        fetchDataJob = viewModelScope.launch {
            _repositoryLiveData.postValue(ViewState.inProgress(showSpinner = true))
            when (val result = repository.fetchDataFromApiAndDump()) {
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