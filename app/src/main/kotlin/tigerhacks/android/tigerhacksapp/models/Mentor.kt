package tigerhacks.android.tigerhacksapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import se.ansman.kotshi.JsonSerializable

/**
 * @author pauldg7@gmail.com (Paul Gillis)
 */
@Entity
@Parcelize
@JsonSerializable
data class Mentor(
    val sponsor: String = "",
    @PrimaryKey val name: String = "",
    val skills: String = "",
    val contact: String = ""
) : Parcelable