package com.example.core.presentation.designsystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.designsystem.RuniqueBlack
import com.example.core.presentation.designsystem.RuniqueGray
import com.example.core.presentation.designsystem.RuniqueTheme

/**
 * A customizable filled action button for Runique.
 *
 * Displays a [text] label or a loading spinner depending on the [isLoading] state.
 * It uses the primary color scheme by default and supports disabled styling.
 *
 * This composable is typically used for primary actions, such as submitting a form,
 * logging in, or confirming user input.
 *
 * @param text The text label displayed inside the button when not loading.
 * @param isLoading Whether the button should show a loading spinner instead of text.
 * @param modifier Modifier used to adjust layout or add additional styling.
 * @param enabled Controls whether the button is clickable or disabled.
 * @param onClick Callback triggered when the button is pressed.
 *
 * Example:
 * ```
 * RuniqueActionButton(
 *     text = "Continue",
 *     isLoading = viewModel.isProcessing,
 *     onClick = { viewModel.submit() }
 * )
 * ```
 */
@Composable
fun RuniqueActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = RuniqueGray,
            disabledContainerColor = RuniqueBlack
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * A customizable outlined action button for Runique.
 *
 * Displays a [text] label or a loading spinner depending on the [isLoading] state.
 *
 * This component wraps an [OutlinedButton] with a rounded shape, subtle border, and
 * dynamic content visibility transitions based on loading state.
 *
 * @param text The text label displayed inside the button when not loading.
 * @param isLoading Whether the button should show a loading spinner instead of text.
 * @param modifier Modifier used to adjust the layout or styling of the button.
 * @param enabled Controls whether the button is clickable or disabled.
 * @param onClick Callback triggered when the button is pressed.
 *
 * Example:
 * ```
 * RuniqueOutlinedActionButton(
 *     text = "Sign In",
 *     isLoading = viewModel.isSigningIn,
 *     onClick = { viewModel.signIn() }
 * )
 * ```
 */
@Composable
fun RuniqueOutlinedActionButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(
            width = 0.5.dp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(100f),
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview
@Composable
private fun RuniqueActionButtonPreview() {
    RuniqueTheme {
        RuniqueActionButton(
            text = "Get Started",
            enabled = true,
            isLoading = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun RuniqueOutlinedActionButtonPreview() {
    RuniqueTheme {
        Column {
            RuniqueOutlinedActionButton(
                text = "Get Started",
                enabled = true,
                isLoading = false,
                onClick = {}
            )
        }
    }
}