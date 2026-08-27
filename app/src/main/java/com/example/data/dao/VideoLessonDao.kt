package com.example.data.dao

import androidx.room.*
import com.example.data.model.SubjectType
import com.example.data.model.VideoLesson
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoLessonDao {

    @Query("SELECT * FROM video_lessons ORDER BY timestamp ASC")
    fun getAllVideos(): Flow<List<VideoLesson>>

    @Query("SELECT * FROM video_lessons WHERE subjectType = :subjectType ORDER BY timestamp ASC")
    fun getVideosBySubject(subjectType: SubjectType): Flow<List<VideoLesson>>

    @Query("SELECT * FROM video_lessons WHERE id = :videoId LIMIT 1")
    suspend fun getVideoById(videoId: String): VideoLesson?

    @Query("SELECT * FROM video_lessons WHERE topicName LIKE '%' || :topicName || '%' OR title LIKE '%' || :topicName || '%' LIMIT 1")
    suspend fun getVideoByTopic(topicName: String): VideoLesson?

    @Query("SELECT * FROM video_lessons WHERE linkedChapterId = :chapterId LIMIT 1")
    suspend fun getVideoByChapterId(chapterId: String): VideoLesson?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoLesson)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoLesson>)

    @Update
    suspend fun updateVideo(video: VideoLesson)

    @Query("DELETE FROM video_lessons WHERE id = :videoId")
    suspend fun deleteVideo(videoId: String)

    @Query("DELETE FROM video_lessons")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM video_lessons")
    suspend fun getCount(): Int
}
