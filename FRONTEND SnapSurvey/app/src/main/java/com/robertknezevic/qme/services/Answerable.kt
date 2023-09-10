package com.robertknezevic.qme.services

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.robertknezevic.qme.models.Answer

interface Answerable {
    fun getAnswer(userId : String, holder: RecyclerView.ViewHolder): Answer
    fun getViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
}