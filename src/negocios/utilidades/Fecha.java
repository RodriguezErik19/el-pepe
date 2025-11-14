package negocios.utilidades;
public class Fecha{
    private int dia;
    private int mes;
    private int anio;

    public Fecha(){

    }

    public Fecha(int dia, int mes, int anio) {
        if (esFechaValida(dia, mes, anio)) {
            this.dia = dia;
            this.mes = mes;
            this.anio = anio;
        } else {
            throw new FechaInvalidaException("Fecha no válida");
            
        }


    }

    public Fecha(String cadena){
        try {
            // buscamos la primera ocurrencia de '/'
            int pos1 = cadena.indexOf('/');
            // buscamos la ultima ocurrencia de '/'
            int pos2 = cadena.lastIndexOf('/');
            // procesamos el dia
            String sDia = cadena.substring(0, pos1);
            dia = Integer.parseInt(sDia);
            // procesamos el mes
            String sMes = cadena.substring(pos1 + 1, pos2);
            mes = Integer.parseInt(sMes);
            // procesamos el año
            String sAnio = cadena.substring(pos2 + 1);
            anio = Integer.parseInt(sAnio);

            if (!esFechaValida(dia, mes, anio)) {
                throw new FechaInvalidaException("Fecha no válida");
            }
        } catch (Exception e) {
            throw new FechaInvalidaException("Formato de fecha no válido. Use 'dd/mm/yyyy'.", e);
        }

    }

    private boolean esFechaValida(int dia, int mes, int anio) {
        if (mes < 1 || mes > 12) {
            return false;
        }
        if (dia < 1 || dia > diasEnMes(mes, anio)) {
            return false;
        }
        return true;
    }

    private int diasEnMes(int mes, int anio) {
        switch (mes) {
            case 2:
                return (esBisiesto(anio)) ? 29 : 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    private boolean esBisiesto(int anio) {
        if (anio % 4 == 0) {
            if (anio % 100 == 0) {
                return anio % 400 == 0;
            } else {
                return true;
            }
        }
        return false;
    }



    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        if (esFechaValida(dia, this.mes, this.anio)) {
            this.dia = dia;
        } else {
            throw new FechaInvalidaException("Día no válido");
        }
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        if (esFechaValida(this.dia, mes, this.anio)) {
            this.mes = mes;
        } else {
            throw new FechaInvalidaException("Mes no válido");
        }
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        if (esFechaValida(this.dia, this.mes, anio)) {
            this.anio = anio;
        } else {
            throw new FechaInvalidaException("Año no válido");
        }
    }

    @Override
    public String toString() {
       
        return String.format("%02d/%02d/%04d", dia, mes, anio);
    }

    @Override
    public boolean equals(Object obj) {
        //convertir el objeto obj de tipo Object a un objeto fecha
        Fecha otra = (Fecha) obj;
        if((this.dia == otra.dia ) &&(this.mes == otra.mes) && (this.anio == otra.anio))
           return true;
        else
           return false;
           

        
    }
}