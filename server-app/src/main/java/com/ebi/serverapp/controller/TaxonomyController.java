package com.ebi.serverapp.controller;

import java.util.HashMap;
import java.util.List;
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

import com.ebi.serverapp.entity.Taxonomy;
import com.ebi.serverapp.service.ITaxonomyService;

@Controller
@RequestMapping("api")
@CrossOrigin(origins = { "http://localhost:4200" })
public class TaxonomyController {
	@Autowired
	private ITaxonomyService taxonomyService;

	@GetMapping("taxonomy")
	public ResponseEntity<Taxonomy> getTaxonomyById(@RequestParam("id") String id) {
		Taxonomy taxonomy = taxonomyService.getTaxonomyById(Integer.parseInt(id));
		return new ResponseEntity<Taxonomy>(taxonomy, HttpStatus.OK);
	}

	@GetMapping("all-taxonomies")
	public ResponseEntity<Map<String, Object>> getAllTaxonomies(@RequestParam("currentPage") String currentPage,
			@RequestParam("itemsPerPage") String itemsPerPage) {
		List<Taxonomy> list = taxonomyService.getAllTaxonomies(Integer.parseInt(currentPage),
				Integer.parseInt(itemsPerPage));
		Map<String, Object> jsonResponse = new HashMap<String, Object>();
		jsonResponse.put("totalCount", taxonomyService.getTotalCount());
		jsonResponse.put("taxonomy", list);
		return (new ResponseEntity<Map<String, Object>>(jsonResponse, HttpStatus.OK));
	}

	@PostMapping("taxonomy")
	public ResponseEntity<Void> createTaxonomy(@RequestBody Taxonomy taxonomy, UriComponentsBuilder builder) {
		boolean flag = taxonomyService.createTaxonomy(taxonomy);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/taxonomy?id={id}").buildAndExpand(taxonomy.getTaxonomyId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("taxonomy")
	public ResponseEntity<Taxonomy> updateTaxonomy(@RequestBody Taxonomy taxonomy) {
		taxonomyService.updateTaxonomy(taxonomy);
		return new ResponseEntity<Taxonomy>(taxonomy, HttpStatus.OK);
	}

	@DeleteMapping("taxonomy")
	public ResponseEntity<Void> deleteTaxonomy(@RequestParam("id") String id) {
		taxonomyService.deleteTaxonomy(Integer.parseInt(id));
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}