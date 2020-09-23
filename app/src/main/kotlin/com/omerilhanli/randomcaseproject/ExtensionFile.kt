package com.omerilhanli.randomcaseproject

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun Int.getRandomIndexList(): ArrayList<Int> {
    val length = this
    val indexList = ArrayList<Int>()
    while (indexList.size < length) {
        val rndNum = (0 until length).random()
        if (indexList.size == 0) {
            indexList.add(rndNum)
        } else {
            var has = false
            indexList.forEach {
                if (it == rndNum) {
                    has = true
                }
            }
            if (has.not()) {
                indexList.add(rndNum)
            }
        }
    }
    return indexList
}

fun RecyclerView.prepareIt(context: Context, adapter: RecyclerAdapter) {
    this.adapter = adapter
    this.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
}
