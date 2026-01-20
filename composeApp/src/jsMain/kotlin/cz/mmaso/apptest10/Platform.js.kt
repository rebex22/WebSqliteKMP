package cz.mmaso.apptest10

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import cz.mmaso.apptest10.sqldelight.hockey.utils.DbHelper
import cz.mmaso.apptest10.sqldelight.hockey.utils.DbRepoImpl
import cz.mmaso.apptest10.sqldelight.hockey.utils.IDbRepo
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.invoke
import kotlin.js.Date

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun getRepo() : IDbRepo {
    return DbRepoImpl()
}

actual typealias DbDate = kotlin.js.Date

actual class DateAdapter : ColumnAdapter<DbDate, Long> {
    override fun encode(value: DbDate) = value.getTime().toLong()
    override fun decode(databaseValue: Long) = DbDate(databaseValue)
}

actual fun testDb() : Boolean {
    val scope    = MainScope()
    val dbHelper = DbHelper()

    scope.launch {
        dbHelper.withDatabase { database ->
            val players = database.playerQueries.forTeam(-1).awaitAsList()
            println("Players:")
            players.forEach { player ->
                println( player )
            }
            println("Teams:")
            val teams = database.teamQueries.selectAll().awaitAsList()
            teams.forEach { team ->
                println( team )
            }
        }
    }

    return true
}
