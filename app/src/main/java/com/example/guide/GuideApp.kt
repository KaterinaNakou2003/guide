@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.guide

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.guide.ui.navigation.GuideNavHost

/**
 * Top level composable that represents screens for the application.
 */
@Composable
fun GuideApp(navController: NavHostController = rememberNavController()) {
    GuideNavHost(navController = navController)
}
