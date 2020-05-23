package com.gdm.converter;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.gdm.bean.MultasBean;
import com.gdm.domain.Cidade;

@FacesConverter("cidadeConverter")
public class CidadeConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent componente, String value) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (componente == null) {
			throw new NullPointerException("componente");
		}

		FacesContext ctx = FacesContext.getCurrentInstance();
		ValueExpression vex = ctx.getApplication().getExpressionFactory().createValueExpression(ctx.getELContext(),
				"#{multasBean}", MultasBean.class);
		MultasBean bean = (MultasBean) vex.getValue(ctx.getELContext());
		Cidade cidade;
		try {
			cidade = bean.obterCidadeCodigo(new Long(value));
		} catch (NumberFormatException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor desconhecido",
					"Erro no Objeto");
			throw new ConverterException(message);
		}
		if (cidade == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Valor desconhecido",
					"Erro no Objeto");
			throw new ConverterException(message);
		}
		return cidade;

	}

	@Override
	public String getAsString(FacesContext context, UIComponent componente, Object value) {
		if (context == null) {
			throw new NullPointerException("context");
		}
		if (componente == null) {
			throw new NullPointerException("componente");
		}

		return String.valueOf(((Cidade) value).getCodigo());
	}

}
