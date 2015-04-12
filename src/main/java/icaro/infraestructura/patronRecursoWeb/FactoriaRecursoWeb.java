package icaro.infraestructura.patronRecursoWeb;

import icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.DescInstanciaRecursoAplicacion;
import icaro.infraestructura.patronRecursoWeb.imp.FactoriaRecursoSimpleWeb;


public abstract class FactoriaRecursoWeb {

	private static FactoriaRecursoWeb instance;

	public static FactoriaRecursoWeb instance() {
		if (instance == null)
			instance = new FactoriaRecursoSimpleWeb();
		return instance;
	}
	
	public abstract void crearRecursoSimple(DescInstanciaRecursoAplicacion recurso);
	
}
