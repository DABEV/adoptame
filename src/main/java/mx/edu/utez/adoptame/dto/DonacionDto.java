package mx.edu.utez.adoptame.dto;

import java.io.Serializable;
import java.util.Date;

public class DonacionDto implements Serializable {
    private Long id;
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
