package quizDao;

import java.util.*;
import javax.persistence.*;
import quizClasses.*;

final public class RoleDao extends AbstractDao {
	
	public Collection<Role> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT r FROM Role r");
		Collection<Role> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Role findByName(String title) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Role r WHERE r.title = :name").setParameter("name", title);
			Role role = new Role();
			try{
				role=(Role)q.getSingleResult();
			}
			catch(NoResultException e){
				role=null;
			}
			
			return role;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Role findById(int id) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Role r WHERE r.id = :id").setParameter("id", id);
			Role role = new Role();
			try{
				role=(Role)q.getSingleResult();
			}
			catch(NoResultException e){
				role=null;
			}
			return role;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Collection<User> getUsersWithRoles(Role role){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.id = :id").setParameter("id", role.getRoleId());
		Role currentRole = new Role();
		try{
			currentRole=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			currentRole=null;
		}
		Collection<User>listOfUsers = currentRole.getUsersWithRoles();
		
		em.getTransaction().commit();
		em.close();	
		return listOfUsers;
	} 
	
	public Collection<User> getAdminUsers(){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.title = 'admin'");
		Role adminRole = new Role();
		try{
			adminRole=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			adminRole=null;
		}
		Collection<User>listOfAdmins = adminRole.getUsersWithRoles();
		

		em.getTransaction().commit();
		em.close();	
		return listOfAdmins;
	}
		
	public Collection<User> getEditorUsers(){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.title = 'editor'");
		Role editorRole = new Role();
		try{
			editorRole=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			editorRole=null;
		}
		Collection<User>listOfEditors = editorRole.getUsersWithRoles();
		
		em.getTransaction().commit();
		em.close();	
		
		return listOfEditors;
	}
	
	public Collection<User> getCommonUsers(){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.title = 'common'");
		Role commonRole = new Role();
		try{
			commonRole=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			commonRole=null;
		}
		Collection<User>listOfCommoners = commonRole.getUsersWithRoles();

		em.getTransaction().commit();
		em.close();	
		
		return listOfCommoners;
	}
	
	public boolean userHasRole(User user){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.title = 'common' AND r.title = 'editor' AND r.title = 'admin'");
		
		Role role = new Role();
		try{
			role=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			role=null;
		}
		
		Role userRole = user.getRole();
		
		em.getTransaction().commit();
		em.close();	
			
		if(userRole!=null && role!=null && userRole.equals(role.getRole())){
			return true;
		}
		return false;
	}
	
	public void save(Role role){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT r FROM Role r WHERE r.title = :title").setParameter("title", role.getRole());
		Role currentRole = new Role();
		try{
			currentRole=(Role)q1.getSingleResult();
		}
		catch(NoResultException e){
			currentRole=null;
		}
		if(currentRole==null){
				em.persist(role);
		}
		
		em.getTransaction().commit();
		em.close();	
		
	}
	
	public void update(String oldRoleName, Role newRole) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		Role oldRole=this.findByName(oldRoleName);
		Role newRole1=this.findByName(newRole.getRole());
		if(oldRole!=null&&newRole1==null){
			oldRole.setRole(newRole.getRole());
			if(!newRole.getUsersWithRoles().isEmpty()){
				Collection<User>oldUsers=oldRole.getUsersWithRoles();
				oldUsers.addAll(newRole.getUsersWithRoles());
				oldRole.getUsersWithRoles().clear();
				oldRole.setUsersWithRoles(oldUsers);
			}
			em.merge(oldRole);
		}
		em.getTransaction().commit();
		em.close();	
	}
	
	public void deleteRole(String name){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		Query q1 = em.createQuery("SELECT u FROM User u");
		Collection<User>users=q1.getResultList();
		if(!name.equals("admin")&&!name.equals("common")){
			Role roleToDelete = this.findByName(name);
			Role commonRole=this.findByName("common");
			if(roleToDelete!=null&&commonRole!=null){
				Collection <User> roleUsers=roleToDelete.getUsersWithRoles();
				
				for(User user: users){
					if(user.getRole().getRole().equals(roleToDelete.getRole())){
						user.setRole(commonRole);
						commonRole.addUserWithRole(user);
						em.merge(user);
					}
				}
				roleUsers.clear();
				roleToDelete=em.merge(roleToDelete);
				
				em.remove(roleToDelete);

			}
		}
		em.getTransaction().commit();
		em.close();	
	}	
}