package com.project.artistPortfolio.ArtistPortfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "link_types")
public class LinkTypes {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="link_type_id")
	 private int id;
	 
	 @Column(name="text")
	 private String text;
	 
	 @Column(name="table_name")
	 private String tableName;
	 
	 @Column(name="field")
	 private String field;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	 
	 

}
