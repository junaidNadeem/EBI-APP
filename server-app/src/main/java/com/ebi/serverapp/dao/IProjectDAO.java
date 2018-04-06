package com.ebi.serverapp.dao;

import java.util.List;
import java.util.Map;

import com.ebi.serverapp.entity.Project;

public interface IProjectDAO {
	static String ALIAS = "project";

	// get total count and paginated result with/without filter
	List<Project> getAllProjects(String queryFilter, Map<String, Object> params, int currentPage, int itemsPerPage);

	// get all study types
	List<String> getAllStudyTypes();

	// get project with its taxonomy
	Project getProjectById(String projectId);

	void createProject(Project project);

	void updateProject(Project project);

	void deleteProject(String projectId);

	boolean projectExists(String projectId);

	long getTotalCount(String queryFilter, Map<String, Object> params);

}
