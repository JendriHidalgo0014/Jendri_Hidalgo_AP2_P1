package edu.ucne.jendri_hidalgo_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.jendri_hidalgo_ap2_p1.data.local.entities.BorrameEntity

@Database(
    entities = [
        BorrameEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){

}