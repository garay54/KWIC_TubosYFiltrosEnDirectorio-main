package filtros;

/*Alumnos: Eduardo Alfonso Huerta Mora, Mario Alberto Roman y David Tejeda
 * Materia: Arquitectura de Software
 * Proyecto: KWIC Directorio
 * Docente: Dra. María Lucía Barrón Estrada*/

import filters.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            //medium input para ingresar la ruta a trabajar
            String ruta;
            JFileChooser jFC = new JFileChooser();
            jFC.setDialogTitle("KWIC - Seleccione el archivo de entrada deseado");
            jFC.setCurrentDirectory(new File("src"));
            int res = jFC.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                ruta = jFC.getSelectedFile().getPath();
            } else {
                ruta = "src/ruta.txt";
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese palabra a buscar: ");
            String nombre = scanner.nextLine();
            System.out.println("Palabra a buscar: \""+nombre+"\"");

            Pipe out2Lector = new Pipe();
            Pipe lectorToAlpha = new Pipe();
            Pipe alphaToOutput = new Pipe();

            Filter input = new Input(ruta, out2Lector);
            Filter in = new LectorDelDirectorio(out2Lector,nombre,lectorToAlpha);
            Filter alpha = new Alphabetizer(lectorToAlpha, alphaToOutput);
            Filter out = new Output(alphaToOutput);

            input.start();
            in.start();
            alpha.start();
            out.start();

        }catch (IOException e){
            System.err.println("No se pudo leer el archivo");
        }
    }
}
