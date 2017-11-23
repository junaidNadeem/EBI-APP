package com.ebi.serverapp.service;

import java.util.List;

import com.ebi.serverapp.entity.Project;;

public interface IProjectService {
	List<Project> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage);

	Project getProjectById(String projectId);

	boolean createProject(Project project);

	void updateProject(Project project);

	void deleteProject(String projectId);

	long getTotalCount(String studyType, int taxonomyId);

	List<String> getAllStudyTypes();
}
