package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ExamCategory
import com.example.ui.theme.Indigo600
import com.example.ui.theme.Slate100
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate800

@Composable
fun ExamSelectionScreen(
    onExamSelected: (ExamCategory) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.MilitaryTech,
            contentDescription = "Army Logo",
            tint = Indigo600,
            modifier = Modifier.size(80.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Choose Your Target Exam",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = Slate800,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Text(
            text = "Select your exam for the 2026–2027 recruitment batch to get customized study material & mock tests.",
            style = MaterialTheme.typography.bodyMedium,
            color = Slate500,
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        ExamCard(
            title = ExamCategory.SSC_GD.title,
            subtitle = "BSF, CISF, CRPF, SSB, ITBP",
            icon = Icons.Default.Security,
            onClick = { onExamSelected(ExamCategory.SSC_GD) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        ExamCard(
            title = ExamCategory.AGNIVEER.title,
            subtitle = "Indian Army Agnipath Scheme",
            icon = Icons.Default.Star,
            onClick = { onExamSelected(ExamCategory.AGNIVEER) }
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        ExamCard(
            title = ExamCategory.TERRITORIAL_ARMY.title,
            subtitle = "Officers & JCOs / OR",
            icon = Icons.Default.MilitaryTech,
            onClick = { onExamSelected(ExamCategory.TERRITORIAL_ARMY) }
        )
    }
}

@Composable
fun ExamCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Indigo600.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Indigo600,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Slate800,
                    fontSize = 18.sp
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Slate500
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Select",
                tint = Slate500
            )
        }
    }
}
