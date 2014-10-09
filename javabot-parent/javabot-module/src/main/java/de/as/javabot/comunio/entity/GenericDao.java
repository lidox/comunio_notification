package de.as.javabot.comunio.entity;

public interface GenericDao<E> {
	
	public E create(E t);

	public void delete(Object id);

	public E find(Object id);

	public E update(E t);
}
