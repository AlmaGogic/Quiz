package ba.fet.rwa.lv10.domain;

import java.util.*;
import javax.persistence.*;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	int id;
	String title;
	@OneToMany(mappedBy="role")
	Collection<User> listOfUsers;
	
	public Role(){
		id=0;
		title=null;
		listOfUsers=new ArrayList<User>();
	}
	
	public Role(int _id,String _title, User user){
		id=_id;
		title=_title;
		listOfUsers.add(user);
	}
	
	public int getRoleId(){
		return id;
	}
	
	public void setRoleId(int _id){
		id=_id;
	}
	
	public String getRole(){
		return title;
	}
	
	public void setRole(String _title){
		title=_title;
	}
	
	public Collection<User> getUsersWithRoles(){
		return listOfUsers;
	}
	
	public void addUserWithRole(User user){
		listOfUsers.add(user);
	}
	
	public void removeUserWithRole(User user){
		listOfUsers.remove(user);
	}
	
	public void setUsersWithRoles(Collection<User> _listOfUsers){
		for(User user : _listOfUsers){
			listOfUsers.add(user);
		}
	}
	
}