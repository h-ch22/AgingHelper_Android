package com.cj.aginghelper.frameworks.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LockPerson
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cj.aginghelper.authorization.ui.AuthorizationView
import com.cj.aginghelper.home.ui.HomeView
import com.cj.aginghelper.more.ui.MoreView
import com.cj.aginghelper.prediction.ui.PredictionView

const val HOME = "HOME"
const val PREDICTION = "PREDICTION"
const val AUTHORIZATION = "AUTHORIZATION"
const val MORE = "MORE"

sealed class BottomNavItem(
    val title: String, val icon: ImageVector, val screenRoute: String
){
    object home: BottomNavItem("홈", Icons.Rounded.Home, HOME)
    object prediction: BottomNavItem("예측", Icons.Rounded.PersonSearch, PREDICTION)
    object authorization: BottomNavItem("인증", Icons.Rounded.LockPerson, AUTHORIZATION)
    object more: BottomNavItem("더 보기", Icons.Rounded.MoreHoriz, MORE)
}

@Composable
fun NavigationGraph(navController: NavHostController){
    NavHost(navController = navController, startDestination = BottomNavItem.home.screenRoute){
        composable(BottomNavItem.home.screenRoute){
            HomeView()
        }

        composable(BottomNavItem.prediction.screenRoute){
            PredictionView()
        }

        composable(BottomNavItem.authorization.screenRoute){
            AuthorizationView()
        }

        composable(BottomNavItem.more.screenRoute){
            MoreView()
        }
    }
}