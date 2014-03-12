package org.consulmed.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.consulmed.model.*;
import org.consumed.jpa.ConsultaBD;
import org.consumed.jpa.MedicoBD;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ConsultaService {

	public ConsultaService() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Medico> obterListaMedicos()
	{
		MedicoBD bd = new MedicoBD();
		
		return bd.getList();
	}
	
	public Calendar obterDataCorrente()
	{
		return Calendar.getInstance();
	}
	
	public List<Consulta> obterConsultas(int medico, Calendar data) 
	{
		ConsultaBD bd = new ConsultaBD();
		
		List<Consulta> consultasBD = bd.getList(medico, data); //Precisa vir ordenado por data
		
		int horarioInicio = 8; //Horario de trabalho começando as 08:00
		int numeroConsultas = 11; //Com uma hora de almoço
		int horaInicioAlmoco = 12; // Assumindo que o almoço comece ao meio dia
		
		return Consulta.obterLista(horarioInicio, numeroConsultas, horaInicioAlmoco, consultasBD, data);
	}
	
	public Consulta cancelarConsulta(int idConsulta) 
	{
		ConsultaBD bd = new ConsultaBD();
		
		Consulta consulta = bd.get(new Consulta(idConsulta)); // Obtem a consulta do banco
		
		consulta.cancelar(); // Cancela a consulta
		
		bd.set(consulta); // Atualiza o BD
		
		return consulta;
	}
	
	public Consulta realizarConsulta(int idConsulta) 
	{
		ConsultaBD bd = new ConsultaBD();
		
		Consulta consulta = bd.get(new Consulta(idConsulta)); // Obtem a consulta do banco
		
		consulta.realizar(); // Cancela a consulta
		
		bd.set(consulta); // Atualiza o BD
		
		return consulta;
	}
	
	public Consulta marcarConsulta(int medico, Calendar data, String paciente, String plano) 
	{
		Consulta novaConsulta = new Consulta(medico, data, paciente, plano);
		
		ConsultaBD bd = new ConsultaBD();
		
		bd.add(novaConsulta);
		
		return novaConsulta;
	}
}
