package com.example.data

import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insert(project: ProjectEntity): Long = projectDao.insertProject(project)

    suspend fun delete(id: Long) = projectDao.deleteProject(id)

    suspend fun getById(id: Long): ProjectEntity? = projectDao.getProjectById(id)
}
