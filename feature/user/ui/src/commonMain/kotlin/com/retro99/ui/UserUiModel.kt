import com.retro99.auth.domain.model.UserDomainModel
import dev.gitlive.firebase.auth.FirebaseUser

data class UserUiModel(
    val id: String,
    val email: String?,
    val displayName: String?,
    val isEmailVerified: Boolean,
)

fun UserDomainModel.toUiModel() = UserUiModel(
    id = id,
    email = email,
    displayName = displayName,
    isEmailVerified = isEmailVerified
)

fun FirebaseUser.toUiModel() = UserUiModel(
    id = uid,
    email = email,
    displayName = displayName,
    isEmailVerified = isEmailVerified
)