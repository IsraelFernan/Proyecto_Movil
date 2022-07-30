package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import Model.Venta;
import Model.Detalle;
import Interfaces.ApiDetalle;
import Interfaces.ApiProductos;
import Model.ModeloAdaptador;
import Model.Producto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Imagenes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lista;
    private List<ModeloAdaptador> listaAdaptador = new ArrayList<>();
    Adaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int idCliente = getIntent().getIntExtra("idCliente",0);
        Toast.makeText(this, String.valueOf(idCliente), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_imagenes);
        obtenerDatos();
        findViewById(R.id.listProImagenes);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                ListView listaProductos = findViewById(R.id.listProImagenes);
                if(!response.isSuccessful()){
                    Toast.makeText(Imagenes.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Producto> datos = response.body();
                ListView lista = findViewById(R.id.listProImagenes);
                lista.setOnItemClickListener(Imagenes.this);
                for(Producto post: datos){
                    listaAdaptador.add(new ModeloAdaptador(post.getNombre(),post.getImagen()));
                }
                adaptador = new Adaptador(Imagenes.this,R.layout.item,listaAdaptador);
                listaProductos.setAdapter(adaptador);
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(Imagenes.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void agregarVenta(int idcliente){
        Venta venta = new Venta(idcliente,0,'ABIERTO');

    }

    public void agregarDetalle(int idcliente){
        Detalle detalle = new Detalle();
    }

}