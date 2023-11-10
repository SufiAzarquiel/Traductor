package net.azarquiel.traductor.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.traductor.R
import net.azarquiel.traductor.model.Word

class WordAdapter(val context: Context,
                    val layout: Int
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    private var dataList: List<Word> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setWords(words: List<Word>) {
        this.dataList = words
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Word){
            var tvSpanish = itemView.findViewById(R.id.tvSpanish) as TextView
            var tvEnglish = itemView.findViewById(R.id.tvEnglish) as TextView

            tvSpanish.text = dataItem.spWord
            tvEnglish.text = dataItem.enWord

            itemView.tag = dataItem

        }

    }
}