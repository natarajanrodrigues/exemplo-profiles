package br.edu.ifpb.praticas.teste;

import java.util.Calendar;
import java.util.Date;

/**
 * @author diogomoreira.
 */
public class NotaFiscal {

    private String cliente;
    private double valor;
    private Date data;

    public NotaFiscal() {
    }

    public NotaFiscal(String cliente, double valor, Date data) {
        this.cliente = cliente;
        this.valor = valor;
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public double getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
