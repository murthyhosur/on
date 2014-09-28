package org.on.authentication;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Auth_GroupPrivilege")
@IdClass(GroupPrivilege.PrimaryKey.class)
@SuppressWarnings("unused")
public class GroupPrivilege {
	
	@Id
	@ManyToOne
	@JoinColumn(name="groupname")
	private Group group;
	@Id
	@ManyToOne
	@JoinColumn(name="privilegeCode")
	private Privilege privilege;
	
	GroupPrivilege() {
		
	}
	
	public GroupPrivilege(Group group,Privilege privilege) {
		this.group = group;
		this.privilege = privilege;
	}
	
	public static class PrimaryKey implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Group group;
		private Privilege privilege ;
		
		public PrimaryKey() {
			
		}

		public Group getGroup() {
			return group;
		}

		public void setGroup(Group group) {
			this.group = group;
		}

		public Privilege getPrivilege() {
			return privilege;
		}

		public void setPrivilege(Privilege privilege) {
			this.privilege = privilege;
		}
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(this.group != null)
				hashCode ^= this.group.getGroupName().hashCode();
			if(this.privilege != null)
				hashCode ^= this.privilege.getPrivilegeCode().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryKey))
				return false; 
			PrimaryKey target = (PrimaryKey) obj;
			return ((this.privilege == null) ? target.privilege == null : this.privilege.equals(target)) &&
			((this.group == null) ? target.group == null : this.group.equals(target));
		}
	}
}
