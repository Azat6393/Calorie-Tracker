package com.berdimyradov.myapplication.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Item(
    val calories: Double,
    val carbohydrates_total_g: Double,
    val fat_total_g: Double,
    val name: String,
    val protein_g: Double,
    @PrimaryKey(autoGenerate = true)
    var id: Int
)