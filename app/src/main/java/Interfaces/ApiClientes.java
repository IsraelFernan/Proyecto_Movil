package Interfaces;

import java.util.List;
import Model.Clientes;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiClientes {
    @GET("Clientes")
    Call<List<Clientes>> obtenerDatos();

    @POST("Clientes")
    Call<ResponseBody> insertarDatos(@Body Clientes clientes);
}
