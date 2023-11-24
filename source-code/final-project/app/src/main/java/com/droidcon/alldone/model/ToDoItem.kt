package com.droidcon.alldone.model

import android.os.Parcel
import android.os.Parcelable

data class ToDoItem(
    val id: Int,
    val title: String,
    val description: String,
    val category: ToDoCategory = ToDoCategory.PERSONAL,
    val complete: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        id = parcel.readInt(),
        title = parcel.readString() ?: "",
        description = parcel.readString() ?: "",
        category = ToDoCategory.fromString(parcel.readString() ?: ""),
        complete = parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(category.name)
        parcel.writeByte(if (complete) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ToDoItem> {
        override fun createFromParcel(parcel: Parcel): ToDoItem {
            return ToDoItem(parcel)
        }

        override fun newArray(size: Int): Array<ToDoItem?> {
            return arrayOfNulls(size)
        }
    }
}