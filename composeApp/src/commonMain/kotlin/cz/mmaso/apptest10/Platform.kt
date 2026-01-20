package cz.mmaso.apptest10

import app.cash.sqldelight.ColumnAdapter
import cz.mmaso.apptest10.sqldelight.hockey.utils.IDbRepo
import kotlinx.coroutines.CoroutineDispatcher

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun testDb() : Boolean

expect class DbDate(year: Int, month: Int, day: Int)

expect class DateAdapter() : ColumnAdapter<DbDate, Long>

expect fun getRepo() : IDbRepo

expect val IoDispatcher: CoroutineDispatcher