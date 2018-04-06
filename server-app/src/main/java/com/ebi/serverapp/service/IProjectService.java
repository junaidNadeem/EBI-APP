package com.ebi.serverapp.service;

import java.util.Map;

import com.ebi.serverapp.entity.Project;;

public interface IProjectService {
	// get paginated result with/without filter and total count
	Map<String, Object> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage);

	// get all study types
	Map<String, Object> getAllStudyTypes();

	// get project with its taxonomy
	Map<String, Object> getTaxonomyProjectById(String projectId);

	boolean createProject(Project project);

	void updateProject(Project project);

	void deleteProject(String projectId);

	long getTotalCount(String studyType, int taxonomyId);

}
