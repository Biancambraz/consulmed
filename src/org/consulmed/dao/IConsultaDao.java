package org.consulmed.dao;

import java.util.Calendar;
import java.util.List;

import org.consulmed.model.Consulta;

public interface IConsultaDao extends IDao<Consulta>
{
	List<Consulta> getList(int medico, Calendar data);
}
