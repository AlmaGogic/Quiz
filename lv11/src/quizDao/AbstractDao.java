package quizDao;

import javax.persistence.*;

public abstract class AbstractDao {

	private static final String PERSISTENCE_UNIT = "lv10example";

	public EntityManager createEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		return emf.createEntityManager();
	}
}