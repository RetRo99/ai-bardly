package com.retro99.data.remote

import com.retro99.base.result.AppResult
import com.retro99.base.result.runCatchingAsAppError
import com.retro99.data.remote.model.UserDto
import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
class FirebaseUserRemoteDataSource(
    private val firebaseAuth: FirebaseAuth,
) : UsersRemoteDataSource {

    override suspend fun getCurrentUser(): UserDto? {
        return firebaseAuth.currentUser?.let { user ->
            UserDto(
                id = user.uid,
                email = user.email,
                displayName = user.displayName,
                isEmailVerified = user.isEmailVerified,
            )
        }
    }

    override fun observeCurrentUser(): Flow<UserDto?> {
        return firebaseAuth.authStateChanged.map { user ->
            user?.let {
                UserDto(
                    id = user.uid,
                    email = user.email,
                    displayName = user.displayName,
                    isEmailVerified = user.isEmailVerified,
                )
            }
        }
    }

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): AppResult<UserDto?> {
        return runCatchingAsAppError {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password).user?.let { user ->
                    UserDto(
                        id = user.uid,
                        email = user.email,
                        displayName = user.displayName,
                        isEmailVerified = user.isEmailVerified,
                    )
                }
        }
    }

    override suspend fun fetchUserWithEmailAndPassword(
        email: String,
        password: String
    ): AppResult<UserDto?> {
        return runCatchingAsAppError {
            firebaseAuth
                .signInWithEmailAndPassword(email, password).user?.let { user ->
                    UserDto(
                        id = user.uid,
                        email = user.email,
                        displayName = user.displayName,
                        isEmailVerified = user.isEmailVerified,
                    )
                }
        }
    }

    override suspend fun getCurrentUserFirebaseToken(): String? {
        return firebaseAuth.currentUser?.getIdToken(false)
    }
}
