package com.lambadam.projectwords.scores.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lambadam.projectwords.R
import com.lambadam.projectwords.models.Difficulty
import com.lambadam.projectwords.scores.contract.DifficultyAdapterContract
import com.lambadam.projectwords.util.GameUtil

/**
 * Yüksek skor alanında
 * Zorlukları listelediğimiz yer
 */
class DifficultyAdapter(list: List<Difficulty>,private val listener: DifficultyAdapterContract): RecyclerView.Adapter<DifficultyAdapter.ViewHolder>() {
    private val difficulties: List<Difficulty> = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_difficulty_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return difficulties.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDifficulty.text = GameUtil.getDifficultyPrettyFormat(difficulties[position].toString())

        holder.tvDifficulty.setOnClickListener { listener.onDifficultyClick(difficulties[position]) }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvDifficulty: Button = itemView.findViewById(R.id.btDifficulty)
    }

}