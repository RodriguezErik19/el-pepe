package datos;

/**
 * 
 */

public class Estudiante {
	//Atributos
	private int codigo;
	private String nombre;
	private Email email;
	private Fecha fechaNac;
	private String sexo;
	private String programa;
	//Metodos
	//Constructor parametrizado
	
	public Estudiante() {
		
	}
	public Estudiante(int codigo, String nombre, Email email, Fecha fechaNac, String sexo, String programa) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.email = email; 
		this.fechaNac = fechaNac;
		this.sexo = sexo;
		this.programa = programa;
	}
	
	//getter y setter
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Fecha getFechaNac() {
		return fechaNac;
	}
	public void setFechaNac(Fecha fechaNac) {
		this.fechaNac = fechaNac;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getPrograma() {
		return programa;
	}
	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public Email getEmail() {
        return email;
    }

    public void setEmail(Email correoElectronico) {
        this.email = correoElectronico; // Validar el correo electrónico
    }
	@Override
	public String toString() {
		return codigo + ", " + nombre + ", " + email + ", " + fechaNac
				+ ", " + sexo + ", " + programa + "\n";
	}
	

}
