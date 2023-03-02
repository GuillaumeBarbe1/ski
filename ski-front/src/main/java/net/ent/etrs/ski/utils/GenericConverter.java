package net.ent.etrs.ski.utils;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;


@SuppressWarnings("serial")
@Named
@ViewScoped
public class GenericConverter implements Converter<Object>, Serializable {
	private BidiMap<UUID, Object> temporaryStore;

	@PostConstruct
	public void init()
	{
		temporaryStore = new DualHashBidiMap<UUID, Object>();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try
		{
			UUID key = UUID.fromString(value);
			if (key != null && temporaryStore.containsKey(key)) {
				return temporaryStore.get(key);
			}
			else {
				return value;
			}
		}
		catch (IllegalArgumentException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
	{
		if (value == null) {
			return null;
		}
		else {
			if (!temporaryStore.containsValue(value)) {
				UUID id = UUID.randomUUID();
				temporaryStore.put(id, value);
				return id.toString();
			}
			else {
				return temporaryStore.getKey(value).toString();
			}
		}
	}
}
