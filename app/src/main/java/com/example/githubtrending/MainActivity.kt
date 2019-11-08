package com.example.githubtrending

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.databinding.ActivityMainBinding
import com.example.githubtrending.models.RepositoryModel
import com.example.githubtrending.utils.NetworkHelper
import com.example.githubtrending.utils.spannableUtils.CustomSpannableString
import com.example.githubtrending.utils.spannableUtils.SpannableStringHelper
import com.example.githubtrending.utils.spannableUtils.SpannableStringProperties
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.lang.StringBuilder


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

        mainBinding.layoutNoInternetError.tvSampleTextView.text = setString()

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

    fun setString(): SpannableStringBuilder {
        val primaryString: String = "Jack Evans"
        val secondaryString: String = "-54321"
//        val context: Context = this
//
//        val rc = ForegroundColorSpan(Color.RED)
//        val yc = ForegroundColorSpan(Color.YELLOW)
//        val cc = BackgroundColorSpan(resources.getColor(R.color.colorAccent))
//        val size = AbsoluteSizeSpan(20, true)
//
//        val typeface: Typeface? = ResourcesCompat.getFont(context, R.font.)
//
//            ContextCompat.getColor(context)
//        val primarySpannableString = SpannableString(primaryString)
//        primarySpannableString.setSpan(cc, 0, primaryString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        primarySpannableString.setSpan(TypefaceSpan(typeface!!), 0, primaryString.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        val spannableStringBuilder = SpannableStringBuilder().append()
//            .append(primarySpannableString)
//            .append(secondaryString)

        val properties1 =  SpannableStringProperties.Builder()
            .setFontSize(32)
            .setFontColorResId(R.color.colorAccent)
            .setFonttypeface(Typeface.create("lato", Typeface.BOLD_ITALIC))
            .build()

        val properties =  SpannableStringProperties.Builder()
            .setFontSize(42)
            .setFontColorResId(R.color.color_52575C)
            .setFonttypeface(Typeface.create("lato", Typeface.ITALIC))
            .build()

        val map: HashMap<String, SpannableStringProperties> = hashMapOf()
        map.put(primaryString, properties)
        map.put(secondaryString, properties1)




        val spannableStringHelper = SpannableStringHelper(this, map)
        return spannableStringHelper.getSpannableStringBuilder()

    }

}
