package Interfaces;

import java.util.List;
import Model.Detalle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiDetalle {
    @GET("Detalle")
    Call<List<Detalle>> obtenerDatos();

    @POST("Detalle")
    Call<ResponseBody> insertarDatos(@Body Detalle detalle);
}
