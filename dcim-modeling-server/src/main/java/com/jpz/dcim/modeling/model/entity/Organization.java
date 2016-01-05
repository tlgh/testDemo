package com.jpz.dcim.modeling.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "organization")
public class Organization implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid-hex")
	private String id;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "principal_user_id")
	private User principal;

	private String name;

	private String description;

	@Column(name = "create_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Column(name = "last_modify_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifyTime;

	private int index;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", nullable = true)
	private Organization parent = null;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.REFRESH)
	private Set<User> members = new LinkedHashSet<User>();

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH)
	private Set<Organization> children = new LinkedHashSet<Organization>();

	public Organization() {
		super();
	}

	public Organization(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Set<User> getMembers() {
		return members;
	}

	public void setMembers(Set<User> members) {
		this.members = members;
	}

	public Set<Organization> getChildren() {
		return children;
	}

	public void setChildren(Set<Organization> children) {
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
