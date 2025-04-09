package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import org.acme.validation.ObjetivoValido;

import java.time.LocalDateTime;

@Entity
public class Treino extends PanacheEntity {

    @NotBlank(message = "O nome do treino é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    public String nome;

    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
    public String notas;

    @NotNull(message = "A duração do treino é obrigatório")
    @DecimalMin(value = "0.01", message = "A duração deve ser maior que zero")
    @Column(nullable = false)
    public double duracao;


    public LocalDateTime data = LocalDateTime.now();
    @ObjetivoValido
    public String objetivo;
    public String exercicios;

    // Getters e Setters


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getExercicios() {
        return exercicios;
    }

    public void setExercicios(String exercicios) {
        this.exercicios = exercicios;
    }
}
