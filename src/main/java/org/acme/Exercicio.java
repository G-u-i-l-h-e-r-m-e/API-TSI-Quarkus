package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;

@Entity
public class Exercicio extends PanacheEntity {

    @NotBlank(message = "O nome do treino é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    public String nome;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
    public String descricao;


    public String musculos_principais;
    public String musculos_secundarios;
    public String tipo;
    public String instrucoes;

    // Getters e Setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMusculos_principais() {
        return musculos_principais;
    }

    public void setMusculos_principais(String musculos_principais) {
        this.musculos_principais = musculos_principais;
    }

    public String getMusculos_secundarios() {
        return musculos_secundarios;
    }

    public void setMusculos_secundarios(String musculos_secundarios) {
        this.musculos_secundarios = musculos_secundarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }
}
