package br.unitins.a2topicos.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.unitins.a2topicos.dao.AutorDAO;
import br.unitins.a2topicos.model.Autor;

@FacesConverter(forClass = Autor.class)
public class AutorConverter implements Converter<Autor> {

	@Override
	public Autor getAsObject(FacesContext context, UIComponent component, String value) {
		AutorDAO dao = new AutorDAO();
		return dao.buscarPorId(Integer.parseInt(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Autor autor) {

		return autor.getId().toString();
	}

}
