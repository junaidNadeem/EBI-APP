package com.ebi.serverapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebi.serverapp.dao.IProjectDAO;
import com.ebi.serverapp.dao.ITaxonomyDAO;
import com.ebi.serverapp.dao.ProjectDAO;
import com.ebi.serverapp.entity.Project;
import com.ebi.serverapp.entity.Taxonomy;

@Service
public class ProjectService implements IProjectService {
	@Autowired
	private IProjectDAO projectDAO;
	@Autowired
	private ITaxonomyDAO taxonomyDAO;

	// get project with its taxonomy
	@Override
	public Map<String, Object> getTaxonomyProjectById(String projectId) {
		Project project = projectDAO.getProjectById(projectId);
		Taxonomy taxonomy = taxonomyDAO.getTaxonomyById(project.getTaxonomyId());
		Map<String, Object> TaxonomyProjectMap = new HashMap<String, Object>();
		TaxonomyProjectMap.put("project", project);
		TaxonomyProjectMap.put("taxonomy", taxonomy);
		return TaxonomyProjectMap;
	}

	// get total count and paginated result with/without filter
	@Override
	public Map<String, Object> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage) {
		String studyTypeFilter = "1=1", taxonomyIdFilter = "1=1", queryFilter = "", tableAlias = ProjectDAO.getAlias();
		Map<String, Object> params = new HashMap<String, Object>();
		if (taxonomyId != -1) {
			taxonomyIdFilter = tableAlias + ".taxonomyId = :taxonomyId";
			params.put("taxonomyId", taxonomyId);
		}
		if (!studyType.equals("-1")) {
			studyTypeFilter = tableAlias + ".studyType = :studyType";
			params.put("studyType", studyType);
		}
		queryFilter = taxonomyIdFilter + " AND " + studyTypeFilter;

		List<Project> projectList = projectDAO.getAllProjects(queryFilter, params, currentPage, itemsPerPage);
		long totalCount = projectDAO.getTotalCount(queryFilter, params);
		Map<String, Object> projectMap = new HashMap<String, Object>();
		projectMap.put("totalCount", totalCount);
		projectMap.put("project", projectList);
		return projectMap;
	}

	// get all study type
	@Override
	public Map<String, Object> getAllStudyTypes() {
		List<String> studyTypesList = projectDAO.getAllStudyTypes();
		Map<String, Object> studyTypesMap = new HashMap<String, Object>();
		studyTypesMap.put("studyTypes", studyTypesList);
		return studyTypesMap;
	}

	// create project
	@Override
	public synchronized boolean createProject(Project project) {
		if (projectDAO.projectExists(project.getProjectId())) {
			return false;
		} else {
			projectDAO.createProject(project);
			return true;
		}
	}

	@Override
	public void updateProject(Project project) {
		projectDAO.updateProject(project);
	}

	@Override
	public void deleteProject(String projectId) {
		projectDAO.deleteProject(projectId);
	}

	@Override
	public long getTotalCount(String studyType, int taxonomyId) {
		String studyTypeFilter = "1=1", taxonomyIdFilter = "1=1", queryFilter = "", tableAlias = ProjectDAO.getAlias();
		Map<String, Object> params = new HashMap<String, Object>();
		if (taxonomyId != -1) {
			taxonomyIdFilter = tableAlias + ".taxonomyId = :taxonomyId";
			params.put("taxonomyId", taxonomyId);
		}
		if (!studyType.equals("-1")) {
			studyTypeFilter = tableAlias + ".studyType = :studyType";
			params.put("studyType", studyType);
		}
		queryFilter = taxonomyIdFilter + " AND " + studyTypeFilter;

		return projectDAO.getTotalCount(queryFilter, params);
	}

}
