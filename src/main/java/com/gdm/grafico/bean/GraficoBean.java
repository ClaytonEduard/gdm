package com.gdm.grafico.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import com.gdm.dao.MultaDAO;
import com.gdm.domain.Multa;

@SuppressWarnings("serial")
@ManagedBean
public class GraficoBean implements Serializable {
	private List<Multa> multas;
	private List<Multa> multas1;
	private LineChartModel graficoMultas;
	private LineChartModel graficoMultas1;

	public LineChartModel getGraficoMultas1() {
		return graficoMultas1;
	}

	public LineChartModel getGraficoMultas() {
		return graficoMultas;
	}

	@PostConstruct
	public void init() {
//		graficoMultasAnimado();

	}

	@SuppressWarnings("unused")
	private void graficoMultasAnimado() {
		graficoMultas = linhasLineares();
		graficoMultas.setTitle("Gráfico de Linhas");
		graficoMultas.setAnimate(true);
		graficoMultas.setLegendPosition("se");
		Axis ind = graficoMultas.getAxis(AxisType.Y);
		ind.setMin(0);
		ind.setMax(8);

		graficoMultas1 = linhasLineares();
		graficoMultas1.setTitle("Gráfico de Linha2");
		graficoMultas1.setAnimate(true);
		graficoMultas1.setLegendPosition("ne");
		graficoMultas1.setShowPointLabels(true);
		graficoMultas1.getAxes().put(AxisType.X, new CategoryAxis("Quantidade"));
		ind = graficoMultas1.getAxis(AxisType.Y);
		ind.setMin(0);
		ind.setMax(100);

	}

	private LineChartModel linhasLineares() {
		MultaDAO dao = new MultaDAO();
		multas = dao.listar();

		MultaDAO dao2 = new MultaDAO();
		multas1 = dao2.listar();
		LineChartModel model = new LineChartModel();

		LineChartSeries series1 = new LineChartSeries();
		series1.setLabel("Multas");
		// series1.set(multas.size(), multas.size());

		// funciona
		for (int i = 0; i <= multas.size(); i++) {
			series1.set(i, multas.size());

		}
		LineChartSeries series2 = new LineChartSeries();
		series2.setLabel("Data de Lancameto");
		 for (int i = 0; i <= multas.size(); i++) {
		 series2.set(i, multas1.size());
		
		 }
		//
		// for (Multa multa : multas) {
		// listData.add(multa.getDataLancamento());
		// }
		// for (int i = 0; i <= listData.size(); i++) {
		// List<Date> Map;
		// series2.setData(Map,listData, i);
		// }
		// metodo basico
		// series1.set(multas.size(), multas.size());
		// series1.set(multas.size(), multas.size());

		// for (int i = 0; i <= multas.hashCode(); i++) {
		// series1.set(i,multas.toString());
		//
		// }
		model.addSeries(series1);
		model.addSeries(series2);
		return model;

	}

	@SuppressWarnings("unused")
	private BarChartModel initBarModel() {
		BarChartModel model = new BarChartModel();

		ChartSeries boys = new ChartSeries();
		boys.setLabel("Boys");
		boys.set("2004", 120);
		boys.set("2005", 100);
		boys.set("2006", 44);
		boys.set("2007", 150);
		boys.set("2008", 25);

		ChartSeries girls = new ChartSeries();
		girls.setLabel("Girls");
		girls.set("2004", 52);
		girls.set("2005", 60);
		girls.set("2006", 110);
		girls.set("2007", 135);
		girls.set("2008", 120);

		model.addSeries(boys);
		model.addSeries(girls);

		return model;
	}

}
