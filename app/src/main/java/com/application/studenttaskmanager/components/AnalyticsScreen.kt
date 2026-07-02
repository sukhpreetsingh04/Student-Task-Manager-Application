package com.application.studenttaskmanager.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.application.studenttaskmanager.data.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    tasks: List<TaskItem>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val completed = tasks.count { it.isCompleted }
    val pending = tasks.size - completed
    val completionRate = if (tasks.isEmpty()) 0f else completed.toFloat() / tasks.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analytics") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    titleContentColor = Color(0xFFFFB74D),
                    navigationIconContentColor = Color(0xFFFFB74D)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            SummaryCard(
                title = "${(completionRate * 100).toInt()}% completion",
                subtitle = "$completed completed - $pending pending"
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Productivity Graph",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFFB74D)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CompletionChart(completed = completed, pending = pending)
                    Spacer(modifier = Modifier.height(12.dp))
                    Row {
                        Text("Completed: $completed", modifier = Modifier.weight(1f))
                        Text("Pending: $pending")
                    }
                }
            }
        }
    }
}

@Composable
private fun CompletionChart(completed: Int, pending: Int) {
    val total = (completed + pending).coerceAtLeast(1)
    val completedRatio = completed.toFloat() / total
    val pendingRatio = pending.toFloat() / total

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val barWidth = size.width / 5f
        val maxHeight = size.height - 24f
        val completedHeight = maxHeight * completedRatio
        val pendingHeight = maxHeight * pendingRatio

        drawLine(
            color = Color(0xFF9E9E9E),
            start = Offset(0f, maxHeight),
            end = Offset(size.width, maxHeight),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )

        drawRect(
            color = Color(0xFFFFB74D),
            topLeft = Offset(size.width / 3f - barWidth / 2f, maxHeight - completedHeight),
            size = Size(barWidth, completedHeight)
        )

        drawRect(
            color = Color(0xFF90CAF9),
            topLeft = Offset(size.width * 2f / 3f - barWidth / 2f, maxHeight - pendingHeight),
            size = Size(barWidth, pendingHeight)
        )
    }
}

