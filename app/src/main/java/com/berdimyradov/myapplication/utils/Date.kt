package com.berdimyradov.myapplication.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        .format(Date(System.currentTimeMillis())).toString()
}