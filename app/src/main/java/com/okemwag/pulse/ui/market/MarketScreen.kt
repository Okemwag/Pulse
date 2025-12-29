package com.okemwag.pulse.ui.market

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.okemwag.design.theme.PulseGradients

/**
 * Community Marketplace Screen
 * 
 * Buy, sell, and trade items within the community.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen() {
    var selectedCategory by remember { mutableStateOf<MarketCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var viewMode by remember { mutableStateOf(ViewMode.GRID) }
    
    val categories = listOf(
        MarketCategory("For Sale", Icons.Filled.ShoppingCart, Color(0xFF4CAF50)),
        MarketCategory("Services", Icons.Filled.Build, Color(0xFF2196F3)),
        MarketCategory("Jobs", Icons.Filled.Person, Color(0xFF9C27B0)),
        MarketCategory("Housing", Icons.Filled.Home, Color(0xFFFF9800)),
        MarketCategory("Vehicles", Icons.Filled.Star, Color(0xFFE91E63)),
        MarketCategory("Free", Icons.Filled.Favorite, Color(0xFF00BCD4))
    )
    
    val listings = remember {
        listOf(
            MarketItem(
                id = "1",
                title = "Vintage Wooden Desk",
                description = "Beautiful oak desk in excellent condition. Minor scratches. Must pick up.",
                price = 150.0,
                originalPrice = 250.0,
                category = "For Sale",
                seller = "John D.",
                location = "Downtown",
                timeAgo = "2 hours ago",
                isFeatured = true,
                isNegotiable = true
            ),
            MarketItem(
                id = "2",
                title = "Professional Photography",
                description = "Event photography services. Weddings, parties, corporate events. 10+ years experience.",
                price = 200.0,
                originalPrice = null,
                category = "Services",
                seller = "Sarah's Studio",
                location = "City-wide",
                timeAgo = "5 hours ago",
                isFeatured = false,
                isNegotiable = false
            ),
            MarketItem(
                id = "3",
                title = "Babysitter Available",
                description = "Experienced babysitter available weekday evenings and weekends. CPR certified.",
                price = 15.0,
                originalPrice = null,
                category = "Services",
                seller = "Emily R.",
                location = "North Side",
                timeAgo = "1 day ago",
                isFeatured = false,
                isNegotiable = true
            ),
            MarketItem(
                id = "4",
                title = "2BR Apartment for Rent",
                description = "Spacious 2 bedroom apartment. Modern kitchen, in-unit laundry, parking included.",
                price = 1200.0,
                originalPrice = null,
                category = "Housing",
                seller = "Property Mgmt",
                location = "East Village",
                timeAgo = "3 days ago",
                isFeatured = true,
                isNegotiable = false
            ),
            MarketItem(
                id = "5",
                title = "Part-time Cashier Needed",
                description = "Local grocery store seeking friendly cashier. Flexible hours, competitive pay.",
                price = 16.0,
                originalPrice = null,
                category = "Jobs",
                seller = "Fresh Foods",
                location = "Main St.",
                timeAgo = "1 week ago",
                isFeatured = false,
                isNegotiable = false
            ),
            MarketItem(
                id = "6",
                title = "Free Moving Boxes",
                description = "Assorted moving boxes and packing paper. Must take all. Available this weekend only.",
                price = 0.0,
                originalPrice = null,
                category = "Free",
                seller = "Mike T.",
                location = "Westside",
                timeAgo = "2 days ago",
                isFeatured = false,
                isNegotiable = false
            )
        )
    }
    
    val filteredListings = listings.filter { listing ->
        (selectedCategory == null || listing.category == selectedCategory?.name) &&
        (searchQuery.isEmpty() || listing.title.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF00C853),
                                Color(0xFF64DD17)
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
                        text = "Marketplace",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row {
                        IconButton(onClick = { 
                            viewMode = if (viewMode == ViewMode.GRID) ViewMode.LIST else ViewMode.GRID
                        }) {
                            Icon(
                                if (viewMode == ViewMode.GRID) Icons.Filled.List else Icons.Filled.Menu,
                                contentDescription = "Toggle View",
                                tint = Color.White
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search marketplace...", color = Color.White.copy(alpha = 0.7f)) },
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
            ExtendedFloatingActionButton(
                onClick = { /* Create listing */ },
                containerColor = Color(0xFF00C853),
                contentColor = Color.White,
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                text = { Text("Sell") }
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
            // Categories
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories) { category ->
                        CategoryCard(
                            category = category,
                            isSelected = selectedCategory == category,
                            onClick = { 
                                selectedCategory = if (selectedCategory == category) null else category
                            }
                        )
                    }
                }
            }
            
            // Featured Section
            val featuredItems = filteredListings.filter { it.isFeatured }
            if (featuredItems.isNotEmpty()) {
                item {
                    Text(
                        text = "Featured",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
                item {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(featuredItems) { item ->
                            FeaturedListingCard(
                                listing = item,
                                modifier = Modifier.width(280.dp)
                            )
                        }
                    }
                }
            }
            
            // All Listings
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "All Listings",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${filteredListings.size} items",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            
            // Listings Grid/List
            items(filteredListings.chunked(2)) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { listing ->
                        ListingCard(
                            listing = listing,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty space if odd number
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            if (filteredListings.isEmpty()) {
                item {
                    EmptyMarketState()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryCard(
    category: MarketCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) category.color else category.color.copy(alpha = 0.1f)
        ),
        modifier = Modifier.size(width = 100.dp, height = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                category.icon,
                contentDescription = null,
                tint = if (isSelected) Color.White else category.color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = category.name,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) Color.White else category.color,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeaturedListingCard(
    listing: MarketItem,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { /* Navigate to detail */ },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                
                // Featured badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "FEATURED",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = listing.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            Text(
                text = listing.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    if (listing.originalPrice != null && listing.originalPrice > listing.price) {
                        Text(
                            text = "$${listing.originalPrice.toInt()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.outline,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                    Text(
                        text = if (listing.price == 0.0) "FREE" else "$${listing.price.toInt()}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00C853)
                    )
                }
                
                IconButton(
                    onClick = { /* Save */ },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "Save",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListingCard(
    listing: MarketItem,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { /* Navigate to detail */ },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Image placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = listing.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = listing.location,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (listing.price == 0.0) "FREE" else "$${listing.price.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (listing.price == 0.0) Color(0xFF00BCD4) else Color(0xFF00C853)
                )
                
                if (listing.isNegotiable) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = "OBO",
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyMarketState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Outlined.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "No listings found",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Be the first to post in this category!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

data class MarketItem(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val originalPrice: Double?,
    val category: String,
    val seller: String,
    val location: String,
    val timeAgo: String,
    val isFeatured: Boolean,
    val isNegotiable: Boolean
)

data class MarketCategory(
    val name: String,
    val icon: ImageVector,
    val color: Color
)

enum class ViewMode { GRID, LIST }
