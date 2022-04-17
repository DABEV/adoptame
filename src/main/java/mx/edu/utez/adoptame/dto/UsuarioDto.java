package mx.edu.utez.adoptame.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import mx.edu.utez.adoptame.validator.EmailFormat;
import mx.edu.utez.adoptame.validator.NameFormat;
import mx.edu.utez.adoptame.validator.ParagraphFormat;
import mx.edu.utez.adoptame.validator.PhoneNumberFormat;

public class UsuarioDto implements Serializable {
    private Long id;
    
    @NotBlank(message = "Campo requerido")
    @NameFormat(message = "Sólo se permiten letras")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String nombre;    

    @NotBlank(message = "Campo requerido")
    @NameFormat(message = "Sólo se permiten letras")
    @Size(max = 50, message = "Máximo 50 caracteres")
    private String apellidos;    

    @NotBlank(message = "Campo requerido")
    @ParagraphFormat
    @Size(max = 250, message = "Máximo 250 caracteres")
    private String direccion;    

    @NotBlank(message = "Campo requerido")
    @EmailFormat
    private String correo;    

    @NotBlank(message = "Campo requerido")
    @PhoneNumberFormat
    private String telefono;

    private String contrasena;    
    private Date fechaRegistro;
    private Boolean habilitado;
    
    private Set<RolDto> roles;
    private List<DonacionDto> donativos;
    private List<FavoritoDto> favoritos;
    private List<SolicitudDto> solicitudes;
    
    public UsuarioDto() {
        // constructor vacío
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean isHabilitado() {
        return this.habilitado;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Set<RolDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RolDto> roles) {
        this.roles = roles;
    }

    public List<DonacionDto> getDonativos() {
        return donativos;
    }

    public void setDonativos(List<DonacionDto> donativos) {
        this.donativos = donativos;
    }

    public List<FavoritoDto> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<FavoritoDto> favoritos) {
        this.favoritos = favoritos;
    }

    public List<SolicitudDto> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudDto> solicitudes) {
        this.solicitudes = solicitudes;
    }

    // Metodo para agregar roles
    public void addRol(RolDto rol) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        
        roles.add(rol);
    }
    
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", apellidos='" + getApellidos() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", contrasena='" + getContrasena() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", habilitado='" + isHabilitado() + "'" +
            ", roles='" + getRoles() + "'" +
            ", donativos='" + getDonativos() + "'" +
            ", favoritos='" + getFavoritos() + "'" +
            ", solicitudes='" + getSolicitudes() + "'" +
            "}";
    }
}
