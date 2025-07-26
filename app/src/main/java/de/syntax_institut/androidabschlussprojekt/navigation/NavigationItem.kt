package de.syntax_institut.androidabschlussprojekt.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class TabItem (
    val route: Any,
    val tabTitle: String,
    val tabIcon: ImageVector
){
    MAP(MyLibraryRoute, "Bibliothek", Icons.Filled.LocalLibrary),
    BROWSE(BrowseRoute,"Entdecken", Icons.Filled.Search),
    HERB(BookRoute, "Bücher", Icons.AutoMirrored.Filled.MenuBook),
    FAVOURITES(WatchlistRoute, "Merkliste", Icons.Filled.Bookmark),
    INFO(InfoRoute, "Info", Icons.Filled.Info),
}