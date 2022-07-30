package Model;

public class Producto {
    public int idproducto;
    public String nombre;
    public double precio;
    public int cantidad;
    public String imagen;

    public int getIdproducto(){
        return idproducto;
    }
    public String getNombre(){
        return nombre;
    }
    public double getPrecio(){
        return precio;
    }
    public int getCantidad(){
        return cantidad;
    }
    public String getImagen(){return imagen;}
}
