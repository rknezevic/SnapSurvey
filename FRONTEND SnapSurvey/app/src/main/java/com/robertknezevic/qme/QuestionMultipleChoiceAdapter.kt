package com.robertknezevic.qme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Answer
import com.robertknezevic.qme.models.AnswerOption
import com.robertknezevic.qme.models.Question
import com.robertknezevic.qme.services.AnswerOptionService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionMultipleChoiceAdapter(private val question: Question) : RecyclerView.Adapter<QuestionMultipleChoiceAdapter.ViewHolder>() {

    private val answerOptionService: AnswerOptionService = AnswerOptionService.instance




    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val radioGroup: RadioGroup = itemView.findViewById(R.id.radioGroup)
        val questionText: TextView = itemView.findViewById(R.id.multipleChoiceQuestionText)

        suspend fun getSelectedOption(userId : String): Answer {
            val checkedRadioButtonId = radioGroup.checkedRadioButtonId
            if (checkedRadioButtonId == View.NO_ID) {
                throw IllegalStateException("Niti jedna opcija nije odabrana.")
            }
            val index = radioGroup.indexOfChild(radioGroup.findViewById(checkedRadioButtonId))
            val selectedOptionId = question.AnswerOptionIds.get(index)
            val selectedOption = loadAnswerOption(selectedOptionId)
            return Answer(
                QuestionId = question.Id,
                AnswerText = selectedOption.Text,
                SelectedOptionId = selectedOption.Id,
                UserId = userId
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.multiple_choice_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.questionText.text = question.QuestionText

        CoroutineScope(Dispatchers.Main).launch {
            for (answerOptionId in question.AnswerOptionIds) {
                val answerOption = loadAnswerOption(answerOptionId)
                val radioButton = RadioButton(holder.itemView.context)
                radioButton.text = answerOption.Text
                radioButton.id = View.generateViewId()
                holder.radioGroup.addView(radioButton)
            }

            holder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val index = holder.radioGroup.indexOfChild(group.findViewById(checkedId))
                if (index >= 0 && index < question.AnswerOptionIds.size) {
                    val selectedOptionId = question.AnswerOptionIds[index]
                    CoroutineScope(Dispatchers.Main).launch {
                        val selectedOption = loadAnswerOption(selectedOptionId)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    private suspend fun loadAnswerOption(answerOptionId: String): AnswerOption {
        return answerOptionService.getAnswerOption(answerOptionId)
    }
}

