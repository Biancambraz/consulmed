$(document).ready(function() {
	$(".modalbox").fancybox();
	$("#agendarConsulta").submit(function() { event.preventDefault(); });
	
	carregarMedicos();
	
	carregarDataCorrente();
	
	$("#btnConsultar").click(btnConsultarAgendaClick);
	$("#btnSalvar").click(marcarConsulta);
	
	$("#lnkDiaAnterior").click(diaAnterior);
	$("#lnkProximoDia").click(proximoDia);
})

Date.prototype.addDays = function(days)
{
    var dat = new Date(this.valueOf());
    dat.setDate(dat.getDate() + days);
    return dat;
}

function carregarMedicos()
{
	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"OBTER_MEDICOS" }, function( responseObj ) {
						
			if(responseObj.errorMessage)
			{
				console.log(responseObj.errorMessage);
			}
			else
			{
				var res = jQuery.parseJSON(responseObj.response);
				
				$("#Medico").append($('<option></option>').val("").html("---"));
				
				for(var index in res) {
					$("#Medico").append($('<option></option>').val(res[index].id).html(res[index].nome));
				}
			}
		},"json");
}

function carregarDataCorrente()
{
	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"OBTER_DATA_CORRENTE" }, function( responseObj ) {
						
			if(responseObj.errorMessage)
			{
				console.log(responseObj.errorMessage);
			}
			else
			{
				var res = jQuery.parseJSON(responseObj.response);
				
				console.log(res);
				
				var month = parseInt(res.month) + 1;
				
				if(parseInt(month) < 10)
				{
					month = "0" + month;
				}
				
				var dayOfMonth = res.dayOfMonth;
				
				if(parseInt(dayOfMonth) < 10)
				{
					dayOfMonth = "0" + dayOfMonth;
				}
				
				var data = res.year + "-" + month + "-" + dayOfMonth;
				
				console.log(data);
				
				$("#Data2").val(data);
			}
		},"json");
}

function getFormatedDate(year, month, day)
{
	month = parseInt(month) + 1;
				
	if(parseInt(month) < 10)
	{
		month = "0" + month;
	}
	
	if(parseInt(day) < 10)
	{
		day = "0" + day;
	}
	
	var data = year + "-" + month + "-" + day;
	
	return data;
}

function btnConsultarAgendaClick()
{
	var idMedico = $("#Medico").val();
	var dataConsulta = $("#Data2").val();
	
	$("#nomeMedico").text($("#Medico > option:selected").text());
	$("#data").text($("#Data2").val());

	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"OBTER_CONSULTAS", medico: idMedico, data: dataConsulta }, function( responseObj ) {
						
			if(responseObj.errorMessage)
			{
				console.log(responseObj.errorMessage);
			}
			else
			{
				$("#tabelaConsultas > tbody").empty();
				 
				var res = jQuery.parseJSON(responseObj.response);
				
				console.log(res);
				
				for(var index in res)
				{
					var celText = "";
					var hora = res[index].data.hourOfDay;
					
					var linha = document.createElement("tr");
					
					/*coluna hora*/
					var colHora = document.createElement("td");					
					celText = formataHora(res[index].data.hourOfDay);
					colHora.appendChild(document.createTextNode(celText));					
					linha.appendChild(colHora);	
					
					/*coluna status*/
					var colStatus = document.createElement("td");
					if(res[index].status)
					{
						celText = res[index].status.nome;
					}
					else
					{
						celText = "-";
					}
					colStatus.appendChild(document.createTextNode(celText));
					linha.appendChild(colStatus);

					/*coluna paciente*/
					var colPaciente = document.createElement("td");
					if(res[index].paciente)
					{
						celText = res[index].paciente;
					}
					else
					{
						celText = "-";
					}
					colPaciente.appendChild(document.createTextNode(celText));
					linha.appendChild(colPaciente);
					
					/*coluna plano de saude*/
					var colPlano = document.createElement("td");
					if(res[index].planoSaude)
					{
						celText = res[index].planoSaude;
					}
					else
					{
						celText = "-";
					}
					colPlano.appendChild(document.createTextNode(celText));										
					linha.appendChild(colPlano);
					
					var colOpcao = document.createElement("td");
					
					console.log(res[index].status);
					
					if(res[index].status && res[index].status.id == "2")
					{
						$(colOpcao).append("<a class='' href='#inline' style='width:80px; align: center' onclick='javascript:cancelarConsulta(" + res[index].id + ");'> <img src='cancel.png' /> </a>");
						$(colOpcao).append("<a class='' href='#inline' style='width:80px; align: center' onclick='javascript:realizarConsulta(" + res[index].id + ");'> <img src='check.png' />  </a>");
					}
					else if(!res[index].status || res[index].status.id != "3")
					{
						$(colOpcao).append("<a href='#inline' class='modalbox' style='width:120px; align: center' onclick='javascript:marcarClick(" + hora + ");'> <img src='add.png' />  </a>");
						
					}
					
					linha.appendChild(colOpcao);
					
					$('#tabelaConsultas > tbody:last').append(linha);
				}
			}
		},"json");
}

function formataHora(hora)
{
	var resultado = hora;
	
	if(parseInt(resultado) < 10)
	{
		resultado = "0" + resultado;
	}					
	resultado = resultado + ":00";
	
	return resultado;
}

function marcarClick(hora)
{
	$("#horaConsulta").val(""+formataHora(hora));
	$("#dataConsulta").val($("#Data2").val());
	$("#nomePaciente").val("");
	$("#planoSaude").val("");
}

function marcarConsulta()
{
	var horaConsulta = $("#horaConsulta").val();
	var idMedico = $("#Medico").val();
	var dataConsulta = $("#Data2").val() + " " + formataHora(horaConsulta);
	var nomePaciente = $("#nomePaciente").val();
	var planoSaude = $("#planoSaude").val();
	
	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"MARCAR_CONSULTA", medico : idMedico, data : dataConsulta, paciente: nomePaciente, plano_saude: planoSaude }, function( responseObj ) {

		if(responseObj.errorMessage)
		{
			console.log(responseObj.errorMessage);
		}
		else
		{
			var res = jQuery.parseJSON(responseObj.response);
			
			parent.$.fancybox.close();
			
			btnConsultarAgendaClick();
		}
		
	},"json");	
}

function cancelarConsulta(idConsulta)
{
	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"CANCELAR_CONSULTA", consulta: idConsulta }, function( responseObj ) {
						
			if(responseObj.errorMessage)
			{
				console.log(responseObj.errorMessage);
			}
			else
			{
				var res = jQuery.parseJSON(responseObj.response);
				
				btnConsultarAgendaClick();
			}
		},"json");
}

function realizarConsulta(idConsulta)
{
	$.post( "http://localhost:8080/consulmed/EntryPointServlet", { action:"REALIZAR_CONSULTA", consulta: idConsulta }, function( responseObj ) {
						
		if(responseObj.errorMessage)
		{
			console.log(responseObj.errorMessage);
		}
		else
		{
			var res = jQuery.parseJSON(responseObj.response);
			
			btnConsultarAgendaClick();
		}
	},"json");
}

function proximoDia()
{
	var dataConsulta = new Date($("#Data2").val()); //Esta retornando o mes e dia com -1 

	var dataCorrigida = new Date(dataConsulta.getFullYear() + "-" + (dataConsulta.getMonth() + 1) + "-" + (dataConsulta.getDate() + 1));
	dataCorrigida = dataCorrigida.addDays(1);
	
	var data = getFormatedDate(dataCorrigida.getFullYear(), dataCorrigida.getMonth(), dataCorrigida.getDate());
	
	$("#Data2").val(data);
	
	btnConsultarAgendaClick();
}

function diaAnterior()
{
	var dataConsulta = new Date($("#Data2").val()); //Esta retornando o mes e dia com -1 

	var dataCorrigida = new Date(dataConsulta.getFullYear() + "-" + (dataConsulta.getMonth() + 1) + "-" + (dataConsulta.getDate() + 1));
	dataCorrigida = dataCorrigida.addDays(-1);
	
	var data = getFormatedDate(dataCorrigida.getFullYear(), dataCorrigida.getMonth(), dataCorrigida.getDate());
	
	$("#Data2").val(data);
	
	btnConsultarAgendaClick();
}