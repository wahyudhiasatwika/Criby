package com.developer.rozan.criby.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.rozan.criby.data.local.entity.HistoryAudioEntity

@Database(entities = [HistoryAudioEntity::class], version = 1)
abstract class HistoryAudioRoomDatabase : RoomDatabase() {
    abstract fun historyAudioDao(): HistoryAudioDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryAudioRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryAudioRoomDatabase {
            if (INSTANCE == null) {
                synchronized(HistoryAudioRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryAudioRoomDatabase::class.java, "db_criby"
                    )
                        .build()
                }
            }
            return INSTANCE as HistoryAudioRoomDatabase
        }
    }
}