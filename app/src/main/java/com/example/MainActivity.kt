package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.data.model.SubjectType
import com.example.data.viewmodel.BookViewModel
import com.example.ui.screens.*
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.SSCGDBookTheme

sealed class Screen(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", "Home", { Icon(Icons.Default.Home, contentDescription = "Home") })
    object Videos : Screen("videos", "Videos", { Icon(Icons.Default.PlayCircle, contentDescription = "Videos") })
    object Revision : Screen("revision", "Revision", { Icon(Icons.Default.Bolt, contentDescription = "Revision") })
    object MockTests : Screen("mock_tests", "Mock Tests", { Icon(Icons.Default.Quiz, contentDescription = "Mock Tests") })
    object AiTutor : Screen("ai_tutor", "AI Tutor", { Icon(Icons.Default.AutoAwesome, contentDescription = "AI Tutor") })
    object Bookmarks : Screen("bookmarks", "Bookmarks", { Icon(Icons.Default.Bookmark, contentDescription = "Bookmarks") })
}

class MainActivity : ComponentActivity() {

    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SSCGDBookTheme {
                MainApp(viewModel = bookViewModel)
            }
        }
    }
}

@Composable
fun MainApp(viewModel: BookViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Videos,
        Screen.Revision,
        Screen.MockTests,
        Screen.AiTutor
    )

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Videos.route,
        Screen.Revision.route,
        Screen.MockTests.route,
        Screen.AiTutor.route,
        Screen.Bookmarks.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = androidx.compose.ui.graphics.Color.White,
                    tonalElevation = 0.dp,
                    modifier = Modifier.border(width = 1.dp, color = com.example.ui.theme.Slate100)
                ) {
                    bottomNavItems.forEach { screen ->
                        val isSelected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = screen.icon,
                            label = { 
                                Text(
                                    screen.title,
                                    fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Medium
                                ) 
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = com.example.ui.theme.Indigo600,
                                unselectedIconColor = com.example.ui.theme.Slate400,
                                selectedTextColor = com.example.ui.theme.Indigo600,
                                unselectedTextColor = com.example.ui.theme.Slate400,
                                indicatorColor = com.example.ui.theme.Indigo50
                            ),
                            modifier = Modifier.testTag("nav_item_${screen.route}")
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "exam_selection",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("exam_selection") {
                ExamSelectionScreen(
                    onExamSelected = { exam ->
                        viewModel.selectExam(exam)
                        navController.navigate(Screen.Home.route) {
                            popUpTo("exam_selection") { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Home.route) {
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToSubject = { subject ->
                        navController.navigate("subject/${subject.name}")
                    },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    },
                    onNavigateToMockTest = { testId ->
                        navController.navigate("mock_test_runner/$testId")
                    },
                    onNavigateToRevision = {
                        navController.navigate(Screen.Revision.route)
                    },
                    onNavigateToAiTutor = {
                        navController.navigate(Screen.AiTutor.route)
                    },
                    onNavigateToStrategy = {
                        navController.navigate("exam_strategy")
                    },
                    onNavigateToVideos = {
                        navController.navigate(Screen.Videos.route)
                    },
                    onWatchVideo = { videoId ->
                        navController.navigate("video_player/$videoId")
                    }
                )
            }

            composable(
                route = "subject/{subjectName}",
                arguments = listOf(navArgument("subjectName") { type = NavType.StringType })
            ) { backStackEntry ->
                val subjectName = backStackEntry.arguments?.getString("subjectName") ?: SubjectType.REASONING.name
                val subject = try {
                    SubjectType.valueOf(subjectName)
                } catch (e: Exception) {
                    SubjectType.REASONING
                }
                TopicListScreen(
                    subject = subject,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    },
                    onWatchVideo = { videoId ->
                        navController.navigate("video_player/$videoId")
                    }
                )
            }

            composable(
                route = "chapter/{chapterId}",
                arguments = listOf(navArgument("chapterId") { type = NavType.StringType })
            ) { backStackEntry ->
                val chapterId = backStackEntry.arguments?.getString("chapterId") ?: ""
                ChapterDetailScreen(
                    chapterId = chapterId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onAskAiTutor = { topicTitle ->
                        navController.navigate("ai_tutor?query=${android.net.Uri.encode(topicTitle)}")
                    },
                    onWatchVideo = { videoId ->
                        navController.navigate("video_player/$videoId")
                    }
                )
            }

            composable(Screen.Videos.route) {
                VideoLearningScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onWatchVideo = { videoId ->
                        navController.navigate("video_player/$videoId")
                    },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    }
                )
            }

            composable(
                route = "video_player/{videoId}",
                arguments = listOf(navArgument("videoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
                VideoPlayerScreen(
                    videoId = videoId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    },
                    onNavigateToPractice = {
                        navController.navigate("practice_quiz")
                    },
                    onNavigateToMockTests = {
                        navController.navigate(Screen.MockTests.route)
                    },
                    onAskAiTutor = { topic ->
                        navController.navigate("ai_tutor?query=${android.net.Uri.encode(topic)}")
                    },
                    onSelectVideo = { nextVideoId ->
                        navController.navigate("video_player/$nextVideoId") {
                            popUpTo("video_player/$videoId") { inclusive = true }
                        }
                    }
                )
            }

            composable(Screen.Revision.route) {
                RevisionScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.MockTests.route) {
                MockTestScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onStartTest = { testId ->
                        navController.navigate("mock_test_runner/$testId")
                    }
                )
            }

            composable(
                route = "mock_test_runner/{testId}",
                arguments = listOf(navArgument("testId") { type = NavType.IntType })
            ) { backStackEntry ->
                val testId = backStackEntry.arguments?.getInt("testId") ?: 1
                MockTestRunnerScreen(
                    testId = testId,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(
                route = "ai_tutor?query={query}",
                arguments = listOf(navArgument("query") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                })
            ) { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query")
                AiTutorScreen(
                    initialQuery = query,
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Screen.Bookmarks.route) {
                BookmarksScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() },
                    onNavigateToChapter = { chapterId ->
                        navController.navigate("chapter/$chapterId")
                    }
                )
            }

            composable("practice_quiz") {
                PracticeQuizScreen(
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable("exam_strategy") {
                ExamStrategyScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
