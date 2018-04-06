package com.ebi.serverapp.controller;

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
import com.ebi.serverapp.service.IProjectService;

@Controller
@RequestMapping("api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class ProjectController {
	@Autowired
	private IProjectService projectService;

	// get project with its taxonomy
	@GetMapping("project")
	public ResponseEntity<Map<String, Object>> getTaxonomyProjectById(@RequestParam("id") String id) {
		Map<String, Object> TaxonomyProjectMap = projectService.getTaxonomyProjectById(id);
		return new ResponseEntity<Map<String, Object>>(TaxonomyProjectMap, HttpStatus.OK);
	}

	// get paginated result with/without filter and total count
	@GetMapping("all-projects")
	public ResponseEntity<Map<String, Object>> getAllProjects(@RequestParam("taxonomyId") String taxonomyId,
			@RequestParam("studyType") String studyType, @RequestParam("currentPage") String currentPage,
			@RequestParam("itemsPerPage") String itemsPerPage) {
		Map<String, Object> projectMap = projectService.getAllProjects(studyType, Integer.parseInt(taxonomyId),
				Integer.parseInt(currentPage), Integer.parseInt(itemsPerPage));
		return (new ResponseEntity<Map<String, Object>>(projectMap, HttpStatus.OK));
	}

	// get all study type
	@GetMapping("all-study-types")
	public ResponseEntity<Map<String, Object>> getAllStudyTypes() {
		Map<String, Object> studyTypesMap = projectService.getAllStudyTypes();
		return (new ResponseEntity<Map<String, Object>>(studyTypesMap, HttpStatus.OK));
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