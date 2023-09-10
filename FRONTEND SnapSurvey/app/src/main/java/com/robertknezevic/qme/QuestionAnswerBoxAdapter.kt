package com.robertknezevic.qme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Answer
import com.robertknezevic.qme.models.Question

class QuestionAnswerBoxAdapter(var question: Question, private val recyclerView: RecyclerView) : RecyclerView.Adapter<QuestionAnswerBoxAdapter.ViewHolder>() {


    fun getAnswerFromViewHolder(position: Int, userId: String): Answer? {
        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? ViewHolder
        return viewHolder?.getAnswer(userId)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var questionTextView: TextView = itemView.findViewById(R.id.questionTextView)


        fun getAnswer(userId: String): Answer {
            val answerEditText = itemView.findViewById<EditText>(R.id.answerBox)
            val answerText = answerEditText.text.toString()

            return Answer(
                QuestionId = question.Id,
                AnswerText = answerText,
                SelectedOptionId = null,
                UserId = userId
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_question_answer_box_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionTextView.text = question.QuestionText
    }
    override fun getItemCount(): Int {
        // Samo jedno pitanje
        return 1
    }


}
