package com.avijit.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.avijit.myapplication.ui.theme.MyApplicationTheme

// ─── Colour tokens (matches Figma) ──────────────────────────────────────────
private val ColorBorderLight   = Color(0xFFE0E0E0)
private val ColorDivider       = Color(0xFFE6E6E6)
private val ColorHint          = Color(0xFF828282)
private val ColorSocialBg      = Color(0xFFEEEEEE)
private val ShapeButton        = RoundedCornerShape(8.dp)

/**
 * BTS-3 — Login / Sign-in screen.
 *
 * Mirrors the Figma design:
 *  • App name title at top
 *  • "Create an account" heading + subtitle (vertically centred)
 *  • Email input  →  Continue button
 *  • "or" divider
 *  • Continue with Google (coloured G logo)
 *  • Continue with Apple  (Apple logo)
 *  • Terms of Service / Privacy Policy footer
 */
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

            // ── App name ─────────────────────────────────────────────────────
            Spacer(modifier = Modifier.height(102.dp))
            Text(
                text = "App name",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                letterSpacing = (-0.24).sp
            )

            // Push the content block to the vertical centre
            Spacer(modifier = Modifier.weight(1f))

            // ── Heading ───────────────────────────────────────────────────────
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

            // ── Email input + Continue button ─────────────────────────────────
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
                            color = ColorHint
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = ShapeButton,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = ColorBorderLight,
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
                    shape = ShapeButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── "or" divider ──────────────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = ColorDivider)
                Text(
                    text = "or",
                    modifier = Modifier.padding(horizontal = 8.dp),
                    fontSize = 14.sp,
                    color = ColorHint
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = ColorDivider)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Social login buttons ──────────────────────────────────────────
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Continue with Google
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = ShapeButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorSocialBg,
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = "Google logo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continue with Google",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }

                // Continue with Apple
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = ShapeButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ColorSocialBg,
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_apple),
                            contentDescription = "Apple logo",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Continue with Apple",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Terms & Privacy footer ────────────────────────────────────────
            val termsText = buildAnnotatedString {
                withStyle(SpanStyle(color = ColorHint, fontSize = 12.sp)) {
                    append("By clicking continue, you agree to our ")
                }
                pushStringAnnotation(tag = "TERMS", annotation = "terms")
                withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp)) {
                    append("Terms of Service")
                }
                pop()
                withStyle(SpanStyle(color = ColorHint, fontSize = 12.sp)) {
                    append(" and ")
                }
                pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                withStyle(SpanStyle(color = Color.Black, fontSize = 12.sp)) {
                    append("Privacy Policy")
                }
                pop()
            }
            ClickableText(
                text = termsText,
                style = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                onClick = { /* TODO: open Terms / Privacy */ }
            )

            // Balance the top weight so content is centred
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
