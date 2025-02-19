package com.ai.bardly.user.data.remote

import com.ai.bardly.user.data.remote.model.UserDto
import dev.gitlive.firebase.auth.FirebaseAuth
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

    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String
    ): Result<UserDto?> {
        return runCatching {
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
    ): Result<UserDto?> {
        return runCatching {
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
}
