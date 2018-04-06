package com.ebi.serverapp.dao;

import java.util.List;
import com.ebi.serverapp.entity.Taxonomy;;

public interface ITaxonomyDAO {
	// get List of paginated taxonomy-list
	List<Taxonomy> getAllTaxonomies(int currentPage, int itemsPerPage);

	Taxonomy getTaxonomyById(int taxonomyId);

	void createTaxonomy(Taxonomy taxonomy);

	void updateTaxonomy(Taxonomy taxonomy);

	void deleteTaxonomy(int taxonomyId);

	boolean taxonomyExists(int taxonomyId);

	long getTotalCount();
}
