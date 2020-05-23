package com.gdm.grafico.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;



@SuppressWarnings("serial")
@ManagedBean
public class ChartView implements Serializable {

	private LineChartModel animatedModel1;
	private BarChartModel animatedModel2;
	private PieChartModel graficoPizza;

	@PostConstruct
	public void init() {
		createAnimatedModels();
		graficoPizzas();
	}

	public PieChartModel getGraficoPizza() {
		return graficoPizza;
	}

	public LineChartModel getAnimatedModel1() {
		return animatedModel1;
	}

	public BarChartModel getAnimatedModel2() {
		return animatedModel2;
	}

	private void createAnimatedModels() {
		animatedModel1 = initLinearModel();
		// animatedModel1.setTitle("Line Chart");
		animatedModel1.setAnimate(true);
		animatedModel1.setLegendPosition("se");
		Axis yAxis = animatedModel1.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(10);

		animatedModel2 = initBarModel();
		// animatedModel2.setTitle("Bar Charts");
		animatedModel2.setAnimate(true);
		animatedModel2.setLegendPosition("ne");
		yAxis = animatedModel2.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(200);
	}

	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();


//		dates.add(y);
		boys.setLabel("Multas");
//		boys.set(dates, x);
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		// ChartSeries girls = new ChartSeries();
		// girls.setLabel("Girls");
		// girls.set("2004", 52);
		// girls.set("2005", 60);
		// girls.set("2006", 110);
		// girls.set("2007", 135);
		// girls.set("2008", 120);

		model.addSeries(boys);
		// model.addSeries(girls);

		return model;
	}

	private LineChartModel initLinearModel() {
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Dianteria");

		series1.set(1, 2);
		series1.set(2, 1);
		series1.set(3, 3);
		series1.set(4, 6);
		series1.set(5, 8);
		series1.set(6, 3);
		series1.set(7, 4);
		series1.set(8, 3);

		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Traseira");

		series2.set(1, 6);
		series2.set(2, 3);
		series2.set(3, 2);
		series2.set(4, 7);
		series2.set(5, 9);
		series2.set(6, 6);
		series2.set(7, 3);
		series2.set(8, 9);

		model.addSeries(series1);
		model.addSeries(series2);

		return model;
	}

	private void graficoPizzas() {
		graficoPizza();
	}

	private void graficoPizza() {
		graficoPizza = new PieChartModel();
		graficoPizza.set("Toco", 10);
		graficoPizza.set("Truck", 25);
		graficoPizza.set("Bi-Truck", 17);
		graficoPizza.set("Carreta", 50);
		graficoPizza.set("Bi-Trem", 61);

		graficoPizza.setTitle("Categorias");
		graficoPizza.setLegendPosition("w");

	}

	public void itemSelect(ItemSelectEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selecionado",
				"Item : " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


}
