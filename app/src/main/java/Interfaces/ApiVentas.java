package Interfaces;

import java.util.List;
import Model.Venta;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiVentas {
    @GET("Factura")
    Call<List<Venta>> obtenerDatos();

    @POST("Factura")
    Call<ResponseBody> insertarDatos(@Body Venta venta);
}
