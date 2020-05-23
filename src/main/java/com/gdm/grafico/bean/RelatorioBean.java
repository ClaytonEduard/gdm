package com.gdm.grafico.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;

import com.gdm.dao.MultaDAO;
import com.gdm.domain.Multa;

@SuppressWarnings("serial")
@ManagedBean
public class RelatorioBean implements Serializable {

	private Multa multa;
	private List<Multa> multaPlaca;
	private List<Multa> listaTipoVeiculo;
	private List<Multa> listaMultas;
	private ArrayList<Multa>[][] multasPlacas;
	private BarChartModel graficoEixos;
	private List<String> listaPlacas = new ArrayList<>();

	private BarChartModel graficoDatas;

	private BarChartModel graficoPlacas;

	private BarChartModel graficoTipoVeiculo;
	private BarChartModel test;

	// private BarChartModel graficoCidades;

	@PostConstruct
	public void geraRelatorioMulta() {
		graficoPorEixos();
		graficoPorMeses();
		graficoPlacas();
		// graficoTipoVeiculo();
		testeBarras();
		graficoPorTipoVeiculo();
	}

	// meetodo de criar a animavcao
	private void graficoPorEixos() {
		// Grafico por eixos
		graficoEixos = populaGrafico();
		graficoEixos.setTitle("Multas por Eixo");
		graficoEixos.setAnimate(true);
		graficoEixos.setLegendPosition("ne");
		Axis yAxis = graficoEixos.getAxis(AxisType.Y);
		graficoEixos.getAxes().put(AxisType.X, new CategoryAxis("Eixos"));
		yAxis.setMin(0);
		MultaDAO dao = new MultaDAO();
		yAxis.setLabel("Quantidades");
		listaMultas = dao.listarPorEmpresa();
		yAxis.setMax(listaMultas.size() + 5);

		// grafico de datas

		graficoDatas = graficoPorMeses();
		graficoDatas.setTitle("Grafico de datas");
		graficoDatas.setAnimate(true);
		graficoDatas.setLegendPosition("ne");
		yAxis = graficoDatas.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setLabel("Datas");
		yAxis.setMax(listaMultas.size());

		test = testeBarras();
		test.setTitle("Test Placas");
		test.setAnimate(true);
		test.setLegendCols(1);
		test.setLegendPosition("ne");
		yAxis = test.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setLabel("Data test");
		yAxis.setMax(listaMultas.size());
		// Grafico de veiculo
		graficoTipoVeiculo = graficoPorTipoVeiculo();
		graficoTipoVeiculo.setTitle("Grafico tipo de Veiculo");
		graficoTipoVeiculo.setAnimate(true);
		graficoTipoVeiculo.setLegendPosition("ne");
		yAxis = graficoTipoVeiculo.getAxis(AxisType.X);
		yAxis.setMin(0);
		yAxis.setTickAngle(-45);
		yAxis.setMax(listaMultas.size()+10);

		// Grafico de Placas

		graficoPlacas = graficoPlacas();
		graficoPlacas.setTitle("Grafico de Placas");
		graficoPlacas.setAnimate(true);
		graficoPlacas.setLegendPosition("ne");
		yAxis = graficoPlacas.getAxis(AxisType.X);
		yAxis.setMin(0);
		yAxis.setLabel("Placas");
		yAxis.setTickAngle(-50);
		yAxis.setMax(listaMultas.size());

		// yAxis = graficoPlacas.getAxis(AxisType.X);
		// axis.setTickAngle(-50);

	}

	private BarChartModel populaGrafico() {
		BarChartModel model = new BarChartModel();
		// grafico de eixos inicio
		ChartSeries eixo = new ChartSeries();

		int e1 = 0;
		int e2 = 0;
		int e3 = 0;
		int e4 = 0;
		int e5 = 0;
		int e6 = 0;
		int e7 = 0;

		// legenda Superior
		eixo.setLabel("Eixos");

		MultaDAO dao = new MultaDAO();
		listaMultas = dao.listarPorEmpresa();
		// percorer todas as multas cadastradas
		for (Multa multa : listaMultas) {
			if (multa.getG1Diferenca() != null) {
				e1++;
			}
			if (multa.getG2Diferenca() != null) {
				e2++;
			}
			if (multa.getG3Diferenca() != null) {
				e3++;
			}
			if (multa.getG4Diferenca() != null) {
				e4++;
			}
			if (multa.getG5Diferenca() != null) {
				e5++;
			}
			if (multa.getG6Diferenca() != null) {
				e6++;
			}
			if (multa.getG7Diferenca() != null) {
				e7++;
			}

		}

		eixo.set("Eixo 1", e1);
		eixo.set("Eixo 2", e2);
		eixo.set("Eixo 3", e3);
		eixo.set("Eixo 4", e4);
		eixo.set("Eixo 5", e5);
		eixo.set("Eixo 6", e6);
		eixo.set("Eixo 7", e7);

		model.addSeries(eixo);
		model.setShowPointLabels(true);
		// fim grafico por eixo

		return model;

	}

	@SuppressWarnings("deprecation")
	private BarChartModel graficoPorMeses() {
		BarChartModel model1 = new BarChartModel();
		ChartSeries data = new ChartSeries();
		Date Mes;
		int janeiro = 0;
		int fevereiro = 0;
		int marco = 0;
		int abril = 0;
		int maio = 0;
		int junho = 0;
		int julho = 0;
		int agosto = 0;
		int setembro = 0;
		int outubro = 0;
		int novembro = 0;
		int dezembro = 0;

		for (Multa multa : listaMultas) {
			Mes = multa.getDataOcorencia();
			if (Mes != null) {
				if (Mes.getMonth() == 0) {
					System.out.println("Data" + Mes + "J" + janeiro);
					janeiro++;
				}
				if (Mes.getMonth() == 1) {
					System.out.println("Data" + Mes + "F" + fevereiro);
					fevereiro++;
				}
				if (Mes.getMonth() == 2) {
					System.out.println("Data" + Mes + "M" + marco);
					marco++;
				}
				if (Mes.getMonth() == 3) {
					System.out.println("Data" + Mes + "A" + abril);
					abril++;
				}
				if (Mes.getMonth() == 4) {
					System.out.println("Data" + Mes + "M" + maio);
					maio++;
				}
				if (Mes.getMonth() == 5) {
					System.out.println("Data" + Mes + "JN" + junho);
					junho++;
				}
				if (Mes.getMonth() == 6) {
					System.out.println("Data" + Mes + "JU" + julho);
					julho++;
				}
				if (Mes.getMonth() == 7) {
					System.out.println("Data" + Mes + "A" + agosto);
					agosto++;
				}
				if (Mes.getMonth() == 8) {
					System.out.println("Data" + Mes + "S" + setembro);
					setembro++;
				}
				if (Mes.getMonth() == 9) {
					System.out.println("Data" + Mes + "O" + outubro);
					outubro++;
				}
				if (Mes.getMonth() == 10) {
					System.out.println("Data" + Mes + "N" + novembro);
					novembro++;
				}
				if (Mes.getMonth() == 11) {
					System.out.println("Data" + Mes + "D" + dezembro);
					dezembro++;
				}

			}

		}

		data.setLabel("Datas");
		data.set("Jan", janeiro);
		data.set("Fev", fevereiro);
		data.set("Mar", marco);
		data.set("Abr", abril);
		data.set("Mai", maio);
		data.set("Jun", junho);
		data.set("Jul", julho);
		data.set("Ago", agosto);
		data.set("Set", setembro);
		data.set("Out", outubro);
		data.set("Nov", novembro);
		data.set("Dez", dezembro);

		model1.addSeries(data);
		model1.setShowPointLabels(true);
		// System.out.println("Janeiro: " + janeiro);
		// System.out.println("Fevereiro: " + fevereiro);

		return model1;
	}

	private BarChartModel testeBarras() {
		BarChartModel model = new BarChartModel();
		ChartSeries tipo = new ChartSeries();
		MultaDAO dao = new MultaDAO();
		multaPlaca = dao.listar();

		for (int i = 0; i < multaPlaca.size(); i++) {
			int contador = 0;
			// String p = multaPlaca.get(i).getPlacaCavalo();
			for (int j = 1; j < multaPlaca.size(); j++) {

				@SuppressWarnings("unused")
				String m = multaPlaca.get(j).getPlacaCavalo();
				if (multaPlaca.get(i).getPlacaCavalo().toString()
						.equals(multaPlaca.get(j).getPlacaCavalo().toString())) {
					contador++;
				}
				tipo.set(multaPlaca.get(i).getPlacaCavalo(), contador);

			}

		}

		model.setTitle("Teste");
		model.addSeries(tipo);

		model.setShowPointLabels(true);

		return model;
	}

	// Grafico de placas
	private BarChartModel graficoPlacas() {
		BarChartModel model = new BarChartModel();
		ChartSeries tipo = new ChartSeries();
		MultaDAO dao = new MultaDAO();
		multaPlaca = dao.listar();

		for (int i = 0; i < multaPlaca.size(); i++) {
			int contador = 0;
			// String p = multaPlaca.get(i).getPlacaCavalo();
			for (int j = 1; j < multaPlaca.size(); j++) {

				if (multaPlaca.get(i).getPlacaCavalo().toString()
						.equals(multaPlaca.get(j).getPlacaCavalo().toString())) {
					contador++;
				}
				tipo.set(multaPlaca.get(i).getPlacaCavalo(), contador);

			}

		}

		model.setTitle("Teste");
		model.addSeries(tipo);

		model.setShowPointLabels(true);

		return model;
	}

	private BarChartModel graficoPorTipoVeiculo() {

		BarChartModel model = new BarChartModel();
		ChartSeries tipo = new ChartSeries();
		MultaDAO dao = new MultaDAO();
		listaTipoVeiculo = dao.listar();

		for (int i = 0; i < listaTipoVeiculo.size(); i++) {
			int contador = 0;
			// String p = multaPlaca.get(i).getPlacaCavalo();
			for (int j = 1; j < listaTipoVeiculo.size(); j++) {

				// String m = multaPlaca.get(j).getVeiculo().getApelido();

				if (listaTipoVeiculo.get(i).getVeiculo()
						.getCodigo() == (listaTipoVeiculo.get(j).getVeiculo().getCodigo())) {
					System.out.println("For i :" + listaTipoVeiculo.get(i).getVeiculo().getApelido().toString()
							+ listaTipoVeiculo.get(i).getVeiculo().getCodigo());
					System.out.println("For j :" + listaTipoVeiculo.get(j).getVeiculo().getApelido().toString()
							+ listaTipoVeiculo.get(j).getVeiculo().getCodigo());
					contador++;
				}
				if (listaTipoVeiculo.get(i).getVeiculo().getCodigo() != null) {
					tipo.set(listaTipoVeiculo.get(i).getVeiculo().getApelido().toString(), contador);
				}
			}

		}

		model.setTitle("Tipo de Veiculo ");
	
		model.addSeries(tipo);

		model.setShowPointLabels(true);

		return model;

	}

	@SuppressWarnings("unused")
	private BarChartModel graficoTipoVeiculo() {
		BarChartModel model = new BarChartModel();
		ChartSeries tipo = new ChartSeries();
		long t = 0;
		// int veiculo0 = 0;
		// String veiculo00 = null;
		int veiculo1 = 0;
		String veiculo01 = "";
		int veiculo2 = 0;
		String veiculo02 = "";
		int veiculo3 = 0;
		String veiculo03 = "";
		int veiculo4 = 0;
		String veiculo04 = "";
		int veiculo5 = 0;
		String veiculo05 = "";
		int veiculo6 = 0;
		String veiculo06 = "";
		int veiculo7 = 0;
		String veiculo07 = "";
		int veiculo8 = 0;
		String veiculo08 = "";
		int veiculo9 = 0;
		String veiculo09 = "";
		int veiculo10 = 0;
		String veiculo010 = "";
		int veiculo11 = 0;
		String veiculo011 = "";
		int veiculo12 = 0;
		String veiculo012 = "";
		int veiculo13 = 0;
		String veiculo013 = "";
		int veiculo14 = 0;
		String veiculo014 = "";
		int veiculo15 = 0;
		String veiculo015 = "";
		int veiculo16 = 0;
		String veiculo016 = "";
		int veiculo17 = 0;
		String veiculo017 = "";
		int veiculo18 = 0;
		String veiculo018 = "";
		int veiculo19 = 0;
		String veiculo019 = "";
		int veiculo20 = 0;
		String veiculo020 = "";

		// laco para contar a quantidade de multa por tipo
		for (Multa multa : listaMultas) {
			t = multa.getVeiculo().getCodigo();

			// if (t == 0) {
			// veiculo0++;
			// }
			if (t == 1) {
				veiculo1++;
			}
			if (t == 2) {
				veiculo2++;
			}
			if (t == 3) {
				veiculo3++;
			}
			if (t == 4) {
				veiculo4++;
			}
			if (t == 5) {
				veiculo5++;
			}
			if (t == 6) {
				veiculo6++;
			}
			if (t == 7) {
				veiculo7++;
			}
			if (t == 8) {
				veiculo8++;
			}
			if (t == 9) {
				veiculo9++;
			}
			if (t == 10) {
				veiculo10++;
			}
			if (t == 11) {
				veiculo11++;
			}
			if (t == 12) {
				veiculo12++;
			}
			if (t == 13) {
				veiculo13++;
			}
			if (t == 14) {
				veiculo14++;
			}
			if (t == 15) {
				veiculo15++;
			}
			if (t == 16) {
				veiculo16++;
			}
			if (t == 17) {
				veiculo17++;
			}
			if (t == 18) {
				veiculo18++;
			}
			if (t == 19) {
				veiculo19++;
			}
			if (t == 20) {
				veiculo20++;
			}
		}

		for (Multa multa : listaMultas) {
			t = multa.getVeiculo().getCodigo();
			// if (t == 0) {
			// veiculo00 = multa.getVeiculo().getApelido();
			// }
			if (t == 1) {
				veiculo01 = multa.getVeiculo().getApelido();
			}
			if (t == 2) {
				veiculo02 = multa.getVeiculo().getApelido();
			}
			if (t == 3) {
				veiculo03 = multa.getVeiculo().getApelido();
			}
			if (t == 4) {
				veiculo04 = multa.getVeiculo().getApelido();
			}
			if (t == 5) {
				veiculo05 = multa.getVeiculo().getApelido();
			}
			if (t == 6) {
				veiculo06 = multa.getVeiculo().getApelido();
			}
			if (t == 7) {
				veiculo07 = multa.getVeiculo().getApelido();
			}
			if (t == 8) {
				veiculo08 = multa.getVeiculo().getApelido();
			}
			if (t == 9) {
				veiculo09 = multa.getVeiculo().getApelido();
			}
			if (t == 10) {
				veiculo010 = multa.getVeiculo().getApelido();
			}
			if (t == 11) {
				veiculo011 = multa.getVeiculo().getApelido();
			}
			if (t == 12) {
				veiculo012 = multa.getVeiculo().getApelido();
			}
			if (t == 13) {
				veiculo013 = multa.getVeiculo().getApelido();
			}
			if (t == 14) {
				veiculo014 = multa.getVeiculo().getApelido();
			}
			if (t == 15) {
				veiculo015 = multa.getVeiculo().getApelido();
			}
			if (t == 16) {
				veiculo016 = multa.getVeiculo().getApelido();
			}
			if (t == 17) {
				veiculo017 = multa.getVeiculo().getApelido();
			}
			if (t == 18) {
				veiculo018 = multa.getVeiculo().getApelido();
			}
			if (t == 19) {
				veiculo019 = multa.getVeiculo().getApelido();
			}
			if (t == 20) {
				veiculo020 = multa.getVeiculo().getApelido();
			}
		}

		tipo.setLabel("Tipo de Veiculos");
		// tipo.set(veiculo00, veiculo0);
		tipo.set(veiculo01, veiculo1);
		tipo.set(veiculo02, veiculo2);
		tipo.set(veiculo03, veiculo3);
		tipo.set(veiculo04, veiculo4);
		tipo.set(veiculo05, veiculo5);
		tipo.set(veiculo06, veiculo6);
		tipo.set(veiculo07, veiculo7);
		tipo.set(veiculo08, veiculo8);
		tipo.set(veiculo09, veiculo9);
		tipo.set(veiculo010, veiculo10);
		tipo.set(veiculo011, veiculo11);
		tipo.set(veiculo012, veiculo12);
		tipo.set(veiculo013, veiculo13);
		tipo.set(veiculo014, veiculo14);
		tipo.set(veiculo015, veiculo15);
		tipo.set(veiculo016, veiculo16);
		tipo.set(veiculo017, veiculo17);
		tipo.set(veiculo018, veiculo18);
		tipo.set(veiculo019, veiculo19);
		tipo.set(veiculo020, veiculo20);

		model.addSeries(tipo);

		model.setShowPointLabels(true);
		return model;
	}

	public List<Multa> getMultaPlaca() {
		return multaPlaca;
	}

	public void setMultaPlaca(List<Multa> multaPlaca) {
		this.multaPlaca = multaPlaca;
	}

	public List<Multa> getListaMultas() {
		return listaMultas;
	}

	public void setListaMultas(List<Multa> listaMultas) {
		this.listaMultas = listaMultas;
	}

	public BarChartModel getGraficoEixos() {
		return graficoEixos;
	}

	public void setGraficoEixos(BarChartModel graficoEixos) {
		this.graficoEixos = graficoEixos;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}

	public BarChartModel getGraficoDatas() {
		return graficoDatas;
	}

	public void setGraficoDatas(BarChartModel graficoDatas) {
		this.graficoDatas = graficoDatas;
	}

	public BarChartModel getGraficoPlacas() {
		return graficoPlacas;
	}

	public void setGraficoPlacas(BarChartModel graficoPlacas) {
		this.graficoPlacas = graficoPlacas;
	}

	public BarChartModel getGraficoTipoVeiculo() {
		return graficoTipoVeiculo;
	}

	public void setGraficoTipoVeiculo(BarChartModel graficoTipoVeiculo) {
		this.graficoTipoVeiculo = graficoTipoVeiculo;
	}

	public List<String> getListaPlacas() {
		return listaPlacas;
	}

	public void setListaPlacas(List<String> listaPlacas) {
		this.listaPlacas = listaPlacas;
	}

	public ArrayList<Multa>[][] getMultasPlacas() {
		return multasPlacas;
	}

	public void setMultasPlacas(ArrayList<Multa>[][] multasPlacas) {
		this.multasPlacas = multasPlacas;
	}

	public BarChartModel getTest() {
		return test;
	}

	public void setTest(BarChartModel test) {
		this.test = test;
	}

}
