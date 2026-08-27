package com.example.data.repository

import com.example.data.dao.UserProgressDao
import com.example.data.dao.VideoLessonDao
import com.example.data.model.*
import com.example.data.provider.SSCBookDataProvider
import com.example.data.provider.VideoDataProvider
import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val userProgressDao: UserProgressDao,
    private val videoLessonDao: VideoLessonDao
) {

    val allSubjects: List<SubjectType> = SSCBookDataProvider.allSubjects

    fun getAllChapters(): List<Chapter> = SSCBookDataProvider.getAllChapters()

    fun getChaptersForSubject(subject: SubjectType): List<Chapter> =
        SSCBookDataProvider.getChaptersBySubject(subject)

    fun getChapterById(id: String): Chapter? =
        SSCBookDataProvider.getChapterById(id)

    fun getAllMockTests(): List<MockTest> = SSCBookDataProvider.mockTests

    fun getMockTestById(id: Int): MockTest? =
        SSCBookDataProvider.mockTests.firstOrNull { it.id == id }

    fun getAllRevisionFacts(): List<RevisionFact> = SSCBookDataProvider.revisionFactCategoryList

    // User Progress & Bookmarks (Room)
    val userProgress: Flow<List<UserProgress>> = userProgressDao.getAllProgress()
    val bookmarks: Flow<List<UserBookmark>> = userProgressDao.getAllBookmarks()
    val mockTestAttempts: Flow<List<MockTestAttempt>> = userProgressDao.getAllMockTestAttempts()
    val userNotes: Flow<List<UserNote>> = userProgressDao.getAllNotes()

    // Video Lessons (Room)
    val allVideos: Flow<List<VideoLesson>> = videoLessonDao.getAllVideos()

    suspend fun ensureDefaultVideosSeeded() {
        val count = videoLessonDao.getCount()
        if (count == 0) {
            videoLessonDao.insertVideos(VideoDataProvider.defaultVideos)
        }
    }

    suspend fun getVideosForSubject(subjectType: SubjectType): Flow<List<VideoLesson>> {
        return videoLessonDao.getVideosBySubject(subjectType)
    }

    suspend fun getVideoById(id: String): VideoLesson? {
        return videoLessonDao.getVideoById(id) ?: VideoDataProvider.defaultVideos.firstOrNull { it.id == id }
    }

    suspend fun getVideoForChapter(chapterId: String): VideoLesson? {
        return videoLessonDao.getVideoByChapterId(chapterId) 
            ?: VideoDataProvider.defaultVideos.firstOrNull { it.linkedChapterId == chapterId }
    }

    suspend fun getVideoByTopic(topicName: String): VideoLesson? {
        return videoLessonDao.getVideoByTopic(topicName)
            ?: VideoDataProvider.defaultVideos.firstOrNull { it.topicName.equals(topicName, ignoreCase = true) }
    }

    suspend fun saveVideo(video: VideoLesson) {
        videoLessonDao.insertVideo(video)
    }

    suspend fun updateVideo(video: VideoLesson) {
        videoLessonDao.updateVideo(video)
    }

    suspend fun deleteVideo(videoId: String) {
        videoLessonDao.deleteVideo(videoId)
    }

    suspend fun resetVideosToDefault() {
        videoLessonDao.clearAll()
        videoLessonDao.insertVideos(VideoDataProvider.defaultVideos)
    }

    suspend fun saveChapterProgress(chapterId: String, readPercentage: Float, isCompleted: Boolean) {
        userProgressDao.saveProgress(
            UserProgress(
                chapterId = chapterId,
                readPercentage = readPercentage,
                isCompleted = isCompleted,
                lastReadTimestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun toggleBookmark(id: String, title: String, type: String, subtitle: String): Boolean {
        val isBookmarked = userProgressDao.isBookmarked(id)
        if (isBookmarked) {
            userProgressDao.removeBookmark(id)
            return false
        } else {
            userProgressDao.addBookmark(
                UserBookmark(
                    id = id,
                    title = title,
                    type = type,
                    subtitle = subtitle
                )
            )
            return true
        }
    }

    suspend fun isBookmarked(id: String): Boolean = userProgressDao.isBookmarked(id)

    suspend fun saveMockTestAttempt(attempt: MockTestAttempt) {
        userProgressDao.saveMockTestAttempt(attempt)
    }

    suspend fun saveNote(chapterId: String, topicTitle: String, content: String) {
        userProgressDao.saveNote(
            UserNote(
                chapterId = chapterId,
                topicTitle = topicTitle,
                noteContent = content
            )
        )
    }

    suspend fun deleteNote(noteId: Long) {
        userProgressDao.deleteNote(noteId)
    }
}
