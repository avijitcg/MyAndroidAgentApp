package com.avijit.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avijit.myapplication.ui.theme.MyApplicationTheme

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(102.dp))

            Text(
                text = "App name",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                letterSpacing = (-0.24).sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Heading
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Create an account",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
                Text(
                    text = "Enter your email to sign up for this app",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Input + Buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            text = "email@domain.com",
                            fontSize = 14.sp,
                            color = Color(0xFF828282)
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFE0E0E0),
                        focusedBorderColor = Color.Black,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                )

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Continue", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Divider
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE6E6E6))
                Text(
                    text = "or",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF828282)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color(0xFFE6E6E6))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEEEEEE),
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(text = "G  Continue with Google", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEEEEEE),
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(0.dp)
                ) {
                    Text(text = "\uF8FF  Continue with Apple", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Terms
            val termsText = buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF828282), fontSize = 12.sp)) {
                    append("By clicking continue, you agree to our ")
                }
                withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp)) {
                    pushStringAnnotation("TERMS", "terms")
                    append("Terms of Service")
                    pop()
                }
                withStyle(SpanStyle(color = Color(0xFF828282), fontSize = 12.sp)) {
                    append(" and ")
                }
                withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp)) {
                    pushStringAnnotation("PRIVACY", "privacy")
                    append("Privacy Policy")
                    pop()
                }
            }
            ClickableText(
                text = termsText,
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                onClick = {}
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen()
    }
}
