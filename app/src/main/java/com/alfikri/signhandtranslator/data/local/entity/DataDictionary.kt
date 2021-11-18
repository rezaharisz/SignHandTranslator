package com.alfikri.signhandtranslator.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "signHand")
data class DataDictionary(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @NotNull
    @ColumnInfo(name = "image_hand")
    val imageHand: String,

    @NotNull
    @ColumnInfo(name = "alphabet")
    val alphabet: String,

    @NotNull
    @ColumnInfo(name = "set_favorite")
    var setFavorite: Boolean = false

)