package com.example.githubtrending

import android.content.Context
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtrending.databinding.RepositoryLayoutBinding
import com.example.githubtrending.models.RepositoryModel

class RecyclerViewRepositoryAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerViewRepositoryAdapter.RepositoryHolder>() {

    private var repositories: List<RepositoryModel> = arrayListOf()
    private var previousExpandedPosition: Int? = null

    private val expandedConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            clone(context, R.layout.repository_layout_base)
            setVisibility(R.id.expandableWidget, View.VISIBLE)
        }
    }

    private val baseConstraintSet: ConstraintSet by lazy {
        ConstraintSet().apply {
            clone(context, R.layout.repository_layout_base)
        }
    }


    fun setRepositories(repositories: List<RepositoryModel>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val repositoryLayoutBinding: RepositoryLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.repository_layout,
            parent,
            false
        )
        return RepositoryHolder(repositoryLayoutBinding)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {

        holder.repositoryLayoutBinding.repository = repositories[position]

        previousExpandedPosition?.let { it ->
            when (it) {
                holder.adapterPosition ->
                    applyExpandedConstraints(holder)
                else -> {
                    applyBaseConstraints(holder)
                }
            }
        } ?: kotlin.run {
            applyBaseConstraints(holder)
        }

        holder.repositoryLayoutBinding.arrowDown.setOnClickListener { view ->
            previousExpandedPosition?.let { it ->
                previousExpandedPosition = holder.adapterPosition
                notifyItemChanged(it)
            }
            previousExpandedPosition = holder.adapterPosition
            applyExpandedConstraints(holder)
        }

    }

    private fun applyExpandedConstraints(holder: RepositoryHolder) {
        TransitionManager.beginDelayedTransition(holder.repositoryLayoutBinding.constraintRepositoryMain)
        expandedConstraintSet.run {
            applyTo(holder.repositoryLayoutBinding.constraintRepositoryMain)
        }
    }

    private fun applyBaseConstraints(holder: RepositoryHolder) {
        TransitionManager.beginDelayedTransition(holder.repositoryLayoutBinding.constraintRepositoryMain)
        baseConstraintSet.run {
            baseConstraintSet.applyTo(holder.repositoryLayoutBinding.constraintRepositoryMain)
        }
    }

    class RepositoryHolder(val repositoryLayoutBinding: RepositoryLayoutBinding) : RecyclerView.ViewHolder(repositoryLayoutBinding.root)

}