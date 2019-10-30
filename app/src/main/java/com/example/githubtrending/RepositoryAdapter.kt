package com.example.githubtrending

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubtrending.databinding.RepositoryLayoutBinding

class RepositoryAdapter(val context: Context) : RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private var repositories: List<ViewModelRepositoryModel> = arrayListOf()
//    private var expandedPosition: Int? = null

    fun setrepositories(repositories: List<ViewModelRepositoryModel>) {
        this.repositories = repositories
        // TODO: Change way to update the adapter
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        /*val itemView = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)*/

        val repositoryLayoutBinding: RepositoryLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.repository_layout, parent, false)
        return RepositoryHolder(repositoryLayoutBinding)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val currentRepository = repositories[position]
        holder.repositoryLayoutBinding.repository = currentRepository
//        expandedPosition.let {
//            if (it == holder.adapterPosition) {
//                expandConstraints()
//            } else {
//                resetDefaultConstraints()
//            }
//        }

//        holder.avatar.setOnClickListener {
//            val someData = repositories[holder.adapterPosition]
//            expandedPosition = holder.adapterPosition
//            notifyItemChanged(holder.adapterPosition)
//        }

//
//        Glide.with(context)
//            .load(currentRepository.avatarURL)
//            .into(holder.avatar)
    }


    class RepositoryHolder(val repositoryLayoutBinding: RepositoryLayoutBinding) : RecyclerView.ViewHolder(repositoryLayoutBinding.root)

}