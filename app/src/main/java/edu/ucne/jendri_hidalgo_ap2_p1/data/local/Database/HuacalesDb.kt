package edu.ucne.jendri_hidalgo_ap2_p1.data.local.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Dao.HucalesDao
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.Entities.HuacalesEntity

@Database(
    entities = [
        HuacalesEntity::class,

    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class HuacalesDb : RoomDatabase() {
    abstract fun HucalesDao(): HucalesDao

}
