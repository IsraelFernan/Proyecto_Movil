package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Interfaces.ApiProductos;
import Model.Producto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfertaP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta_p);
        obtenerDatos();
    }
    private void obtenerDatos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiProductos apiProductos = retrofit.create(ApiProductos.class);
        Call<List<Producto>> call = apiProductos.obtenerDatos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                ArrayList<String> nombres = new ArrayList<>();
                ArrayList<Image> imagenes = new ArrayList<Image>();
                if(!response.isSuccessful()){
                    Toast.makeText(OfertaP.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Producto> datos = response.body();
                ImageView img = findViewById(R.id.img);
                for(Producto post: datos){
                    nombres.add(post.getNombre());
                    /*Image im = Picasso.get().load('"'+post.getImagen()+'"').into(img);*/
                    /*imagenes.add();*/
                }
                /*ArrayAdapter<ImageView> adaptador = new ArrayAdapter<ImageView>(OfertaP.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,imagenes);
                listaProductos.setAdapter(adaptador);*/
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(OfertaP.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void insertarProductos(){
        Producto producto = new Producto();
        producto.nombre = "Pinza inserta";
        producto.precio = 2.25;
        producto.cantidad = 101;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiProductos apiProductos = retrofit.create(ApiProductos.class);
        Call<ResponseBody> call = apiProductos.insertarDatos(producto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}