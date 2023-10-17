package com.example.stashbydas.Clases


import android.os.Parcel
import android.os.Parcelable

data class Producto(
    val nombre: String,
    var cantidad: Int,
    val precio: Long,
    val categoria: List<String>,
    val tiendas: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readLong(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(cantidad)
        parcel.writeLong(precio)
        parcel.writeStringList(categoria)
        parcel.writeStringList(tiendas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Producto> {
        override fun createFromParcel(parcel: Parcel): Producto {
            return Producto(parcel)
        }

        override fun newArray(size: Int): Array<Producto?> {
            return arrayOfNulls(size)
        }
    }
}