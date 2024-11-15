package com.meuProjeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "noticias")

public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(max = 1000, message = "A descrição não pode ter mais que 1000 caracteres")
    private String descricao;

    @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$", message = "URL inválida")
    private String url;

    @Column(name = "processada", nullable = false)
    private boolean processada;

    public Noticia() {
    }

    public Noticia(String titulo, String descricao, String url, boolean processada) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.processada = processada;
    
    }    

    public long getId() {
        return id;
    }
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isProcessada(boolean processada){
        return processada;
    }
        
    public void setProcessada(boolean processada) {
        this.processada = processada;
    }
            
}
