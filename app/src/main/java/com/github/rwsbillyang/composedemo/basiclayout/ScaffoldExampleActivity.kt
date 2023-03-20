package com.github.rwsbillyang.composedemo.basiclayout

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.rwsbillyang.composedemo.ui.theme.Purple700
import kotlinx.coroutines.launch

//https://juejin.cn/post/7037737642208952357
class ScaffoldExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = Color.White) {
                ScaffoldExample()
            }
        }
    }
}
@Composable
fun ScaffoldExample() {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                onMenuClicked = {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
        },
        bottomBar = { BottomBar() },
        drawerContent = {
            Drawer()
        },
        content = {
            Log.d("ScaffoldExample", it.toString())
            Content()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        when (scaffoldState.snackbarHostState.showSnackbar(
                            message = "Snack Bar",
                            actionLabel = "Dismiss"
                        )) {
                            SnackbarResult.Dismissed -> {
                            }

                            SnackbarResult.ActionPerformed -> {
                            }
                        }
                    }
                }
            ) {
                Text(text = "+", color = Color.White, fontSize = 26.sp)
            }
        }
    )
}



@Composable
fun Drawer() {
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        repeat(5) { item ->
            Text(text = "Item $item", modifier = Modifier.padding(8.dp), color = Color.Black)
        }
    }
}

@Composable
fun Content() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(text = "Content", color = Purple700)
    }
}
@Composable
fun BottomBar() {
    BottomAppBar(
        backgroundColor = Purple700
    ) {
        Text(text = "BottomAppBar", color = Color.White)
    }
}

@Composable
fun TopBar(onMenuClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Scaffold Example", color = Color.White)
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.clickable(onClick = onMenuClicked),
                tint = Color.White
            )
        },
        backgroundColor = Purple700,
        elevation = 12.dp
    )
}
