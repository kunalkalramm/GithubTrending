package com.example.githubtrending

sealed class ViewState<T> {
    class inProgress <T> (val showSpinner: Boolean): ViewState<T>()
    class onNetworkError <T> (val exception: Exception): ViewState<T>()
    class onSuccess <T> (val data: T): ViewState<T>()
}