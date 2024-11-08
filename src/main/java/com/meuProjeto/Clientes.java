package com.meuProjeto;

import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "clientes")
public class Clientes {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)  //Delegando a responsabilidade de auto incremneto ao banco, nessa coluna de id;
private int id;

@NotBlank(message = "O nome é obrigatório.")
@Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
private String nome;

@NotBlank(message = "O email é obrigatório.")
@Email(message = "O email deve ser válido.")
private String email;

@PastOrPresent(message = "A data de nascimento deve ser uma data válida (passado ou presente)")
@JsonbTypeAdapter(CustomDateAdapter.class)
private LocalDate nascimento;


public Clientes() {
}

public Clientes(int id, String nome, String email, String nascimento) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.nascimento = nascimento != null ? LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;}


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getNascimento() {
        return nascimento;
    }
    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

 
}
