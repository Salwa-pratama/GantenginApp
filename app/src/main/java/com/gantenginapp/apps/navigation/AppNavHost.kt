package com.gantenginapp.apps.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gantenginapp.apps.model.User
import com.gantenginapp.apps.pages.login.LoginScreen
import com.gantenginapp.apps.pages.register.RegisterScreen
import com.gantenginapp.apps.pages.home.HomeContent
import  com.gantenginapp.apps.pages.allPages.ProfileScreen
import  com.gantenginapp.apps.pages.allPages.BarberDetailScreen
import  com.gantenginapp.apps.pages.allPages.RegisterStoreScreen
import  com.gantenginapp.apps.pages.allPages.MyStore
import  com.gantenginapp.apps.viewmodel.UserViewModel
import com.gantenginapp.apps.remote.RetrofitClient
import com.gantenginapp.apps.viewmodel.StoreViewModes
@Composable
fun AppNavHost(
    navController: NavHostController,
    onUserLogin: (User) -> Unit,
    userViewModel: UserViewModel = viewModel ()
) {
    val currentUser by userViewModel.user.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = { User ->
                    userViewModel.setUser(User)
                    onUserLogin(User)
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterSuccess = { User ->
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Routes.HOME) {
            HomeContent(
                onProfileClick = {
                    navController.navigate(Routes.PROFILE)
                },
                onDetailClick = {
                    navController.navigate(Routes.DETAIL_STORE)
                },
                viewModel = StoreViewModes(api = RetrofitClient.instance),

            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                user = currentUser,
                onBackClick = {navController.popBackStack()},
                onRegistStoreClick = {navController.navigate(Routes.REGIST_STORE)},
                onGoToMyStore = {navController.navigate(Routes.MY_STORE)}
            )
        }

        composable(Routes.DETAIL_STORE) {
            BarberDetailScreen()
        }

        // Registrasi Toko
        composable (Routes.REGIST_STORE){
            RegisterStoreScreen(
                user = currentUser,
                onRegisterStoreSuccess = {
                    val uid = currentUser?.id ?: return@RegisterStoreScreen
                    userViewModel.refreshUser {
                        RetrofitClient.instance.getUser(uid)
                    }
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.REGIST_STORE) { inclusive = true }
                    }
                }
            )
        }

        // MyStore
        composable (Routes.MY_STORE) {
            MyStore()
        }
    }
}
