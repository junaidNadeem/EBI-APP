package com.ebi.serverapp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "taxonomies")
public class Taxonomy implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "taxonomy_id")
	private int taxonomyId;
	@Column(name = "taxonomy_common_name")
	private String taxonomyCommonName;
	@Column(name = "taxonomy_scientific_name")
	private String taxonomyScientificName;

	public int getTaxonomyId() {
		return taxonomyId;
	}

	public void setTaxonomyId(int taxonomyId) {
		this.taxonomyId = taxonomyId;
	}

	public String getTaxonomyCommonName() {
		return taxonomyCommonName;
	}

	public void setTaxonomyCommonName(String taxonomyCommonName) {
		this.taxonomyCommonName = taxonomyCommonName;
	}

	public String getTaxonomyScientificName() {
		return taxonomyScientificName;
	}

	public void setTaxonomyScientificName(String taxonomyScientificName) {
		this.taxonomyScientificName = taxonomyScientificName;
	}

}