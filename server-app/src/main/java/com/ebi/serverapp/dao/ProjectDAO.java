package com.ebi.serverapp.dao;

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

	// get total count and paginated result with/without filter
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getAllProjects(String queryFilter, Map<String, Object> params, int currentPage,
			int itemsPerPage) {
		String tableAlias = ProjectDAO.getAlias();
		final String jpql = "FROM Project " + tableAlias + " WHERE " + queryFilter + " ORDER BY " + tableAlias
				+ ".title ASC";
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
	public long getTotalCount(String queryFilter, Map<String, Object> params) {
		String tableAlias = ProjectDAO.getAlias();
		final String jpql = "SELECT count(*) FROM Project " + tableAlias + " WHERE " + queryFilter;
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

	public static String getAlias() {
		return ProjectDAO.ALIAS;
	}
}
