package de.as.javabot.comunio.entity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.as.javabot.comunio.User;

//@Ignore
public class UserDaoImplTest {

	private static EntityManager em = null;
//	@EJB(name="orderDao")
//	private UserDao orderDao;
	
    @BeforeClass
    public static void setUpClass() throws Exception {
        if (em == null) {
            em = (EntityManager) Persistence.
                      createEntityManagerFactory("testPU").
                      createEntityManager();
        }
    }

    
//	@Test
//	public void testCreateUser() {
//		System.out.println("go");
//		//User testUser = new User("testLogin", "testPasswort");
//		UserDaoImpl db = new UserDaoImpl();
//		//db.create(testUser);
//		List<User> test = db.getUserByName("testSQL");
//		System.out.println("tet: "+test.toString());
//		
//	}
	
	@Test
	public void testCreateUser2() {
		java.util.Map<Object,Object> map = new java.util.HashMap<Object,Object>();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("testPU", map);
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		// we'll put some codes here later
		User u = new User();
		
		u.setLogin("junit-user");
		u.setPassword("top secret");
		em.persist(u);
		//
		em.getTransaction().commit();
		em.close();
		factory.close();
		System.out.println();
	}

}
