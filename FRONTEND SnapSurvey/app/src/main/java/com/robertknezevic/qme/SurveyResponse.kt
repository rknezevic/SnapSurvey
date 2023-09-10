package com.robertknezevic.qme

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Question
import com.robertknezevic.qme.models.SurveyAssignment
import com.robertknezevic.qme.services.SurveyAssignmentService
import com.robertknezevic.qme.models.Answer
import com.robertknezevic.qme.services.AnswerOptionService
import com.robertknezevic.qme.services.AnswerService
import com.robertknezevic.qme.services.SurveyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SurveyResponse : Fragment() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var sharedPreferences : SharedPreferences
    private var surveyAssignmentService: SurveyAssignmentService = SurveyAssignmentService.instance
    private lateinit var surveyAssignment: SurveyAssignment
    private var surveyService: SurveyService = SurveyService.instance
    private var answerService : AnswerService = AnswerService.instance
    private var answerOptionService : AnswerOptionService = AnswerOptionService.instance



    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_survey_response, container, false)
        recyclerView = view.findViewById(R.id.surveyResponseRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)


        recyclerView.adapter = ConcatAdapter()

        val takeSurvey = TakeSurvey()



        lifecycleScope.launch {
            val receivedBundle = arguments
            val surveyId = receivedBundle?.getString("surveyId")
            Log.d("SURVEY ID ", surveyId.toString())
            val survey = surveyId?.let { surveyService.getSurveyById(it) }
            val surveyTitle = survey?.Title

            val inputTitle = view.findViewById<TextView>(R.id.responseTitle)
            inputTitle.text = surveyTitle

            val adapters = mutableListOf<RecyclerView.Adapter<*>>()



            surveyAssignment = getSurveyAssignment()

            val questions = getSurveyQuestions()



            for (question in questions) {
                val adapter: RecyclerView.Adapter<*> = when {
                    !question.ImageUrl.isNullOrEmpty() -> QuestionImageAdapter(question)
                    question.AnswerOptionIds.isNullOrEmpty() -> QuestionAnswerBoxAdapter(question, recyclerView)
                    else -> QuestionMultipleChoiceAdapter(question)

                }
                adapters.add(adapter)
            }

            val concatAdapter = ConcatAdapter(adapters)
            recyclerView.adapter = concatAdapter

            val submitButton = view.findViewById<Button>(R.id.submitButtonResponse)
            val userId = sharedPreferences.getString("user_id", null)


            submitButton.setOnClickListener {
                lifecycleScope.launch {
                    val answers = mutableListOf<Answer>()

                    for ((position,adapter) in adapters.withIndex()) {
                        Log.d("Debug", "Position: $position, Adapter: $adapter")


                        if (adapter is QuestionMultipleChoiceAdapter) {
                            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? QuestionMultipleChoiceAdapter.ViewHolder
                            val answer = userId?.let { it1 -> viewHolder?.getSelectedOption(it1) }
                            Log.d("VISESTRUKI", answer.toString())
                            answer?.let { answers.add(it) }
                        }
                        else if (adapter is QuestionImageAdapter) {
                            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? QuestionImageAdapter.ViewHolder
                            val answer = userId?.let { it1 -> viewHolder?.getAnswer(it1) }
                            Log.d("SLIKA", answer.toString())
                            answer?.let { answers.add(it) }
                        }
                        else if (adapter is QuestionAnswerBoxAdapter) {
                            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) as? QuestionAnswerBoxAdapter.ViewHolder
                            val answer = userId?.let { it1 -> viewHolder?.getAnswer(it1) }
                            Log.d("TEXT BOX", answer.toString())
                            answer?.let { answers.add(it) }
                        }
                    }
                    Log.d("ODGOVORI", answers.toString())
                    answerService.submitAnswer(answers)

                    val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragmentContainerView, takeSurvey)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                }
            }
        }
        return view
    }

    private suspend fun getSurveyQuestions(): List<Question> {
        return withContext(Dispatchers.IO) {
            surveyAssignmentService.getSurveyQuestions(surveyAssignment.Id)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private suspend fun getSurveyAssignment(): SurveyAssignment {

        sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user_id", null)

        val receivedBundle = arguments
        val surveyId = receivedBundle?.getString("surveyId")

            if (surveyId != null && userId != null) {
                Log.d("surveyAssignment", surveyAssignmentService.getSurveyAssignment(surveyId, userId).toString())
                return surveyAssignmentService.getSurveyAssignment(surveyId, userId)
            }else{
                Log.d("ERROR", "SURVEY ID ILI USER ID SU NULL")
                throw error("ERROR NESTO JE NULL")
            }


    }

}
