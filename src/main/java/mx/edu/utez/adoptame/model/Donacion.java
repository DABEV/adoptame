package mx.edu.utez.adoptame.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "donaciones")
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Double monto;
    
    @Column(name = "fecha_donacion", nullable = false)
    @CreationTimestamp
    private Date fechaDonacion;
    
    @Column(nullable = true, length = 20)
    private String autorizacion;
    
    @Column(columnDefinition = "tinyint not null default 0")
    private Boolean estado;
    
    @ManyToOne
    @JoinColumn(name = "donador_id", nullable = false)
    private Usuario donador;

    public Donacion() {
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

    public Usuario getDonador() {
        return donador;
    }

    public void setDonador(Usuario donador) {
        this.donador = donador;
    }
    
}
