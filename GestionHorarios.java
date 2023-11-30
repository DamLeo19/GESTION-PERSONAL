import java.util.HashMap;

class Horario {
    String claveEmpleado;  // Nueva propiedad para asociar el horario con un empleadoa
    String dia;
    String horaInicio;
    String horaFin;


    public Horario(String claveEmpleado, String dia, String horaInicio, String horaFin) {
        this.claveEmpleado = claveEmpleado;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Clave Empleado: " + claveEmpleado + ", Día: " + dia + ", Hora de inicio: " + horaInicio
                + ", Hora de fin: " + horaFin;
    }
}

class GestionHorarios implements Gestion<Horario> {
    HashMap<String, Horario> horarios;

    public GestionHorarios() {
        horarios = new HashMap<>();
    }

    public void agregarHorario(String claveEmpleado, String claveHorario, Horario horario) {
        String clave = claveEmpleado + "-" + claveHorario;  // Utilizando una clave única
        horarios.put(clave, horario);
    }

    public Horario obtenerHorario(String claveEmpleado, String claveHorario) {
        String clave = claveEmpleado + "-" + claveHorario;
        return horarios.get(clave);
    }

    public void mostrarTodosHorarios() {
        System.out.println("\n==== Todos los Horarios ====");
        for (Horario horario : horarios.values()) {
            System.out.println(horario);
        }
    }

    @Override
    public void agregarDesdeTexto(String texto) {
        String[] partes = texto.split(", ");
        if (partes.length == 5) {
            String claveEmpleado = partes[0].substring(partes[0].indexOf(":") + 2);
            Horario horario = new Horario(
                    claveEmpleado,
                    partes[1].substring(partes[1].indexOf(":") + 2),
                    partes[2].substring(partes[2].indexOf(":") + 2),
                    partes[3].substring(partes[3].indexOf(":") + 2)
            );
            agregarHorario(claveEmpleado, horario.dia, horario);
        }
    }

    @Override
    public String convertirATexto(Horario horario) {
        return horario.claveEmpleado + ", " + horario.toString();
    }

    @Override
    public Iterable<Horario> obtenerTodos() {
        return horarios.values();
    }
}
