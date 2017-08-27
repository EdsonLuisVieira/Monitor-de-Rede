<<<<<<< HEAD
package Comon;
import java.util.ArrayList;

/**
 * Classe que manipula um conjunto de computadores conectados na rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class ColecaoComputador {
	
	/** Atributos */
	
	/** Lista de Computadores */
	ArrayList <Computador> listaComputador;
	
	
	/** Método Construtor */
	
	public ColecaoComputador() {
		listaComputador = new ArrayList<Computador>();
	}
	
	
	/** Método Adiciona Computador
	 * @param pc Computador
	 */
	public void addComputador(Computador pc) {
		listaComputador.add(pc);
	}
	
	
	/** Método Remover Computador por IP
	 * @param ip do Computador
	 * @throws Exception caso não consiga remover
	 */
	public void removerComputadorPip(String ip) throws Exception {
		try {
			listaComputador.remove(pesquisarComputadorPip(ip));
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/** Método Pesquisar Computador por IP
	 * @param ip do Computador
	 * @return Computador
	 * @throws Exception caso não exista computador com o IP informado
	 */
	public Computador pesquisarComputadorPip(String ip) throws Exception {
		for (Computador pc: listaComputador)
			if (pc.getIP().equals(ip))
				return pc;
		throw new Exception("Nao exite computador com este IP");
	}
	
	
	/** Método Pesquisar Computador por IP
	 * @param ip do Computador
	 * @return Verdadeiro ou Falso
	 */
	public boolean boleanPCporIp (String ip) {
	for (Computador pc: listaComputador)
		if (pc.getIP().equals(ip))
			return true;
	return false;
	}
	
	
	/** Método Listar Computadores imprime todos os Computadores da Coleção */
	public void listarComputadores(){
		for (Computador pc : listaComputador)
			System.out.println(pc);
	}
	
	
	/** Metodo Retornar Lista de Computadores 
	 * @return lista
	 */
	public ArrayList<String> retornarListaComputadores(){
		ArrayList<String> lista = new ArrayList<String>();
		for (Computador pc : listaComputador)
			lista.add(pc.toString());
		return lista;
	}
	
	
	/**
	 * Método que retorna o tamanho da Coleção de Computadores
	 * @return int
	 */
	public int size() {
		return listaComputador.size();
	}
}
=======
package Comon;
import java.util.ArrayList;

/**
 * Classe que manipula um conjunto de computadores conectados na rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class ColecaoComputador {
	
	/** Atributos */
	
	/** Lista de Computadores */
	ArrayList <Computador> listaComputador;
	
	
	/** Método Construtor */
	
	public ColecaoComputador() {
		listaComputador = new ArrayList<Computador>();
	}
	
	
	/** Método Adiciona Computador
	 * @param pc Computador
	 */
	public void addComputador(Computador pc) {
		listaComputador.add(pc);
	}
	
	
	/** Método Remover Computador por IP
	 * @param ip do Computador
	 * @throws Exception caso não consiga remover
	 */
	public void removerComputadorPip(String ip) throws Exception {
		try {
			listaComputador.remove(pesquisarComputadorPip(ip));
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/** Método Pesquisar Computador por IP
	 * @param ip do Computador
	 * @return Computador
	 * @throws Exception caso não exista computador com o IP informado
	 */
	public Computador pesquisarComputadorPip(String ip) throws Exception {
		for (Computador pc: listaComputador)
			if (pc.getIP().equals(ip))
				return pc;
		throw new Exception("Nao exite computador com este IP");
	}
	
	
	/** Método Pesquisar Computador por IP
	 * @param ip do Computador
	 * @return Verdadeiro ou Falso
	 */
	public boolean boleanPCporIp (String ip) {
	for (Computador pc: listaComputador)
		if (pc.getIP().equals(ip))
			return true;
	return false;
	}
	
	
	/** Método Listar Computadores imprime todos os Computadores da Coleção */
	public void listarComputadores(){
		for (Computador pc : listaComputador)
			System.out.println(pc);
	}
	
	
	/** Metodo Retornar Lista de Computadores 
	 * @return lista
	 */
	public ArrayList<String> retornarListaComputadores(){
		ArrayList<String> lista = new ArrayList<String>();
		for (Computador pc : listaComputador)
			lista.add(pc.toString());
		return lista;
	}
	
	
	/**
	 * Método que retorna o tamanho da Coleção de Computadores
	 * @return int
	 */
	public int size() {
		return listaComputador.size();
	}
}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
