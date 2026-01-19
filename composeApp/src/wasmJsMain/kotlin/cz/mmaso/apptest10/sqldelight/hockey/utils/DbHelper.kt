package cz.mmaso.apptest10.sqldelight.hockey.utils

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import app.cash.sqldelight.driver.worker.createDefaultWebWorkerDriver
import cz.mmaso.apptest10.Date
import cz.mmaso.apptest10.DateAdapter
import cz.mmaso.apptest10.sqldelight.hockey.HockeyDb
import cz.mmaso.apptest10.sqldelight.hockey.utils.PlayerVals.Position
import cz.mmaso.apptest10.sqldelight.hockey.utils.PlayerVals.Shoots
import cz.mmaso.apptest10.sqldelight.hockey.utils.Player
import cz.mmaso.apptest10.sqldelight.hockey.utils.Team
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.khronos.webgl.Uint8Array
import org.w3c.dom.Worker

@OptIn(ExperimentalWasmJsInterop::class)
class DbHelper {
    private lateinit var driver: SqlDriver
    private var db: HockeyDb? = null

    private val mutex = Mutex()
    private val scope = CoroutineScope(Dispatchers.Default)

    init {
        driver = createDefaultWebWorkerDriver()
    }

    suspend fun withDatabase(block: suspend (HockeyDb) -> Unit): Unit = mutex.withLock {
        if (db == null) {
            db = createDb(driver)
            seedData(db!!)
        }
        block(db!!)
    }

    internal fun dbClear() {
        driver.close()
    }

    private suspend fun createDb(driver: SqlDriver): HockeyDb {
        HockeyDb.Schema.awaitCreate(driver)

        return HockeyDb(
            driver = driver,
            playerAdapter = Player.Adapter(
                shootsAdapter = EnumColumnAdapter(),
                positionAdapter = EnumColumnAdapter(),
                birth_dateAdapter = DateAdapter(),
                numberAdapter = IntColumnAdapter,
                ageAdapter = IntColumnAdapter,
                weightAdapter = FloatColumnAdapter,
            ),
        )
    }

    private suspend fun seedData(db: HockeyDb) = db.apply {
        // Seed data time!
        val ducks = "Anaheim Ducks"
        val pens = "Pittsburgh Penguins"
        val sharks = "San Jose Sharks"
        val sens = "Ottawa Senators"

        // Populate teams.
        teamQueries.insertTeam(ducks,  "Randy Carlyle", true)
        teamQueries.insertTeam(pens,  "Mike Sullivan", true)
        teamQueries.insertTeam(sharks, "Peter DeBoer", false)
        teamQueries.insertTeam(sens,  "D. J. Smith", false)

        playerQueries.insertPlayer(
            "Corey", "Perry", 10, ducks, 30, Date(1985, 5, 16), 210F,
            Shoots.RIGHT, Position.RIGHT_WING,
        )
        playerQueries.insertPlayer(
            "Ryan", "Getzlaf", 15, ducks, 30, Date(1985, 5, 10),221F,
            Shoots.RIGHT, Position.CENTER,
        )
        teamQueries.setCaptain(15, ducks)

        playerQueries.insertPlayer(
            "Sidney", "Crosby", 87, pens, 28, Date(1987, 8, 7), 200F,
            Shoots.LEFT, Position.CENTER,
        )
        teamQueries.setCaptain(87, pens)

        playerQueries.insertPlayer(
            "Erik", "Karlsson", 65, sharks, 28, Date(1990, 5, 31), 190F,
            Shoots.RIGHT, Position.DEFENSE,
        )

        playerQueries.insertPlayer(
            "Joe", "Pavelski", 8, sharks, 31, Date(1984, 7, 18), 194F,
            Shoots.RIGHT, Position.CENTER,
        )
        teamQueries.setCaptain(8, sharks)

        playerQueries.insertPlayer(
            "Brady", "Tkachuk", 7, sens, 24, Date(1999, 9, 16), 221F,
            Shoots.LEFT, Position.LEFT_WING,
        )
        teamQueries.setCaptain(7, sens)
    }
}


