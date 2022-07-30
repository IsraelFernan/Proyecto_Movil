package Model;

public class Venta {
    private int idcliente;
    private int total;
    private String estado;

    public Venta(int idcliente, int total, String estado) {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
