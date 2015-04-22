package icaro.aplicaciones.informacion.dominioRecipe2Me.anotaciones;

public class AnotacionInformacion {
	
	private Integer orden;
	private Integer comienzo;
	private String anotacion;
	private String informacion;
	
	public AnotacionInformacion() {
	
	}
	
	
	public AnotacionInformacion(String anotacion, String informacion,Integer orden, Integer comienzo) {
		super();
		this.anotacion = anotacion;
		this.informacion = informacion;
		this.comienzo = comienzo;
		this.orden = orden;
	}


	public String getAnotacion() {
		return anotacion;
	}
	public void setAnotacion(String anotacion) {
		this.anotacion = anotacion;
	}
	public String getInformacion() {
		return informacion;
	}
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}


	public Integer getOrden() {
		return orden;
	}


	public void setOrden(Integer orden) {
		this.orden = orden;
	}


	public Integer getComienzo() {
		return comienzo;
	}


	public void setComienzo(Integer comienzo) {
		this.comienzo = comienzo;
	}
	

}
