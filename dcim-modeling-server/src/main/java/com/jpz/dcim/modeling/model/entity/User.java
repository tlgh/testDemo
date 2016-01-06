package com.jpz.dcim.modeling.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
	
	@Column(nullable = false)
	private String name; 
	
	@Column(nullable = false, length = 1)
	private Short sex = 1;  // 0:女/1:男
	
	@Column(nullable = true, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = true, unique = true)
	private String mobile;
	
	private Date birthday;
	
	@Column(name = "last_login_time", length = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime;
	
	@ManyToMany(cascade = { CascadeType.PERSIST })
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, uniqueConstraints = { @UniqueConstraint(columnNames = {
			"user_id", "role_id" }) })
	private List<Role> roles;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "organization_id", nullable = true)
	private Organization organization;
	
	@Column(name = "position", nullable = false)
	private int position = 0;
	
	/**
	 * 逻辑删除标记
	 */
	private boolean deleted=false;
	
	/**
	 * 把该用户排另一个用户之前
	 */
	@Transient 
	private String beforeAt;
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getBeforeAt() {
		return beforeAt;
	}

	public void setBeforeAt(String beforeAt) {
		this.beforeAt = beforeAt;
	}


	
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}