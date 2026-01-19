package cz.mmaso.apptest10

import app.cash.sqldelight.ColumnAdapter

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun testDb() : Boolean {

    val d = java.util.Date(0)

    return false
}

actual typealias Date = java.util.Date

actual class DateAdapter : ColumnAdapter<Date, Long> {
    override fun encode(value: Date) = value.time
    override fun decode(databaseValue: Long) = Date(databaseValue)
}
