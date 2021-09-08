package com.alfikri.signhandtranslator.data.local.room

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary

@Dao
interface SignHandDao {

    @Query("SELECT * FROM signHand ORDER BY alphabet ASC")
    fun getData(): DataSource.Factory<Int, DataDictionary>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg dataDictionary: DataDictionary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(dataDictionary: List<DataDictionary>)

}