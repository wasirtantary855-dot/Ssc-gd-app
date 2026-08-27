package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.IndiaGreen
import com.example.ui.theme.SaffronOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamStrategyScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SSC GD Exam Strategy & Guidelines", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MilitaryTech, contentDescription = null, tint = GoldAccent, modifier = Modifier.size(32.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "SSC GD 60-Day Success Blueprint",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Proven roadmap to crack the GD Constable exam in your very first attempt!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Strategy Steps
            item {
                StrategyCard(
                    stepNumber = "Phase 1 (Days 1–20)",
                    title = "Concept Building & Formula Mastery",
                    points = listOf(
                        "Master all core concepts in Elementary Maths and Reasoning chapters.",
                        "Memorize squares from 1 to 30, cubes from 1 to 15, and EJOTY alphabet positions.",
                        "Revise Indian Polity Articles, historical battles, and basic General Science."
                    )
                )
            }

            item {
                StrategyCard(
                    stepNumber = "Phase 2 (Days 21–45)",
                    title = "Topic-wise Practice & Shortcut Tricks",
                    points = listOf(
                        "Solve 30-40 targeted questions per chapter under strict time limits.",
                        "Apply shortcut formulas for Percentage, Profit & Loss, Ratio, and Time & Work.",
                        "Practice English grammar rules, idioms, synonyms, and antonyms daily."
                    )
                )
            }

            item {
                StrategyCard(
                    stepNumber = "Phase 3 (Days 46–60)",
                    title = "Full-Length Mock Tests & Time Management",
                    points = listOf(
                        "Attempt 1 full CBT mock test (60 minutes) daily in exam-simulated conditions.",
                        "Perform in-depth error analysis and avoid wild guessing (-0.25 negative marking).",
                        "Maintain physical fitness and practice the 5km running test (PET)."
                    )
                )
            }

            // Exam Day Checklist
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "🎯 Exam Day Strategy & Time Breakdown",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = SaffronOrange
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val checklist = listOf(
                            "Attempt English/Hindi (8 mins) and General Awareness (8 mins) first.",
                            "Tackle Reasoning Ability (15 mins) with focus on accuracy.",
                            "Dedicate remaining 25+ mins to Elementary Mathematics.",
                            "Skip doubtful questions immediately to protect your score from negative marking.",
                            "Carry Admit Card, original Photo ID proof (Aadhaar/Voter ID), and passport photos."
                        )

                        checklist.forEach { item ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = IndiaGreen, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StrategyCard(stepNumber: String, title: String, points: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                color = SaffronOrange,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = stepNumber,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            points.forEach { pt ->
                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                    Text("• ", fontWeight = FontWeight.Bold, color = GoldAccent)
                    Text(text = pt, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
