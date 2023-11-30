import java.util.*;
import java.io.*;

class Tarea {
    String nombre;

    public Tarea(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

class AsignacionTareas {
    private Queue<Tarea> colaTareas;

    public AsignacionTareas() {
        this.colaTareas = new LinkedList<>();
    }

    public void asignarTareaEnTxt(String nombreArchivo, String empleado, Tarea tarea) {
        List<String> lineas = new ArrayList<>();

        File archivo = new File(nombreArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            boolean empleadoEncontrado = false;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(empleado)) {
                    empleadoEncontrado = true;
                    String nuevaLinea = linea + "," + tarea.getNombre();
                    lineas.add(nuevaLinea);
                } else {
                    lineas.add(linea);
                }
            }
            if (!empleadoEncontrado) {
                String nuevaLinea = empleado + "," + tarea.getNombre();
                lineas.add(nuevaLinea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String linea : lineas) {
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Queue<Tarea> cargarCola(String nombreArchivo, String empleado) {
        Queue<Tarea> colaTareas = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(empleado)) {
                    for (int i = 1; i < partes.length; i++) {
                        colaTareas.offer(new Tarea(partes[i]));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return colaTareas;
    }

    public void completarTarea(String nombreArchivo, String empleado) {
        colaTareas = cargarCola(nombreArchivo, empleado);

        if (colaTareas.isEmpty()) {
            System.out.println("El empleado no tiene tareas pendientes.");
            return;
        }

        colaTareas.poll();

        File archivoOriginal = new File(nombreArchivo);
        File archivoTemp = new File(nombreArchivo + ".tmp");

        try (Scanner scanner = new Scanner(archivoOriginal);
             PrintWriter writer = new PrintWriter(archivoTemp)) {

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");
                if (partes[0].equals(empleado)) {
                    if (!colaTareas.isEmpty()) {
                        StringBuilder nuevaLinea = new StringBuilder(partes[0]);
                        for (Tarea tarea : colaTareas) {
                            nuevaLinea.append(",").append(tarea.getNombre());
                        }
                        writer.println(nuevaLinea.toString());
                    }
                } else {
                    writer.println(linea);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        archivoOriginal.delete();
        archivoTemp.renameTo(archivoOriginal);
    }

    public int cantidadTareas(String nombreArchivo, String empleado) {
        colaTareas = cargarCola(nombreArchivo, empleado);
        return colaTareas.size();
    }
}