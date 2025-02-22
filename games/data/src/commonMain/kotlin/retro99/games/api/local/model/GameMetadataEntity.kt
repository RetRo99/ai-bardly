package retro99.games.api.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    foreignKeys = [ForeignKey(
        entity = GameEntity::class,
        parentColumns = ["id"],
        childColumns = ["gameId"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class GameMetadataEntity(
    @PrimaryKey val gameId: Int,
    val lastOpenTime: LocalDateTime
)