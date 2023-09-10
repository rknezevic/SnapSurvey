package com.robertknezevic.qme

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.robertknezevic.qme.models.Survey
import com.robertknezevic.qme.services.LoginService
import com.robertknezevic.qme.services.LoginServiceInterface
import com.robertknezevic.qme.services.SurveyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewSurvey : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loginService: LoginService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var loginServiceInterface: LoginServiceInterface
    private var surveyService : SurveyService = SurveyService.instance
    private var surveyList = mutableListOf<Survey>()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_survey, container, false)
        recyclerView = view.findViewById(R.id.viewSurveyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        loginServiceInterface = LoginServiceInterface.instance

        val scope = CoroutineScope(Dispatchers.Main)

        fetchSurveys()
        loginService = LoginService(sharedPreferences, loginServiceInterface)

        val logoutButton = view.findViewById<Button>(R.id.logoutButtonFilled)
        logoutButton.setOnClickListener {
            scope.launch{
                val response = loginService.signoutAsync()

                if(response){
                    Toast.makeText(requireContext(), "Signed out successfully!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@ViewSurvey.requireContext(), SignInActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(requireContext(), "Sign out failed! Backend pao", Toast.LENGTH_SHORT).show()
                }

            }

        }
        val takeSurvey = TakeSurvey()
        val showAvailableButton = view.findViewById<Button>(R.id.viewAvailable)

        showAvailableButton.setOnClickListener{
            val fragmentTransaction : FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainerView, takeSurvey)
            fragmentTransaction?.commit()
        }

        return view
    }
    private val scope = CoroutineScope(Dispatchers.Main)


    private fun fetchSurveys(){
        sharedPreferences = requireActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

        val userToken = sharedPreferences.getString("user_token", null)
            ?: throw error("Nije dobro dohvacena vrijednost user tokena")
        Log.d("USER TOKEN", userToken)

        scope.launch {
            try {
                val response : List<Survey> = userToken.let { surveyService.getFilledSurveys("Bearer $userToken") }
                surveyList.addAll(response)
                recyclerView.adapter = ViewSurveyAdapter(requireContext(), surveyList)
            }catch(e : Exception){
                Log.e("FetchSurveys", "GRESKA PRI DOHVACANJU ANKETA")
            }

        }
    }
}

