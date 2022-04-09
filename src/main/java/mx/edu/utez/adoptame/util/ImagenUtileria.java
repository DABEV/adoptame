package mx.edu.utez.adoptame.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ImagenUtileria {
    
    private ImagenUtileria() {
    }

    public static String guardarImagen(MultipartFile multipartFile, String ruta) {

        String nombreImagen = multipartFile.getOriginalFilename();
        try {
            String rutaArchivo = ruta + "/" + nombreImagen;
            File imagen = new File(rutaArchivo);
            multipartFile.transferTo(imagen);
            return nombreImagen;
        } catch (IOException e) {
            return "null";
        }

    }
}