package model;

import java.util.Date;

public class HistoricoCargo {
    private Cargo cargo;
    private Date dataInicio;
    private Date dataFim;

    public HistoricoCargo(Cargo cargo, Date dataInicio, Date dataFim) {
        this.cargo = cargo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // Getters e Setters
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }
}