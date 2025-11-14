package negocios;


public interface Criterio<T>{
    public abstract int comparar(T a, T b);

}