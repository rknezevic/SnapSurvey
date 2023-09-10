package com.robertknezevic.qme

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.robertknezevic.qme.services.SurveyService
import com.robertknezevic.qme.models.Survey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

@SuppressLint("NotifyDataSetChanged")
class TakeSurveyAdapter(private val context: Context, private val surveyList: List<Survey>) : RecyclerView.Adapter<TakeSurveyAdapter.SurveyViewHolder>() {

    init {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_take_survey_adapter, parent, false)
        return SurveyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurveyViewHolder, position: Int) {
        val survey = surveyList[position]
        holder.bind(survey)

        holder.goButtonTextView.setOnClickListener {
            val surveyId = survey.Id
            val bundle = Bundle()

            bundle.putString("surveyId", surveyId)


            val surveyResponse = SurveyResponse()
            surveyResponse.arguments = bundle

            val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainerView, surveyResponse)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun getItemCount(): Int {
        return surveyList.size
    }

    class SurveyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTextView : TextView = itemView.findViewById(R.id.surveyTitle)
        val goButtonTextView : TextView = itemView.findViewById(R.id.goButton)
        val descriptionEditText : TextView = itemView.findViewById(R.id.surveyDescription)
        fun bind(survey: Survey) {
            titleTextView.text = survey.Title
            descriptionEditText.text = survey.Description

        }
    }
}


