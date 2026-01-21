package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    private Integer id;
    private String nome;
    private String email;
    private Date dataAniversario;
    private Double salario;

    private Department departamento;

    public Seller() {
    }

    public Seller(Integer id, String nome, String email, Date dataAniversario, Double salario, Department departamento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataAniversario = dataAniversario;
        this.salario = salario;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(Date dataAniversario) {
        this.dataAniversario = dataAniversario;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Department getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Department departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataAniversario=" + dataAniversario +
                ", salario=" + salario +
                ", departamento=" + departamento +
                '}';
    }
}
