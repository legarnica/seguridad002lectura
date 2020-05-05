package cl.lherrera.gsss.dto;

public class DetalleDTO {

	private String clave;
	private String valor;
	
	public DetalleDTO() {
		// TODO Auto-generated constructor stub
	}

	public DetalleDTO(String clave, String valor) {
		super();
		this.clave = clave;
		this.valor = valor;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "DetalleDTO [clave=" + clave + ", valor=" + valor + "]";
	}
}
