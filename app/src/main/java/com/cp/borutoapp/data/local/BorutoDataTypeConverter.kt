package com.cp.borutoapp.data.local

import androidx.room.TypeConverter
import com.cp.borutoapp.util.Constant.SEPARATOR_DATABASE_TYPE_CONVERTER

class BorutoDataTypeConverter {

    @TypeConverter
    fun fromListToString(list: List<String>) = list.joinToString(SEPARATOR_DATABASE_TYPE_CONVERTER)

    @TypeConverter
    fun fromStringToList(str: String) = str.split(SEPARATOR_DATABASE_TYPE_CONVERTER)
}
