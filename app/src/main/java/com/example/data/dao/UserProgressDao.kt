package com.example.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.MockTestAttempt
import com.example.data.model.UserBookmark
import com.example.data.model.UserNote
import com.example.data.model.UserProgress
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress")
    fun getAllProgress(): Flow<List<UserProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProgress(progress: UserProgress)

    @Query("SELECT * FROM user_bookmarks ORDER BY timestamp DESC")
    fun getAllBookmarks(): Flow<List<UserBookmark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: UserBookmark)

    @Query("DELETE FROM user_bookmarks WHERE id = :bookmarkId")
    suspend fun removeBookmark(bookmarkId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM user_bookmarks WHERE id = :bookmarkId)")
    suspend fun isBookmarked(bookmarkId: String): Boolean

    @Query("SELECT * FROM mock_test_attempts ORDER BY timestamp DESC")
    fun getAllMockTestAttempts(): Flow<List<MockTestAttempt>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMockTestAttempt(attempt: MockTestAttempt)

    @Query("SELECT * FROM user_notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<UserNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveNote(note: UserNote)

    @Query("DELETE FROM user_notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: Long)
}
