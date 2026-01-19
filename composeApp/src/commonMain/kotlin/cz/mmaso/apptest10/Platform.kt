package cz.mmaso.apptest10

import app.cash.sqldelight.ColumnAdapter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


expect fun testDb() : Boolean

expect class DbDate(year: Int, month: Int, day: Int)

expect class DateAdapter() : ColumnAdapter<DbDate, Long>
