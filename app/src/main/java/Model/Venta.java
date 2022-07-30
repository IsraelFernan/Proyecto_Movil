package Model;

public class Venta {
    private int idventa;
    private int idcliente;
    private float total;
    private String estado;

    public Venta(int idventa,int idcliente, float total, String estado) {
        this.idventa = idventa;
        this.idcliente = idcliente;
        this.total = total;
        this.estado = estado;
    }
    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }
}
