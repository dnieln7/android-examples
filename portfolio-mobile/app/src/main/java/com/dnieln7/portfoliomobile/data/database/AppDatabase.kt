package com.dnieln7.portfoliomobile.data.database

import android.content.Context
import androidx.room.*
import com.dnieln7.portfoliomobile.data.database.converter.AppConverters
import com.dnieln7.portfoliomobile.data.database.dao.ProjectDao
import com.dnieln7.portfoliomobile.domain.model.Project

@Database(
    entities = [Project::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
@TypeConverters(AppConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}