package mx.edu.utez.adoptame.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.edu.utez.adoptame.validator.DescriptionFormat;
import mx.edu.utez.adoptame.validator.ParagraphFormat;

public class BlogDto {

    private Long id;

    @Size(max = 50, message = "Máximo 50 caracteres")
    @DescriptionFormat(message = "Sólo se permiten números y letras")
    private String titulo;

    @NotBlank
    @NotNull
    @ParagraphFormat(message = "Caracteres no válidos")
    @Size(max = 200, message = "Máximo 200 caracteres")
    private String contenido;

    private Boolean esPrincipal;
    private String imagen; 
    private Date fechaRegistro;

    public BlogDto() {
        //controlador
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(Boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}
