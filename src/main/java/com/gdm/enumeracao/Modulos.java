package com.gdm.enumeracao;

public enum Modulos {
	GDM, VISTORIA, INDICADORES, FINANCEIRO;

	@Override
	public String toString() {
		switch (this) {
		case GDM:
			return "Gdm";
		case INDICADORES:
			return "Indicadores";
		case VISTORIA:
			return "Vistoria";
		case FINANCEIRO:
			return "Financeiro";
		default:
			return null;
		}
	}

}
