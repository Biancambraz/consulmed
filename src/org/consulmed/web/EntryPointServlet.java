package org.consulmed.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.consulmed.model.Consulta;
import org.consulmed.model.Medico;
import org.consulmed.service.ConsultaService;
import org.consumed.jpa.MedicoBD;

import com.google.gson.Gson;

/**
 * Servlet implementation class ConsultaAgendaServlet
 */
@WebServlet("/EntryPointServlet")
public class EntryPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EntryPointServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		doRequest(request, response);
	}
	
	private enum Action
	{
		OBTER_MEDICOS, OBTER_DATA_CORRENTE, OBTER_CONSULTAS, MARCAR_CONSULTA, CANCELAR_CONSULTA, REALIZAR_CONSULTA, ACAO_INESPERADA
	}
	
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		
		Action action = null;
		
		try 
		{
			action = Action.valueOf(request.getParameter("action"));
		} 
		catch (Exception e) 
		{
			action = Action.ACAO_INESPERADA;
		}
		
		ConsultaService service = new ConsultaService();
		
		Gson jsonBuilder = new Gson();
		
		RequestResponse requestResponse = new RequestResponse();
		
		switch (action) 
		{
			case OBTER_MEDICOS:
				
				try 
				{
					List<Medico> medicos = service.obterListaMedicos();
					
					requestResponse.setResponse(jsonBuilder.toJson(medicos));
				} 
				catch (Exception e) 
				{
					requestResponse.setMessage(e.getMessage());
				}
				
				break;
				
			case OBTER_DATA_CORRENTE:
				
				requestResponse.setResponse(jsonBuilder.toJson(service.obterDataCorrente()));
				
				break;
			case OBTER_CONSULTAS:
				
				try 
				{	
					int medico = 0;
					Date data = null;
					
					try 
					{
						medico = Integer.parseInt(request.getParameter("medico"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'medico' incorreto");
					}
					
					try 
					{
						data = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("data"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'data' incorreto");
					}
					
					Calendar cal = Calendar.getInstance();
				    cal.setTime(data);
					
					List<Consulta> consultas = service.obterConsultas(medico, cal);
					
					requestResponse.setResponse(jsonBuilder.toJson(consultas));
				} 
				catch (Exception e) 
				{
					requestResponse.setMessage(e.getMessage());
				}
				
				break;
				
			case MARCAR_CONSULTA:
				
				try 
				{
					int medico = 0;				
					
					try 
					{
						medico = Integer.parseInt(request.getParameter("medico"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'medico' incorreto");
					}
					
					Date data = null;
					
					try 
					{
						data = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(request.getParameter("data"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'data' incorreto");
					}
					
					Calendar cal = Calendar.getInstance();
				    cal.setTime(data);
				    
			    	String paciente = "";				
					
					try 
					{
						paciente = request.getParameter("paciente");
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'paciente' incorreto");
					}
					
					String planoSaude = "";				
					
					try 
					{
						planoSaude = request.getParameter("plano_saude");
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'plano_saude' incorreto");
					}
				    
					Consulta novaConsulta = service.marcarConsulta(medico, cal, paciente, planoSaude);
					
					requestResponse.setResponse(jsonBuilder.toJson(novaConsulta));
					
				} 
				catch (Exception e) 
				{
					requestResponse.setMessage(e.getMessage());
				}
				
				break;
			
			case CANCELAR_CONSULTA:
				try 
				{
					int idConsulta = 0;				
					
					try 
					{
						idConsulta = Integer.parseInt(request.getParameter("consulta"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'consulta' incorreto");
					}
					
					Consulta consulta = service.cancelarConsulta(idConsulta);
					
					requestResponse.setResponse(jsonBuilder.toJson(consulta));
				} 
				catch (Exception e) 
				{
					requestResponse.setMessage(e.getMessage());
				}
				
				break;
				
			case REALIZAR_CONSULTA:
				try 
				{
					int idConsulta = 0;				
					
					try 
					{
						idConsulta = Integer.parseInt(request.getParameter("consulta"));
					} 
					catch (Exception e) 
					{
						throw new RuntimeException("Parametro 'consulta' incorreto");
					}
					
					Consulta consulta = service.realizarConsulta(idConsulta);
					
					requestResponse.setResponse(jsonBuilder.toJson(consulta));
				} 
				catch (Exception e) 
				{
					requestResponse.setMessage(e.getMessage());
				}
				
				break;
				
			default:
				
				requestResponse.setMessage("Ação inesperada !!");
		}
		
		out.write(jsonBuilder.toJson(requestResponse));
	}

}
