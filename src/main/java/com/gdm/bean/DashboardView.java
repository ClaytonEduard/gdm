package com.gdm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean
public class DashboardView implements Serializable {
	private List<String> imagens = new ArrayList<>();
	private DashboardModel model;

	@PostConstruct
	public void init() {
		model = new DefaultDashboardModel();

		DashboardColumn column1 = new DefaultDashboardColumn();
		DashboardColumn column2 = new DefaultDashboardColumn();
		DashboardColumn column3 = new DefaultDashboardColumn();
		DashboardColumn column4 = new DefaultDashboardColumn(); // para colocar
																// as imagens
		DashboardColumn column5 = new DefaultDashboardColumn(); // ele referente
																// a valores
																// numericos
		DashboardColumn column6 = new DefaultDashboardColumn(); // carrocel dos
																// veiculos

		column1.addWidget("sports");
		column1.addWidget("finance");

		column2.addWidget("lifestyle");
		column2.addWidget("weather");

		column3.addWidget("totais");
		column3.addWidget("politics");

		column4.addWidget("imagens");

		column5.addWidget("veiculosPes");
		column6.addWidget("qtdMultas");

		model.addColumn(column1);
		model.addColumn(column2);
		model.addColumn(column3);
		model.addColumn(column4);
		model.addColumn(column5);
		model.addColumn(column6);

		geraImagem();
	}

	public void handleReorder(DashboardReorderEvent event) {
		FacesMessage message = new FacesMessage();
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		message.setSummary("Reordered: " + event.getWidgetId());
		message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex()
				+ ", Sender index: " + event.getSenderColumnIndex());

		addMessage(message);

	}

	public void handleClose(CloseEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed",
				"Closed panel id:'" + event.getComponent().getId() + "'");

		addMessage(message);
	}

	public void handleToggle(ToggleEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled",
				"Status:" + event.getVisibility().name());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public DashboardModel getModel() {
		return model;
	}

	public void geraImagem() {
		long i = 0;
		for (i =1; i <= 30; i++) {
			imagens.add(i + ".png");
		}
	}

	public List<String> getImagens() {
		return imagens;
	}

}
