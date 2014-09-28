package org.on.authentication;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Auth_User")
public class User {
	
	@Id
	@Column(length=16)
	private String username;
	@Column(name="password",length=16)
	private String password;
	
	User() {
		
	}
	
	public User(String username,String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User))
			return false;
		User target = (User)obj;
		return (this.username == null ? target.username == null : this.username.equals(target.username)) &&
		(this.password == null ? target.password == null : this.password.equals(target.password));
	}
	
}
