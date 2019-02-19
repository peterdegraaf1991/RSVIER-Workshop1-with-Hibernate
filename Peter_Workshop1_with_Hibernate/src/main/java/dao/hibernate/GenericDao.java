package dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import controller.*;

public class GenericDao<T> {
	
	private Class<T> classToSet = null;
	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	
	public GenericDao() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistence");
		this.entityManager = entityManagerFactory.createEntityManager();
	}
	
	public GenericDao(Class<T> classToSet){
		this.entityManagerFactory = Persistence.createEntityManagerFactory("TestPersistence");
		this.entityManager = entityManagerFactory.createEntityManager();
		this.classToSet = classToSet;
	}
	
	public T createObject(T object) {
		entityManager.getTransaction().begin();
		entityManager.persist(object);
		entityManager.getTransaction().commit();
		entityManagerFactory.close();;
		return object;
	}

	public T readObjectById(int id) {
		entityManager.clear();
		T object = entityManager.find(classToSet,id);
		entityManagerFactory.close();
		return object;
	}
	
	
	public T readSingleObjectByColumn(String column, int value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(classToSet);
		Root<T> i = criteria.from(classToSet);
		criteria.select(i).where(cb.equal(i.get(column), value));
		TypedQuery<T> query = entityManager.createQuery(criteria);
		T object = query.getSingleResult();
		
/*		T object = (T) entityManager.createQuery("SELECT :class FROM TABLE :column = :value ")
				.setParameter("class", classToSet)
				.setParameter("column", column)
				.setParameter("value", value)
				.getSingleResult();
				*/
		return object;
	}
	
	public T readSingleObjectByColumn(String column, String value) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = cb.createQuery(classToSet);
		Root<T> i = criteria.from(classToSet);
		criteria.select(i).where(cb.equal(i.get(column), value));
		TypedQuery<T> query = entityManager.createQuery(criteria);
		T object = query.getSingleResult();
		return object;
		
/*		T object = (T) entityManager.createQuery("SELECT a FROM Account a WHERE :column = :value ")
				.setParameter("class", classToSet)
				.setParameter("column", column)
				.setParameter("value", value)
				.getSingleResult();
*/
	}
	
	public List<T> readAllObjects(Class<T> type) {
		
		    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<T> criteria = builder.createQuery(type);
		    criteria.from(type);
		    List<T> list = entityManager.createQuery(criteria).getResultList();
		    return list;
	}
	
	public void updateObject(T object) {
		entityManager.getTransaction().begin();
		entityManager.merge(object);
		entityManager.getTransaction().commit();
		entityManagerFactory.close();
	}
	
	public void deleteObject(int id) {
			entityManager.clear();
			entityManager.getTransaction().begin();
			T object = entityManager.find(classToSet, (long)id);
			entityManager.remove(object);
			entityManager.getTransaction().commit();
			entityManagerFactory.close();
	}
}