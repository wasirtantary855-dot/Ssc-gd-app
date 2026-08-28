package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ExamCategory(val title: String) {
    SSC_GD("SSC GD Constable"),
    AGNIVEER("Agniveer"),
    TERRITORIAL_ARMY("Territorial Army")
}

enum class SubjectType(val titleHindi: String, val titleEnglish: String) {
    INTRO("SSC GD Exam Overview", "SSC GD Exam Overview & Pattern"),
    REASONING("General Intelligence & Reasoning", "General Intelligence & Reasoning"),
    GENERAL_KNOWLEDGE("General Knowledge & General Awareness", "General Knowledge & Awareness"),
    CURRENT_AFFAIRS("Current Affairs 2026-2027", "Current Affairs 2026-2027"),
    MATHEMATICS("Elementary Mathematics", "Elementary Mathematics"),
    HINDI_LANGUAGE("General English & Language", "English & General Language"),
    ENGLISH_LANGUAGE("General English", "English Language"),
    PRACTICE_SETS("Subject-wise Practice Sets", "Subject Practice Sets"),
    MOCK_TESTS("20 Full CBT Mock Tests", "20 Full Mock Tests"),
    REVISION("Quick Revision Notes & Formulas", "Quick Revision & Formulas"),
    EXAM_STRATEGY("Exam Strategy & Tips", "Exam Strategy & Tips"),
    VIDEO_LEARNING("Video Classes & Lectures", "Video Classes & Lectures")
}

enum class DifficultyLevel(val labelHindi: String) {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard")
}

data class Chapter(
    val id: String,
    val examCategory: ExamCategory = ExamCategory.SSC_GD,
    val subjectType: SubjectType,
    val titleHindi: String,
    val titleEnglish: String,
    val chapterNumber: Int,
    val description: String,
    val conceptExplanation: String,
    val rulesAndConcepts: List<String> = emptyList(),
    val formulas: List<FormulaBox> = emptyList(),
    val shortTricks: List<String> = emptyList(),
    val solvedExamples: List<SolvedExample> = emptyList(),
    val practiceQuestions: List<Question> = emptyList(),
    val revisionFacts: List<String> = emptyList(),
    val estimatedReadTimeMinutes: Int = 15
)

data class SolvedExample(
    val id: Int,
    val questionText: String,
    val solutionText: String,
    val shortTrickText: String? = null
)

data class Question(
    val id: String,
    val chapterId: String? = null,
    val mockTestId: Int? = null,
    val subjectType: SubjectType,
    val questionText: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctOptionIndex: Int, // 0 for A, 1 for B, 2 for C, 3 for D
    val detailedSolution: String,
    val difficultyLevel: DifficultyLevel = DifficultyLevel.MEDIUM,
    val isPreviousYearStyle: Boolean = true
)

data class FormulaBox(
    val title: String,
    val formulaText: String,
    val explanation: String
)

data class MockTest(
    val id: Int,
    val examCategory: ExamCategory = ExamCategory.SSC_GD,
    val title: String,
    val description: String,
    val totalQuestions: Int = 80, // SSC GD Pattern: 20 Reasoning + 20 GK + 20 Maths + 20 Hindi/English
    val totalMarks: Int = 160,
    val durationMinutes: Int = 60,
    val questions: List<Question> = emptyList()
)

data class RevisionFact(
    val id: String,
    val category: String, // e.g. History, Geography, Science, Maths Formula, Constitution
    val title: String,
    val content: String,
    val bulletPoints: List<String> = emptyList()
)

@Entity(tableName = "user_bookmarks")
data class UserBookmark(
    @PrimaryKey val id: String,
    val title: String,
    val type: String, // CHAPTER, QUESTION, FACT
    val subtitle: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_progress")
data class UserProgress(
    @PrimaryKey val chapterId: String,
    val isCompleted: Boolean = false,
    val readPercentage: Float = 0.0f,
    val lastReadTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "mock_test_attempts")
data class MockTestAttempt(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mockTestId: Int,
    val mockTestTitle: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val unattempted: Int,
    val scoreObtained: Float,
    val maxScore: Float,
    val timeTakenSeconds: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_notes")
data class UserNote(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chapterId: String,
    val topicTitle: String,
    val noteContent: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "video_lessons")
data class VideoLesson(
    @PrimaryKey val id: String,
    val examCategory: ExamCategory = ExamCategory.SSC_GD,
    val subjectType: SubjectType,
    val topicName: String,
    val title: String,
    val youtubeVideoId: String,
    val youtubeUrl: String,
    val duration: String = "45 mins",
    val instructor: String = "SSC GD Expert Faculty",
    val description: String = "",
    val linkedChapterId: String? = null,
    val isWatched: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

