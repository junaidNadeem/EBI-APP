package com.ebi.serverapp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "project_id")
	private String projectId;
	@Column(name = "title")
	private String title;
	@Column(name = "description")
	private String description;
	@Column(name = "source_type")
	private String sourceType;
	@Column(name = "study_type")
	private String studyType;
	@Column(name = "eva_center_name")
	private String evaCenterName;
	@Column(name = "center_name")
	private String centerName;
	@Column(name = "taxonomy_id")
	private int taxonomyId;
	// @Column(name = "taxonomy_id", insertable=false, updatable=false)
	// private int taxonomyId;
	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "taxonomy_id")
	// private Taxonomy taxonomy;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getStudyType() {
		return studyType;
	}

	public void setStudyType(String studyType) {
		this.studyType = studyType;
	}

	public String getEvaCenterName() {
		return evaCenterName;
	}

	public void setEvaCenterName(String evaCenterName) {
		this.evaCenterName = evaCenterName;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public int getTaxonomyId() {
		return taxonomyId;
	}

	public void setTaxonomyId(int taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

}