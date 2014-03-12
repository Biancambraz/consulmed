package org.consulmed.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="consulta")
public class Consulta {
	
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name = "data", nullable = false)
	private Calendar data;
	
	@ManyToOne
	@JoinColumn(name="id_status")
	private SituacaoConsulta status;
	
	@Column(name = "paciente", nullable = false)
	private String paciente;
	
	@Column(name = "plano_saude", nullable = false)
	private String planoSaude;
	
	@ManyToOne
	@JoinColumn(name="id_medico")
	private Medico medico;
	
	
	public Consulta() {
		// TODO Auto-generated constructor stub
	}
	
	public Consulta(int id) {
		this.setId(id);
	}
	
	public Consulta(int medico, Calendar data, String paciente, String plano) {
		Medico medicoConsulta = new Medico();
		medicoConsulta.setId(medico);
		
		this.setData(data);
		this.setPaciente(paciente);
		this.setPlano(plano);
		this.setMedico(medicoConsulta);
		this.setStatus(SituacaoConsulta.MARCADA);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public SituacaoConsulta getStatus() {
		return status;
	}
	
	public void setStatus(SituacaoConsulta status) {
		this.status = status;
	}
	
	public String getPaciente() {
		return paciente;
	}
	
	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}
	
	public String getPlano() {
		return planoSaude;
	}
	
	public void setPlano(String plano) {
		this.planoSaude = plano;
	}
	
	public Medico getMedico() {
		return medico;
	}
	
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
	public int getHoraConsulta()
	{	
		return this.getData().get(Calendar.HOUR_OF_DAY);
	}
	
	public void cancelar()
	{
		this.setStatus(SituacaoConsulta.CANCELADA);
	}
	
	public void realizar()
	{
		this.setStatus(SituacaoConsulta.REALIZADA);
	}
	
	public static List<Consulta> obterLista(int horarioInicio, int numeroConsultas, int horaInicioAlmoco, List<Consulta> consultasBD, Calendar data)
	{
		List<Consulta> consultas = new LinkedList<Consulta>();
		
		for (int hora = horarioInicio; hora < (horarioInicio + numeroConsultas); hora++) 
		{
			if (hora != horaInicioAlmoco) // Assumindo uma hora de almoço 
			{ 
				consultas.add(obtemConsulta(hora, consultasBD, data));
			}
		}
		
		return consultas;
	}
	
	private static Consulta obtemConsulta(int hora, List<Consulta> consultas, Calendar data)
	{
		Consulta resultado = null;
		
		for (Consulta consulta : consultas) 
		{
			if (consulta.getHoraConsulta() == hora) 
			{
				resultado = consulta;
			}
		}
		
		if (resultado != null) 
		{
			consultas.remove(resultado);
		}
		else
		{
			resultado = new Consulta();
		
			int day = data.get(Calendar.DAY_OF_MONTH);
			int month = data.get(Calendar.MONTH);
			int year = data.get(Calendar.YEAR);
			
			Calendar dataHora = Calendar.getInstance();			
			dataHora.set(year, month, day, hora, 00);
			
			resultado.setData(dataHora);
		}
		
		return resultado;
	} 
}
