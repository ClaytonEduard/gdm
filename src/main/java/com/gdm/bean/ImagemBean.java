package com.gdm.bean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean

@RequestScoped
public class ImagemBean {
	@ManagedProperty("#{param.caminho}")
	private String caminho;
	// classe do prime para guarda os bits da foto
	private StreamedContent foto;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getFoto() throws IOException {
		if (caminho == null || caminho.isEmpty()) {
			// caminho deve ser alterado de acordo com o endereco do site
			Path path = Paths.get("C:/Multas 1.1/Uploads/caminhoes/branco.png");
//			Path path = Paths.get("webapps/gdm/resources/caminhoes/branco.png");  
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		} else {
			Path path = Paths.get(caminho);
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		}
		return foto;

	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
