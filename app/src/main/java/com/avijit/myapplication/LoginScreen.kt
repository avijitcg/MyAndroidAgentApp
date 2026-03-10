package com.avijit.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.avijit.myapplication.ui.theme.MyApplicationTheme

// ─── String constants (also used by unit tests) ──────────────────────────────
internal const val LOGIN_SCREEN_TITLE      = "Capgemini DCX"
internal const val LOGIN_EMAIL_PLACEHOLDER = "email@domain.com"

// ─── Colour tokens (matches Figma node 1:1588) ───────────────────────────────
private val ColorBgGradientTop    = Color(0xFFCBEFEB)  // light teal  – Figma bg
private val ColorBgGradientBottom = Color(0xFFFFFFFF)  // white       – Figma bg
private val ColorBorderLight      = Color(0xFFE0E0E0)
private val ColorDivider          = Color(0xFFE6E6E6)
private val ColorHint             = Color(0xFF828282)
private val ColorSocialBg         = Color(0xFFEEEEEE)
private val ColorError            = Color(0xFFB00020)  // Material error red
private val ShapeButton           = RoundedCornerShape(8.dp)

/**
 * BTS-5 — Login / Sign-in screen.
 *
 * Mirrors Figma design node 1:1588:
 *  • Light teal-to-white vertical gradient background
 *  • App launcher icon + "Capgemini DCX" title at top
 *  • "Create an account" heading + subtitle (vertically centred)
 *  • Email input  →  Continue button (wired to [LoginViewModel])
 *  • Inline validation error shown below the field when [LoginUiState.emailError] is set
 *  • Continue button disabled while [LoginUiState.isLoading]
 *  • "or" divider
 *  • Continue with Google (coloured G logo)
 *  • Continue with Apple  (Apple logo)
 *  • Terms of Service / Privacy Policy footer
 *
 * @param viewModel Defaults to a [LoginViewModel] scoped to the nearest
 *                  [androidx.lifecycle.ViewModelStoreOwner] (Activity/Fragment).
 */
@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(ColorBgGradientTop, ColorBgGradientBottom)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── App logo + title ──────────────────────────────────────────────
            Spacer(modifier = Modifier.height(58.dp))
            Image(
                // R.mipmap.ic_launcher resolves to an <adaptive-icon> XML on API 26+,
                // which Compose's painterResource() cannot decode (only VectorDrawable
                // and rasterized assets are supported). Use the rasterized PNG logo instead.
                painter = painterResource(id = R.drawable.ic_cg_logo),
                contentDescription = "$LOGIN_SCREEN_TITLE logo",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = LOGIN_SCREEN_TITLE,
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

            // ── Email input + inline error + Continue button ──────────────────
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Field + error message grouped so error sits directly below input
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.onEmailChange(it) },
                        placeholder = {
                            Text(
                                text = LOGIN_EMAIL_PLACEHOLDER,
                                fontSize = 14.sp,
                                color = ColorHint
                            )
                        },
                        isError = uiState.emailError != null,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        shape = ShapeButton,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = ColorBorderLight,
                            focusedBorderColor = Color.Black,
                            errorBorderColor = ColorError,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            errorContainerColor = Color.White
                        ),
                        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp)
                    )
                    if (uiState.emailError != null) {
                        Text(
                            text = uiState.emailError!!,
                            color = ColorError,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                        )
                    }
                }

                Button(
                    onClick = { viewModel.onContinueClick() },
                    enabled = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    shape = ShapeButton,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Black.copy(alpha = 0.38f),
                        disabledContentColor = Color.White.copy(alpha = 0.38f)
                    )
                ) {
                    Text(
                        text = if (uiState.isLoading) "Loading…" else "Continue",
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
