<<<<<<< HEAD
package io;
import java.util.Scanner;

/**
 * Classe que auxilia leitura dos dados pelo Teclado
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Ler {
	/** Atributos */
	
	/**	Expressão Regular para IP */
	private static final String regexip = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$";
	/**	Expressão Regular para Máscara de Rede */
	private static final String regexmk = "^(3[01]|2[0-4]|1[0-9]|[0-9])$";
	/** Objeto para Leitura de Teclado */
	private static Scanner scan = new Scanner (System.in);
	
	
	/**
	  * Função que lê um inteiro com tratamento
	  * @return retorna o inteiro que foi lido.
	  */
	public static int inteiro() {
		while(!scan.hasNextInt()){
			scan.nextLine();
			System.out.println("Valor invalido, digite um valor inteiro");	
		}
		int i = scan.nextInt();
		scan.nextLine();
		return (i);
	}
	
	/**
	  * Função que lê uma string
	  * @return retorna a string que foi lida.
	  */
	public static String string(){
		return (scan.nextLine());
	}
	
	
	/**
	 *  Função que lê IP
	  * @return retorna o IP lido.
	 */
	public static String ip(){
		String ip;
		do {
			System.out.print("Digite o ip: ");
			ip = Ler.string();
		} while (! ip.matches(regexip) );
		return ip;
	}
	
	
	/**
	 *  Função que lê máscara da rede
	  * @return retorna a mascara lida.
	 */
	public static String mascara(){
		String mask;
		do {
			System.out.print("Digite a mascara da rede(CIDR): ");
			mask = Ler.string();
		} while (! mask.matches(regexmk) );
		return mask;
	}
=======
package io;
import java.util.Scanner;

/**
 * Classe que auxilia leitura dos dados pelo Teclado
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Ler {
	/** Atributos */
	
	/**	Expressão Regular para IP */
	private static final String regexip = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]|[0-9])$";
	/**	Expressão Regular para Máscara de Rede */
	private static final String regexmk = "^(3[01]|2[0-4]|1[0-9]|[0-9])$";
	/** Objeto para Leitura de Teclado */
	private static Scanner scan = new Scanner (System.in);
	
	
	/**
	  * Função que lê um inteiro com tratamento
	  * @return retorna o inteiro que foi lido.
	  */
	public static int inteiro() {
		while(!scan.hasNextInt()){
			scan.nextLine();
			System.out.println("Valor invalido, digite um valor inteiro");	
		}
		int i = scan.nextInt();
		scan.nextLine();
		return (i);
	}
	
	/**
	  * Função que lê uma string
	  * @return retorna a string que foi lida.
	  */
	public static String string(){
		return (scan.nextLine());
	}
	
	
	/**
	 *  Função que lê IP
	  * @return retorna o IP lido.
	 */
	public static String ip(){
		String ip;
		do {
			System.out.print("Digite o ip: ");
			ip = Ler.string();
		} while (! ip.matches(regexip) );
		return ip;
	}
	
	
	/**
	 *  Função que lê máscara da rede
	  * @return retorna a mascara lida.
	 */
	public static String mascara(){
		String mask;
		do {
			System.out.print("Digite a mascara da rede(CIDR): ");
			mask = Ler.string();
		} while (! mask.matches(regexmk) );
		return mask;
	}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
}