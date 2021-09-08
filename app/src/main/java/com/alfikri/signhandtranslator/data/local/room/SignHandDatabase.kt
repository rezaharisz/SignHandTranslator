package com.alfikri.signhandtranslator.data.local.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alfikri.signhandtranslator.R
import com.alfikri.signhandtranslator.data.local.entity.DataDictionary
import com.alfikri.signhandtranslator.utils.ERROR_MSG
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(entities = [DataDictionary::class], version = 1, exportSchema = false)
abstract class SignHandDatabase: RoomDatabase() {

    abstract fun signHandDao(): SignHandDao

    companion object{

        @Volatile
        private var INSTANCE: SignHandDatabase? = null

        fun getInstance(context: Context): SignHandDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SignHandDatabase::class.java,
                    "signhand.db"
                ).addCallback(object : Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let {
                            Executors.newSingleThreadExecutor().execute {
                                starterData(context.applicationContext, it.signHandDao())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private fun starterData(context: Context, dao: SignHandDao){
            val signHand = loadJsonArray(context)
            try {
                if (signHand != null){
                    for (i in 0 until signHand.length()){
                        val item = signHand.getJSONObject(i)
                        dao.insertAll(
                            DataDictionary(
                                item.getInt("id"),
                                item.getString("hand_picture"),
                                item.getString("alphabet")
                            )
                        )
                    }
                }
            } catch (exception: JSONException){
                Log.d(ERROR_MSG, "jsonSignHand = ${exception.message.toString()}")
            }
        }

        private fun loadJsonArray(context: Context): JSONArray?{
            val builder = StringBuilder()
            val `in` = context.resources.openRawResource(R.raw.item_hands)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null){
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("signhand")
            } catch (exception: IOException){
                Log.d(ERROR_MSG, "jsonSignHand = ${exception.message.toString()}")
            } catch (exception: JSONException){
                Log.d(ERROR_MSG, "jsonSignHand = ${exception.message.toString()}")
            }
            return null
        }

    }

}