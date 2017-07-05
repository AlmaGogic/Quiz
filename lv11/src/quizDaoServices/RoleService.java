package quizDaoServices;

import java.util.*;

import quizClasses.*;
import quizDao.*;

public class RoleService {
	
		private RoleDao roleDao;
		
		public RoleService(RoleDao _roleDao) {
			this.roleDao = _roleDao;
		}
		
		public void create(Role role) {
			roleDao.save(role);
		}
		
		// Nađi sve role-ove
		public Collection<Role> findAll() {
			return roleDao.findAll();
		}
		//Nađi role koji ima zadano ime
		public Role findByName(String title){
			return roleDao.findByName(title);
		}
		//Nađi role koji ima zadani id
		public Role findById(int id){
			return roleDao.findById(id);
		}
		//Pronalazi sve usere koji imaju zadani role
		public Collection<User> findUsersAssociatedWithRole(Role role){
			return roleDao.getUsersWithRoles(role);
		}
		//Update-uje role sa zadanim imenom, tako da on bude 
		//jednak novom role-u 
		public void update(String roleName, Role newRole){
			roleDao.update(roleName,newRole);
		}
		//Dohvata sve usere koji su admini
		public Collection<User> getAdmins(){
			return roleDao.getAdminUsers();
		} 
		//delete briše bilo koji role osim common i admin. Sve
		//usere koji su imali izbrisani role prebacuje na common role
		//t.j. onaj sa najmanjim privilegijama
		public void delete(String name){
			roleDao.deleteRole(name);
		}
		//Nađi sve usere koji imaju editor role
		public Collection<User> getEditorUsers(){
			return roleDao.getEditorUsers();
		}
		//Nađi sve usere koji imaju common role
		public Collection<User> getCommonUsers(){
			return roleDao.getCommonUsers();
		}
		// Provjeri da li user ima postavljen role
		public boolean checkUserRole(User user){
			return roleDao.userHasRole(user);
		}
		
	}