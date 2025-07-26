package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.syntax_institut.androidabschlussprojekt.AboutUs.screen.AboutUsScreen
import de.syntax_institut.androidabschlussprojekt.R
import de.syntax_institut.androidabschlussprojekt.faq.presentation.screens.FAQScreen
import de.syntax_institut.androidabschlussprojekt.remotebooks.domain.model.RemoteBook
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens.BookScreen
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens.BrowseScreen
import de.syntax_institut.androidabschlussprojekt.remotebooks.presentation.screens.DetailScreen
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.screens.ISBNSCannerManuallyScreen
import de.syntax_institut.androidabschlussprojekt.login.presentation.screens.InfoScreen
import de.syntax_institut.androidabschlussprojekt.login.presentation.screens.LoginScreen
import de.syntax_institut.androidabschlussprojekt.library.presentation.screens.LibraryScreen
import de.syntax_institut.androidabschlussprojekt.login.presentation.screens.RegistrationScreen
import de.syntax_institut.androidabschlussprojekt.scanner.presentation.screens.ScannerScreen
import de.syntax_institut.androidabschlussprojekt.ui.theme.VintageWoodBrown
import de.syntax_institut.androidabschlussprojekt.watchlist.presentation.screens.WatchListScreen

@Composable
fun AppStartWithNavigation() {

    val navController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf(TabItem.HERB) }
    val NavBarUnselected = Color(0xFFE6D9C6)

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = VintageWoodBrown,
            ) {
                TabItem.entries.forEach { tabItem ->
                    NavigationBarItem(
                        selected = selectedTab == tabItem,
                        onClick = { selectedTab = tabItem },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            unselectedIconColor = NavBarUnselected,
                            selectedTextColor = Color.Black,
                            unselectedTextColor = NavBarUnselected,
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        ),
                        icon = {
                            Icon(
                                imageVector = tabItem.tabIcon,
                                contentDescription = "TabItem"
                            )
                        },
                        label = {
                            Text(tabItem.tabTitle)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(ScannerRoute)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Image(
                    painter = painterResource(R.drawable.baseline_settings_overscan_24),
                    contentDescription = "Scan"
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = selectedTab.route,
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            composable<BookRoute> {
                BookScreen(
                    onNavigateToToDetail = { book: RemoteBook ->
                        navController.navigate(
                            DetailRoute(
                                id = book.id,
                                title = book.title.ifBlank { "Unbekannter Titel" },
                                authors = book.authors.takeIf { it.isNotEmpty() }?.joinToString(", ") ?: "Unbekannt",
                                imageUrl = book.thumbnail,
                                averageRating = book.averageRating,
                                ratingsCount = book.ratingsCount,
                                isbn = book.isbn ?: "Unbekannt",
                                description = book.description.ifBlank { "Keine Beschreibung vorhanden" }
                            )
                        )
                    }
                )
            }
            composable<WatchlistRoute> {
                WatchListScreen()
            }

            composable<DetailRoute> {
                DetailScreen(
                    navController = navController
                )
            }
            composable<MyLibraryRoute> {
                LibraryScreen()
            }

            composable<BrowseRoute> {
                BrowseScreen()
            }

            composable<InfoRoute> {
                InfoScreen(
                    onNavigateToFAQScreen = {
                        navController.navigate(FAQRoute)
                    },
                    onNavigateToAboutUsScreen = {
                        navController.navigate(AboutUsRoute)
                    }
                )
            }

            composable<ScannerRoute> {
               ScannerScreen(
                   onNavigateToISBNScannerManually = {
                       navController.navigate(ISBNNumberRoute)
                   }
               )
            }

            composable<ISBNNumberRoute> {
                ISBNSCannerManuallyScreen()
            }

            composable<FAQRoute> {
                FAQScreen(navController = navController)
            }

            composable<AboutUsRoute> {
                AboutUsScreen()
            }

            composable<LoginRoute> {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(RegisterRoute)
                    },
                )
            }

            composable<RegisterRoute> {
                RegistrationScreen(
                    onNavigateToLogin = {
                        navController.navigate(LoginRoute)
                    }
                )
            }
        }
    }
}
