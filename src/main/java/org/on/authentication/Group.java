package org.on.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Auth_Group")
public class Group {
	
	@Id
	@Column(length=16)
	private String groupName;
	private String description;
	
	Group() {
		
	}
	
	public Group(String groupName,String description) {
		this.groupName = groupName;
		this.description = description;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
