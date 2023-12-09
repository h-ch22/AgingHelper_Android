package com.cj.aginghelper.frameworks.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cj.aginghelper.frameworks.models.BottomNavItem
import com.cj.aginghelper.frameworks.models.NavigationGraph
import com.cj.aginghelper.ui.theme.AgingHelperColorPalette
import com.cj.aginghelper.ui.theme.AgingHelperTheme
import com.cj.aginghelper.ui.theme.accent
import com.cj.aginghelper.ui.theme.gray
import com.cj.aginghelper.ui.theme.white

@Composable
fun MainView(){
    val navItems = listOf(
        BottomNavItem.home,
        BottomNavItem.prediction,
        BottomNavItem.authorization,
        BottomNavItem.more
    )

    val navController = rememberNavController()

    AgingHelperTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = AgingHelperColorPalette.current.background, contentColor = accent) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    navItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.screenRoute,
                            onClick = {
                                navController.navigate(item.screenRoute){
                                    navController.graph.startDestinationRoute?.let{
                                        popUpTo(it){
                                            saveState = true
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(imageVector = item.icon, contentDescription = null)
                            }, colors = NavigationBarItemDefaults.colors(
                                selectedTextColor = accent,
                                selectedIconColor = white,
                                indicatorColor = accent.copy(alpha = 0.75F),
                                unselectedIconColor = gray,
                                unselectedTextColor = gray
                            ), label = {
                                Text(text = item.title)
                            }, alwaysShowLabel = false)
                    }
                }
            }
        ) {
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(it)) {
                Box(modifier = Modifier.padding(it)){
                    NavigationGraph(navController = navController)
                }
            }
        }
    }
}

@Preview
@Composable
fun MainViewPreview(){
    MainView()
}