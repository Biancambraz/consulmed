package org.consumed.jpa;

import java.util.List;

import javax.persistence.*;

import org.consulmed.dao.IMedicoDao;
import org.consulmed.model.Medico;

public class MedicoBD extends JPABase implements IMedicoDao{
	
	public MedicoBD() {
		super();
	}
	
	@Override
	public void add(Medico object) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			manager.getTransaction().begin();		
			manager.persist(object);
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			
			manager.getTransaction().rollback();
			
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
	
	@Override
	public void remove(Medico object) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			manager.getTransaction().begin();		
			manager.remove(object);
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			manager.getTransaction().rollback();
			
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}		
	}
	
	@Override
	public Medico get(Medico object) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			return manager.find(Medico.class, object.getId());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
	
	@Override
	public void set(Medico object) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			manager.getTransaction().begin();		
			manager.merge(object);
			manager.getTransaction().commit();
			
		} catch (Exception e) {
			manager.getTransaction().rollback();
			
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
	
	@Override
	public List<Medico> getList() {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			return manager.createQuery("SELECT m FROM Medico m ORDER BY m.nome").getResultList(); 
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
}
