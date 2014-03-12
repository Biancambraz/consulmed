package org.consulmed.dao;

import java.util.List;

import org.consulmed.model.Medico;

public interface IMedicoDao extends IDao<Medico>{
	List<Medico> getList();
}
