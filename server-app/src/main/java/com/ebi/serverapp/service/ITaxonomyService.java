package com.ebi.serverapp.service;

import java.util.List;

import com.ebi.serverapp.entity.Taxonomy;;

public interface ITaxonomyService {
	List<Taxonomy> getAllTaxonomies(int currentPage, int itemsPerPage);

	Taxonomy getTaxonomyById(int taxonomyId);

	boolean createTaxonomy(Taxonomy taxonomy);

	void updateTaxonomy(Taxonomy taxonomy);

	void deleteTaxonomy(int taxonomyId);

	long getTotalCount();
}
