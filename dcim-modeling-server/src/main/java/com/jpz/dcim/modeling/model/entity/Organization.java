package com.jpz.dcim.modeling.model.entity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;


@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "principal_user_id")
	private User principal;

	private String name;

	private String description;

	private int position;

	@ForeignKey(name = "null")
	@ManyToOne()
	@JoinColumn(name = "parent_id", nullable = true)
	private Organization parent = null;

	@OneToMany(mappedBy = "organization")
	private List<User> members = new ArrayList<User>();

	@OneToMany(mappedBy = "parent")
	@OrderBy(value="position ASC")
	private List<Organization> children = new ArrayList<Organization>();
	
	/**
	 * 逻辑删除标记
	 */
	@Column(name="isDeleted", nullable=false )
	private Boolean deleted=false;
	
	@Transient
	private String shouldBeforeAt ;
	

	public String getShouldBeforeAt() {
		return shouldBeforeAt;
	}

	public void setShouldBeforeAt(String shouldBeforeAt) {
		this.shouldBeforeAt = shouldBeforeAt;
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Organization() {
	}

	public Organization(String id) {
		super(id);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public void addMembers(User user) {
		this.getMembers();
		this.members.add(user);
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Organization> getChildren() {
		return children;
	}
	
	public void addChildren(Organization org){
		this.getChildren();
		this.children.add(org);
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public Organization getParent() {
		return this.parent;
	}

	public User getPrincipal() {
		return principal;
	}

	public void setPrincipal(User principal) {
		this.principal = principal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

}
