package com.okemwag.pulse.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.okemwag.design.theme.PulseGradients

/**
 * User Profile Screen
 * 
 * Displays user information, stats, activity, and settings.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val user = remember {
        ProfileUser(
            name = "Alex Johnson",
            username = "@alexj",
            avatarInitial = "A",
            bio = "Community member since 2021. Love local events and supporting small businesses! 🌱",
            isVerified = true,
            walletAddress = "0x1234...5678",
            tokenBalance = 2450L,
            reputationScore = 95,
            joinDate = "March 2021",
            postsCount = 47,
            followersCount = 234,
            followingCount = 189
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                actions = {
                    IconButton(onClick = { /* Settings */ }) {
                        Icon(Icons.Outlined.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Profile Header
            item {
                ProfileHeader(user = user)
            }
            
            // Stats Cards
            item {
                StatsSection(user = user)
            }
            
            // Wallet Card
            item {
                WalletCard(
                    walletAddress = user.walletAddress,
                    tokenBalance = user.tokenBalance,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            
            // Quick Actions
            item {
                QuickActionsSection()
            }
            
            // Activity Section
            item {
                Text(
                    text = "Recent Activity",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            
            items(getActivityItems()) { activity ->
                ActivityItem(activity = activity)
            }
            
            // Settings Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
            
            items(getSettingsItems()) { setting ->
                SettingsItem(setting = setting)
            }
            
            // Logout
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { /* Logout */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Outlined.ExitToApp, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sign Out")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: ProfileUser) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(PulseGradients.primaryGradient)
                )
                .border(4.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = user.avatarInitial,
                style = MaterialTheme.typography.displayMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Name with verified badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (user.isVerified) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Verified",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        Text(
            text = user.username,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.outline
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Bio
        Text(
            text = user.bio,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Follower stats
        Row(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            StatItem(count = user.postsCount, label = "Posts")
            StatItem(count = user.followersCount, label = "Followers")
            StatItem(count = user.followingCount, label = "Following")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Edit Profile Button
        OutlinedButton(
            onClick = { /* Edit profile */ },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Icon(Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile")
        }
    }
}

@Composable
private fun StatItem(count: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun StatsSection(user: ProfileUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Reputation Card
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${user.reputationScore}%",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
                Text(
                    text = "Reputation",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
        
        // Member Since Card
        Card(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF2196F3).copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user.joinDate,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2196F3),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Member Since",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
private fun WalletCard(
    walletAddress: String,
    tokenBalance: Long,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pulse Wallet",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Icon(
                        Icons.Filled.Lock,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "$tokenBalance",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PULSE Tokens",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = walletAddress,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilledTonalButton(
                            onClick = { /* Send */ },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Send")
                        }
                        FilledTonalButton(
                            onClick = { /* Receive */ },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = Color.White.copy(alpha = 0.2f),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Receive")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionsSection() {
    LazyRow(
        modifier = Modifier.padding(vertical = 16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val actions = listOf(
            QuickAction("My Posts", Icons.Outlined.Edit, Color(0xFF4CAF50)),
            QuickAction("Saved", Icons.Outlined.Favorite, Color(0xFFFF9800)),
            QuickAction("History", Icons.Outlined.Refresh, Color(0xFF9C27B0)),
            QuickAction("Drafts", Icons.Outlined.Email, Color(0xFF2196F3))
        )
        
        items(actions) { action ->
            QuickActionChip(action = action)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuickActionChip(action: QuickAction) {
    AssistChip(
        onClick = { /* Navigate */ },
        label = { Text(action.label) },
        leadingIcon = {
            Icon(
                action.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = action.color
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = action.color.copy(alpha = 0.1f)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityItem(activity: ActivityItemData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { /* Navigate to activity */ }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(activity.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    activity.icon,
                    contentDescription = null,
                    tint = activity.color,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = activity.subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            
            Text(
                text = activity.time,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsItem(setting: SettingsItemData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        onClick = { /* Navigate */ }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                setting.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
            
            Text(
                text = setting.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            
            Icon(
                Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

// Data Classes
data class ProfileUser(
    val name: String,
    val username: String,
    val avatarInitial: String,
    val bio: String,
    val isVerified: Boolean,
    val walletAddress: String,
    val tokenBalance: Long,
    val reputationScore: Int,
    val joinDate: String,
    val postsCount: Int,
    val followersCount: Int,
    val followingCount: Int
)

data class QuickAction(
    val label: String,
    val icon: ImageVector,
    val color: Color
)

data class ActivityItemData(
    val title: String,
    val subtitle: String,
    val time: String,
    val icon: ImageVector,
    val color: Color
)

data class SettingsItemData(
    val title: String,
    val icon: ImageVector
)

private fun getActivityItems() = listOf(
    ActivityItemData("Posted a news update", "Community Garden Opening", "2h ago", Icons.Filled.Edit, Color(0xFF4CAF50)),
    ActivityItemData("Earned 25 tokens", "Content verification reward", "5h ago", Icons.Filled.Star, Color(0xFFFF9800)),
    ActivityItemData("Commented on alert", "Road Construction Notice", "1d ago", Icons.Filled.Email, Color(0xFF2196F3)),
    ActivityItemData("Listed an item", "Vintage Wooden Desk", "2d ago", Icons.Filled.ShoppingCart, Color(0xFF9C27B0))
)

private fun getSettingsItems() = listOf(
    SettingsItemData("Account Settings", Icons.Outlined.Person),
    SettingsItemData("Notifications", Icons.Outlined.Notifications),
    SettingsItemData("Privacy & Security", Icons.Outlined.Lock),
    SettingsItemData("Wallet Settings", Icons.Outlined.Star),
    SettingsItemData("Help & Support", Icons.Outlined.Info),
    SettingsItemData("About Pulse", Icons.Outlined.Info)
)
