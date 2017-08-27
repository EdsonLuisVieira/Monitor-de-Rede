<<<<<<< HEAD
package Comon;

/**
 * Classe que generaliza Dispositivos que podem ser monitorados
 * @author Edson Vieira
 * @author Miguel Cabral
 */

 public class Dispositivo{
	 /** Atributos */

	/** Endereço Físico do Dispositivo */
	private String mac;
	/** Endereço do Dispositivo na rede */
	private String ip;
	/** Identificação do Dispositivo na rede */
	private String host;

	
	/**
	 * Método Construtor de Dispostivo
	 * @param host do Dispositivo
	 * @param IP do Dispositivo
	 * @param MAC do Dispositivo
	 */
	public Dispositivo(String host, String IP, String MAC){
		this.host = host;
		this.ip   = IP;
		this.mac  = MAC;
	}

	
	/** Método GetMAC
	 *  @return Endereço Físico */
	public String getMac(){
		return this.mac;
	}

	
	/** Método SetMAC
	 *  @param mac do Dispositivo */
	public void setMac(String mac){
		this.mac = mac;
	}

	
	/** Método GetIP
	 *  @return ip do Dispositivo  */
	public String getIP(){
		return this.ip;
	}
	
	
	/** Método SetIP
	 *  @param ip do Dispositivo  */
	public void setIp(String ip){
		this.ip = ip;
	}

	
	/** Método GetHost
	 *  @return host */
	public String getHost(){
		return this.host;
	}
	
	
	/** Método SetHost
	 *  @param host do Dispositivo */
	public void setHost(String host){
		this.host = host;
	}

	
	/** Método hasCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
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
		Dispositivo other = (Dispositivo) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		return true;
	}

	
	/** Método toString() */
	@Override
	public String toString() {
		return "Dispositivo [mac=" + mac + ", ip=" + ip + ", host=" + host + "]";
	}
}
=======
package Comon;

/**
 * Classe que generaliza Dispositivos que podem ser monitorados
 * @author Edson Vieira
 * @author Miguel Cabral
 */

 public class Dispositivo{
	 /** Atributos */

	/** Endereço Físico do Dispositivo */
	private String mac;
	/** Endereço do Dispositivo na rede */
	private String ip;
	/** Identificação do Dispositivo na rede */
	private String host;

	
	/**
	 * Método Construtor de Dispostivo
	 * @param host do Dispositivo
	 * @param IP do Dispositivo
	 * @param MAC do Dispositivo
	 */
	public Dispositivo(String host, String IP, String MAC){
		this.host = host;
		this.ip   = IP;
		this.mac  = MAC;
	}

	
	/** Método GetMAC
	 *  @return Endereço Físico */
	public String getMac(){
		return this.mac;
	}

	
	/** Método SetMAC
	 *  @param mac do Dispositivo */
	public void setMac(String mac){
		this.mac = mac;
	}

	
	/** Método GetIP
	 *  @return ip do Dispositivo  */
	public String getIP(){
		return this.ip;
	}
	
	
	/** Método SetIP
	 *  @param ip do Dispositivo  */
	public void setIp(String ip){
		this.ip = ip;
	}

	
	/** Método GetHost
	 *  @return host */
	public String getHost(){
		return this.host;
	}
	
	
	/** Método SetHost
	 *  @param host do Dispositivo */
	public void setHost(String host){
		this.host = host;
	}

	
	/** Método hasCode() */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
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
		Dispositivo other = (Dispositivo) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		return true;
	}

	
	/** Método toString() */
	@Override
	public String toString() {
		return "Dispositivo [mac=" + mac + ", ip=" + ip + ", host=" + host + "]";
	}
}
>>>>>>> 07d3e858e426bf5c237ce48f2b6d9086e5518a64
