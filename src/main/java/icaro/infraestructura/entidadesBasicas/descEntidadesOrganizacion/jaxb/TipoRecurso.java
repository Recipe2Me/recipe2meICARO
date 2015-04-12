package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TipoRecurso")
@XmlEnum
public enum TipoRecurso {

	@XmlEnumValue("RecursoSimple")
	RecursoSimple("RecursoSimple"),
	@XmlEnumValue("RecursoWeb")
	RecursoWeb("RecursoWeb");
	
	private final String value;
	
	
	TipoRecurso(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoRecurso fromValue(String v) {
        for (TipoRecurso c: TipoRecurso.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
