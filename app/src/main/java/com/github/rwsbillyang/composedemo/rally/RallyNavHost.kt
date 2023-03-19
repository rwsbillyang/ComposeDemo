package com.github.rwsbillyang.composedemo.rally


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.github.rwsbillyang.composedemo.rally.screen.AccountsScreen
import com.github.rwsbillyang.composedemo.rally.screen.BillsScreen
import com.github.rwsbillyang.composedemo.rally.screen.OverviewScreen
import com.github.rwsbillyang.composedemo.rally.screen.SingleAccountScreen

object SingleAccount2 : RallyDestination {
    // Added for simplicity, this icon will not in fact be used, as SingleAccount isn't
    // part of the RallyTabRow selection
    override val icon = Icons.Filled.Money
    override val route = "single_account"
    override val screen: () -> Unit
        get() = TODO("Not yet implemented")
    const val accountTypeArg = "account_type"
    val routeWithArgs = "$route/{$accountTypeArg}" //  "single_account/{account_type}"
    val arguments = listOf(
        navArgument(accountTypeArg) { type = NavType.StringType }
    )
    val deepLinks = listOf(
        navDeepLink { uriPattern = "rally://$route/{$accountTypeArg}" }// "rally://single_account/{account_type}"
    )
}

@Composable
fun RallyNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Overview.route,
        modifier = modifier
    ) {
        composable(route = Overview.route) {
            OverviewScreen(
                onClickSeeAllAccounts = {
                    navController.navigateSingleTopTo(Accounts.route)
                },
                onClickSeeAllBills = {
                    navController.navigateSingleTopTo(Bills.route)
                },
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Accounts.route) {
            AccountsScreen(
                onAccountClick = { accountType ->
                    navController.navigateToSingleAccount(accountType)
                }
            )
        }
        composable(route = Bills.route) {
            BillsScreen()
        }
        composable(
            route = SingleAccount2.routeWithArgs,
            arguments = SingleAccount2.arguments,
            deepLinks = SingleAccount2.deepLinks
        ) { navBackStackEntry ->
            val accountType =
                navBackStackEntry.arguments?.getString(SingleAccount.accountTypeArg)
            SingleAccountScreen(accountType)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        // 弹出到导航图的起始目的地，以免在您选择标签页时在返回堆栈上积累大量目的地
        //这意味着，在任何目的地按下返回箭头都会将整个返回堆栈弹出到“Overview”屏幕
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item 确保返回堆栈顶部最多只有给定目的地的一个副本
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        //定此导航操作是否应恢复 PopUpToBuilder.saveState 或 popUpToSaveState 属性之前保存的任何状态。请注意，如果之前未使用要导航到的目的地 ID 保存任何状态，此项不会产生任何影响
        //这意味着，重按同一标签页会保留屏幕上之前的数据和用户状态，而无需重新加载
        restoreState = true
    }

private fun NavHostController.navigateToSingleAccount(accountType: String) {
    this.navigateSingleTopTo("${SingleAccount.route}/$accountType")
}
