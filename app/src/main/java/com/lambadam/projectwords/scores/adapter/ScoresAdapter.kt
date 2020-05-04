package com.lambadam.projectwords.scores.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lambadam.projectwords.R
import com.lambadam.projectwords.models.HighScore

/**
 * Kullanıcının Sectiği zorluğa göre yüksek skor listelediğimiz yer
 */
class ScoresAdapter(private val scores: List<HighScore>): RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_scores_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvScores.text = String.format("%s %s %s %s",
            "${position+1}.",
           scores[position].username,
            " -- Score : ",scores[position].score)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvScores: TextView = itemView.findViewById(R.id.tvScores)
    }

}