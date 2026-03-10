package com.avijit.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [LoginScreen] string constants and basic logic.
 *
 * Compose rendering tests (screenshot / semantic assertions) live in the
 * androidTest source-set.  These JVM-only tests guard the design-system tokens
 * that are expressed as [internal const val]s so they can be verified without
 * an emulator / device.
 *
 * Jira: BTS-3   Figma node: 1:1588
 */
class LoginScreenTest {

    // ── Title ─────────────────────────────────────────────────────────────────

    @Test
    fun appTitle_matchesFigmaSpec() {
        assertEquals(
            "Screen title must match Figma node 1:1618 (BTS-3)",
            "Capgemini DCX",
            LOGIN_SCREEN_TITLE
        )
    }

    @Test
    fun appTitle_isNotBlank() {
        assertTrue(
            "Screen title must not be blank",
            LOGIN_SCREEN_TITLE.isNotBlank()
        )
    }

    // ── Email placeholder ──────────────────────────────────────────────────────

    @Test
    fun emailPlaceholder_matchesFigmaSpec() {
        assertEquals(
            "Email placeholder must match Figma spec",
            "email@domain.com",
            LOGIN_EMAIL_PLACEHOLDER
        )
    }

    @Test
    fun emailPlaceholder_isValidEmailFormat() {
        assertTrue(
            "Placeholder must look like a valid e-mail address",
            LOGIN_EMAIL_PLACEHOLDER.contains("@") && LOGIN_EMAIL_PLACEHOLDER.contains(".")
        )
    }

    @Test
    fun emailPlaceholder_doesNotContainSpaces() {
        assertFalse(
            "Email placeholder must not contain spaces",
            LOGIN_EMAIL_PLACEHOLDER.contains(" ")
        )
    }
}
