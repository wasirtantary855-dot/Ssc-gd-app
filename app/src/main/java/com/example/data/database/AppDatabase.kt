package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.dao.UserProgressDao
import com.example.data.dao.VideoLessonDao
import com.example.data.model.MockTestAttempt
import com.example.data.model.UserBookmark
import com.example.data.model.UserNote
import com.example.data.model.UserProgress
import com.example.data.model.VideoLesson

@Database(
    entities = [
        UserProgress::class,
        UserBookmark::class,
        MockTestAttempt::class,
        UserNote::class,
        VideoLesson::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
    abstract fun videoLessonDao(): VideoLessonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ssc_gd_book_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
