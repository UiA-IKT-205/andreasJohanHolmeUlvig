package com.example.handleliste

import android.content.Context

class Datasource(val context: Context) {

    fun getItemList(): Array<String> {

        return context.resources.getStringArray(R.array.item_array)
    }
}

