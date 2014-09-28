package org.on.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Auth_Privilege")
public class Privilege {
	@Id
	@Column(length=16)
	private String privilegeCode;
	private String description;
	
	Privilege() {
		
	}
	
	public Privilege(String privilegeCode,String description) {
		this.privilegeCode = privilegeCode;
		this.description = description;
	}

	public String getPrivilegeCode() {
		return privilegeCode;
	}

	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
