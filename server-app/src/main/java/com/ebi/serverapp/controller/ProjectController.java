package com.ebi.serverapp.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.ebi.serverapp.entity.Project;
import com.ebi.serverapp.entity.Taxonomy;
import com.ebi.serverapp.service.IProjectService;
import com.ebi.serverapp.service.ITaxonomyService;

@Controller
@RequestMapping("api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProjectController {
	@Autowired
	private IProjectService projectService;
	@Autowired
	private ITaxonomyService taxonomyService;

	@GetMapping("project")
	public ResponseEntity<Map<String, Object>> getProjectById(@RequestParam("id") String id) {
		Project project = projectService.getProjectById(id);
		int i = project.getTaxonomyId();
		Taxonomy taxonomy = taxonomyService.getTaxonomyById(i);
		// List<Project> list = new ArrayList<Project>();
		// list.add(project);
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("project", project);
		jsonResponse.put("taxonomy", taxonomy);
		return new ResponseEntity<Map<String, Object>>(jsonResponse, HttpStatus.OK);
	}

	@GetMapping("all-projects")
	public ResponseEntity<Map<String, Object>> getAllProjects(@RequestParam("taxonomyId") String taxonomyId,
			@RequestParam("studyType") String studyType, @RequestParam("currentPage") String currentPage,
			@RequestParam("itemsPerPage") String itemsPerPage) {
		List<Project> list = projectService.getAllProjects(studyType, Integer.parseInt(taxonomyId),
				Integer.parseInt(currentPage), Integer.parseInt(itemsPerPage));
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("totalCount", projectService.getTotalCount(studyType, Integer.parseInt(taxonomyId)));
		jsonResponse.put("project", list);
		return (new ResponseEntity<Map<String, Object>>(jsonResponse, HttpStatus.OK));
	}

	@GetMapping("all-study-types")
	public ResponseEntity<Map<String, Object>> getAllStudyTypes() {
		List<String> list = projectService.getAllStudyTypes();
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("studyTypes", list);
		return (new ResponseEntity<Map<String, Object>>(jsonResponse, HttpStatus.OK));
	}

	@PostMapping("project")
	public ResponseEntity<Void> createProject(@RequestBody Project project, UriComponentsBuilder builder) {
		boolean flag = projectService.createProject(project);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/project?id={id}").buildAndExpand(project.getProjectId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("project")
	public ResponseEntity<Project> updateProject(@RequestBody Project project) {
		projectService.updateProject(project);
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}

	@DeleteMapping("project")
	public ResponseEntity<Void> deleteProject(@RequestParam("id") String id) {
		projectService.deleteProject(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}