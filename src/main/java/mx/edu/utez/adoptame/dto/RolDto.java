package mx.edu.utez.adoptame.dto;

import java.io.Serializable;
import java.util.Objects;

public class RolDto implements Serializable {
    private Long id;
    private String nombre;    

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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RolDto)) {
            return false;
        }
        RolDto rol = (RolDto) o;
        return Objects.equals(id, rol.id) && Objects.equals(nombre, rol.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
