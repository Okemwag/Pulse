package com.okemwag.pulse.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.okemwag.pulse.ui.alerts.AlertsScreen
import com.okemwag.pulse.ui.home.HomeScreen
import com.okemwag.pulse.ui.market.MarketScreen
import com.okemwag.pulse.ui.news.NewsScreen
import com.okemwag.pulse.ui.profile.ProfileScreen
import com.okemwag.pulse.ui.splash.SplashScreen

/**
 * Navigation Routes
 */
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object News : Screen("news")
    object Alerts : Screen("alerts")
    object Classifieds : Screen("classifieds")
    object Profile : Screen("profile")
    object Wallet : Screen("wallet")
}

/**
 * Bottom Navigation Items
 */
data class BottomNavItem(
    val screen: Screen,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int = 0
)

val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screen.Home,
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    BottomNavItem(
        screen = Screen.News,
        title = "News",
        selectedIcon = Icons.Filled.List,
        unselectedIcon = Icons.Outlined.List
    ),
    BottomNavItem(
        screen = Screen.Alerts,
        title = "Alerts",
        selectedIcon = Icons.Filled.Notifications,
        unselectedIcon = Icons.Outlined.Notifications,
        badgeCount = 3
    ),
    BottomNavItem(
        screen = Screen.Classifieds,
        title = "Market",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart
    ),
    BottomNavItem(
        screen = Screen.Profile,
        title = "Profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person
    )
)

/**
 * Main Navigation Controller
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulseNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    
    // Determine if we should show bottom nav
    val showBottomBar = currentRoute in bottomNavItems.map { it.screen.route }
    
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                PulseBottomNavigation(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(paddingValues),
            enterTransition = {
                fadeIn(animationSpec = tween(300)) +
                    slideInHorizontally(animationSpec = tween(300)) { it / 4 }
            },
            exitTransition = {
                fadeOut(animationSpec = tween(300)) +
                    slideOutHorizontally(animationSpec = tween(300)) { -it / 4 }
            },
            popEnterTransition = {
                fadeIn(animationSpec = tween(300)) +
                    slideInHorizontally(animationSpec = tween(300)) { -it / 4 }
            },
            popExitTransition = {
                fadeOut(animationSpec = tween(300)) +
                    slideOutHorizontally(animationSpec = tween(300)) { it / 4 }
            }
        ) {
            // Splash Screen
            composable(Screen.Splash.route) {
                SplashScreen(
                    onSplashComplete = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            
            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToNews = { navController.navigate(Screen.News.route) },
                    onNavigateToAlerts = { navController.navigate(Screen.Alerts.route) }
                )
            }
            
            // News Screen
            composable(Screen.News.route) {
                NewsScreen()
            }
            
            // Alerts Screen
            composable(Screen.Alerts.route) {
                AlertsScreen()
            }
            
            // Marketplace Screen
            composable(Screen.Classifieds.route) {
                MarketScreen()
            }
            
            // Profile Screen
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            
            // Wallet Screen (placeholder for now)
            composable(Screen.Wallet.route) {
                PlaceholderScreen(title = "Wallet", description = "Your token balance and transactions")
            }
        }
    }
}

/**
 * Beautiful Bottom Navigation Bar
 */
@Composable
fun PulseBottomNavigation(
    navController: NavController,
    currentRoute: String?
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = NavigationBarDefaults.Elevation
    ) {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.screen.route
            
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount > 0) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

/**
 * Placeholder Screen for unimplemented features
 */
@Composable
private fun PlaceholderScreen(
    title: String,
    description: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
