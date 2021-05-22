package com.dicoding.githubuserapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritePengguna::class],
    version = 1
)
abstract class PenggunaDatabase: RoomDatabase() {
    companion object{
        var INSTANSI : PenggunaDatabase? = null

        fun getDatabase(context: Context): PenggunaDatabase?{
            if (INSTANSI == null){
                synchronized(PenggunaDatabase::class){
                    INSTANSI = Room.databaseBuilder(context.applicationContext, PenggunaDatabase::class.java, "pengguna_database").build()

                }
            }
            return INSTANSI
        }
    }

    abstract fun favoritePenggunaDao(): FavoritePenggunaDao
}