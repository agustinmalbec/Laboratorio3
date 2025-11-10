package com.example.laboratorio3.model;

import java.io.Serializable;

public class Pago implements Serializable {
    private int idPago;
    private int idContrato;
    private Contrato contrato;
    private int mes;
    private int monto;
    private String detalle;
    public Pago() {}

    public Pago(int idPago, int idContrato, Contrato contrato, int mes, int monto, String detalle) {
        this.idPago = idPago;
        this.idContrato = idContrato;
        this.contrato = contrato;
        this.mes = mes;
        this.monto = monto;
        this.detalle = detalle;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}