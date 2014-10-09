package de.as.javabot.comunio.entity;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class GenericDaoImpl<E> implements GenericDao<E> {

	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	// dieser string steht in der persistence.xml
	@PersistenceContext(unitName = "testPU")
	protected EntityManager em;

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	public GenericDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[0];
	}

	public GenericDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public E create(E t) {
		return create(t, false);
	}

	private E create(final E t, boolean flush) {
		this.em.persist(t);
		if (flush) {
			this.em.flush();
		}
		return t;
	}

	@Override
	public void delete(Object id) {
		this.em.remove(this.em.getReference(entityClass, id));
	}

	@Override
	public E find(Object id) {
		return (E) this.em.find(entityClass, id);
	}

	@Override
	public E update(E t) {
		return this.em.merge(t);
	}

	/**
	 * The method provides the possibility to search for a list of entities. You
	 * have to provide the name of the query and the key value pairs for
	 * parameter which shall be added to the query. The number of entries in the
	 * keyValue array shall not be odd due to the fact that for each parameter a
	 * key and a value has to be provided.
	 * 
	 * @param queryName
	 *            name of the NamedQuery which has to be executed
	 * @param maxResult
	 *            number of maximum returned results
	 * @param keyValue
	 *            list of key value information, no odd number of entries is
	 *            allowed
	 * @return list of entities
	 * @throws Exception
	 * @throws NotFoundException
	 *             The exception is thrown if an odd number of entries is
	 *             provided in the keyValue parameter and if no entry was found
	 *             after the execution of the NamedQuery
	 */
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public List<E> searchEntity(String queryName, int maxResult,
			Object... keyValue) throws Exception {

		Query query = this.em.createNamedQuery(queryName);
		if (maxResult > 0) {
			query.setMaxResults(maxResult);
		}
		Set<String> keys = null;
		List<String> values = null;

		// check for odd number of keyValue parameter
		if ((keyValue.length % 2) == 0) {
			keys = new HashSet<String>();
			values = new ArrayList<String>();
			for (int i = 0; i < keyValue.length;) {
				// add parameter to query
				query.setParameter(keyValue[i].toString(), keyValue[i + 1]);

				// store key and value information for a possible exception
				// message
				keys.add(keyValue[i].toString());
				values.add(keyValue[i + 1] != null ? keyValue[i + 1].toString()
						: "");
				i = i + 2;
			}
		} else {
			throw new Exception("The number of keyValue parameter shall not be odd.");
		}

		// search
		// if (LOG.isDebugEnabled()) {
		System.out.println("Search entity: [query=" + queryName + "|keys="
				+ keys + "|values=" + values + "]");
		// }
		@SuppressWarnings("unchecked")
		List<E> resutlList = query.getResultList();
		if (resutlList == null || resutlList.size() == 0) {
			throw new Exception("No " + entityClass.getSimpleName()
					+ " was found for keys: " + keys + " and values: " + values);
		}
		// return found merchant
		return resutlList;
	}

}
