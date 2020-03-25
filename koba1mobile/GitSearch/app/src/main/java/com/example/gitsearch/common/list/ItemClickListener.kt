package com.example.gitsearch.common.list

import android.view.View
import java.util.*

interface ItemClickListener {
    fun onItemClick(v: View, item: ItemData)
}