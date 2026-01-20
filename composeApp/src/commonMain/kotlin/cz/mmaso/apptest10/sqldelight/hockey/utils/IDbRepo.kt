package cz.mmaso.apptest10.sqldelight.hockey.utils

import kotlinx.coroutines.flow.Flow

interface IDbRepo {
    suspend fun selectAllPlayers(): List<SelectAll>
    suspend fun selectAllTeams(): List<Team>
    suspend fun selectPlayersByTeam(teamId: Long): List<ForTeam>

    suspend fun getAllTasks(): Flow<List<SelectAll>>
}

