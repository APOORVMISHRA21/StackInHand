package com.example.stackinhand.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object Utils {

     fun convertTimestampToDate(timestamp: Long) : String{
        return try {
            val sdf =  SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            val netDate = Date(timestamp * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }
}