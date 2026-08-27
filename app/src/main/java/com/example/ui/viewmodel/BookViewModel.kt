package com.example.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.model.*
import com.example.data.repository.BookRepository
import com.example.data.service.GeminiHelper
import com.example.data.service.TextToSpeechManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "user" or "ai"
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository
    val ttsManager: TextToSpeechManager = TextToSpeechManager(application)

    init {
        val db = AppDatabase.getDatabase(application)
        repository = BookRepository(db.userProgressDao(), db.videoLessonDao())
        viewModelScope.launch {
            repository.ensureDefaultVideosSeeded()
        }
    }

    // State flows
    val allSubjects: List<SubjectType> = repository.allSubjects
    val allChapters: List<Chapter> = repository.getAllChapters()
    val allMockTests: List<MockTest> = repository.getAllMockTests()
    val allRevisionFacts: List<RevisionFact> = repository.getAllRevisionFacts()

    val allVideos: StateFlow<List<VideoLesson>> = repository.allVideos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProgress: StateFlow<List<UserProgress>> = repository.userProgress
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userBookmarks: StateFlow<List<UserBookmark>> = repository.bookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val mockTestAttempts: StateFlow<List<MockTestAttempt>> = repository.mockTestAttempts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userNotes: StateFlow<List<UserNote>> = repository.userNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                sender = "ai",
                text = "Hello Aspirant! 👋 I am your SSC GD AI Exam Tutor. Ask me any doubt regarding Mathematics formulas, Reasoning shortcuts, General Knowledge facts, English grammar, or 60-day study strategy."
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading

    val isSpeaking = ttsManager.isSpeaking

    fun getChapterById(id: String): Chapter? = repository.getChapterById(id)

    fun getChaptersForSubject(subject: SubjectType): List<Chapter> = repository.getChaptersForSubject(subject)

    fun getMockTestById(id: Int): MockTest? = repository.getMockTestById(id)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun markChapterRead(chapterId: String) {
        viewModelScope.launch {
            repository.saveChapterProgress(chapterId, 1.0f, isCompleted = true)
        }
    }

    fun toggleBookmark(id: String, title: String, type: String, subtitle: String) {
        viewModelScope.launch {
            repository.toggleBookmark(id, title, type, subtitle)
        }
    }

    fun saveMockTestResult(
        testId: Int,
        testTitle: String,
        totalQ: Int,
        correct: Int,
        wrong: Int,
        unattempted: Int,
        score: Float,
        maxScore: Float,
        timeSeconds: Int
    ) {
        viewModelScope.launch {
            repository.saveMockTestAttempt(
                MockTestAttempt(
                    mockTestId = testId,
                    mockTestTitle = testTitle,
                    totalQuestions = totalQ,
                    correctAnswers = correct,
                    wrongAnswers = wrong,
                    unattempted = unattempted,
                    scoreObtained = score,
                    maxScore = maxScore,
                    timeTakenSeconds = timeSeconds
                )
            )
        }
    }

    fun saveNote(chapterId: String, topicTitle: String, content: String) {
        viewModelScope.launch {
            repository.saveNote(chapterId, topicTitle, content)
        }
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            repository.deleteNote(noteId)
        }
    }

    suspend fun getVideoById(id: String): VideoLesson? = repository.getVideoById(id)

    suspend fun getVideoForChapter(chapterId: String): VideoLesson? = repository.getVideoForChapter(chapterId)

    suspend fun getVideoByTopic(topicName: String): VideoLesson? = repository.getVideoByTopic(topicName)

    fun addOrUpdateVideo(
        id: String? = null,
        subjectType: SubjectType,
        topicName: String,
        title: String,
        youtubeUrlOrId: String,
        duration: String,
        instructor: String,
        description: String,
        linkedChapterId: String? = null
    ) {
        viewModelScope.launch {
            val videoId = id ?: "vid_custom_${System.currentTimeMillis()}"
            val cleanId = com.example.data.provider.VideoDataProvider.extractYouTubeId(youtubeUrlOrId)
            val fullUrl = if (youtubeUrlOrId.startsWith("http")) youtubeUrlOrId else "https://www.youtube.com/watch?v=$cleanId"
            val video = VideoLesson(
                id = videoId,
                subjectType = subjectType,
                topicName = topicName.trim(),
                title = title.trim(),
                youtubeVideoId = cleanId,
                youtubeUrl = fullUrl,
                duration = duration.ifBlank { "45 mins" },
                instructor = instructor.ifBlank { "SSC GD Faculty" },
                description = description.trim(),
                linkedChapterId = linkedChapterId,
                timestamp = System.currentTimeMillis()
            )
            repository.saveVideo(video)
        }
    }

    fun deleteVideo(videoId: String) {
        viewModelScope.launch {
            repository.deleteVideo(videoId)
        }
    }

    fun resetVideosToDefault() {
        viewModelScope.launch {
            repository.resetVideosToDefault()
        }
    }

    fun sendAiQuestion(userQuestion: String, subjectContext: String? = null) {
        if (userQuestion.isBlank()) return
        val userMsg = ChatMessage(sender = "user", text = userQuestion)
        _chatMessages.update { it + userMsg }
        _isAiLoading.value = true

        viewModelScope.launch {
            val responseText = GeminiHelper.askGeminiTutor(userQuestion, subjectContext)
            val aiMsg = ChatMessage(sender = "ai", text = responseText)
            _chatMessages.update { it + aiMsg }
            _isAiLoading.value = false
        }
    }

    fun speakText(text: String) {
        ttsManager.speak(text)
    }

    fun stopSpeaking() {
        ttsManager.stop()
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager.shutdown()
    }
}
