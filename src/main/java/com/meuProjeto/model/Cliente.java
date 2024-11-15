package com.meuProjeto.model;

import jakarta.persistence.GenerationType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.meuProjeto.util.CustomDateAdapter;
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
public class Cliente {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
        private String nome;

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email deve ser válido.")
        private String email;

        @PastOrPresent(message = "A data de nascimento deve ser uma data válida (passado ou presente)")
        @JsonbTypeAdapter(CustomDateAdapter.class)
        private LocalDate nascimento;

        public Cliente() {
        }

        public Cliente( String nome, String email, String nascimento) {
            this.nome = nome;
            this.email = email;
            this.nascimento = nascimento != null ? LocalDate.parse(nascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
        }

        //Adicionando esse contrutor para poder aceitar o formato correto, caso não passe, ele chamará o construtor acima;
        public Cliente(String nome, String email, LocalDate nascimento) {
            this.nome = nome;
            this.email = email;
            this.nascimento = nascimento;
        }

        public boolean isAniversariante() {
            return nascimento != null && nascimento.getDayOfMonth() == LocalDate.now().getDayOfMonth() &&
            nascimento.getMonth() == LocalDate.now().getMonth();
        }

        public Long getId() {
            return id;
        }

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
 

