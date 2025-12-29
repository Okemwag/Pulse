package com.okemwag.pulse.ui.news

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.okemwag.design.theme.PulseGradients

/**
 * News Feed Screen
 * 
 * Displays community news with filtering, search, and interaction features.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen() {
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    
    val categories = listOf("All", "Local", "Events", "Community", "Government", "Sports")
    
    val newsList = remember {
        listOf(
            NewsItem(
                id = "1",
                title = "Community Garden Grand Opening This Weekend",
                content = "Join us for the grand opening of our new community garden. Fresh vegetables, herbs, and flowers available for all residents. Activities for kids included!",
                author = "Sarah Johnson",
                category = "Community",
                timeAgo = "2 hours ago",
                likes = 45,
                comments = 12,
                isVerified = true,
                imageUrl = null
            ),
            NewsItem(
                id = "2",
                title = "Road Closure Notice: Main Street Repairs",
                content = "Main Street will be closed for repairs from Monday through Friday next week. Please use alternate routes via Oak Avenue or Pine Street.",
                author = "City Council",
                category = "Government",
                timeAgo = "4 hours ago",
                likes = 23,
                comments = 8,
                isVerified = true,
                imageUrl = null
            ),
            NewsItem(
                id = "3",
                title = "Local Team Wins Regional Championship!",
                content = "Our local basketball team brought home the trophy after an exciting final match. Congratulations to all the players and coaches!",
                author = "Mike Thompson",
                category = "Sports",
                timeAgo = "6 hours ago",
                likes = 89,
                comments = 34,
                isVerified = false,
                imageUrl = null
            ),
            NewsItem(
                id = "4",
                title = "Annual Summer Festival Returns Next Month",
                content = "Mark your calendars! The annual summer festival is back with live music, food vendors, carnival rides, and fireworks. Don't miss it!",
                author = "Events Committee",
                category = "Events",
                timeAgo = "8 hours ago",
                likes = 156,
                comments = 42,
                isVerified = true,
                imageUrl = null
            ),
            NewsItem(
                id = "5",
                title = "New Farmer's Market Opens Every Saturday",
                content = "Support local farmers and artisans at our new weekly market. Fresh produce, homemade goods, and crafts available from 8 AM to 2 PM.",
                author = "Local Business Assoc.",
                category = "Local",
                timeAgo = "1 day ago",
                likes = 67,
                comments = 19,
                isVerified = true,
                imageUrl = null
            )
        )
    }
    
    val filteredNews = newsList.filter { news ->
        (selectedCategory == "All" || news.category == selectedCategory) &&
        (searchQuery.isEmpty() || news.title.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = PulseGradients.primaryGradient
                        )
                    )
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Text(
                    text = "News Feed",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search news...", color = Color.White.copy(alpha = 0.7f)) },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White.copy(alpha = 0.5f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    singleLine = true
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Create new post */ },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Create Post")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Category Chips
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }
            
            // News Cards
            items(filteredNews) { news ->
                NewsCard(
                    news = news,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
            
            // Empty State
            if (filteredNews.isEmpty()) {
                item {
                    EmptyNewsState()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsCard(
    news: NewsItem,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(false) }
    var likeCount by remember { mutableIntStateOf(news.likes) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { /* Navigate to detail */ }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = news.author.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = news.author,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (news.isVerified) {
                                Icon(
                                    Icons.Filled.CheckCircle,
                                    contentDescription = "Verified",
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Text(
                            text = news.timeAgo,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                
                // Category Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = news.category,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Title
            Text(
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Content preview
            Text(
                text = news.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Like Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = {
                                isLiked = !isLiked
                                likeCount = if (isLiked) likeCount + 1 else likeCount - 1
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Like",
                                tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = likeCount.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    
                    // Comment Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = { /* Open comments */ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Email,
                                contentDescription = "Comments",
                                tint = MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Text(
                            text = news.comments.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
                
                // Share Button
                IconButton(
                    onClick = { /* Share */ },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyNewsState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.Info,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No news found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Try adjusting your filters",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

data class NewsItem(
    val id: String,
    val title: String,
    val content: String,
    val author: String,
    val category: String,
    val timeAgo: String,
    val likes: Int,
    val comments: Int,
    val isVerified: Boolean,
    val imageUrl: String?
)
