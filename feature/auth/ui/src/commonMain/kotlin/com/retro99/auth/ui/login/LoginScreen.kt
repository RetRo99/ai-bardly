package com.retro99.auth.ui.login

import UserUiModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextButton
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
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import com.retro99.auth.ui.components.LoginInputField
import com.retro99.base.ui.BaseScreen
import com.retro99.base.ui.IntentDispatcher
import com.retro99.base.ui.resources.DrawableRes
import com.retro99.translations.StringRes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import resources.icons.ic_visibility
import resources.icons.ic_visibility_off
import resources.translations.login_already_have_account
import resources.translations.login_do_not_have_account
import resources.translations.login_email
import resources.translations.login_no_matching_user
import resources.translations.login_or_with
import resources.translations.login_password
import resources.translations.login_sign_in
import resources.translations.login_sign_in_title
import resources.translations.login_sign_in_with_google
import resources.translations.login_sign_up
import resources.translations.login_sign_up_title
import resources.translations.login_sign_up_with_google
import toUiModel

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
        LoginTitle(viewState.loginMode)
        Spacer(modifier = Modifier.height(16.dp))

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
            Column {
                NoMatchingUserRow()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        LoginButton(
            loginMode = viewState.loginMode,
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
        Spacer(modifier = Modifier.height(16.dp))

        OrWithDivider()

        Spacer(modifier = Modifier.height(16.dp))

        GoogleLoginSection(
            loginMode = viewState.loginMode,
            onResult = { result ->
                intentDispatcher(LoginIntent.LoginWithGoogleResult(result))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))


        if (viewState.loginMode == LoginMode.SignIn) {
            LoginFooterRow(
                loginMode = viewState.loginMode,
                onActionClick = { intentDispatcher(LoginIntent.OnFooterClicked) }
            )
        }
    }
}

@Composable
private fun LoginFooterRow(
    loginMode: LoginMode,
    onActionClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val text = when (loginMode) {
            LoginMode.SignIn -> stringResource(StringRes.login_do_not_have_account)
            LoginMode.SignUp -> stringResource(StringRes.login_already_have_account)
        }
        TextButton(onClick = onActionClick) {
            Text(text = text)
        }
    }
}

@Composable
private fun NoMatchingUserRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(StringRes.login_no_matching_user),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun LoginTitle(
    loginMode: LoginMode,
    modifier: Modifier = Modifier
) {
    val text = when (loginMode) {
        LoginMode.SignIn -> stringResource(StringRes.login_sign_in_title)
        LoginMode.SignUp -> stringResource(StringRes.login_sign_up_title)
    }
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium,
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
        labelResId = StringRes.login_email,
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
        labelResId = StringRes.login_password,
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done,
        leadingIcon = Icons.Default.Lock,
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) DrawableRes.ic_visibility
                        else DrawableRes.ic_visibility_off
                    ),
                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
private fun AuthTextField(
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
private fun LoginButton(
    enabled: Boolean,
    onSignInClick: () -> Unit,
    loginMode: LoginMode
) {
    val text = when (loginMode) {
        LoginMode.SignIn -> stringResource(StringRes.login_sign_in)
        LoginMode.SignUp -> stringResource(StringRes.login_sign_up)
    }
    Button(
        onClick = onSignInClick,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = enabled
    ) {
        Text(text)
    }
}

@Composable
private fun GoogleLoginSection(
    loginMode: LoginMode,
    onResult: (Result<UserUiModel?>) -> Unit
) {
    GoogleButtonUiContainerFirebase(
        linkAccount = false,
        onResult = { onResult(it.map { it?.toUiModel() }) }
    ) {
        val text = when (loginMode) {
            LoginMode.SignIn -> stringResource(StringRes.login_sign_in_with_google)
            LoginMode.SignUp -> stringResource(StringRes.login_sign_up_with_google)
        }
        GoogleSignInButton(
            modifier = Modifier.fillMaxWidth(),
            text = text
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
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            color = dividerColor
        )

        Text(
            text = stringResource(StringRes.login_or_with),
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