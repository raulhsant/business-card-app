package me.estudos.businesscard

import android.app.Application
import me.estudos.businesscard.data.AppDatabase
import me.estudos.businesscard.data.BusinessCardRepository

class App : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao()) }
}
