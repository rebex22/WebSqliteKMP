package cz.mmaso.apptest10.sqldelight.hockey.utils

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import cz.mmaso.apptest10.IoDispatcher
import kotlinx.coroutines.flow.Flow

class DbRepoImpl : IDbRepo {
    private val dbHelper = DbHelper()

    override suspend fun getAllTasks(): Flow<List<SelectAll>> {

        return dbHelper.getDb( true ).playerQueries.selectAll()
            .asFlow()
            .mapToList(IoDispatcher )
    }

    override suspend fun selectAllPlayers(): List<SelectAll> {
        var players: List<SelectAll> = emptyList()

        dbHelper.getDb( true ).playerQueries.selectAll().asFlow().mapToList(IoDispatcher )


        dbHelper.withDatabase { database ->
            players = database.playerQueries.selectAll().executeAsList()
        }
        return players
    }

    override suspend fun selectAllTeams(): List<Team> {
        var teams: List<Team> = emptyList()
        dbHelper.withDatabase { database ->
            teams = database.teamQueries.selectAll().executeAsList()
        }
        return teams
    }

    override suspend fun selectPlayersByTeam(teamId: Long): List<ForTeam> {
        var players: List<ForTeam> = emptyList()
        dbHelper.withDatabase { database ->
            database.playerQueries.forTeam( teamId ).executeAsList()
            players = database.playerQueries.forTeam(teamId).executeAsList()
        }
        return players
    }
}