package com.ai.bardly.feature.login.ui

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.ic_visibility
import ai_bardly.composeapp.generated.resources.ic_visibility_off
import ai_bardly.composeapp.generated.resources.login_email
import ai_bardly.composeapp.generated.resources.login_or_with
import ai_bardly.composeapp.generated.resources.login_password
import ai_bardly.composeapp.generated.resources.login_sign_in
import ai_bardly.composeapp.generated.resources.login_sign_in_title
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.auth.FirebaseUser
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignInScreen(
    component: SignInPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        SignInScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun SignInScreenContent(
    viewState: SignInViewState,
    intentDispatcher: IntentDispatcher<SignInIntent>,
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .imePadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SignInTitle()

        EmailField(
            email = viewState.email,
            onEmailChange = { email ->
                intentDispatcher(SignInIntent.EmailInputChange(email))
            },
            borderColor = Color.Black.copy(alpha = 0.4f)
        )

        PasswordField(
            password = viewState.password,
            onPasswordChange = { password ->
                intentDispatcher(SignInIntent.PasswordInputChange(password))
            },
            passwordVisible = viewState.isPasswordVisible,
            onPasswordVisibilityChange = { isVisible ->
                intentDispatcher(SignInIntent.TogglePasswordVisibility(isVisible))
            },
            borderColor = Color.Black.copy(alpha = 0.4f)
        )

        SignInButton(
            enabled = viewState.isValidEmail && viewState.isValidPassword && !viewState.isLoading,
            onSignInClick = {
                intentDispatcher(SignInIntent.SignInWithEmail(viewState.email, viewState.password))
            }
        )

        OrWithDivider(
            modifier = Modifier.padding(bottom = 32.dp)
        )

        GoogleSignInSection(
            onResult = { result ->
                intentDispatcher(SignInIntent.SignInWithGoogleResult(result))
            }
        )

        if (viewState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        viewState.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Composable
private fun SignInTitle() {
    Text(
        text = stringResource(Res.string.login_sign_in_title),
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(bottom = 32.dp)
    )
}

@Composable
private fun EmailField(
    email: String,
    onEmailChange: (String) -> Unit,
    borderColor: Color
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = borderColor,
        unfocusedBorderColor = borderColor
    )

    OutlinedTextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text(stringResource(Res.string.login_email)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = borderColor
            )
        }
    )
}

@Composable
private fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    borderColor: Color
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = borderColor,
        unfocusedBorderColor = borderColor
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(Res.string.login_password)) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if (passwordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = borderColor
            )
        },
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) Res.drawable.ic_visibility
                        else Res.drawable.ic_visibility_off
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    tint = borderColor
                )
            }
        }
    )
}

@Composable
private fun SignInButton(
    enabled: Boolean,
    onSignInClick: () -> Unit
) {
    Button(
        onClick = onSignInClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
        enabled = enabled
    ) {
        Text(stringResource(Res.string.login_sign_in))
    }
}

@Composable
private fun GoogleSignInSection(
    onResult: (Result<FirebaseUser?>) -> Unit
) {
    GoogleButtonUiContainerFirebase(
        linkAccount = false,
        onResult = onResult
    ) {
        GoogleSignInButton(
            modifier = Modifier.fillMaxWidth()
        ) {
            this.onClick()
        }
    }
}


@Composable
private fun OrWithDivider(
    modifier: Modifier = Modifier,
    dividerColor: Color = Color.LightGray,
    textColor: Color = Color.Gray
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            color = dividerColor
        )

        Text(
            text = stringResource(Res.string.login_or_with),
            color = textColor,
        )

        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            color = dividerColor
        )
    }
}