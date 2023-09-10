package com.robertknezevic.qme

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Survey

class ViewSurveyAdapter(private val context: Context, private val surveyList: List<Survey>) : RecyclerView.Adapter<ViewSurveyAdapter.SurveyViewHolder>() {

    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_view_survey_adapter, parent, false)
        return SurveyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val survey = surveyList[position]
        holder.bind(survey)
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTextView : TextView = itemView.findViewById(R.id.surveyTitleFilled)
        private val viewButton : Button = itemView.findViewById(R.id.viewButton)
        private val descriptionTextView : TextView = itemView.findViewById(R.id.surveyDescriptionFilled)
        fun bind(survey: Survey) {
            titleTextView.text = survey.Title
            descriptionTextView.text = survey.Description
            viewButton.setOnClickListener {

            }
        }
    }
}