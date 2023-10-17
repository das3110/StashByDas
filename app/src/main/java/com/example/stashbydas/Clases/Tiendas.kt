package com.example.stashbydas.Clases

import android.os.Parcel
import android.os.Parcelable

data class Tiendas(
    var nombre: String?,
    var direccion: String?,
    var horario: String?,
    var tiposDePago: List<String> = listOf("Efectivo", "Tarjetas","Efectivo y/o Tarjetas"),
    var categoria: List<String>? = listOf("Farmacia", "Supermercado","Negocio","Feria")
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList() ?: listOf("Efectivo", "Tarjetas ", "Efectivo y/o Tarjetas"),
        parcel.createStringArrayList() ?: listOf("Farmacia", "Supermercado", "Negocio","Feria")
    )



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(direccion)
        parcel.writeString(horario)
        parcel.writeString(tiposDePago.toString())
        parcel.writeString(categoria.toString())

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