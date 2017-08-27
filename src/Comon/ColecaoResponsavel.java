<<<<<<< HEAD
package Comon;
import java.util.ArrayList;

/**
 * Classe que manipula um conjunto de responsáveis por monitorar a rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class ColecaoResponsavel {
	
	/** Atributos */
	
	/** Lista de Responsáveis */
	ArrayList <Responsavel> listaResponsavel ;
	
	
	/** Método Construtor */
	
	public ColecaoResponsavel(){
		listaResponsavel = new ArrayList <Responsavel>();
	}
	
	
	/** Método Adiciona Responsável
	 * @param resp Responsável
	 * @throws Exception caso já exista um responsável com a mesma matrícula.
	 */
	public void addResponsavel (Responsavel resp) throws Exception {
		try{
			if (pesquisaPmatricula(resp.getMatricula())!=null)
				throw new Exception ("Já existe Responsável com esta Matrícula.");
		} catch (Exception e) {
			listaResponsavel.add(resp);
		}
	}
	
	
	/** Método Remover Responsável por Matrícula
	 * @param id Matrícula do Responsável
	 * @param pass Senha do Responsável 
	 * @throws Exception caso não consiga achar um resposavel com a matrícula informada.
	 */
	public void removerAdmin(String id,String pass) throws Exception {
		for (Responsavel r : listaResponsavel)
			if (r.getSenha().equals(pass) && r.getMatricula().equals(id))
				listaResponsavel.remove(r);
		throw new Exception ("Sem Responsáveis com esta Matrícula");
	}
	
	
	/** Método Pesquisar Responsável por Matrícula
	 * @param matricula do Responsável
	 * @return Responsável
	 * @throws Exception  caso não consiga achar um resposável com a matrícula informada.
	 */
	public Responsavel pesquisaPmatricula(String matricula) throws Exception {
		for (Responsavel r : listaResponsavel)
			if (r.getMatricula().equals(matricula))
				return r;

		throw new Exception ("Sem Responsáveis com esta Matrícula");
	}
	
	
	/**
	 * Método editar() Responsável apenas se matrícula e senha forem as antigas
	 * @param matriculaOrig Matrícula antes de ser editada
	 * @param pass Senha antes de ser editada
	 * @param matricula Nova matrícula
	 * @param usuario Novo nome de usuário
	 * @param senha Nova senha
	 */
	public void editar(String matriculaOrig, String pass, String matricula, String usuario, String senha) {
		for (Responsavel rep : listaResponsavel)
			if ((rep.getSenha().equals(pass)) && (rep.getMatricula().equals(matriculaOrig))) {	
				rep.setMatricula(matricula);
				rep.setSenha(senha);
				rep.setUsuario(usuario);
				return;
			}
	}
	
	
	/**
	 * Método verificar() se existe Responsável especificado na Coleção
	 * @param matricula do Responsável
	 * @param pass Senha do Responsável
	 * @return Verdadeiro se encontrar Responsável com matrícula e senha informada. Falso se não.
	 */
	public boolean verificar(String matricula,String pass) {
		for (Responsavel r : listaResponsavel)
			if ((r.getSenha().equals(pass)) && (r.getMatricula().equals(matricula)))
				return true;
		return false;
	}
	
	
	/**
	 * Método listar() imprime todos os responsáveis da Coleção
	 */
	public void listar() {
		for (Responsavel rep : listaResponsavel)
			System.out.println(rep.toString());
			
	}
	
	
	/**
	 * Método Verificar Responsável na Coleção
	 * @param resp Responsável
	 * @return Verdadeiro se encontrar, Falso se não.
	 */
	public boolean verificarResp( Responsavel resp) {
		for (Responsavel rep : listaResponsavel)
			if (rep.equals(resp))
				return true;
		return false;
	}
	
=======
package Comon;
import java.util.ArrayList;

/**
 * Classe que manipula um conjunto de responsáveis por monitorar a rede
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class ColecaoResponsavel {
	
	/** Atributos */
	
	/** Lista de Responsáveis */
	ArrayList <Responsavel> listaResponsavel ;
	
	
	/** Método Construtor */
	
	public ColecaoResponsavel(){
		listaResponsavel = new ArrayList <Responsavel>();
	}
	
	
	/** Método Adiciona Responsável
	 * @param resp Responsável
	 * @throws Exception caso já exista um responsável com a mesma matrícula.
	 */
	public void addResponsavel (Responsavel resp) throws Exception {
		try{
			if (pesquisaPmatricula(resp.getMatricula())!=null)
				throw new Exception ("Já existe Responsável com esta Matrícula.");
		} catch (Exception e) {
			listaResponsavel.add(resp);
		}
	}
	
	
	/** Método Remover Responsável por Matrícula
	 * @param id Matrícula do Responsável
	 * @param pass Senha do Responsável 
	 * @throws Exception caso não consiga achar um resposavel com a matrícula informada.
	 */
	public void removerAdmin(String id,String pass) throws Exception {
		for (Responsavel r : listaResponsavel)
			if (r.getSenha().equals(pass) && r.getMatricula().equals(id))
				listaResponsavel.remove(r);
		throw new Exception ("Sem Responsáveis com esta Matrícula");
	}
	
	
	/** Método Pesquisar Responsável por Matrícula
	 * @param matricula do Responsável
	 * @return Responsável
	 * @throws Exception  caso não consiga achar um resposável com a matrícula informada.
	 */
	public Responsavel pesquisaPmatricula(String matricula) throws Exception {
		for (Responsavel r : listaResponsavel)
			if (r.getMatricula().equals(matricula))
				return r;

		throw new Exception ("Sem Responsáveis com esta Matrícula");
	}
	
	
	/**
	 * Método editar() Responsável apenas se matrícula e senha forem as antigas
	 * @param matriculaOrig Matrícula antes de ser editada
	 * @param pass Senha antes de ser editada
	 * @param matricula Nova matrícula
	 * @param usuario Novo nome de usuário
	 * @param senha Nova senha
	 */
	public void editar(String matriculaOrig, String pass, String matricula, String usuario, String senha) {
		for (Responsavel rep : listaResponsavel)
			if ((rep.getSenha().equals(pass)) && (rep.getMatricula().equals(matriculaOrig))) {	
				rep.setMatricula(matricula);
				rep.setSenha(senha);
				rep.setUsuario(usuario);
				return;
			}
	}
	
	
	/**
	 * Método verificar() se existe Responsável especificado na Coleção
	 * @param matricula do Responsável
	 * @param pass Senha do Responsável
	 * @return Verdadeiro se encontrar Responsável com matrícula e senha informada. Falso se não.
	 */
	public boolean verificar(String matricula,String pass) {
		for (Responsavel r : listaResponsavel)
			if ((r.getSenha().equals(pass)) && (r.getMatricula().equals(matricula)))
				return true;
		return false;
	}
	
	
	/**
	 * Método listar() imprime todos os responsáveis da Coleção
	 */
	public void listar() {
		for (Responsavel rep : listaResponsavel)
			System.out.println(rep.toString());
			
	}
	
	
	/**
	 * Método Verificar Responsável na Coleção
	 * @param resp Responsável
	 * @return Verdadeiro se encontrar, Falso se não.
	 */
	public boolean verificarResp( Responsavel resp) {
		for (Responsavel rep : listaResponsavel)
			if (rep.equals(resp))
				return true;
		return false;
	}
	
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
}