package cz.mmaso.apptest10.sqldelight.hockey.utils

interface IDbRepo {
    suspend fun selectAllPlayers(): List<SelectAll>
    suspend fun selectAllTeams(): List<Team>
    suspend fun selectPlayersByTeam(teamId: Long): List<ForTeam>
}

