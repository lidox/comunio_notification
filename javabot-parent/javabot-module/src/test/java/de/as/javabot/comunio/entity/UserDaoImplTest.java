package de.as.javabot.comunio.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import de.as.javabot.comunio.User;

@Ignore
public class UserDaoImplTest {

	private static EntityManager em = null;
	
    @BeforeClass
    public static void setUpClass() throws Exception {
        if (em == null) {
            em = (EntityManager) Persistence.
                      createEntityManagerFactory("testPU").
                      createEntityManager();
        }
    }


	@Test
	public void testCreateUser1() {
		System.out.println("go");
		
	}
    
	@Test
	public void testCreateUser() {
		System.out.println("go");
		//User testUser = new User("testLogin", "testPasswort");
		UserDaoImpl db = new UserDaoImpl();
		//db.create(testUser);
		List<User> test = db.getUserByName("testSQL");
		System.out.println("tet: "+test.toString());
		
	}

}
