import java.io.*;
import java.util.List;

class ManejoArchivos {
    // Método para cargar datos desde un archivo .txt
    public static <T> void cargarDatos(String rutaArchivo, Gestion<T> gestion) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                gestion.agregarDesdeTexto(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para guardar datos en un archivo .txt sin eliminar los existentes
    public static <T> void guardarDatos(String rutaArchivo, Gestion<T> gestion) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            for (T elemento : gestion.obtenerTodos()) {
                bw.write(gestion.convertirATexto(elemento));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void guardarDatos(String nombreArchivo, RegistroAsistencia registroAsistencia) {
            // ... (código existente)
        }

        public static void cargarDatos(String nombreArchivo, RegistroAsistencia registroAsistencia) {
            // ... (código existente)
        }

        public static void cargarDatosEmpleados(String nombreArchivo, GestionEmpleados gestionEmpleados) {
            try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    String[] partes = linea.split(", ");
                    if (partes.length == 2) {
                        String claveEmpleado = partes[0].substring(partes[0].indexOf(":") + 2);
                        String nombreEmpleado = partes[1].substring(partes[1].indexOf(":") + 2);
                        gestionEmpleados.agregarDesdeTexto(claveEmpleado + ", " + nombreEmpleado);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    

}
