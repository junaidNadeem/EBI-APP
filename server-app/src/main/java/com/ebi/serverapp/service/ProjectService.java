package com.ebi.serverapp.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebi.serverapp.dao.IProjectDAO;
import com.ebi.serverapp.entity.Project;

@Service
public class ProjectService implements IProjectService {
	@Autowired
	private IProjectDAO projectDAO;

	@Override
	public Project getProjectById(String projectId) {
		Project obj = projectDAO.getProjectById(projectId);
		return obj;
	}

	@Override
	public List<Project> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage) {

		return projectDAO.getAllProjects(studyType, taxonomyId, currentPage, itemsPerPage);
	}

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
		return projectDAO.getTotalCount(studyType, taxonomyId);
	}

	@Override
	public List<String> getAllStudyTypes() {
		return projectDAO.getAllStudyTypes();
	}

}
