package com.ebi.serverapp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebi.serverapp.entity.Taxonomy;

@Transactional
@Repository
public class TaxonomyDAO implements ITaxonomyDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Taxonomy getTaxonomyById(int taxonomyId) {
		return entityManager.find(Taxonomy.class, taxonomyId);
	}

	// get List of paginated taxonomy-list
	@SuppressWarnings("unchecked")
	@Override
	public List<Taxonomy> getAllTaxonomies(int currentPage, int itemsPerPage) {
		String hql = "FROM Taxonomy as t ORDER BY t.taxonomyCommonName ASC";
		return (List<Taxonomy>) entityManager.createQuery(hql).setFirstResult((currentPage - 1) * itemsPerPage)
				.setMaxResults(itemsPerPage).getResultList();
	}

	@Override
	public void createTaxonomy(Taxonomy taxonomy) {
		entityManager.persist(taxonomy);
	}

	@Override
	public void updateTaxonomy(Taxonomy taxonomy) {
		Taxonomy artcl = getTaxonomyById(taxonomy.getTaxonomyId());
		artcl.setTaxonomyCommonName(taxonomy.getTaxonomyCommonName());
		artcl.setTaxonomyScientificName(taxonomy.getTaxonomyScientificName());
		entityManager.flush();
	}

	@Override
	public void deleteTaxonomy(int taxonomyId) {
		entityManager.remove(getTaxonomyById(taxonomyId));
	}

	@Override
	public boolean taxonomyExists(int taxonomyId) {
		String hql = "FROM Taxonomy as t WHERE t.taxonomyId = ?";
		int count = entityManager.createQuery(hql).setParameter(1, taxonomyId).getResultList().size();
		return count > 0 ? true : false;
	}

	@Override
	public long getTotalCount() {
		return ((Long) entityManager.createQuery("select count(*) from Taxonomy").getSingleResult());
	}
}
