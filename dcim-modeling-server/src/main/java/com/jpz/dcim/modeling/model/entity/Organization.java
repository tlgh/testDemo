package com.jpz.dcim.modeling.model.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "organization")
public class Organization extends BaseEntity{
	

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

	private int position;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "parent_id", nullable = true)
	private Organization parent = null;

	@OneToMany(mappedBy = "organization", cascade = CascadeType.REFRESH)
	private List<User> members = new ArrayList<User>();

	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH)
	private List<Organization> children = new ArrayList<Organization>();



	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
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
