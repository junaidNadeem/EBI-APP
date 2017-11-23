package com.ebi.serverapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebi.serverapp.dao.ITaxonomyDAO;
import com.ebi.serverapp.entity.Taxonomy;

@Service
public class TaxonomyService implements ITaxonomyService {
	@Autowired
	private ITaxonomyDAO taxonomyDAO;

	@Override
	public Taxonomy getTaxonomyById(int taxonomyId) {
		Taxonomy obj = taxonomyDAO.getTaxonomyById(taxonomyId);
		return obj;
	}

	@Override
	public List<Taxonomy> getAllTaxonomies(int currentPage, int itemsPerPage) {
		return taxonomyDAO.getAllTaxonomies(currentPage, itemsPerPage);
	}

	@Override
	public synchronized boolean createTaxonomy(Taxonomy taxonomy) {
		if (taxonomyDAO.taxonomyExists(taxonomy.getTaxonomyId())) {
			return false;
		} else {
			taxonomyDAO.createTaxonomy(taxonomy);
			return true;
		}
	}

	@Override
	public void updateTaxonomy(Taxonomy taxonomy) {
		taxonomyDAO.updateTaxonomy(taxonomy);
	}

	@Override
	public void deleteTaxonomy(int taxonomyId) {
		taxonomyDAO.deleteTaxonomy(taxonomyId);
	}

	@Override
	public long getTotalCount() {
		return taxonomyDAO.getTotalCount();
	}
}
