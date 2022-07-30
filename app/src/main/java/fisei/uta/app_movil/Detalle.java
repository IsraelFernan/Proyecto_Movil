package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import Interfaces.ApiDetalle;
import Interfaces.ApiProductos;
import Model.ModeloAdaptador;
import Model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
    }

    public void obtenerDetalle(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiDetalle apiDetalle = retrofit.create(ApiDetalle.class);
        Model.Detalle Detalle = new Model.Detalle();
        Call<List<Model.Detalle>> call = apiDetalle.obtenerDatos();
        call.enqueue(new Callback<List<Model.Detalle>>() {
            @Override
            public void onResponse(Call<List<Model.Detalle>> call, Response<List<Model.Detalle>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Detalle.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Model.Detalle> datos = response.body();
                ListView lista = findViewById(R.id.listProImagenes);
                for(Model.Detalle post: datos){
                }
            }

            @Override
            public void onFailure(Call<List<Model.Detalle>> call, Throwable t) {
                Toast.makeText(Detalle.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}