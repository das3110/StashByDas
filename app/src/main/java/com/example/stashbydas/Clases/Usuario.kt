package com.example.stashbydas.Clases
import android.os.Parcel
import android.os.Parcelable
data class Usuario(
    val nombre: String?,
    val email: String?,
    val telefono: String?,
    var presupuesto: Long
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong()

    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeLong(presupuesto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}
