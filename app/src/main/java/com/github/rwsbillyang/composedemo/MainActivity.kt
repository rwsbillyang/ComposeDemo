package com.github.rwsbillyang.composedemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.github.rwsbillyang.composedemo.demo.DemoActivity
import com.github.rwsbillyang.composedemo.rally.RallyActivity
import com.github.rwsbillyang.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = this
                    Column {
                        Row{
                          Button(onClick = { startActivity(Intent(context, DemoActivity::class.java)) }) {
                              Text("Demo")
                          }
                        }
                        Row{
                            Button(onClick = { startActivity(Intent(context, RallyActivity::class.java)) }) {
                                Text("RallyApp")
                            }
                        }
                    }
                }
            }
        }
    }


}
