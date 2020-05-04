package com.lambadam.projectwords.playscene.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lambadam.projectwords.R

/**
 * Kullanıcı Doğru kelimeleri sectiğinde recyclerview da gösterdiğimiz adapter
 */
class WordsAdapter(
    private val wordsList: List<String> = emptyList()
) : RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    private val mutableWordList: MutableList<String> = wordsList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_words_adapter, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mutableWordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvWord.text = mutableWordList[position]
    }

     fun addNewWord(word: String) {
         mutableWordList.add(word)
         notifyItemInserted(mutableWordList.size-1)
     }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tvWord)
    }

}