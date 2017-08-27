<<<<<<< HEAD
package Monitor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import Comon.ColecaoComputador;
import Comon.ColecaoResponsavel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import Comon.Computador;


/**
 * Classe auxiliar que roda no sistema monitor
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Monitorar {
	
	/** Atributos */
	
	/** Porta que o Cliente fica esperando informações */
	public static int porta = 8888;
	/** Coleção de Computadores da Rede */
	private static ColecaoComputador laboratorio;
	
	
	/** Varre a rede (/24) tentando pingar em todos os IPs
	 * @param rede do Monitor
	 * @return Lista (String) de IPs alcançados
	 */
	public static ArrayList<String> DispConectados(String rede) {
		ArrayList<String> laboratorio = new ArrayList<String>();
		int i;
		String campo[] = rede.split("\\.");
		String host = campo[0] + "." + campo[1] + "." + campo[2] + ".";
		System.out.printf("Carregando[");
		try {
			for (i=0;i<250;i++){
				//System.out.printf("Carregando...%3.0f %%\r", i/2.55);
				System.out.printf(".");
				if (InetAddress.getByName(host+i).isReachable(200))
					laboratorio.add(host+i);
			}
		} catch (Exception e) {System.err.println(e);};
		System.out.printf("]");
		return laboratorio ;
	}

	
	/** Dada a lista de IPs alcançados, tenta pegar informações dos dispositivos
	 * @param rede do Monitor
	 * @param hosts da Rede
	 * @return ColecaoComputador contendo informações de uma Lista de IPs
	 */
	public static ColecaoComputador infoDispConectados(String rede, ArrayList<String> hosts) {
		try {
			for (String host : hosts)
				try {
					Socket sock = new Socket(host,porta);
					enviaOpcao(sock, "1");
					ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
		            Computador pc = (Computador) is.readObject();
					laboratorio.addComputador(pc);
		            sock.close();
				} catch (Exception e) {
					System.out.println("Intruso: " + host);
				}
		} catch (Exception e) {System.err.println(e);};
		return laboratorio ;
	}
	
	
	/** Busca informações do dispostivo dado o IP
	 * @param ip do Dispostivo
	 * @return Computador contendo informações do dispositivo
	 * @throws Exception Caso o dispositivo não tenha uma aplicação cliente ou não esteja alcançável
	 */
	public static Computador infoUmDisp(String ip) throws Exception {
		Computador pc;
		ObjectInputStream in = null;
		if (InetAddress.getByName(ip).isReachable(500)) {
			try {
				Socket sock = new Socket(ip,porta);
				enviaOpcao(sock, "1");
				in = new ObjectInputStream(sock.getInputStream());
	            pc = (Computador) in.readObject();
				sock.close();
				return pc;
			} catch (Exception e) { throw new Exception ("Dispositivo intruso IP:"+ip); }
		}
		throw new Exception ("Não foi possível alcançar o dispositivo.");
	}
	
	
	/** Dada uma lista de IPs e uma rede, verifica quais dispositivos não tem aplicacao cliente ativa 
	 * @param rede a ser Monitorada
	 * @param hosts da rede
	 * @return Lista (String) de IPs de dispostivos que não tem aplicação cliente ativa
	 */
	public static ArrayList<String> intruso(String rede, ArrayList<String> hosts) {
		ArrayList<String> intruso = new ArrayList <String>();
		System.out.println("Carregando...");
		for (String ip : hosts)
			try {
				infoUmDisp(ip);
			} catch (Exception e) {
				intruso.add(ip);
			}
		return intruso;
	}
	
	
	/** Salva os dados passados num arquivo
	 * @param caminho do arquivo
	 * @param rede monitorada
	 * @param intruso registrados na rede
	 * @param laboratorio ColecaoComputador
	 */
	public static void salvaArquivo(String caminho, String rede, ArrayList<String> intruso, ColecaoComputador laboratorio) {
		try {
			BufferedWriter fr = new BufferedWriter(new FileWriter(caminho,true));
			for (String disp : intruso) {
			   fr.write("Intruso: "+disp.toString());
		       fr.newLine();
			}
			fr.write("Conectados: \n"+laboratorio.retornarListaComputadores());
		    fr.newLine();
			fr.flush();
			fr.close();
		}catch (Exception e) {};
	}
	
	
	/** Captura a tela do dispositivo com IP no arquivo
	 * @param ip do Dispositivo
	 * @param arquivo que será salva a Imagem
	 * @return Verdadeiro caso consiga capturar salvar a imagem, Falso caso contrário
	 */
	public static boolean capturaTela(String ip, String arquivo) {
		try {  
			int filesize=6022386;
			int bytesRead;
			int current = 0;
			Socket sock = new Socket(ip,porta);
			enviaOpcao(sock, "2");
           	byte [] mybytearray  = new byte [filesize];
           	InputStream is = sock.getInputStream();
           	FileOutputStream fos = new FileOutputStream(arquivo+".png");
           	BufferedOutputStream bos = new BufferedOutputStream(fos);
           	bytesRead = is.read(mybytearray,0,mybytearray.length);
           	current = bytesRead;
           	do {
           		bytesRead =is.read(mybytearray, current, (mybytearray.length-current));
           		if(bytesRead >= 0) current += bytesRead;
           	} while(bytesRead > -1);
           	bos.write(mybytearray, 0 , current);
           	bos.close();
           	sock.close();
           	return true;
		}catch (Exception e){return false;}
		
	}
	
	
	/** Solicita as portas abertas no dispositivo com IP no arquivo
	 * @param ip do Dispositivo
	 * @param arquivo que será salvo o log das portas
	 * @return Verdadeiro caso consiga salvar os dados, falso caso contrário
	 */
	public static boolean capturaProcessos(String ip, String arquivo) {
		try {  
			int filesize=6022386;
			int bytesRead;
			int current = 0;
			Socket sock = new Socket(ip,porta);
			enviaOpcao(sock, "3");
           	byte [] mybytearray  = new byte [filesize];
           	InputStream is = sock.getInputStream();
           	FileOutputStream fos = new FileOutputStream(arquivo+".txt");
           	BufferedOutputStream bos = new BufferedOutputStream(fos);
           	System.out.println("read");
           	bytesRead = is.read(mybytearray,0,mybytearray.length);
           	current = bytesRead;
           	do {
           		bytesRead =is.read(mybytearray, current, (mybytearray.length-current));
           		if(bytesRead >= 0) current += bytesRead;
           	} while(bytesRead > -1);
           	System.out.println("write");
           	bos.write(mybytearray, 0 , current);
           	bos.close();
           	is.close();
           	sock.close();
           	return true;
		}catch (Exception e){ System.err.println(e.getMessage());return false; }
	}

	
	/** Salva a ColecaoComputador laboratório em XML
	 * @param laboratorio ColecaoComputador
	 */
	public void salvaEmXML(ColecaoComputador laboratorio){
		XStream xStream = new XStream(new StaxDriver());
        xStream.alias("computador", Computador.class);
        xStream.alias("laboratorio", List.class);
        File arquivo = new File("pcs.xml");
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(laboratorio).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
	} 
	
	
	/** Recupera uma ColecaoComputador de um XML
	 * @param arquivo que será lido o XML
	 * @return ColecaoComputador que estava salva no XML
	 */
	public static ColecaoComputador lerDoXML(String arquivo){
		try {
			ColecaoComputador laboratorio1;
			XStream xStream = new XStream(new StaxDriver());
			//Questões de segurança
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("computador", Computador.class);
			xStream.alias("laboratorio", List.class);
			xStream.processAnnotations(Computador.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(arquivo)); //"pcs.xml"
		    laboratorio1 = (ColecaoComputador) xStream.fromXML(input);
			input.close();
			return laboratorio1;
			
		} catch (Exception e) {e.printStackTrace();};
		return null;
	}
	
	
	/** Método Gravar Responsáveis no XML
	 * @param admin ColecaoResponsavel
	 */
	public static void salvaAdminEmXML(ColecaoResponsavel admin){
		XStream xStream = new XStream(new StaxDriver());
        xStream.alias("Responsavel", Computador.class);
        xStream.alias("ColecaoResponsavel", List.class);
        File arquivo = new File("admin.xml");
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(admin).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
	}
	
	
	/** Método Ler Responsáveis no XML
	 * @return ColecaoResponsavel lida do XML
	 */
	public static ColecaoResponsavel lerAdminDoXML () {
		try {
			ColecaoResponsavel admin;
			XStream xStream = new XStream(new StaxDriver());
			//Questões de segurança
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("Responsavel", Computador.class);
			xStream.alias("ColecaoResponsavel", List.class);
			xStream.processAnnotations(Computador.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream("admin.xml"));
			admin = (ColecaoResponsavel) xStream.fromXML(input);
			input.close();
			return admin;
		
		}catch (Exception e) {e.getMessage();};
		return null;	
	}
	
	
	/** Envia sinal de desligamento para o dispositivo com IP
	 * @param ip do Dispositivo
	 * @return Verdadeiro caso consiga enviar o sinal, Falso caso contrário
	 */
	public static boolean desligarPIp(String ip) {
	   try {
		   Socket sock = new Socket(ip,porta);
		   enviaOpcao(sock, "4");
		   sock.close();
		   return true;
	   } catch (Exception ioe) {System.out.println(ioe.getMessage());};
	   return false;
	}
   
	
	/** Envia sinal de Alerta para o dispositivo com IP
	 * @param ip do Dispositivo
	 * @return Verdadeiro caso consiga enviar o sinal, Falso caso contrario
	 */
	public static boolean alertarPIp(String ip) {
	   try {
		   Socket sock = new Socket(ip,porta);
		   enviaOpcao(sock, "5");
		   sock.close();
		   return true;
	   } catch (Exception ioe) {System.out.println(ioe.getMessage());};
	   return false;
	}
   
	
	/** Método Envia a opção via Socket para o Cliente
	 * @param sock Socket que será conectador
	 * @param op Opção que será enviada
	 * @throws Exception caso não consiga enviar a opção
	 */
	public static void enviaOpcao(Socket sock, String op) throws Exception {	   
		PrintWriter pout= new PrintWriter (sock.getOutputStream(),true);
		pout.println(op);
	}
	
=======
package Monitor;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import Comon.ColecaoComputador;
import Comon.ColecaoResponsavel;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import Comon.Computador;


/**
 * Classe auxiliar que roda no sistema monitor
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Monitorar {
	
	/** Atributos */
	
	/** Porta que o Cliente fica esperando informações */
	public static int porta = 8888;
	/** Coleção de Computadores da Rede */
	private static ColecaoComputador laboratorio;
	
	
	/** Varre a rede (/24) tentando pingar em todos os IPs
	 * @param rede do Monitor
	 * @return Lista (String) de IPs alcançados
	 */
	public static ArrayList<String> DispConectados(String rede) {
		ArrayList<String> laboratorio = new ArrayList<String>();
		int i;
		String campo[] = rede.split("\\.");
		String host = campo[0] + "." + campo[1] + "." + campo[2] + ".";
		System.out.printf("Carregando[");
		try {
			for (i=0;i<250;i++){
				//System.out.printf("Carregando...%3.0f %%\r", i/2.55);
				System.out.printf(".");
				if (InetAddress.getByName(host+i).isReachable(200))
					laboratorio.add(host+i);
			}
		} catch (Exception e) {System.err.println(e);};
		System.out.printf("]");
		return laboratorio ;
	}

	
	/** Dada a lista de IPs alcançados, tenta pegar informações dos dispositivos
	 * @param rede do Monitor
	 * @param hosts da Rede
	 * @return ColecaoComputador contendo informações de uma Lista de IPs
	 */
	public static ColecaoComputador infoDispConectados(String rede, ArrayList<String> hosts) {
		try {
			for (String host : hosts)
				try {
					Socket sock = new Socket(host,porta);
					enviaOpcao(sock, "1");
					ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
		            Computador pc = (Computador) is.readObject();
					laboratorio.addComputador(pc);
		            sock.close();
				} catch (Exception e) {
					System.out.println("Intruso: " + host);
				}
		} catch (Exception e) {System.err.println(e);};
		return laboratorio ;
	}
	
	
	/** Busca informações do dispostivo dado o IP
	 * @param ip do Dispostivo
	 * @return Computador contendo informações do dispositivo
	 * @throws Exception Caso o dispositivo não tenha uma aplicação cliente ou não esteja alcançável
	 */
	public static Computador infoUmDisp(String ip) throws Exception {
		Computador pc;
		ObjectInputStream in = null;
		if (InetAddress.getByName(ip).isReachable(500)) {
			try {
				Socket sock = new Socket(ip,porta);
				enviaOpcao(sock, "1");
				in = new ObjectInputStream(sock.getInputStream());
	            pc = (Computador) in.readObject();
				sock.close();
				return pc;
			} catch (Exception e) { throw new Exception ("Dispositivo intruso IP:"+ip); }
		}
		throw new Exception ("Não foi possível alcançar o dispositivo.");
	}
	
	
	/** Dada uma lista de IPs e uma rede, verifica quais dispositivos não tem aplicacao cliente ativa 
	 * @param rede a ser Monitorada
	 * @param hosts da rede
	 * @return Lista (String) de IPs de dispostivos que não tem aplicação cliente ativa
	 */
	public static ArrayList<String> intruso(String rede, ArrayList<String> hosts) {
		ArrayList<String> intruso = new ArrayList <String>();
		System.out.println("Carregando...");
		for (String ip : hosts)
			try {
				infoUmDisp(ip);
			} catch (Exception e) {
				intruso.add(ip);
			}
		return intruso;
	}
	
	
	/** Salva os dados passados num arquivo
	 * @param caminho do arquivo
	 * @param rede monitorada
	 * @param intruso registrados na rede
	 * @param laboratorio ColecaoComputador
	 */
	public static void salvaArquivo(String caminho, String rede, ArrayList<String> intruso, ColecaoComputador laboratorio) {
		try {
			BufferedWriter fr = new BufferedWriter(new FileWriter(caminho,true));
			for (String disp : intruso) {
			   fr.write("Intruso: "+disp.toString());
		       fr.newLine();
			}
			fr.write("Conectados: \n"+laboratorio.retornarListaComputadores());
		    fr.newLine();
			fr.flush();
			fr.close();
		}catch (Exception e) {};
	}
	
	
	/** Captura a tela do dispositivo com IP no arquivo
	 * @param ip do Dispositivo
	 * @param arquivo que será salva a Imagem
	 * @return Verdadeiro caso consiga capturar salvar a imagem, Falso caso contrário
	 */
	public static boolean capturaTela(String ip, String arquivo) {
		try {  
			int filesize=6022386;
			int bytesRead;
			int current = 0;
			Socket sock = new Socket(ip,porta);
			enviaOpcao(sock, "2");
           	byte [] mybytearray  = new byte [filesize];
           	InputStream is = sock.getInputStream();
           	FileOutputStream fos = new FileOutputStream(arquivo+".png");
           	BufferedOutputStream bos = new BufferedOutputStream(fos);
           	bytesRead = is.read(mybytearray,0,mybytearray.length);
           	current = bytesRead;
           	do {
           		bytesRead =is.read(mybytearray, current, (mybytearray.length-current));
           		if(bytesRead >= 0) current += bytesRead;
           	} while(bytesRead > -1);
           	bos.write(mybytearray, 0 , current);
           	bos.close();
           	sock.close();
           	return true;
		}catch (Exception e){return false;}
		
	}
	
	
	/** Solicita as portas abertas no dispositivo com IP no arquivo
	 * @param ip do Dispositivo
	 * @param arquivo que será salvo o log das portas
	 * @return Verdadeiro caso consiga salvar os dados, falso caso contrário
	 */
	public static boolean capturaProcessos(String ip, String arquivo) {
		try {  
			int filesize=6022386;
			int bytesRead;
			int current = 0;
			Socket sock = new Socket(ip,porta);
			enviaOpcao(sock, "3");
           	byte [] mybytearray  = new byte [filesize];
           	InputStream is = sock.getInputStream();
           	FileOutputStream fos = new FileOutputStream(arquivo+".txt");
           	BufferedOutputStream bos = new BufferedOutputStream(fos);
           	System.out.println("read");
           	bytesRead = is.read(mybytearray,0,mybytearray.length);
           	current = bytesRead;
           	do {
           		bytesRead =is.read(mybytearray, current, (mybytearray.length-current));
           		if(bytesRead >= 0) current += bytesRead;
           	} while(bytesRead > -1);
           	System.out.println("write");
           	bos.write(mybytearray, 0 , current);
           	bos.close();
           	is.close();
           	sock.close();
           	return true;
		}catch (Exception e){ System.err.println(e.getMessage());return false; }
	}

	
	/** Salva a ColecaoComputador laboratório em XML
	 * @param laboratorio ColecaoComputador
	 */
	public void salvaEmXML(ColecaoComputador laboratorio){
		XStream xStream = new XStream(new StaxDriver());
        xStream.alias("computador", Computador.class);
        xStream.alias("laboratorio", List.class);
        File arquivo = new File("pcs.xml");
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(laboratorio).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
	} 
	
	
	/** Recupera uma ColecaoComputador de um XML
	 * @param arquivo que será lido o XML
	 * @return ColecaoComputador que estava salva no XML
	 */
	public static ColecaoComputador lerDoXML(String arquivo){
		try {
			ColecaoComputador laboratorio1;
			XStream xStream = new XStream(new StaxDriver());
			//Questões de segurança
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("computador", Computador.class);
			xStream.alias("laboratorio", List.class);
			xStream.processAnnotations(Computador.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream(arquivo)); //"pcs.xml"
		    laboratorio1 = (ColecaoComputador) xStream.fromXML(input);
			input.close();
			return laboratorio1;
			
		} catch (Exception e) {e.printStackTrace();};
		return null;
	}
	
	
	/** Método Gravar Responsáveis no XML
	 * @param admin ColecaoResponsavel
	 */
	public static void salvaAdminEmXML(ColecaoResponsavel admin){
		XStream xStream = new XStream(new StaxDriver());
        xStream.alias("Responsavel", Computador.class);
        xStream.alias("ColecaoResponsavel", List.class);
        File arquivo = new File("admin.xml");
        FileOutputStream gravar;
        try {
            gravar = new FileOutputStream(arquivo);
            gravar.write(xStream.toXML(admin).getBytes());
            gravar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
	}
	
	
	/** Método Ler Responsáveis no XML
	 * @return ColecaoResponsavel lida do XML
	 */
	public static ColecaoResponsavel lerAdminDoXML () {
		try {
			ColecaoResponsavel admin;
			XStream xStream = new XStream(new StaxDriver());
			//Questões de segurança
			XStream.setupDefaultSecurity(xStream);
			xStream.addPermission(AnyTypePermission.ANY); 
			xStream.alias("Responsavel", Computador.class);
			xStream.alias("ColecaoResponsavel", List.class);
			xStream.processAnnotations(Computador.class);
			BufferedInputStream input = new BufferedInputStream(new FileInputStream("admin.xml"));
			admin = (ColecaoResponsavel) xStream.fromXML(input);
			input.close();
			return admin;
		
		}catch (Exception e) {e.getMessage();};
		return null;	
	}
	
	
	/** Envia sinal de desligamento para o dispositivo com IP
	 * @param ip do Dispositivo
	 * @return Verdadeiro caso consiga enviar o sinal, Falso caso contrário
	 */
	public static boolean desligarPIp(String ip) {
	   try {
		   Socket sock = new Socket(ip,porta);
		   enviaOpcao(sock, "4");
		   sock.close();
		   return true;
	   } catch (Exception ioe) {System.out.println(ioe.getMessage());};
	   return false;
	}
   
	
	/** Envia sinal de Alerta para o dispositivo com IP
	 * @param ip do Dispositivo
	 * @return Verdadeiro caso consiga enviar o sinal, Falso caso contrario
	 */
	public static boolean alertarPIp(String ip) {
	   try {
		   Socket sock = new Socket(ip,porta);
		   enviaOpcao(sock, "5");
		   sock.close();
		   return true;
	   } catch (Exception ioe) {System.out.println(ioe.getMessage());};
	   return false;
	}
   
	
	/** Método Envia a opção via Socket para o Cliente
	 * @param sock Socket que será conectador
	 * @param op Opção que será enviada
	 * @throws Exception caso não consiga enviar a opção
	 */
	public static void enviaOpcao(Socket sock, String op) throws Exception {	   
		PrintWriter pout= new PrintWriter (sock.getOutputStream(),true);
		pout.println(op);
	}
	
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
}