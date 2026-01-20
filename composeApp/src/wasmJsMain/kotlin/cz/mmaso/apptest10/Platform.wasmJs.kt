package cz.mmaso.apptest10

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.async.coroutines.awaitAsList
import cz.mmaso.apptest10.sqldelight.hockey.utils.DbHelper
import cz.mmaso.apptest10.sqldelight.hockey.utils.DbRepoImpl
import cz.mmaso.apptest10.sqldelight.hockey.utils.IDbRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun getRepo() : IDbRepo {
    return DbRepoImpl()
}

actual typealias DbDate = Date

actual class DateAdapter : ColumnAdapter<DbDate, Long> {
    override fun encode(value: DbDate) = value.getTime().toLong()
    override fun decode(databaseValue: Long) = DbDate(databaseValue.toDouble())
}

actual val IoDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun testDb() : Boolean {
    val scope    = MainScope()
    val dbHelper = DbHelper()

    scope.launch {
        dbHelper.withDatabase { database ->

            println("All:")
            val pl = database.playerQueries.selectAll().awaitAsList()
            pl.forEach { player ->
                println( "- ${player.name}, ${player.last_name}, ${player.birth_date.toDateString()}" )
            }

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
