<<<<<<< HEAD
package Comon;

/**
 * Classe que possui informações de uma Pessoa
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Pessoa {
	
	/** Atributos */
	
	/** Nome da Pessoa */
	private String nome;
	/** Cargo da Pessoa */
	private String cargo;
	
	
	/** Método Construtor de Pessoa
	 * @param nome da Pessoa
	 * @param cargo da Pessoa
	 */
	public Pessoa(String nome, String cargo){
		this.nome = nome;
		this.cargo = cargo;
	}
	
	
	/** Método getNome
	 * @return nome da Pessoa
	 */
	public String getNome() {
		return nome;
	}
	
	/** Método getCargo
	 * @return cargo da Pessoa
	 */
	public String getCargo() {
		return cargo;
	}
	
	
	/** Método SetNome
	 * @param nome novo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/** Método SetCargo
	 * @param cargo novo cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
	/** Método hashCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	
	}
	
	
	/** Método equals() */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	/** Método toString() */
	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", cargo=" + cargo + "]";
	}
}
=======
package Comon;

/**
 * Classe que possui informações de uma Pessoa
 * @author Edson Vieira
 * @author Miguel Cabral
 */
public class Pessoa {
	
	/** Atributos */
	
	/** Nome da Pessoa */
	private String nome;
	/** Cargo da Pessoa */
	private String cargo;
	
	
	/** Método Construtor de Pessoa
	 * @param nome da Pessoa
	 * @param cargo da Pessoa
	 */
	public Pessoa(String nome, String cargo){
		this.nome = nome;
		this.cargo = cargo;
	}
	
	
	/** Método getNome
	 * @return nome da Pessoa
	 */
	public String getNome() {
		return nome;
	}
	
	/** Método getCargo
	 * @return cargo da Pessoa
	 */
	public String getCargo() {
		return cargo;
	}
	
	
	/** Método SetNome
	 * @param nome novo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/** Método SetCargo
	 * @param cargo novo cargo
	 */
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	
	/** Método hashCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	
	}
	
	
	/** Método equals() */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	/** Método toString() */
	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + ", cargo=" + cargo + "]";
	}
}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
