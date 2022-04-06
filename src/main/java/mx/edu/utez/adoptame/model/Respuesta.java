package mx.edu.utez.adoptame.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "respuestas")
public class Respuesta {
    @Id
    private Integer codigo;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(nullable = true)
    private String mensaje;
    
    public Respuesta() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
