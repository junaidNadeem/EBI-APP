package com.ebi.serverapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ebi.serverapp.entity.Project;

@Transactional
@Repository
public class ProjectDAO implements IProjectDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Project getProjectById(String projectId) {
		return entityManager.find(Project.class, projectId);
	}

	// @SuppressWarnings("unchecked")
	// @Override
	// public List<Project> getProjectDetailsById(String projectId) {
	// String queryStr = "FROM Project p LEFT OUTER JOIN p.taxonomy t WHERE
	// p.projectId = ?";
	// return entityManager.createQuery(queryStr).setParameter(1,
	// projectId).getResultList();
	//
	// }

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getAllProjects(String studyType, int taxonomyId, int currentPage, int itemsPerPage) {
		String studyTypeFilter = "1=1", taxonomyIdFilter = "1=1", queryFilter = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (taxonomyId != -1) {
			taxonomyIdFilter = "project.taxonomyId = :taxonomyId";
			params.put("taxonomyId", taxonomyId);
		}
		if (!studyType.equals("-1")) {
			studyTypeFilter = "project.studyType = :studyType";
			params.put("studyType", studyType);
		}
		queryFilter = taxonomyIdFilter + " AND " + studyTypeFilter;
		final String jpql = "FROM Project project WHERE " + queryFilter + " ORDER BY project.title ASC";
		Query query = entityManager.createQuery(jpql);
		for (Map.Entry<String, Object> value : params.entrySet()) {
			query.setParameter(value.getKey(), value.getValue());
		}
		query.setFirstResult((currentPage - 1) * itemsPerPage).setMaxResults(itemsPerPage);
		return (List<Project>) query.getResultList();
	}

	@Override
	public void createProject(Project project) {
		entityManager.persist(project);
	}

	@Override
	public void updateProject(Project project) {
		Project artcl = (Project) getProjectById(project.getProjectId());
		artcl.setCenterName(project.getCenterName());
		artcl.setEvaCenterName(project.getEvaCenterName());
		artcl.setStudyType(project.getStudyType());
		artcl.setSourceType(project.getSourceType());
		artcl.setDescription(project.getDescription());
		artcl.setTitle(project.getTitle());
		artcl.setTaxonomyId(project.getTaxonomyId());
		entityManager.flush();
	}

	@Override
	public void deleteProject(String projectId) {
		entityManager.remove(getProjectById(projectId));
	}

	@Override
	public boolean projectExists(String projectId) {
		String queryStr = "FROM Project as p WHERE p.projectId = ?";
		int count = entityManager.createQuery(queryStr).setParameter(1, projectId).getResultList().size();
		return count > 0 ? true : false;
	}

	@Override
	public long getTotalCount(String studyType, int taxonomyId) {
		String studyTypeFilter = "1=1", taxonomyIdFilter = "1=1", queryFilter = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (taxonomyId != -1) {
			taxonomyIdFilter = "project.taxonomyId = :taxonomyId";
			params.put("taxonomyId", taxonomyId);
		}
		if (!studyType.equals("-1")) {
			studyTypeFilter = "project.studyType = :studyType";
			params.put("studyType", studyType);
		}
		queryFilter = taxonomyIdFilter + " AND " + studyTypeFilter;
		final String jpql = "SELECT count(*) FROM Project project WHERE " + queryFilter ;
		Query query = entityManager.createQuery(jpql);
		for (Map.Entry<String, Object> value : params.entrySet()) {
			query.setParameter(value.getKey(), value.getValue());
		}
		return ((Long) query.getSingleResult());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllStudyTypes() {
		String queryStr = "SELECT DISTINCT(p.studyType) from Project as p";
		return entityManager.createQuery(queryStr).getResultList();
	}
}
