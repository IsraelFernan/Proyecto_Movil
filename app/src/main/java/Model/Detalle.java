package Model;

public class Detalle {
    public int idproducto;
    public int idventa;
    public int cantidad;
    public double total;

    public int getIdproducto(){
        return idproducto;
    }
    public int getIdventa(){
        return idventa;
    }
    public int getCantidad(){
        return cantidad;
    }
    public double getTotal(){
        return total;
    }
}