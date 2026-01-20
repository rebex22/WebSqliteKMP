package cz.mmaso.apptest10.sqldelight.hockey.utils

class DbRepoImpl : IDbRepo {
    private val dbHelper = DbHelper()

    override suspend fun selectAllPlayers(): List<SelectAll> {
        var players: List<SelectAll> = emptyList()
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