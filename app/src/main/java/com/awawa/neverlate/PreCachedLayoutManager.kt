package com.awawa.neverlate


import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PreCachedLayoutManager(context: Context, private var extraLayoutSpace: Int) :
    LinearLayoutManager(context) {

    private val defaultExtraLayoutSpace = 2000


    fun setExtraLayoutSpace(extraLayoutSpace: Int) {
        this.extraLayoutSpace = extraLayoutSpace
    }


    override fun getExtraLayoutSpace(state: RecyclerView.State?): Int {
        return if (extraLayoutSpace < 0) defaultExtraLayoutSpace else extraLayoutSpace
    }
}