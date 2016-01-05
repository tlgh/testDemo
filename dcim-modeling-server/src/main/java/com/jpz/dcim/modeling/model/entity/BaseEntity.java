package com.jpz.dcim.modeling.model.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class BaseEntity implements Serializable {	
	@Id
	@GeneratedValue(generator = "uuid-hex")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BaseEntity){
			BaseEntity entity = (BaseEntity)obj;
			return id.equals(entity.getId());
		}
		return false;
	}
	
}
