<<<<<<< HEAD
package Monitor;

import Comon.ColecaoResponsavel;
import Comon.ColecaoComputador;
import Comon.Computador;
import Comon.Responsavel;
import io.Ler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe principal que roda no sistema monitor
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Main {
	
	/** Atributos */
	
	/** Arquivo de Registro */
	public static File registro = new File("registro.log");
	/** Coleção de Computadores (laborátorio) */
	public static ColecaoComputador  pcs  = new ColecaoComputador();
	/** Coleção de Responsáveis Autorizados a usar a Aplicação */
	public static ColecaoResponsavel colResp = new ColecaoResponsavel();
	/** Lista de todos os IPs conectados na rede */
	public static ArrayList<String> hosts = new ArrayList<String>();
	/** Lista de Computadores que não possui Aplicação Cliente */
	public static ArrayList<String> intrusos = new ArrayList<String>();
	/** Computador do monitor */
	public static Computador MONITOR = null;
	
	
	/** Método Principal do Monitor
	 * @param args da Main */
	public static void main(String[] args) {
		
		/** Carregamento dos Responsáveis com Autorização */
		colResp=Monitorar.lerAdminDoXML();
		
		/** Login do Responsável */
		Responsavel resp = login();
		
		/** Autenticação do Responsável
		 *  Caso não autorize, a aplicação se encerra */
		try {
			if (!colResp.verificarResp(resp)) {
				System.out.println("ADMIN invalido");
				return;
			}
		}catch(Exception e) {System.out.println("Erro ao carregar XML"); return;}
		
		/** Tentativa de Construir Computador do Monitor
		 *  caso nao consiga, é solicitado IP da rede
		 */
		try{
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("win") >= 0)
				MONITOR = new Computador();
		} catch (Exception e) {
			System.out.print("Digite o IP da sua rede: ");
			MONITOR.setIp(Ler.ip());
		}
		
		/** @param opção que é lida nos menus */
		int opcao;
		
		/** Escolha da Rede para Monitorar */
		do{
			System.out.println("1-Monitorar sua rede\n"      +
			                   "2-Monitorar uma outra rede\n"+
			                   "Opcao:");
			opcao=Ler.inteiro();
			if(opcao==1){
				hosts = Monitorar.DispConectados(MONITOR.getIP());
				break;
			}
			else if (opcao==2){
				String ip = Ler.ip();
				hosts = Monitorar.DispConectados(ip);
				break;
			}else System.out.println("Digite uma opcao valida");
		}while(opcao !=1||opcao !=2);
		
		/** Menus de Monitoramento */
		while(true) {
			menu();
			opcao = Ler.inteiro();
			if (opcao == 1) {
				while(true){
					menuInfo();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { listarTudo(); 
					} else if (opcao == 2) { infoConectados(hosts);
					} else if (opcao == 3) { buscarInfo();
					} else if (opcao == 4) { listarIntrusos(hosts);
					} else if (opcao == 5) { gerarLogDispositivos(resp, intrusos, pcs);
					} else if (opcao == 6) { break;
					} else { System.out.println("Digite um Valor entre 1 e 5");
				    }
				}
			} else if (opcao == 2) {
				while(true){ 
					menuProcesso();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { capturarPortas();
					} else if (opcao == 2) { capturarPortasAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
				    }
				}
			} else if (opcao == 3) {
				while(true) {
					menuCaptura();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { capturaTela();
					} else if (opcao == 2) { capturaTelaAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }
			} else if (opcao == 4) {
				while(true) {
					menuDesligar();
					opcao = Ler.inteiro();
					if (opcao == 1) 	   { desligarPC();
					} else if (opcao == 2) { desligarAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }
			} else if (opcao == 5) {
				while(true) {
					menuAlertar();
					opcao = Ler.inteiro();
					if (opcao == 1) 	   { AlertarPC();
					} else if (opcao == 2) { AlertarAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }	
			}else if (opcao == 6) {
				while(true) {
					menuAdmin();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { addAdmin();
					} else if (opcao == 2) { delAdmin();
					} else if (opcao == 3) { editAdmin();
					} else if (opcao == 4) { listAdmin();
					} else if (opcao == 5) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
				}
			}else if (opcao == 7) { 
				Monitorar.salvaAdminEmXML(colResp);
				gerarLogDispositivos(resp, intrusos, pcs);
				break;
			} else { System.out.println("Digite um Valor entre 1 e 5"); }
		}
	}

	
	/** Menu principal */
	private static void menu(){
		System.out.println(	"\n---------MONITOR DE REDE-----------\n"						 +
							"1- Buscar Informacoes de Dispositivos Conectados\n"			 + // menuInfo()
							"2- Capturar tarefas ativas em Dispositivo Conectado\n"          + // menuProcesso()
							"3- Capturar tela de Dispositivos Conectados\n"					 + // menuCaptura()
							"4- Desligar Dispositivos Conectados\n"							 + // menuDesligar()
							"5- Alertar Dispositivo\n"                                       + // enviarMensagem()
							"6- Administrador\n"                                             + // menuAdmin()
				            "7- Encerrar programa");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para captura de Informações dos Computadores */
	private static void menuInfo() {
		System.out.println( "\n----------Menu Informacoes-----------\n"			+
	                        "1- Listar Dispositivos Conectados\n"               + // listarTudo()
							"2- Listar Informacoes de Dispositivos Conectados\n"+ // listarConectados()
							"3- Buscar Informacoes de um Dispositivo por IP\n"	+ // buscarInfo()
							"4- Listar IP de dispositivos intrusos\n"			+ // listarIntrusos()
							"5- Salvar log de informacoes em arquivo\n"			+ // gerarLogDispositivos()
							"6- Retornar ao menu principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para captura de Portas dos Computadores */
	private static void menuProcesso() {
		System.out.println(	"\n----------Menu Tarefas-----------\n"					    +
							"1- Capturar Tarefas de um Dispositivo IP\n"				+ // capturarPortas()
							"2- Capturar Tarefas de Todos os Dispositivos de uma rede\n"+ // capturarPortasAll()
							"3- Retornar ao menu principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu captura de Tela dos Computadores */
	private static void menuCaptura(){
		System.out.println(	"\n----------Menu Consultas-----------\n"				 +
							"1- Capturar tela de um único Dispositivo por IP\n"      + // capturaTela()
							"2- Capturar tela de Todos os Dispositivos de uma Rede\n"+ // capturaTelaAll()
							"3- Retornar ao menu Principal");						
		System.out.print("opcao: ");
	}
	
	/** Menu envio do Sinal de Desligamento */
	private static void menuDesligar(){
		System.out.println(	"\n----------Menu Desligar-----------\n"		 +
							"1- Desligar Dispositivo por IP\n"				 + // desligarPC()
							"2- Desligar todos os Dispositivos de uma Rede\n"+ // desligarAll()
							"3- Retornar ao menu Principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para genrenciar Responsáveis */
	private static void menuAdmin(){
		System.out.println(	"\n----------Menu Administrador-----------\n" +
				            "1- Adicionar Administrador\n"				  + // adddUser()
			              	"2- Remover Administrador por matricula\n"    + // delUser()
				            "3- Editar Administrador por matricula\n"     + // editUser()
				            "4- Listar Administrador\n"                   + // listUser()    
			              	"5- Retornar ao menu Principal");
        System.out.print("opcao: ");
	}
	
	/** Menu envio do Sinal de Alertar */
	private static void menuAlertar(){
		System.out.println(	"\n----------Menu Alertar-----------\n"		    +
							"1- Alertar Dispositivo por IP\n"				+  // AlertarPC()
							"2- Alertar todos os Dispositivos de uma Rede\n"+  // AlertarAll()
							"3- Retornar ao menu Principal");
		System.out.print("opcao: ");
	}
	

	/** Método que lista todos os hosts da Rede */
	private static void listarTudo(){
		System.out.println("1-Exibir Dispositivos ja verificados\n" +
	                       "2-Atualizar Lista de Dispositivos\n"    +
	                       "opcao:");
		int x=Ler.inteiro();
		int opcao;
		if (x==1) {
			
			System.out.println("Considerando /24");
			System.out.println("Dispositivos conectados:");
			if (hosts.size() != 0) {
				for(String host : hosts)
					System.out.println(host);
			}else System.out.println("Sem hosts Conectados");
		}else if (x==2) {
			do{
				System.out.println("1-Monitorar sua rede\n"+
				                   "2-Monitorar uma outra rede\n"+
				                   "Opcao:");
				opcao=Ler.inteiro();
				if(opcao==1){
					hosts = Monitorar.DispConectados(MONITOR.getIP());
					break;
				}
				else if (opcao==2){
					String ip = Ler.ip();
					hosts = Monitorar.DispConectados(ip);
					break;
				}else System.out.println("Digite uma opcao valida");
			}while(opcao !=1||opcao !=2);
			System.out.println("Considerando /24");
			System.out.println("Dispositivos conectados:");
			
			if (hosts.size() != 0) {
				for(String host : hosts)
					System.out.println(host);
			}else System.out.println("Sem hosts Conectados");
		
		}else {System.out.println("opcao invalida");}
	}
	
	
	/** Método que busca os objetos Computadores nos Clientes */
	private static void buscarInfo(){
		String ip = Ler.ip();
		try {
			Computador pc = Monitorar.infoUmDisp(ip);
			System.out.println(pc);
			if (!pcs.boleanPCporIp(pc.getIP())) 
				pcs.addComputador(pc);
			System.out.print("Deseja salvar no log [s/n] ? ");
			String resp = Ler.string();
			if (resp.equals("s")) {
				BufferedWriter  gravarArq = new BufferedWriter(new FileWriter(registro.getName(),true));
				gravarArq.newLine();
			    gravarArq.write(LocalDateTime.now()+"\n");
			    gravarArq.write("Dispositivo: "+pc.toString());
			    gravarArq.close();
			}
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	
	
	/** Método que lista Computadores na rede que não possuem aplicação */
	private static void listarIntrusos(ArrayList<String> hosts){
		try {
			for (String pc : hosts) {
				
				if (! pcs.boleanPCporIp(pc)) {
					System.out.println("Intruso: "+pc);
					intrusos.add(pc);
				}
			}
		}catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	
	/** Método Gera um Registro informações coletadas até o momento */
	private static void gerarLogDispositivos(Responsavel resp, ArrayList<String> intruso, ColecaoComputador laboratorio){
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(registro.getName(),true));
			w.newLine();
			w.write("Data e Hora:"+LocalDateTime.now().toString());
			w.newLine();
			w.write("Administrador: "+resp.getUsuario() + "-Matricula: " + resp.getMatricula());
			w.newLine();
			w.flush();
			w.close();
			Monitorar.salvaArquivo(registro.getName(), MONITOR.getIP(), intruso, laboratorio);
			System.out.println("Log gerado com sucesso.");
		} catch (Exception e) {
			System.out.println("Falha ao gerar o log.");
		}
	}
	
	
	/** Método captura portas do Cliente dado o IP */
	private static void capturarPortas(){
		String ip = Ler.ip();
		if ( Monitorar.capturaProcessos(ip, ip+".txt") ) 
			 System.out.println("Salvo no arquivo: " + ip+".txt");
		else System.out.println("Nao foi possivel alcancar o dispositivo.");
		
	}
	
	
	/** Método captura portas de Todos Clientes que possuem a Aplicação */
	private static void capturarPortasAll(ArrayList<String> hosts){
		if (pcs.size()!=0) {
			for (String ip : hosts) {
				if (pcs.boleanPCporIp(ip))
					 if ( Monitorar.capturaProcessos(ip, ip+".txt") )
					     	System.out.println("Salvo no arquivo: " + ip+".txt");
			}
		}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}

	}
	
	
	/** Método Captura tela do Cliente dado IP */
	private static void capturaTela(){
		String ip = Ler.ip();
			   if ( Monitorar.capturaTela(ip, ip+".png"))
				   System.out.println("Salvo no arquivo: " + ip+".png");
			   else System.out.println("Nao foi possivel alcancar o dispositivo.");
	}
	
	
	/** Método Captura tela de Todos Clientes que possuem a Aplicação */
	private static void capturaTelaAll(ArrayList<String> hosts){
		if (pcs.size()!=0) {
			for (String ip : hosts) {
				if (pcs.boleanPCporIp(ip))
			    	if (Monitorar.capturaTela(ip, ip+".png")) 
			    		System.out.println("Salvo no arquivo: " + ip+".png");
			}
		}else System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");
	}
	
	
	/** Método Desliga tela do Cliente dado IP */
	private static void desligarPC(){
		String ip = Ler.ip();
		if (Monitorar.desligarPIp(ip))
				System.out.print(ip+":Desligado\n");		
	}
	
	
	/** Método Desliga tela de Todos Clientes que possuem a Aplicação */
	private static void desligarAll(ArrayList<String> hosts){
		for(String ip : hosts) {
			if (pcs.size()!=0) {
				if (pcs.boleanPCporIp(ip))
					if (Monitorar.desligarPIp(ip))
						System.out.print(ip+":Desligado\n");		
			}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}
		}
	}
	
	
	/** Método Alertar Cliente dado IP */
	private static void AlertarPC(){
		String ip = Ler.ip();
		if (Monitorar.alertarPIp(ip))
				System.out.print(ip+" :Alertado\n");		
	}
	
	
	/** Método Alerta Todos os Clientes que possuem a Aplicação */
	private static void AlertarAll(ArrayList<String> hosts){
		for(String ip : hosts) {
			if (pcs.size()!=0) {
				if (pcs.boleanPCporIp(ip))
					if (Monitorar.alertarPIp(ip))
						System.out.print(ip+":Alertado\n");		
			}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}
		}
	}
	
	
	/** Método Informação de Clientes que possuem a Aplicação */
	private static void infoConectados(ArrayList<String> hosts) {
		for (String ip : hosts)
			try {
				if (! pcs.boleanPCporIp(ip))
					pcs.addComputador(Monitorar.infoUmDisp(ip));
			}catch (Exception ioe) {System.out.println(ioe.getMessage());}
		pcs.listarComputadores();
	}
	
	
	/** Método Login Responsável */
	private static Responsavel login(){
		System.out.print("Usuario: ");
		String user = Ler.string();
		System.out.print("Matricula: ");
		String id = Ler.string();
		System.out.print("Senha: ");
		String pass = Ler.string();
		return new Responsavel(user, id, pass);
	}
	
	
	/** Método Adiciona Responsável na Coleção */
	private static void addAdmin() {
		try {
			System.out.print("Usuario: ");
			String user = Ler.string();
			System.out.print("Matricula: ");
			String id = Ler.string();
			System.out.print("Senha: ");
			String pass = Ler.string();
			colResp.addResponsavel(new Responsavel(user,pass,id));
		}catch(Exception ioe) {System.out.println(ioe.getMessage());}
	}

	
	/** Método Deleta Responsável da Coleção */
	private static void delAdmin() {
		try {
			System.out.print("Matricula: ");
			String id = Ler.string();
			System.out.print("Senha: ");
			String pass = Ler.string();
		    colResp.removerAdmin(id,pass);
		    System.out.println("Usuario removido com sucesso");
		}catch(Exception ioe) {System.out.println(ioe.getMessage());}
	}
	

	/** Método Edita Responsável */
	private static void editAdmin() {
		try {
			System.out.print("Digite a matricula para verificacao: ");
			String matriculaOrig = Ler.string();
			System.out.print("Digite a senha para verificacao: ");
			String passOrig = Ler.string();
			if (colResp.verificar(matriculaOrig,passOrig)) {
				System.out.print("Digite novo Usuario: ");
				String user = Ler.string();
				System.out.print("Digite nova Matricula: ");
			    String matricula = Ler.string();
				System.out.print("Digite nova Senha: ");
				String pass = Ler.string();
				colResp.editar(matriculaOrig,passOrig,matricula,user,pass);
			}else System.out.println("Administrador invalido");
		
		}catch (Exception ioe) {System.out.println(ioe.getMessage());}
	}

	
	/** Método Listar todos os Responsáveis registrados */
	private static void listAdmin() {
		colResp.listar();
	}
}

=======
package Monitor;

import Comon.ColecaoResponsavel;
import Comon.ColecaoComputador;
import Comon.Computador;
import Comon.Responsavel;
import io.Ler;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Classe principal que roda no sistema monitor
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Main {
	
	/** Atributos */
	
	/** Arquivo de Registro */
	public static File registro = new File("registro.log");
	/** Coleção de Computadores (laborátorio) */
	public static ColecaoComputador  pcs  = new ColecaoComputador();
	/** Coleção de Responsáveis Autorizados a usar a Aplicação */
	public static ColecaoResponsavel colResp = new ColecaoResponsavel();
	/** Lista de todos os IPs conectados na rede */
	public static ArrayList<String> hosts = new ArrayList<String>();
	/** Lista de Computadores que não possui Aplicação Cliente */
	public static ArrayList<String> intrusos = new ArrayList<String>();
	/** Computador do monitor */
	public static Computador MONITOR = null;
	
	
	/** Método Principal do Monitor
	 * @param args da Main */
	public static void main(String[] args) {
		
		/** Carregamento dos Responsáveis com Autorização */
		colResp=Monitorar.lerAdminDoXML();
		
		/** Login do Responsável */
		Responsavel resp = login();
		
		/** Autenticação do Responsável
		 *  Caso não autorize, a aplicação se encerra */
		try {
			if (!colResp.verificarResp(resp)) {
				System.out.println("ADMIN invalido");
				return;
			}
		}catch(Exception e) {System.out.println("Erro ao carregar XML"); return;}
		
		/** Tentativa de Construir Computador do Monitor
		 *  caso nao consiga, é solicitado IP da rede
		 */
		try{
			String os = System.getProperty("os.name").toLowerCase();
			if (os.indexOf("win") >= 0)
				MONITOR = new Computador();
		} catch (Exception e) {
			System.out.print("Digite o IP da sua rede: ");
			MONITOR.setIp(Ler.ip());
		}
		
		/** @param opção que é lida nos menus */
		int opcao;
		
		/** Escolha da Rede para Monitorar */
		do{
			System.out.println("1-Monitorar sua rede\n"      +
			                   "2-Monitorar uma outra rede\n"+
			                   "Opcao:");
			opcao=Ler.inteiro();
			if(opcao==1){
				hosts = Monitorar.DispConectados(MONITOR.getIP());
				break;
			}
			else if (opcao==2){
				String ip = Ler.ip();
				hosts = Monitorar.DispConectados(ip);
				break;
			}else System.out.println("Digite uma opcao valida");
		}while(opcao !=1||opcao !=2);
		
		/** Menus de Monitoramento */
		while(true) {
			menu();
			opcao = Ler.inteiro();
			if (opcao == 1) {
				while(true){
					menuInfo();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { listarTudo(); 
					} else if (opcao == 2) { infoConectados(hosts);
					} else if (opcao == 3) { buscarInfo();
					} else if (opcao == 4) { listarIntrusos(hosts);
					} else if (opcao == 5) { gerarLogDispositivos(resp, intrusos, pcs);
					} else if (opcao == 6) { break;
					} else { System.out.println("Digite um Valor entre 1 e 5");
				    }
				}
			} else if (opcao == 2) {
				while(true){ 
					menuProcesso();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { capturarPortas();
					} else if (opcao == 2) { capturarPortasAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
				    }
				}
			} else if (opcao == 3) {
				while(true) {
					menuCaptura();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { capturaTela();
					} else if (opcao == 2) { capturaTelaAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }
			} else if (opcao == 4) {
				while(true) {
					menuDesligar();
					opcao = Ler.inteiro();
					if (opcao == 1) 	   { desligarPC();
					} else if (opcao == 2) { desligarAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }
			} else if (opcao == 5) {
				while(true) {
					menuAlertar();
					opcao = Ler.inteiro();
					if (opcao == 1) 	   { AlertarPC();
					} else if (opcao == 2) { AlertarAll(hosts);
					} else if (opcao == 3) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
			    }	
			}else if (opcao == 6) {
				while(true) {
					menuAdmin();
					opcao = Ler.inteiro();
					if		  (opcao == 1) { addAdmin();
					} else if (opcao == 2) { delAdmin();
					} else if (opcao == 3) { editAdmin();
					} else if (opcao == 4) { listAdmin();
					} else if (opcao == 5) { break;
					} else { System.out.println("Digite um Valor entre 1 e 3");
			        }
				}
			}else if (opcao == 7) { 
				Monitorar.salvaAdminEmXML(colResp);
				gerarLogDispositivos(resp, intrusos, pcs);
				break;
			} else { System.out.println("Digite um Valor entre 1 e 5"); }
		}
	}

	
	/** Menu principal */
	private static void menu(){
		System.out.println(	"\n---------MONITOR DE REDE-----------\n"						 +
							"1- Buscar Informacoes de Dispositivos Conectados\n"			 + // menuInfo()
							"2- Capturar tarefas ativas em Dispositivo Conectado\n"          + // menuProcesso()
							"3- Capturar tela de Dispositivos Conectados\n"					 + // menuCaptura()
							"4- Desligar Dispositivos Conectados\n"							 + // menuDesligar()
							"5- Alertar Dispositivo\n"                                       + // enviarMensagem()
							"6- Administrador\n"                                             + // menuAdmin()
				            "7- Encerrar programa");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para captura de Informações dos Computadores */
	private static void menuInfo() {
		System.out.println( "\n----------Menu Informacoes-----------\n"			+
	                        "1- Listar Dispositivos Conectados\n"               + // listarTudo()
							"2- Listar Informacoes de Dispositivos Conectados\n"+ // listarConectados()
							"3- Buscar Informacoes de um Dispositivo por IP\n"	+ // buscarInfo()
							"4- Listar IP de dispositivos intrusos\n"			+ // listarIntrusos()
							"5- Salvar log de informacoes em arquivo\n"			+ // gerarLogDispositivos()
							"6- Retornar ao menu principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para captura de Portas dos Computadores */
	private static void menuProcesso() {
		System.out.println(	"\n----------Menu Tarefas-----------\n"					    +
							"1- Capturar Tarefas de um Dispositivo IP\n"				+ // capturarPortas()
							"2- Capturar Tarefas de Todos os Dispositivos de uma rede\n"+ // capturarPortasAll()
							"3- Retornar ao menu principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu captura de Tela dos Computadores */
	private static void menuCaptura(){
		System.out.println(	"\n----------Menu Consultas-----------\n"				 +
							"1- Capturar tela de um único Dispositivo por IP\n"      + // capturaTela()
							"2- Capturar tela de Todos os Dispositivos de uma Rede\n"+ // capturaTelaAll()
							"3- Retornar ao menu Principal");						
		System.out.print("opcao: ");
	}
	
	/** Menu envio do Sinal de Desligamento */
	private static void menuDesligar(){
		System.out.println(	"\n----------Menu Desligar-----------\n"		 +
							"1- Desligar Dispositivo por IP\n"				 + // desligarPC()
							"2- Desligar todos os Dispositivos de uma Rede\n"+ // desligarAll()
							"3- Retornar ao menu Principal");
		System.out.print("opcao: ");
	}
	
	
	/** Menu para genrenciar Responsáveis */
	private static void menuAdmin(){
		System.out.println(	"\n----------Menu Administrador-----------\n" +
				            "1- Adicionar Administrador\n"				  + // adddUser()
			              	"2- Remover Administrador por matricula\n"    + // delUser()
				            "3- Editar Administrador por matricula\n"     + // editUser()
				            "4- Listar Administrador\n"                   + // listUser()    
			              	"5- Retornar ao menu Principal");
        System.out.print("opcao: ");
	}
	
	/** Menu envio do Sinal de Alertar */
	private static void menuAlertar(){
		System.out.println(	"\n----------Menu Alertar-----------\n"		    +
							"1- Alertar Dispositivo por IP\n"				+  // AlertarPC()
							"2- Alertar todos os Dispositivos de uma Rede\n"+  // AlertarAll()
							"3- Retornar ao menu Principal");
		System.out.print("opcao: ");
	}
	

	/** Método que lista todos os hosts da Rede */
	private static void listarTudo(){
		System.out.println("1-Exibir Dispositivos ja verificados\n" +
	                       "2-Atualizar Lista de Dispositivos\n"    +
	                       "opcao:");
		int x=Ler.inteiro();
		int opcao;
		if (x==1) {
			
			System.out.println("Considerando /24");
			System.out.println("Dispositivos conectados:");
			if (hosts.size() != 0) {
				for(String host : hosts)
					System.out.println(host);
			}else System.out.println("Sem hosts Conectados");
		}else if (x==2) {
			do{
				System.out.println("1-Monitorar sua rede\n"+
				                   "2-Monitorar uma outra rede\n"+
				                   "Opcao:");
				opcao=Ler.inteiro();
				if(opcao==1){
					hosts = Monitorar.DispConectados(MONITOR.getIP());
					break;
				}
				else if (opcao==2){
					String ip = Ler.ip();
					hosts = Monitorar.DispConectados(ip);
					break;
				}else System.out.println("Digite uma opcao valida");
			}while(opcao !=1||opcao !=2);
			System.out.println("Considerando /24");
			System.out.println("Dispositivos conectados:");
			
			if (hosts.size() != 0) {
				for(String host : hosts)
					System.out.println(host);
			}else System.out.println("Sem hosts Conectados");
		
		}else {System.out.println("opcao invalida");}
	}
	
	
	/** Método que busca os objetos Computadores nos Clientes */
	private static void buscarInfo(){
		String ip = Ler.ip();
		try {
			Computador pc = Monitorar.infoUmDisp(ip);
			System.out.println(pc);
			if (!pcs.boleanPCporIp(pc.getIP())) 
				pcs.addComputador(pc);
			System.out.print("Deseja salvar no log [s/n] ? ");
			String resp = Ler.string();
			if (resp.equals("s")) {
				BufferedWriter  gravarArq = new BufferedWriter(new FileWriter(registro.getName(),true));
				gravarArq.newLine();
			    gravarArq.write(LocalDateTime.now()+"\n");
			    gravarArq.write("Dispositivo: "+pc.toString());
			    gravarArq.close();
			}
		} catch (Exception e) { System.out.println(e.getMessage());}
	}
	
	
	/** Método que lista Computadores na rede que não possuem aplicação */
	private static void listarIntrusos(ArrayList<String> hosts){
		try {
			for (String pc : hosts) {
				
				if (! pcs.boleanPCporIp(pc)) {
					System.out.println("Intruso: "+pc);
					intrusos.add(pc);
				}
			}
		}catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	
	/** Método Gera um Registro informações coletadas até o momento */
	private static void gerarLogDispositivos(Responsavel resp, ArrayList<String> intruso, ColecaoComputador laboratorio){
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(registro.getName(),true));
			w.newLine();
			w.write("Data e Hora:"+LocalDateTime.now().toString());
			w.newLine();
			w.write("Administrador: "+resp.getUsuario() + "-Matricula: " + resp.getMatricula());
			w.newLine();
			w.flush();
			w.close();
			Monitorar.salvaArquivo(registro.getName(), MONITOR.getIP(), intruso, laboratorio);
			System.out.println("Log gerado com sucesso.");
		} catch (Exception e) {
			System.out.println("Falha ao gerar o log.");
		}
	}
	
	
	/** Método captura portas do Cliente dado o IP */
	private static void capturarPortas(){
		String ip = Ler.ip();
		if ( Monitorar.capturaProcessos(ip, ip+".txt") ) 
			 System.out.println("Salvo no arquivo: " + ip+".txt");
		else System.out.println("Nao foi possivel alcancar o dispositivo.");
		
	}
	
	
	/** Método captura portas de Todos Clientes que possuem a Aplicação */
	private static void capturarPortasAll(ArrayList<String> hosts){
		if (pcs.size()!=0) {
			for (String ip : hosts) {
				if (pcs.boleanPCporIp(ip))
					 if ( Monitorar.capturaProcessos(ip, ip+".txt") )
					     	System.out.println("Salvo no arquivo: " + ip+".txt");
			}
		}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}

	}
	
	
	/** Método Captura tela do Cliente dado IP */
	private static void capturaTela(){
		String ip = Ler.ip();
			   if ( Monitorar.capturaTela(ip, ip+".png"))
				   System.out.println("Salvo no arquivo: " + ip+".png");
			   else System.out.println("Nao foi possivel alcancar o dispositivo.");
	}
	
	
	/** Método Captura tela de Todos Clientes que possuem a Aplicação */
	private static void capturaTelaAll(ArrayList<String> hosts){
		if (pcs.size()!=0) {
			for (String ip : hosts) {
				if (pcs.boleanPCporIp(ip))
			    	if (Monitorar.capturaTela(ip, ip+".png")) 
			    		System.out.println("Salvo no arquivo: " + ip+".png");
			}
		}else System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");
	}
	
	
	/** Método Desliga tela do Cliente dado IP */
	private static void desligarPC(){
		String ip = Ler.ip();
		if (Monitorar.desligarPIp(ip))
				System.out.print(ip+":Desligado\n");		
	}
	
	
	/** Método Desliga tela de Todos Clientes que possuem a Aplicação */
	private static void desligarAll(ArrayList<String> hosts){
		for(String ip : hosts) {
			if (pcs.size()!=0) {
				if (pcs.boleanPCporIp(ip))
					if (Monitorar.desligarPIp(ip))
						System.out.print(ip+":Desligado\n");		
			}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}
		}
	}
	
	
	/** Método Alertar Cliente dado IP */
	private static void AlertarPC(){
		String ip = Ler.ip();
		if (Monitorar.alertarPIp(ip))
				System.out.print(ip+" :Alertado\n");		
	}
	
	
	/** Método Alerta Todos os Clientes que possuem a Aplicação */
	private static void AlertarAll(ArrayList<String> hosts){
		for(String ip : hosts) {
			if (pcs.size()!=0) {
				if (pcs.boleanPCporIp(ip))
					if (Monitorar.alertarPIp(ip))
						System.out.print(ip+":Alertado\n");		
			}else {System.out.println("Nao Existe Dispositivos clientes [Recomenda-se recapturar informacoes]");}
		}
	}
	
	
	/** Método Informação de Clientes que possuem a Aplicação */
	private static void infoConectados(ArrayList<String> hosts) {
		for (String ip : hosts)
			try {
				if (! pcs.boleanPCporIp(ip))
					pcs.addComputador(Monitorar.infoUmDisp(ip));
			}catch (Exception ioe) {System.out.println(ioe.getMessage());}
		pcs.listarComputadores();
	}
	
	
	/** Método Login Responsável */
	private static Responsavel login(){
		System.out.print("Usuario: ");
		String user = Ler.string();
		System.out.print("Matricula: ");
		String id = Ler.string();
		System.out.print("Senha: ");
		String pass = Ler.string();
		return new Responsavel(user, id, pass);
	}
	
	
	/** Método Adiciona Responsável na Coleção */
	private static void addAdmin() {
		try {
			System.out.print("Usuario: ");
			String user = Ler.string();
			System.out.print("Matricula: ");
			String id = Ler.string();
			System.out.print("Senha: ");
			String pass = Ler.string();
			colResp.addResponsavel(new Responsavel(user,pass,id));
		}catch(Exception ioe) {System.out.println(ioe.getMessage());}
	}

	
	/** Método Deleta Responsável da Coleção */
	private static void delAdmin() {
		try {
			System.out.print("Matricula: ");
			String id = Ler.string();
			System.out.print("Senha: ");
			String pass = Ler.string();
		    colResp.removerAdmin(id,pass);
		    System.out.println("Usuario removido com sucesso");
		}catch(Exception ioe) {System.out.println(ioe.getMessage());}
	}
	

	/** Método Edita Responsável */
	private static void editAdmin() {
		try {
			System.out.print("Digite a matricula para verificacao: ");
			String matriculaOrig = Ler.string();
			System.out.print("Digite a senha para verificacao: ");
			String passOrig = Ler.string();
			if (colResp.verificar(matriculaOrig,passOrig)) {
				System.out.print("Digite novo Usuario: ");
				String user = Ler.string();
				System.out.print("Digite nova Matricula: ");
			    String matricula = Ler.string();
				System.out.print("Digite nova Senha: ");
				String pass = Ler.string();
				colResp.editar(matriculaOrig,passOrig,matricula,user,pass);
			}else System.out.println("Administrador invalido");
		
		}catch (Exception ioe) {System.out.println(ioe.getMessage());}
	}

	
	/** Método Listar todos os Responsáveis registrados */
	private static void listAdmin() {
		colResp.listar();
	}
}

>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
