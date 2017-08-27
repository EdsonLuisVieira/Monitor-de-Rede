<<<<<<< HEAD
package Comon;

/**
 * Classe que define o Admnistrador da rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Admin extends Pessoa {

	/** Atributos */
	
	/** Lista de Responsáveis */
	ColecaoResponsavel administrador;
	/** Lista de Computadores */
	ColecaoComputador  computerLab;

	
	public Admin(String nome){
		super(nome, "admin");
		administrador = new ColecaoResponsavel();
		computerLab   = new ColecaoComputador();
	}
	
}
=======
package Comon;

/**
 * Classe que define o Admnistrador da rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Admin extends Pessoa {

	/** Atributos */
	
	/** Lista de Responsáveis */
	ColecaoResponsavel administrador;
	/** Lista de Computadores */
	ColecaoComputador  computerLab;

	
	public Admin(String nome){
		super(nome, "admin");
		administrador = new ColecaoResponsavel();
		computerLab   = new ColecaoComputador();
	}
	
}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
