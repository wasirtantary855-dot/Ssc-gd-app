package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.SubjectType
import com.example.data.model.VideoLesson
import com.example.data.provider.VideoDataProvider
import com.example.data.viewmodel.BookViewModel
import com.example.ui.components.openYouTubeVideo
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoLearningScreen(
    viewModel: BookViewModel,
    onBackClick: () -> Unit,
    onWatchVideo: (String) -> Unit,
    onNavigateToChapter: ((String) -> Unit)? = null
) {
    val context = LocalContext.current
    val allVideos by viewModel.allVideos.collectAsState()
    
    val videoList = remember(allVideos) {
        if (allVideos.isNotEmpty()) allVideos else VideoDataProvider.defaultVideos
    }

    var selectedSubjectTab by remember { mutableStateOf<SubjectType?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isAdminMode by remember { mutableStateOf(false) }

    // Dialog state for adding/editing video
    var showEditDialog by remember { mutableStateOf(false) }
    var editingVideo by remember { mutableStateOf<VideoLesson?>(null) }
    var showDeleteConfirmDialog by remember { mutableStateOf<VideoLesson?>(null) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    val filteredVideos = remember(videoList, selectedSubjectTab, searchQuery) {
        videoList.filter { video ->
            val matchSubject = selectedSubjectTab == null || video.subjectType == selectedSubjectTab
            val matchQuery = searchQuery.isBlank() || 
                video.title.contains(searchQuery, ignoreCase = true) ||
                video.topicName.contains(searchQuery, ignoreCase = true) ||
                video.instructor.contains(searchQuery, ignoreCase = true)
            matchSubject && matchQuery
        }
    }

    val subjectCategories = listOf(
        null to "All Subjects (${videoList.size})",
        SubjectType.REASONING to "Reasoning",
        SubjectType.MATHEMATICS to "Mathematics",
        SubjectType.GENERAL_KNOWLEDGE to "GK & Science",
        SubjectType.CURRENT_AFFAIRS to "Current Affairs",
        SubjectType.ENGLISH_LANGUAGE to "English Language"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Video Learning Classes", fontWeight = FontWeight.Bold)
                        Text("Topic-wise high quality video lectures", style = MaterialTheme.typography.bodySmall, color = Slate500)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick, modifier = Modifier.testTag("video_learning_back_btn")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // Admin mode toggle button
                    FilledTonalIconToggleButton(
                        checked = isAdminMode,
                        onCheckedChange = { isAdminMode = it },
                        modifier = Modifier.testTag("admin_mode_toggle")
                    ) {
                        Icon(
                            if (isAdminMode) Icons.Default.AdminPanelSettings else Icons.Default.Tune,
                            contentDescription = "Admin Mode",
                            tint = if (isAdminMode) IndiaGreen else Slate600
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Slate900
                )
            )
        },
        floatingActionButton = {
            if (isAdminMode) {
                ExtendedFloatingActionButton(
                    onClick = {
                        editingVideo = null
                        showEditDialog = true
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("Add Video") },
                    containerColor = Indigo600,
                    contentColor = Color.White,
                    modifier = Modifier.testTag("admin_add_video_fab")
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LightGrayBg)
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Admin Banner if active
            if (isAdminMode) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Indigo50),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Indigo100)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Indigo600)
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text("Admin Video Manager Active", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = Indigo600)
                                    Text("Add, edit, replace links or delete videos", style = MaterialTheme.typography.labelSmall, color = Slate600)
                                }
                            }

                            TextButton(
                                onClick = { showResetConfirmDialog = true },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset Defaults", style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }

            // Learning Flow Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Slate900)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SSC GD Video Learning Flow",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Learn Concept ➔ Watch Video ➔ Practice MCQs ➔ Take Mock Test",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate300
                        )
                    }
                }
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search topics, videos, teachers...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Slate400) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear search")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .testTag("video_search_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Indigo600,
                        unfocusedBorderColor = Slate200
                    ),
                    singleLine = true
                )
            }

            // Subject Filter Chips
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(subjectCategories) { (subject, label) ->
                        val isSelected = selectedSubjectTab == subject
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedSubjectTab = subject },
                            label = { Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Indigo600,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = Slate700
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) Indigo600 else Slate200
                            )
                        )
                    }
                }
            }

            // Video Count Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${filteredVideos.size} Topic Videos Available",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Slate800
                    )
                }
            }

            // Video Cards List
            if (filteredVideos.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.VideoLibrary, contentDescription = null, tint = Slate400, modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("No video lectures found for this filter.", color = Slate600)
                        }
                    }
                }
            } else {
                items(filteredVideos, key = { it.id }) { video ->
                    VideoCardItem(
                        video = video,
                        isAdmin = isAdminMode,
                        onWatch = { onWatchVideo(video.id) },
                        onWatchExternal = { openYouTubeVideo(context, video.youtubeVideoId) },
                        onEdit = {
                            editingVideo = video
                            showEditDialog = true
                        },
                        onDelete = {
                            showDeleteConfirmDialog = video
                        },
                        onOpenNotes = {
                            video.linkedChapterId?.let { chId ->
                                onNavigateToChapter?.invoke(chId)
                            }
                        }
                    )
                }
            }
        }
    }

    // Admin Add / Edit Video Dialog
    if (showEditDialog) {
        AdminVideoEditDialog(
            initialVideo = editingVideo,
            onDismiss = { showEditDialog = false },
            onSave = { updatedVideo ->
                viewModel.addOrUpdateVideo(
                    id = updatedVideo.id.ifBlank { null },
                    subjectType = updatedVideo.subjectType,
                    topicName = updatedVideo.topicName,
                    title = updatedVideo.title,
                    youtubeUrlOrId = updatedVideo.youtubeUrl,
                    duration = updatedVideo.duration,
                    instructor = updatedVideo.instructor,
                    description = updatedVideo.description,
                    linkedChapterId = updatedVideo.linkedChapterId
                )
                showEditDialog = false
            }
        )
    }

    // Delete Confirmation Dialog
    showDeleteConfirmDialog?.let { videoToDelete ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = null },
            title = { Text("Delete Video Class?") },
            text = { Text("Are you sure you want to remove '${videoToDelete.topicName}: ${videoToDelete.title}' from the list?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteVideo(videoToDelete.id)
                        showDeleteConfirmDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Reset Defaults Dialog
    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = { Text("Reset to Default Video Catalog?") },
            text = { Text("This will restore the original curated SSC GD video classes for all topics and reset custom modifications.") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetVideosToDefault()
                        showResetConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Indigo600)
                ) {
                    Text("Reset to Defaults")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun VideoCardItem(
    video: VideoLesson,
    isAdmin: Boolean,
    onWatch: () -> Unit,
    onWatchExternal: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onOpenNotes: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("video_card_${video.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Thumbnail with Play Overlay & Duration Badge
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black)
                    .clickable { onWatch() }
            ) {
                AsyncImage(
                    model = VideoDataProvider.getThumbnailUrl(video.youtubeVideoId),
                    contentDescription = video.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color.Black.copy(alpha = 0.7f),
                        modifier = Modifier.size(54.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Play Video",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                // Duration badge at bottom right
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = video.duration,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Topic Tag at top left
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Indigo600,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = video.topicName,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // Card Body Content
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = video.subjectType.titleEnglish,
                        style = MaterialTheme.typography.labelSmall,
                        color = Indigo600,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = video.instructor,
                        style = MaterialTheme.typography.labelSmall,
                        color = Slate500
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                if (video.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = video.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate600,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Action Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onWatch,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("watch_video_btn_${video.id}"),
                        colors = ButtonDefaults.buttonColors(containerColor = Indigo600),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Watch Video", fontWeight = FontWeight.Bold)
                    }

                    IconButton(
                        onClick = onWatchExternal,
                        modifier = Modifier
                            .size(42.dp)
                            .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                            .testTag("youtube_ext_btn_${video.id}")
                    ) {
                        Icon(
                            Icons.Default.OpenInNew,
                            contentDescription = "Watch on YouTube App",
                            tint = Color(0xFFCC0000),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    if (video.linkedChapterId != null) {
                        IconButton(
                            onClick = onOpenNotes,
                            modifier = Modifier
                                .size(42.dp)
                                .border(1.dp, Slate200, RoundedCornerShape(10.dp))
                                .testTag("notes_btn_${video.id}")
                        ) {
                            Icon(
                                Icons.Default.MenuBook,
                                contentDescription = "Read Topic Notes",
                                tint = IndiaGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                // Admin Controls if enabled
                if (isAdmin) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = Slate100)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = onEdit,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("admin_edit_btn_${video.id}")
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Edit / Replace Link", style = MaterialTheme.typography.labelSmall)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = onDelete,
                            modifier = Modifier.testTag("admin_delete_btn_${video.id}")
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminVideoEditDialog(
    initialVideo: VideoLesson?,
    onDismiss: () -> Unit,
    onSave: (VideoLesson) -> Unit
) {
    var topicName by remember { mutableStateOf(initialVideo?.topicName ?: "") }
    var title by remember { mutableStateOf(initialVideo?.title ?: "") }
    var youtubeUrl by remember { mutableStateOf(initialVideo?.youtubeUrl ?: "") }
    var duration by remember { mutableStateOf(initialVideo?.duration ?: "45 mins") }
    var instructor by remember { mutableStateOf(initialVideo?.instructor ?: "SSC GD Expert Faculty") }
    var description by remember { mutableStateOf(initialVideo?.description ?: "") }
    var selectedSubject by remember { mutableStateOf(initialVideo?.subjectType ?: SubjectType.REASONING) }
    var expandedSubjectDropdown by remember { mutableStateOf(false) }

    val subjectOptions = listOf(
        SubjectType.REASONING to "Reasoning",
        SubjectType.MATHEMATICS to "Mathematics",
        SubjectType.GENERAL_KNOWLEDGE to "GK & General Awareness",
        SubjectType.CURRENT_AFFAIRS to "Current Affairs",
        SubjectType.ENGLISH_LANGUAGE to "English Language"
    )

    val previewVideoId = remember(youtubeUrl) {
        VideoDataProvider.extractYouTubeId(youtubeUrl)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialVideo == null) "Add New Video Lesson" else "Edit / Replace Video Link",
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Topic Name
                item {
                    OutlinedTextField(
                        value = topicName,
                        onValueChange = { topicName = it },
                        label = { Text("Topic Name (e.g. Percentage, Analogy)") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                // Video Title
                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Video Lecture Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                // YouTube URL or Video ID
                item {
                    OutlinedTextField(
                        value = youtubeUrl,
                        onValueChange = { youtubeUrl = it },
                        label = { Text("YouTube URL or Video ID") },
                        placeholder = { Text("https://www.youtube.com/watch?v=... or CHAykHhzjnA") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                // Live Thumbnail Preview
                if (previewVideoId.isNotBlank()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = VideoDataProvider.getThumbnailUrl(previewVideoId),
                                    contentDescription = "Thumbnail Preview",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                Surface(
                                    color = Color.Black.copy(alpha = 0.6f),
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(6.dp)
                                ) {
                                    Text(
                                        text = "Preview ID: $previewVideoId",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Subject Selector
                item {
                    ExposedDropdownMenuBox(
                        expanded = expandedSubjectDropdown,
                        onExpandedChange = { expandedSubjectDropdown = it }
                    ) {
                        OutlinedTextField(
                            value = subjectOptions.firstOrNull { it.first == selectedSubject }?.second ?: selectedSubject.titleEnglish,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Subject Category") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSubjectDropdown) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedSubjectDropdown,
                            onDismissRequest = { expandedSubjectDropdown = false }
                        ) {
                            subjectOptions.forEach { (type, label) ->
                                DropdownMenuItem(
                                    text = { Text(label) },
                                    onClick = {
                                        selectedSubject = type
                                        expandedSubjectDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Duration & Teacher
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = duration,
                            onValueChange = { duration = it },
                            label = { Text("Duration") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = instructor,
                            onValueChange = { instructor = it },
                            label = { Text("Faculty / Channel") },
                            modifier = Modifier.weight(1.5f),
                            singleLine = true
                        )
                    }
                }

                // Description
                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description (Optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (topicName.isNotBlank() && youtubeUrl.isNotBlank()) {
                        val videoId = previewVideoId
                        val finalVideo = VideoLesson(
                            id = initialVideo?.id ?: "vid_custom_${System.currentTimeMillis()}",
                            subjectType = selectedSubject,
                            topicName = topicName.trim(),
                            title = title.ifBlank { "$topicName Complete Masterclass" },
                            youtubeVideoId = videoId,
                            youtubeUrl = if (youtubeUrl.startsWith("http")) youtubeUrl else "https://www.youtube.com/watch?v=$videoId",
                            duration = duration.ifBlank { "45 mins" },
                            instructor = instructor.ifBlank { "SSC GD Faculty" },
                            description = description.trim(),
                            linkedChapterId = initialVideo?.linkedChapterId
                        )
                        onSave(finalVideo)
                    }
                },
                enabled = topicName.isNotBlank() && youtubeUrl.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Indigo600)
            ) {
                Text(if (initialVideo == null) "Add Video" else "Save Changes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
