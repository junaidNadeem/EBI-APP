package com.ebi.serverapp.dao;

import java.util.List;

import com.ebi.serverapp.entity.Project;

public interface IProjectDAO {
	List<Project> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage);

	Project getProjectById(String projectId);

	void createProject(Project project);

	void updateProject(Project project);

	void deleteProject(String projectId);

	boolean projectExists(String projectId);

	long getTotalCount(String studyType, int taxonomyId);

	List<String> getAllStudyTypes();
}
