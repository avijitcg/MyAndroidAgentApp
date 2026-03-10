package com.avijit.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [LoginViewModel].
 *
 * Covers:
 *  - Initial state invariants
 *  - [LoginViewModel.onEmailChange]: updates email, clears stale errors
 *  - [LoginViewModel.onContinueClick]: validates email format (error path)
 *  - [LoginViewModel.onContinueClick]: accepts valid email (success path)
 *  - [EMAIL_REGEX]: edge cases for the validation pattern
 *
 * These are pure JVM tests — no Android framework or Compose runtime needed.
 *
 * Jira: BTS-5   Figma node: 1:1588
 */
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    // ── Initial state ──────────────────────────────────────────────────────────

    @Test
    fun initialState_emailIsEmpty() {
        assertEquals("Initial email must be empty", "", viewModel.uiState.value.email)
    }

    @Test
    fun initialState_noEmailError() {
        assertNull("No error expected on fresh ViewModel", viewModel.uiState.value.emailError)
    }

    @Test
    fun initialState_notLoading() {
        assertFalse("isLoading must be false on init", viewModel.uiState.value.isLoading)
    }

    @Test
    fun initialState_notSuccess() {
        assertFalse("isSuccess must be false on init", viewModel.uiState.value.isSuccess)
    }

    // ── onEmailChange ──────────────────────────────────────────────────────────

    @Test
    fun onEmailChange_updatesEmailInState() {
        viewModel.onEmailChange("hello@example.com")
        assertEquals("hello@example.com", viewModel.uiState.value.email)
    }

    @Test
    fun onEmailChange_overwritesPreviousValue() {
        viewModel.onEmailChange("first@example.com")
        viewModel.onEmailChange("second@example.com")
        assertEquals("second@example.com", viewModel.uiState.value.email)
    }

    @Test
    fun onEmailChange_clearsExistingEmailError() {
        // Prime an error by submitting an invalid email
        viewModel.onContinueClick()
        assertNotNull("Pre-condition: error must be set", viewModel.uiState.value.emailError)

        // Any keystroke should clear the error immediately
        viewModel.onEmailChange("a")
        assertNull("Error must be cleared after user edits field", viewModel.uiState.value.emailError)
    }

    // ── onContinueClick – validation (error path) ──────────────────────────────

    @Test
    fun onContinueClick_emptyEmail_setsEmailError() {
        viewModel.onContinueClick()
        assertNotNull("Empty email must produce an error", viewModel.uiState.value.emailError)
    }

    @Test
    fun onContinueClick_plainText_setsEmailError() {
        viewModel.onEmailChange("notanemail")
        viewModel.onContinueClick()
        assertNotNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun onContinueClick_missingDomain_setsEmailError() {
        viewModel.onEmailChange("user@")
        viewModel.onContinueClick()
        assertNotNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun onContinueClick_missingTld_setsEmailError() {
        viewModel.onEmailChange("user@example")
        viewModel.onContinueClick()
        assertNotNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun onContinueClick_invalidEmail_doesNotSetSuccess() {
        viewModel.onEmailChange("bad@@input")
        viewModel.onContinueClick()
        assertFalse(viewModel.uiState.value.isSuccess)
    }

    @Test
    fun onContinueClick_invalidEmail_doesNotSetLoading() {
        viewModel.onEmailChange("bad")
        viewModel.onContinueClick()
        assertFalse(viewModel.uiState.value.isLoading)
    }

    // ── onContinueClick – success path ─────────────────────────────────────────

    @Test
    fun onContinueClick_validEmail_setsIsSuccess() {
        viewModel.onEmailChange("user@example.com")
        viewModel.onContinueClick()
        assertTrue("Valid email must result in isSuccess = true", viewModel.uiState.value.isSuccess)
    }

    @Test
    fun onContinueClick_validEmail_clearsEmailError() {
        viewModel.onEmailChange("user@example.com")
        viewModel.onContinueClick()
        assertNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun onContinueClick_validEmail_isNotLoading() {
        // The stub sets isLoading then immediately clears it; final state must be false.
        viewModel.onEmailChange("user@example.com")
        viewModel.onContinueClick()
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun onContinueClick_trimsLeadingTrailingWhitespace() {
        // "  user@example.com  " should be treated as valid after trim()
        viewModel.onEmailChange("  user@example.com  ")
        viewModel.onContinueClick()
        assertTrue(viewModel.uiState.value.isSuccess)
    }

    // ── EMAIL_REGEX edge cases ─────────────────────────────────────────────────

    @Test
    fun emailRegex_acceptsStandardAddress() {
        assertTrue(EMAIL_REGEX.matches("hello@example.com"))
    }

    @Test
    fun emailRegex_acceptsSubdomainAddress() {
        assertTrue(EMAIL_REGEX.matches("user@mail.example.co.uk"))
    }

    @Test
    fun emailRegex_acceptsPlusAlias() {
        assertTrue(EMAIL_REGEX.matches("user+tag@example.com"))
    }

    @Test
    fun emailRegex_rejectsMissingAtSign() {
        assertFalse(EMAIL_REGEX.matches("userexample.com"))
    }

    @Test
    fun emailRegex_rejectsMissingDomain() {
        assertFalse(EMAIL_REGEX.matches("user@"))
    }

    @Test
    fun emailRegex_rejectsMissingTld() {
        assertFalse(EMAIL_REGEX.matches("user@example"))
    }

    @Test
    fun emailRegex_rejectsDoubleAt() {
        assertFalse(EMAIL_REGEX.matches("bad@@example.com"))
    }

    @Test
    fun emailRegex_rejectsEmptyString() {
        assertFalse(EMAIL_REGEX.matches(""))
    }
}
