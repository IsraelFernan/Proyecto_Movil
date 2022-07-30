package Interfaces;

import java.util.List;
import Model.Producto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiProductos {
    @GET("Productos")
    Call<List<Producto>> obtenerDatos();

    @POST("Productos")
    Call<ResponseBody> insertarDatos(@Body Producto producto);
}
