package mx.edu.utez.adoptame.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.edu.utez.adoptame.validator.NameFormat;
import mx.edu.utez.adoptame.validator.DescriptionFormat;
import mx.edu.utez.adoptame.validator.ParagraphFormat;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @NotBlank
    @NameFormat(message = "Sólo se permiten letras")
    @Column(nullable = false, length = 50)
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;
    
    @Column(columnDefinition = "tinyint not null default 0")
    private Boolean sexo; 

    @NotNull
    @DescriptionFormat(message = "Sólo se permiten números y letras")
    @Size(max = 30, message = "Máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String edad; 
    
    @Column(columnDefinition = "tinyint not null default 0")
    private Boolean tipo; 
    
    @Column(nullable = true)
    private String imagen;
    
    @Column(nullable = false, length = 20)
    @Size(max = 20, message = "Máximo 20 caracteres")
    private String aprobadoRegistro;

    @Column(columnDefinition = "tinyint not null default 1")
    private Boolean  disponibleAdopcion;

    @Column(columnDefinition = "tinyint not null default 1")
    private Boolean activo;

    @NotNull
    @Column(columnDefinition = "longtext null")
    @ParagraphFormat(message = "Caracteres no válidos")
	private String detalles;
    
    @ManyToOne
    @JoinColumn(name = "tamano_id", nullable = false)
    private Tamano tamano;
    
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
    
    @ManyToOne
    @JoinColumn(name = "caracter_id", nullable = false)
    private Caracter caracter;

    @Column(name = "fecha_registro", nullable = false)
    @CreationTimestamp
    private Date fechaRegistro;

    @OneToMany(mappedBy = "mascota")
    private List<Favorito> favoritos;

    @OneToMany(mappedBy = "mascota")
    private List<Solicitud> solicitudes;

    public Mascota() {
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
