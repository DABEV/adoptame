package mx.edu.utez.adoptame.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

public class DonacionDto implements Serializable {
    private Long id;
    @DecimalMax(value = "999999.99", message = "El monto no debe de ser mayor a $9,999,99.99.")
    @DecimalMin(value = "0.5", message = "El monto no debe de ser mayor a $0.5.")
    @Digits(integer = 8, fraction = 2, message = "El monto solo debe de tener dos dígitos decimales.")
    private Double monto;
    private Date fechaDonacion;
    private String autorizacion;
    private Boolean estado;
    private UsuarioDto donador;

    public DonacionDto() {
        // Constructor vacío
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Date getFechaDonacion() {
        return fechaDonacion;
    }

    public void setFechaDonacion(Date fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public UsuarioDto getDonador() {
        return donador;
    }

    public void setDonador(UsuarioDto donador) {
        this.donador = donador;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", monto='" + getMonto() + "'" +
            ", fechaDonacion='" + getFechaDonacion() + "'" +
            ", autorizacion='" + getAutorizacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", donador='" + getDonador() + "'" +
            "}";
    }
}
