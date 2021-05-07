package filtros;

import filters.Filter;
import filters.Pipe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class LectorDelDirectorio extends Filter {

    String nombreDelArchivo;
    HashSet<String> salidas = new HashSet<String>();

    public LectorDelDirectorio(Pipe filtrolector, String nombre ,Pipe outputDelFiltroLector) {
        super(filtrolector,outputDelFiltroLector);
        nombreDelArchivo = nombre;
    }

    @Override
    public void transform() {
        try {
            String[] rutas = input.read().trim().split("\n");
            for (String ruta:rutas) {
                File folderFile = new File(ruta);
                if (folderFile.exists()) {
                    File[] archivosEnCarpeta = folderFile.listFiles();
                    obtenerDirectorio(archivosEnCarpeta);
                }
                for (String rutasEncontradas: salidas) {
                    output.write(rutasEncontradas + "\n");
                   // System.out.println(rutasEncontradas);
                }
            }

            output.closeWriter();
        }catch (IOException e){ e.printStackTrace(); }
    }

    private void obtenerDirectorio(File[] arregloDeArchivos) throws IOException {
        for (File archivo : arregloDeArchivos) {
            boolean esCarpeta = archivo.isDirectory();
            if (esCarpeta) {
                File[] files = archivo.listFiles();
                obtenerDirectorio(files);
            }
            if (archivo.getName().contains(nombreDelArchivo)){
                salidas.add(archivo.getAbsolutePath());
            }
        }
    }
}
