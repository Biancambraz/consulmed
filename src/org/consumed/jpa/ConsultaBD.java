package org.consumed.jpa;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.consulmed.dao.IConsultaDao;
import org.consulmed.model.Consulta;

public class ConsultaBD extends JPABase implements IConsultaDao{
	
	public ConsultaBD() {
		super();		
	}
	
	@Override
	public void add(Consulta object) {
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
	public Consulta get(Consulta object) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			return manager.find(Consulta.class, object.getId());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
	
	@Override
	public void remove(Consulta object) {
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
	public void set(Consulta object) {
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
	public List<Consulta> getList(int medico, Calendar data) {
		EntityManager manager = factory.createEntityManager();
		
		try {
			
			Calendar dataInicial = (Calendar)data.clone();
			dataInicial.set(Calendar.HOUR_OF_DAY, 0);
			dataInicial.set(Calendar.MINUTE, 0);
			dataInicial.set(Calendar.SECOND, 0);
			
			Calendar dataFinal = (Calendar)data.clone();
			dataFinal.set(Calendar.HOUR_OF_DAY, 23);
			dataFinal.set(Calendar.MINUTE, 59);
			dataFinal.set(Calendar.SECOND, 59);
			
			return manager.createQuery("from Consulta c where c.data between :dataInicial and :dataFinal and c.medico.id = :id_medico")
					.setParameter("dataInicial", dataInicial)
					.setParameter("dataFinal", dataFinal)
					.setParameter("id_medico", medico)
					.getResultList(); 
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally
		{
			manager.close();
		}
	}
}
