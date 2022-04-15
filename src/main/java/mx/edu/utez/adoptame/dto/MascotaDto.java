package mx.edu.utez.adoptame.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.edu.utez.adoptame.validator.NameFormat;
import mx.edu.utez.adoptame.model.Caracter;
import mx.edu.utez.adoptame.model.Color;
import mx.edu.utez.adoptame.model.Favorito;
import mx.edu.utez.adoptame.model.Solicitud;
import mx.edu.utez.adoptame.model.Tamano;
import mx.edu.utez.adoptame.validator.DescriptionFormat;
import mx.edu.utez.adoptame.validator.ParagraphFormat;

public class MascotaDto {
    private Long id;
    
    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    @NameFormat(message = "Sólo se permiten letras")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;
    
    private Boolean sexo; 

    @NotNull(message = "Campo requerido")
    @NotBlank(message = "Campo requerido")
    @DescriptionFormat(message = "Sólo se permiten números y letras")
    @Size(max = 30, message = "Máximo 30 caracteres")
    private String edad; 
    
    private Boolean tipo; 
    
    private String imagen;
    
    @Size(max = 20, message = "Máximo 20 caracteres")
    private String aprobadoRegistro;

    private Boolean  disponibleAdopcion;

    private Boolean activo;

    @ParagraphFormat(message = "Caracteres no válidos")
	private String detalles;

    private Tamano tamano;
    private Color color;
    private Caracter caracter;
    private Date fechaRegistro;
    private List<Favorito> favoritos;
    private List<Solicitud> solicitudes;

    public MascotaDto() {
        //Constructor vacío de mascota
    }

    
    public Boolean getActivo() {
        return activo;
    }


    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


    public Boolean getDisponibleAdopcion() {
        return disponibleAdopcion;
    }


    public void setDisponibleAdopcion(Boolean disponibleAdopcion) {
        this.disponibleAdopcion = disponibleAdopcion;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getSexo() {
        return sexo;
    }

    public void setSexo(Boolean sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
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

    public String getAprobadoRegistro() {
        return aprobadoRegistro;
    }

    public void setAprobadoRegistro(String aprobadoRegistro) {
        this.aprobadoRegistro = aprobadoRegistro;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Tamano getTamano() {
        return tamano;
    }

    public void setTamano(Tamano tamano) {
        this.tamano = tamano;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Caracter getCaracter() {
        return caracter;
    }

    public void setCaracter(Caracter caracter) {
        this.caracter = caracter;
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos) {
        this.favoritos = favoritos;
    }

    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<Solicitud> solicitudes) {
        this.solicitudes = solicitudes;
    }
}
