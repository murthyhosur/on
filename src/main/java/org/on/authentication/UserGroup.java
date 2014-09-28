package org.on.authentication;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Auth_UserGroup")
@IdClass(UserGroup.PrimaryKey.class)
@SuppressWarnings("unused")
public class UserGroup {
	
	@Id
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	@Id
	@ManyToOne
	@JoinColumn(name="groupname")
	private Group group;
	
	UserGroup() {
		
	}
	
	public UserGroup(User user,Group group) {
		this.user = user;
		this.group = group;
	}
	
	public static class PrimaryKey implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private User user;
		private Group group;
		
		public PrimaryKey() {
			
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(this.group != null)
				hashCode ^= this.group.getGroupName().hashCode();
			if(this.user != null)
				hashCode ^= this.user.getUsername().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryKey))
				return false; 
			PrimaryKey target = (PrimaryKey) obj;
			return ((this.user == null) ? target.user == null : this.user.equals(target)) &&
			((this.group == null) ? target.group == null : this.group.equals(target));
		}
	}
}
