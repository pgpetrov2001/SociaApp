package com.example.socialapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.socialapp.ui.screens.SocialTrackerScreen
import com.example.socialapp.ui.theme.SocialAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialAppTheme(darkTheme = true, dynamicColor = false) {
                SocialTrackerScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun SocialTrackerScreenPreview() {
    SocialAppTheme(darkTheme = true, dynamicColor = false) {
        SocialTrackerScreen()
    }
}