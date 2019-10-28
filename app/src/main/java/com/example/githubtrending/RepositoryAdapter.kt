package com.example.githubtrending

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RepositoryAdapter(val context: Context): RecyclerView.Adapter<RepositoryAdapter.RepositoryHolder>() {

    private var repositories: List<ViewModelRepositoryModel> = arrayListOf()

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

        Glide.with(context)
            .load(currentRepository.avatarURL)
            .into(holder.avatar)
    }


    class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var author: TextView = itemView.findViewById(R.id.textUsername)
        var repoName: TextView = itemView.findViewById(R.id.textRepoName)
        var stars: TextView = itemView.findViewById(R.id.textStars)
        var forks: TextView = itemView.findViewById(R.id.textForks)
        var avatar: ImageView = itemView.findViewById(R.id.imageAvatar)
    }

}