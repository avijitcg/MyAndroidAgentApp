package com.avijit.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ─── Email validation regex ───────────────────────────────────────────────────
// Exposed as internal so unit tests can assert against it directly.
internal val EMAIL_REGEX =
    Regex("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

/**
 * Immutable snapshot of the login screen's UI state.
 *
 * @param email         Current value of the email input field.
 * @param emailError    Non-null when the email fails validation; contains the
 *                      human-readable error string to display inline.
 * @param isLoading     True while an async auth operation is in flight;
 *                      the Continue button should be disabled in this state.
 * @param isSuccess     True once authentication completes successfully.
 *                      The host screen / nav layer observes this to navigate away.
 */
data class LoginUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)

/**
 * BTS-5 — ViewModel for the Login / Sign-in screen (Figma node 1:1588).
 *
 * Responsibilities:
 *  1. Own the authoritative [LoginUiState] and expose it as a [StateFlow].
 *  2. Validate the email address when the user taps "Continue".
 *  3. Clear any stale validation error as soon as the user edits the field.
 *
 * No network calls are made here yet; the success transition is a stub that
 * will be replaced in BTS-6 when the AuthRepository is wired in.
 */
class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /** Called on every keystroke in the email field. */
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
    }

    /**
     * Called when the user taps the "Continue" button.
     *
     * Validates the email format; on success sets [LoginUiState.isLoading] and
     * then [LoginUiState.isSuccess] (stub — real auth in BTS-6).
     */
    fun onContinueClick() {
        val email = _uiState.value.email.trim()

        if (!EMAIL_REGEX.matches(email)) {
            _uiState.update {
                it.copy(emailError = "Please enter a valid email address")
            }
            return
        }

        // TODO BTS-6: replace stub with AuthRepository call inside viewModelScope
        _uiState.update { it.copy(isLoading = true, emailError = null) }
        _uiState.update { it.copy(isLoading = false, isSuccess = true) }
    }
}
