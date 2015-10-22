package com.dubby.security.model.entity;


import com.dubby.base.model.entity.HasSerializableEntity;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "SEC_RIGHTS")
public class Rights implements HasSerializableEntity {

    private static final long serialVersionUID = 6320340806112641294L;

    @Id
	@Column(name = "ID", length = 20)
	@Access(AccessType.PROPERTY)
	private String id;

	@Column(name = "DESCRIPTION", length = 200)
	private String description;

	@Column(name = "IS_FORM")
	private Boolean isForm;

    @ForeignKey(name = "none")
    @JoinColumn(name="PARENT")
	@ManyToOne(fetch=FetchType.LAZY)
	private Rights parent;
	
	@Transient
	private List<Rights> listOfChild = new ArrayList<Rights>();

    @Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Rights getParent() {
		return parent;
	}

	public void setParent(Rights parent) {
		this.parent = parent;
	}

	public Boolean getIsForm() {
		return isForm;
	}

	public void setIsForm(Boolean isForm) {
		this.isForm = isForm;
	}

	public List<Rights> getListOfChild() {
		return listOfChild;
	}

	public void setListOfChild(List<Rights> listOfChild) {
		this.listOfChild = listOfChild;
	}
}
