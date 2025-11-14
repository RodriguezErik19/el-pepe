package utilerias;

import java.util.Date;
import java.util.Calendar;

public class Fecha {
    private int anio;
    private int mes;
    private int dia;
    
    //codigo del semestre pasado ya que no vimos en clase ninguno de fecha
    public Fecha() {
        
        Calendar calendario = Calendar.getInstance();
        
        this.anio = calendario.get(Calendar.YEAR);
        this.mes = calendario.get(Calendar.MONTH) + 1; 
        this.dia = calendario.get(Calendar.DAY_OF_MONTH);
      
    }
    
    // metodos para asegurar que num <10 tengan un cero al inicio 
    private String padConCero(int valor) {
        if (valor < 10) {
            return "0" + valor;
        }
        return String.valueOf(valor);
    }
    
   
    @Override
    public String toString() {
        String fechaStr = this.anio + "-" + 
                          padConCero(this.mes) + "-" + 
                          padConCero(this.dia);
                         
        return fechaStr + " ";
    }

   
    
    public int getAnio() {
     return anio;   
     }
    public int getMes() { 
        return mes; 
    }
    public int getDia() {
         return dia;
         }
   
}