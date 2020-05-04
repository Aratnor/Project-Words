package com.lambadam.projectwords.playscene.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lambadam.projectwords.R
import com.lambadam.projectwords.models.LevelLetters
import com.lambadam.projectwords.playscene.contract.OnAdapterUpdate
import com.lambadam.projectwords.util.GameUtil
import java.util.*

/**
 * Kullanıcıların sectikleri harfleri recyclerView gösterdiğimiz adapter
 *
 */
class LetterAdapter: RecyclerView.Adapter<LetterAdapter.ViewHolder> {
    private val letters: LevelLetters

    /**
     * Kullanıcı tıklandığında Stack(Last In First Out-LIFO)yapısında tutuyoruz isimizi kolaylastırıyor
     * kelimeleri göstermek icin
     */
    private val clickStack: Stack<Char> = Stack()

    private var clickCount: Int = 0

    private val listener: OnAdapterUpdate

    private val stringBuilder: StringBuilder = StringBuilder()


        constructor(letterValue: LevelLetters,fragmentListener: OnAdapterUpdate): super() {
            letters = letterValue
            listener = fragmentListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context).inflate(R.layout.item_letters_adapter, parent, false)

        return ViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int {
       return letters.letterList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentLetter = letters.letterList[position]
        /**
         * Büyükharf yaptığında bazı türkçe karakter kaybolduğu icin kullanılan cözüm
         */
        when(currentLetter) {
            'i' -> currentLetter =  'İ'
            'ğ' -> currentLetter = 'Ğ'
            'ç' -> currentLetter = 'Ç'
        }

        // Kullanıcı Harf secimini ve secimi kaldırmasını sağlayan kod
        holder.textView.text = currentLetter.toString().toUpperCase(Locale("tr-TR"))
        holder.llChar.tag = position
        holder.llChar.setOnClickListener {
            if(holder.llChar.tag == position) {
                holder.llChar.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.orange))
                holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
                holder.llChar.tag = -1
                clickStack.add(currentLetter)
                clickCount++
                stringBuilder.append(currentLetter)
                listener.onSelectedChar(stringBuilder.toString())
                if(clickCount == GameUtil.getWordLengthByDifficulty(letters.difficulty)) {
                    wordEntered()
                }
            } else if(!clickStack.isEmpty() &&  clickStack.peek() == currentLetter) {
                holder.llChar.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white80transparent))
                holder.textView.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.black))
                clickStack.pop()
                if(stringBuilder.isNotEmpty()) stringBuilder.deleteCharAt(stringBuilder.length - 1)
                listener.deselectedChar(stringBuilder.toString())
                clickCount--
                holder.llChar.tag = position
            }
        }
    }

    /**
     * Kullanıcı verilen zorluktaki kelime uzunluğunda harf sectiginde buraya girer
     */
    private fun wordEntered()  {
        val builder = StringBuilder()
        while(!clickStack.isEmpty()) {
            builder.append(clickStack.pop())
        }
        val enteredWord = builder.toString().reversed().toUpperCase(Locale.getDefault())

        var isFoundWord = false
        for(word in letters.words) {
            if(enteredWord == word.value.toUpperCase(Locale.getDefault())) {
                isFoundWord = true
                break
            }
        }
        listener.onFinish(isFoundWord, enteredWord)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tvChar)
        val llChar: CardView = itemView.findViewById(R.id.llChar)
    }
}