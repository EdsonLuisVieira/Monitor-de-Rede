<<<<<<< HEAD
package clienteproject;
import Comon.Computador;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Classe que possui informações e comportamentos do Monitorado
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Monitorado {
	
	/** Atributos */
	
	/** Sistema Operacional do Monitorado */
	private static String OS = System.getProperty("os.name").toLowerCase();
	/** ServerSocket do Monitorado */
	private static ServerSocket servsock;
	/** Porta que o ServerSocket ficará aguardando solicitações */
	private static int porta = 8888;
	/** Computador do Monitorado */
	private static Computador pc;
	
	
	/** Método Principal do Monitorado
	 * 	Contém apenas o menu
	 * @param args da Main
	 * @throws Exception caso não consiga abrir a porta
	 */
	public static void main(String[] args) throws Exception {
		
		servsock = new ServerSocket(porta);
		
		while(true){
			Socket sock = servsock.accept();
			System.out.println("conectado");
			try {
				InputStream in = sock.getInputStream();
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				String controle="";
				controle= bin.readLine();
				System.out.println(controle);
				
				if      (controle.equals("1")) { infoDisp(sock);     }
				else if (controle.equals("2")) { printDisp(sock);    }	
				else if (controle.equals("3")) { cmdDisp(sock);      }	
				else if (controle.equals("4")) { DesligarDisp(sock); }
				else if (controle.equals("5")) { alertar(sock);      }
              
			} catch (Exception e) {System.out.println(e.getMessage());}
		}
	}
		

	/** Método Informa Dispositivo envia o Computador Monitorado para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void infoDisp(Socket sock) {			
		try {
			if (isWindows())
				pc = new Computador();
			else
				pc = new Computador(sock.getLocalAddress());
		} catch (Exception e) {e.printStackTrace();}
		try {
			System.out.println("infoDisp");
			System.out.print(pc);
			ObjectOutputStream saida = new ObjectOutputStream(sock.getOutputStream());
			saida.writeObject(pc);
			saida.flush();
			System.out.println("Enviando...");
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	
	/** Método Print Dispositivo envia a imagem atual da tela para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void printDisp(Socket sock) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot;
		try {
			System.out.println("PrintScreen");
			robot = new Robot();
			File myFile = new File ("log.txt");
			BufferedImage bi = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height)); 
			ImageIO.write(bi, "png", myFile);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			OutputStream os = sock.getOutputStream();
			System.out.println("Enviando...");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			bis.close();
			sock.close();
			myFile.delete();
			System.out.println("concluido...");
	
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	
	/** Método Cmd Dispositivo envia informações de processos ativos para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void cmdDisp(Socket sock) {
		String comand="";
		File myFile = new File("info.txt");
		
		try	{
			myFile.createNewFile();
			System.out.println("3");
			if (isWindows()) {
				comand="cmd /c tasklist > info.txt";
				Runtime.getRuntime().exec(comand);
			} else if (isMac()||isUnix()) 
				Runtime.getRuntime().exec("/bin/bash comand");
			Thread.sleep(1000);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			OutputStream os = sock.getOutputStream();
			System.out.println("Enviando...");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			bis.close();
			fis.close();
			sock.close();
			myFile.delete();
			System.out.println("concluido...");
			
	    } catch (Exception e) {System.out.println(e.getMessage());}
	}

	
	/** Método Desligar Dispositivo recebe sinal de desligamento do Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void DesligarDisp(Socket sock) {
		JOptionPane.showMessageDialog(null,"O Computador Será Desligado!!!","",JOptionPane.ERROR_MESSAGE);
		String command = "";
		System.out.println("Desliga");
		if (isWindows())
    		command = "cmd /c shutdown /f /p";
    	else if (isMac())
    		command = "shutdown -h +0";
    	else if (isUnix())
    		command = "poweroff";
		try {
			Runtime.getRuntime().exec (command);
			sock.close();
			System.out.println("concluido...");

		} catch (Exception e) { System.out.println(e.getMessage()); }
	}		
	
	
	/** Método Alertar Dispositivo recebe um Alerta do Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void alertar(Socket sock) {
		try {
			sock.close();
			JOptionPane.showMessageDialog(null,"Você está sendo Monitorado - CUIDADO!!!","",JOptionPane.ERROR_MESSAGE);
			System.out.println("concluido...");
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}		

	/** Verifica se o Sistema Operacional do Monitorado e Windows
	 * @return Verdadeiro caso seja Windows, Falso caso contrário
	 */
	public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }		
	

	/** Verifica se o Sistema Operacional do Monitorado e Mac
	 * @return Verdadeiro caso seja Mac, Falso caso contrário
	 */
	public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }		
	

	/** Verifica se o Sistema Operacional do Monitorado e Linux
	 * @return Verdadeiro caso seja Linux, Falso caso contrário
	 */
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
}
=======
package clienteproject;
import Comon.Computador;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Classe que possui informações e comportamentos do Monitorado
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Monitorado {
	
	/** Atributos */
	
	/** Sistema Operacional do Monitorado */
	private static String OS = System.getProperty("os.name").toLowerCase();
	/** ServerSocket do Monitorado */
	private static ServerSocket servsock;
	/** Porta que o ServerSocket ficará aguardando solicitações */
	private static int porta = 8888;
	/** Computador do Monitorado */
	private static Computador pc;
	
	
	/** Método Principal do Monitorado
	 * 	Contém apenas o menu
	 * @param args da Main
	 * @throws Exception caso não consiga abrir a porta
	 */
	public static void main(String[] args) throws Exception {
		
		servsock = new ServerSocket(porta);
		
		while(true){
			Socket sock = servsock.accept();
			System.out.println("conectado");
			try {
				InputStream in = sock.getInputStream();
				BufferedReader bin = new BufferedReader(new InputStreamReader(in));
				String controle="";
				controle= bin.readLine();
				System.out.println(controle);
				
				if      (controle.equals("1")) { infoDisp(sock);     }
				else if (controle.equals("2")) { printDisp(sock);    }	
				else if (controle.equals("3")) { cmdDisp(sock);      }	
				else if (controle.equals("4")) { DesligarDisp(sock); }
				else if (controle.equals("5")) { alertar(sock);      }
              
			} catch (Exception e) {System.out.println(e.getMessage());}
		}
	}
		

	/** Método Informa Dispositivo envia o Computador Monitorado para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void infoDisp(Socket sock) {			
		try {
			if (isWindows())
				pc = new Computador();
			else
				pc = new Computador(sock.getLocalAddress());
		} catch (Exception e) {e.printStackTrace();}
		try {
			System.out.println("infoDisp");
			System.out.print(pc);
			ObjectOutputStream saida = new ObjectOutputStream(sock.getOutputStream());
			saida.writeObject(pc);
			saida.flush();
			System.out.println("Enviando...");
			
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	
	/** Método Print Dispositivo envia a imagem atual da tela para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void printDisp(Socket sock) {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Robot robot;
		try {
			System.out.println("PrintScreen");
			robot = new Robot();
			File myFile = new File ("log.txt");
			BufferedImage bi = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height)); 
			ImageIO.write(bi, "png", myFile);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			OutputStream os = sock.getOutputStream();
			System.out.println("Enviando...");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			bis.close();
			sock.close();
			myFile.delete();
			System.out.println("concluido...");
	
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}
	
	
	/** Método Cmd Dispositivo envia informações de portas abertas na interface de rede para o Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void cmdDisp(Socket sock) {
		String comand="";
		File myFile = new File("info.txt");
		
		try	{
			myFile.createNewFile();
			System.out.println("3");
			if (isWindows()) {
				comand="cmd /c tasklist > info.txt";
				Runtime.getRuntime().exec(comand);
			} else if (isMac()||isUnix()) 
				Runtime.getRuntime().exec("/bin/bash comand");
			Thread.sleep(1000);
			byte [] mybytearray  = new byte [(int)myFile.length()];
			FileInputStream fis = new FileInputStream(myFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(mybytearray,0,mybytearray.length);
			OutputStream os = sock.getOutputStream();
			System.out.println("Enviando...");
			os.write(mybytearray,0,mybytearray.length);
			os.flush();
			bis.close();
			fis.close();
			sock.close();
			myFile.delete();
			System.out.println("concluido...");
			
	    } catch (Exception e) {System.out.println(e.getMessage());}
	}

	
	/** Método Desligar Dispositivo recebe sinal de desligamento do Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void DesligarDisp(Socket sock) {
		JOptionPane.showMessageDialog(null,"O Computador Será Desligado!!!","",JOptionPane.ERROR_MESSAGE);
		String command = "";
		System.out.println("Desliga");
		if (isWindows())
    		command = "cmd /c shutdown /f /p";
    	else if (isMac())
    		command = "shutdown -h +0";
    	else if (isUnix())
    		command = "poweroff";
		try {
			Runtime.getRuntime().exec (command);
			sock.close();
			System.out.println("concluido...");

		} catch (Exception e) { System.out.println(e.getMessage()); }
	}		
	
	
	/** Método Alertar Dispositivo recebe um Alerta do Monitor
	 * @param sock Socket que vai se conectar
	 */
	private static void alertar(Socket sock) {
		try {
			sock.close();
			JOptionPane.showMessageDialog(null,"Você está sendo Monitorado - CUIDADO!!!","",JOptionPane.ERROR_MESSAGE);
			System.out.println("concluido...");
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}		

	/** Verifica se o Sistema Operacional do Monitorado e Windows
	 * @return Verdadeiro caso seja Windows, Falso caso contrário
	 */
	public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }		
	

	/** Verifica se o Sistema Operacional do Monitorado e Mac
	 * @return Verdadeiro caso seja Mac, Falso caso contrário
	 */
	public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }		
	

	/** Verifica se o Sistema Operacional do Monitorado e Linux
	 * @return Verdadeiro caso seja Linux, Falso caso contrário
	 */
    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }
}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
