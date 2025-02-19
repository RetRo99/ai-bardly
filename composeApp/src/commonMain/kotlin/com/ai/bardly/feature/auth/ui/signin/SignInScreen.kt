package com.ai.bardly.feature.auth.ui.signin

import ai_bardly.composeapp.generated.resources.Res
import ai_bardly.composeapp.generated.resources.ic_visibility
import ai_bardly.composeapp.generated.resources.ic_visibility_off
import ai_bardly.composeapp.generated.resources.login_email
import ai_bardly.composeapp.generated.resources.login_no_matching_user
import ai_bardly.composeapp.generated.resources.login_or_with
import ai_bardly.composeapp.generated.resources.login_password
import ai_bardly.composeapp.generated.resources.login_sign_in
import ai_bardly.composeapp.generated.resources.login_sign_in_title
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.ai.bardly.feature.auth.ui.components.LoginInputField
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.auth.FirebaseUser
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    component: LoginPresenter,
) {
    BaseScreen(component) { viewState, intentDispatcher ->
        LoginScreenContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun LoginScreenContent(
    viewState: LoginViewState,
    intentDispatcher: IntentDispatcher<LoginIntent>,
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
            emailInputField = viewState.emailField,
            onEmailChange = { email ->
                intentDispatcher(LoginIntent.EmailInputChange(email))
            },
        )

        PasswordField(
            passwordInputField = viewState.passwordField,
            onPasswordChange = { password ->
                intentDispatcher(LoginIntent.PasswordInputChange(password))
            },
            passwordVisible = viewState.passwordField.isVisible,
            onPasswordVisibilityChange = { isVisible ->
                intentDispatcher(LoginIntent.TogglePasswordVisibility(isVisible))
            },
        )

        AnimatedVisibility(viewState.showNoMatchingUserError) {
            NoMatchingUserRow()
        }
        SignInButton(
            enabled = viewState.emailField.value.isNotBlank() && viewState.passwordField.value.isNotBlank(),
            onSignInClick = {
                intentDispatcher(
                    LoginIntent.LoginWithEmail(
                        viewState.emailField,
                        viewState.passwordField
                    )
                )
            }
        )

        OrWithDivider(
            modifier = Modifier.padding(bottom = 32.dp)
        )

        GoogleSignInSection(
            onResult = { result ->
                intentDispatcher(LoginIntent.LoginWithGoogleResult(result))
            }
        )
    }
}

@Composable
private fun NoMatchingUserRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),  // Add some space below the row
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.login_no_matching_user),
            color = MaterialTheme.colorScheme.error
        )
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
    emailInputField: LoginInputField.Email,
    onEmailChange: (String) -> Unit,
) {
    AuthTextField(
        inputField = emailInputField,
        onValueChange = onEmailChange,
        labelResId = Res.string.login_email,
        keyboardType = KeyboardType.Email,
        imeAction = ImeAction.Next,
        leadingIcon = Icons.Default.Email
    )
}

@Composable
private fun PasswordField(
    passwordInputField: LoginInputField.Password,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
) {
    AuthTextField(
        inputField = passwordInputField,
        onValueChange = onPasswordChange,
        labelResId = Res.string.login_password,
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        leadingIcon = Icons.Default.Lock,
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) Res.drawable.ic_visibility
                        else Res.drawable.ic_visibility_off
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun AuthTextField(
    inputField: LoginInputField,
    onValueChange: (String) -> Unit,
    labelResId: StringResource,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    leadingIcon: ImageVector,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedBorderColor = Color.Black.copy(alpha = 0.4f),
        errorBorderColor = Color.Red
    )

    val errorMessage =
        if (inputField.showError) stringResource(inputField.state.errorResource) else ""

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = inputField.value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelResId)) },
        isError = inputField.showError,
        singleLine = true,
        colors = textFieldColors,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
            )
        },
        trailingIcon = trailingIcon,
        supportingText = {
            if (inputField.showError) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
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