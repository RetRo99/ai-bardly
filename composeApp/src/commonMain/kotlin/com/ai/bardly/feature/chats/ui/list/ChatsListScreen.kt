package com.ai.bardly.feature.chats.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import com.ai.bardly.base.BaseScreen
import com.ai.bardly.base.IntentDispatcher
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
fun ChatsListScreen(
) {
    BaseScreen<ChatListViewModel, ChatListViewState, ChatListIntent> { viewState, intentDispatcher ->
        ChatsListContent(
            viewState = viewState,
            intentDispatcher = intentDispatcher,
        )
    }
}

@Composable
private fun ChatsListContent(
    viewState: ChatListViewState,
    intentDispatcher: IntentDispatcher<ChatListIntent>,
) {
    // TODO no login at chat details
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        if (viewState.userToken == null) {
            AuthUiHelperButtonsAndFirebaseAuth()
        } else {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewState.userToken, onValueChange = { /*TODO*/ }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { intentDispatcher(ChatListIntent.Logout) }) {
                    Text("Logout")
                }
                val clipboardManager = LocalClipboardManager.current

                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    clipboardManager.setText(
                        annotatedString = buildAnnotatedString {
                            append(text = viewState.userToken)
                        }
                    )
                }) {
                    Text("Copy token")
                }
            }
        }
    }
}

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit = {},
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {

        //Google Sign-In Button and authentication with Firebase
        GoogleButtonUiContainerFirebase(
            onResult = onFirebaseResult,
            linkAccount = false,
        ) {
            GoogleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
        }
    }
}