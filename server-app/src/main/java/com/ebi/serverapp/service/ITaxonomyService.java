package com.ebi.serverapp.service;

import java.util.Map;

import com.ebi.serverapp.entity.Taxonomy;;

public interface ITaxonomyService {
	// get map of paginated taxonomy-list and total count
	Map<String, Object> getAllTaxonomies(int currentPage, int itemsPerPage);

	Taxonomy getTaxonomyById(int taxonomyId);

	boolean createTaxonomy(Taxonomy taxonomy);

	void updateTaxonomy(Taxonomy taxonomy);

	void deleteTaxonomy(int taxonomyId);

	long getTotalCount();
}
