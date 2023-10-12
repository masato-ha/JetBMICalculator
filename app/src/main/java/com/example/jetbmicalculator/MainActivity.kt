package com.example.jetbmicalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetbmicalculator.ui.theme.JetBMICalculatorTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetBMICalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    val isDarkTheme = isSystemInDarkTheme()

                    SideEffect{
                        if(isDarkTheme){
                            systemUiController.setStatusBarColor(Color.Black)
                            systemUiController.setNavigationBarColor(Color.Black)
                        }
                        else{
                            systemUiController.setStatusBarColor(Color.White)
                            systemUiController.setNavigationBarColor(Color.White)
                        }
                    }



                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = "BMI計算アプリ",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        //身長
                        PinkLabeledTextField(
                            value = viewModel.height,
                            onValueChange = { viewModel.height = it },
                            label = "身長(㎝)",
                            placeholder = "170",
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        //体重
                        PinkLabeledTextField(
                            value = viewModel.weight,
                            onValueChange = { viewModel.weight = it },
                            label = "体重(Kg)",
                            placeholder = "65",
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        //計算する
                        Button(
                            onClick = {viewModel.calculateBMI() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF85F6A)
                            ),
                        ) {
                            Text(
                                text = "計算する",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))

                        //結果表示テキスト
                        Text(
                            text = "あなたのBMIは${viewModel.bmi}です",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PinkLabeledTextField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        placeholder: String,
    ) {
        Column {
            Text(
                text = label,
                color = Color(0xFFF85F6A),
                fontWeight = FontWeight.Bold,
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = onValueChange,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent
                ),
                placeholder = { Text(text = placeholder) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
    }
}
