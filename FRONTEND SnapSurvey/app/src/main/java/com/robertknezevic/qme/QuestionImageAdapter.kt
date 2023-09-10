package com.robertknezevic.qme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Answer
import com.robertknezevic.qme.models.Question
import com.squareup.picasso.Picasso

class QuestionImageAdapter(private val question: Question) : RecyclerView.Adapter<QuestionImageAdapter.ViewHolder>() {

    init {
        val question = question
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        var questionText: TextView = itemView.findViewById(R.id.textViewQuestionWhenImage)

        fun getAnswer(userId: String): Answer {
            val editTextAnswer: EditText = itemView.findViewById(R.id.editTextAnswerWhenImage)
            val answerText = editTextAnswer.text.toString()

            return Answer(
                QuestionId = question.Id,
                AnswerText = answerText,
                SelectedOptionId = null,
                UserId = userId
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.questionText.text = question.QuestionText
        Picasso.get().load(question.ImageUrl).into(holder.imageView)


    }

    override fun getItemCount(): Int {
        return 1 // Samo jedno pitanje u ovom adapteru
    }
}
