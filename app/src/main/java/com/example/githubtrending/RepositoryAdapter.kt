package com.example.githubtrending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RepositoryAdapter: RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>(){

    var repositories: List<ViewModelRepositoryModel> = arrayListOf()

    fun setrepositories(repositories: List<ViewModelRepositoryModel>) {
        this.repositories = repositories
        // TODO: Change way to update the adapter
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.repository_layout, parent, false)
        return RepositoryHolder(itemView)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val currentRepository = repositories[position]
        holder.author.text = (currentRepository.author)
        holder.repoName.text = (currentRepository.repoName)
        holder.stars.text = (currentRepository.stars).toString()
        holder.forks.text = (currentRepository.forks).toString()
    }


    class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var author: TextView = itemView.findViewById(R.id.username)
        var repoName: TextView = itemView.findViewById(R.id.repoName)
        var stars: TextView = itemView.findViewById(R.id.starsNum)
        var forks: TextView = itemView.findViewById(R.id.forksNum)

    }
}