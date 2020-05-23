package com.gdm.enumeracao;

public enum TipoUsuario {
	ADMINISTRADOR, GERENTE, PROGRAMADOR, USUARIO;
	// A, G, P, U;

	@Override
	public String toString() {
		switch (this) {
		case ADMINISTRADOR:
			return "Administrador";
		case GERENTE:
			return "Gerente";
		case USUARIO:
			return "Usuário";
		case PROGRAMADOR:
			return "Programador";
		default:
			return null;
		}
	}

}
