package com.okemwag.pulse.ui.alerts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.okemwag.design.theme.PulseGradients

/**
 * Community Alerts Screen
 * 
 * Displays safety alerts, community warnings, and important notifications.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertsScreen() {
    var selectedFilter by remember { mutableStateOf(AlertFilter.ALL) }
    
    val alerts = remember {
        listOf(
            AlertItem(
                id = "1",
                title = "Severe Weather Warning",
                description = "Heavy thunderstorms expected this evening. Stay indoors and avoid travel if possible. Power outages may occur in some areas.",
                type = AlertType.EMERGENCY,
                location = "Entire District",
                timeAgo = "15 minutes ago",
                isActive = true,
                reportCount = 45
            ),
            AlertItem(
                id = "2",
                title = "Road Construction Notice",
                description = "Lane closures on Highway 101 between exits 15-18. Expect delays during peak hours. Use alternate routes.",
                type = AlertType.WARNING,
                location = "Highway 101",
                timeAgo = "2 hours ago",
                isActive = true,
                reportCount = 12
            ),
            AlertItem(
                id = "3",
                title = "Community Clean-Up Event",
                description = "Join us this Saturday for the annual neighborhood clean-up. Supplies provided. Meet at Central Park at 9 AM.",
                type = AlertType.INFO,
                location = "Central Park",
                timeAgo = "5 hours ago",
                isActive = true,
                reportCount = 8
            ),
            AlertItem(
                id = "4",
                title = "Water Service Interruption",
                description = "Scheduled maintenance will affect water service in the downtown area from 2 AM to 6 AM tomorrow.",
                type = AlertType.WARNING,
                location = "Downtown",
                timeAgo = "8 hours ago",
                isActive = true,
                reportCount = 23
            ),
            AlertItem(
                id = "5",
                title = "Lost Pet Alert",
                description = "Golden Retriever named Max missing from Oak Street area. If found, please contact the community center.",
                type = AlertType.INFO,
                location = "Oak Street",
                timeAgo = "1 day ago",
                isActive = true,
                reportCount = 34
            ),
            AlertItem(
                id = "6",
                title = "Fire Hazard - Extreme Heat",
                description = "Due to extreme temperatures and dry conditions, fire risk is elevated. Avoid outdoor burning and dispose of cigarettes safely.",
                type = AlertType.EMERGENCY,
                location = "Regional",
                timeAgo = "2 days ago",
                isActive = false,
                reportCount = 89
            )
        )
    }
    
    val filteredAlerts = when (selectedFilter) {
        AlertFilter.ALL -> alerts
        AlertFilter.EMERGENCY -> alerts.filter { it.type == AlertType.EMERGENCY }
        AlertFilter.WARNING -> alerts.filter { it.type == AlertType.WARNING }
        AlertFilter.INFO -> alerts.filter { it.type == AlertType.INFO }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFF6B6B),
                                Color(0xFFFF8E53)
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Alerts",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Active alerts badge
                    val activeCount = alerts.count { it.isActive }
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                            Text(
                                text = "$activeCount Active",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Filter Tabs
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AlertFilter.entries.forEach { filter ->
                        val selected = selectedFilter == filter
                        FilterChip(
                            selected = selected,
                            onClick = { selectedFilter = filter },
                            label = { Text(filter.label) },
                            leadingIcon = if (filter != AlertFilter.ALL) {
                                { Icon(filter.icon, null, modifier = Modifier.size(16.dp)) }
                            } else null,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color.White,
                                selectedLabelColor = Color(0xFFFF6B6B),
                                containerColor = Color.White.copy(alpha = 0.2f),
                                labelColor = Color.White
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Report alert */ },
                containerColor = Color(0xFFFF6B6B),
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Warning, contentDescription = null) },
                text = { Text("Report") }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredAlerts) { alert ->
                AlertCard(alert = alert)
            }
            
            if (filteredAlerts.isEmpty()) {
                item {
                    EmptyAlertsState(selectedFilter)
                }
            }
            
            // Bottom spacing for FAB
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlertCard(alert: AlertItem) {
    val backgroundColor by animateColorAsState(
        targetValue = when (alert.type) {
            AlertType.EMERGENCY -> Color(0xFFFFF0F0)
            AlertType.WARNING -> Color(0xFFFFF8E1)
            AlertType.INFO -> Color(0xFFF0F7FF)
        },
        label = "AlertCardBackground"
    )
    
    val accentColor = when (alert.type) {
        AlertType.EMERGENCY -> Color(0xFFFF4444)
        AlertType.WARNING -> Color(0xFFFFA000)
        AlertType.INFO -> Color(0xFF2196F3)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        onClick = { /* Navigate to detail */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.weight(1f)
                ) {
                    // Alert Type Icon
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(accentColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = alert.type.icon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    
                    Column {
                        // Type badge
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = accentColor
                        ) {
                            Text(
                                text = alert.type.label,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text = alert.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                // Active indicator
                if (alert.isActive) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = alert.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = alert.location,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                
                Text(
                    text = alert.timeAgo,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = accentColor
                    )
                    Text(
                        text = "${alert.reportCount} reports",
                        style = MaterialTheme.typography.labelMedium,
                        color = accentColor
                    )
                }
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = { /* Share */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Share,
                            contentDescription = "Share",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                    
                    IconButton(
                        onClick = { /* More options */ },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = "More",
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyAlertsState(filter: AlertFilter) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (filter == AlertFilter.ALL) "No alerts" else "No ${filter.label.lowercase()} alerts",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Your community is safe!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

data class AlertItem(
    val id: String,
    val title: String,
    val description: String,
    val type: AlertType,
    val location: String,
    val timeAgo: String,
    val isActive: Boolean,
    val reportCount: Int
)

enum class AlertType(val label: String, val icon: ImageVector) {
    EMERGENCY("Emergency", Icons.Filled.Warning),
    WARNING("Warning", Icons.Filled.Info),
    INFO("Info", Icons.Filled.Notifications)
}

enum class AlertFilter(val label: String, val icon: ImageVector) {
    ALL("All", Icons.Filled.List),
    EMERGENCY("Emergency", Icons.Filled.Warning),
    WARNING("Warning", Icons.Filled.Info),
    INFO("Info", Icons.Filled.Notifications)
}
