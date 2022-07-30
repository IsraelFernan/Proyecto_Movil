package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Interfaces.ApiClientes;
import Interfaces.ApiDetalle;
import Interfaces.ApiVentas;
import Model.Venta;
import Model.Detalle;
import Interfaces.ApiProductos;
import Model.ModeloAdaptador;
import Model.Producto;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Oferta extends AppCompatActivity {

    private ListView lista;
    private List<ModeloAdaptador> listaAdaptador = new ArrayList<>();
    Adaptador adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int idCliente = getIntent().getIntExtra("idCliente",0);
        Toast.makeText(this, String.valueOf(idCliente), Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_oferta);
        obtenerDatos();
        ListView listaImg = findViewById(R.id.listProImagenes);
        listaImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(Oferta.this, "Ayyyyyyyyyy a la verga", Toast.LENGTH_SHORT).show();
                Venta venta = new Venta(0,idCliente,0,"ABIERTO");
                agregarVenta(venta);*/
                listarVentas();
            }
        });
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
                    Toast.makeText(Oferta.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Producto> datos = response.body();
                ListView lista = findViewById(R.id.listProImagenes);
                for(Producto post: datos){
                    listaAdaptador.add(new ModeloAdaptador(post.getNombre(),post.getImagen()));
                }
                adaptador = new Adaptador(Oferta.this,R.layout.item,listaAdaptador);
                listaProductos.setAdapter(adaptador);
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Toast.makeText(Oferta.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void listarVentas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiVentas apiVentas = retrofit.create(ApiVentas.class);
        Call<List<Venta>> call = apiVentas.obtenerDatos();
        call.enqueue(new Callback<List<Venta>>() {
            @Override
            public void onResponse(Call<List<Venta>> call, Response<List<Venta>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Oferta.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Venta> datos = response.body();
                Toast.makeText(Oferta.this, "Esto es el size de las ventas"+String.valueOf(datos.size()), Toast.LENGTH_SHORT).show();
                if(datos.size() == 0){
                    int idCliente = getIntent().getIntExtra("idCliente",0);
                    Venta venta = new Venta(0,idCliente,0,"ABIERTO");
                    agregarVenta(venta);
                }else {
                    for (Venta post : datos) {
                        if (post.getEstado().equals("ABIERTO")) {
                            Toast.makeText(Oferta.this, "Debes actualizar no mas.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Oferta.this, "Agrega", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Venta>> call, Throwable t) {
                Toast.makeText(Oferta.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void agregarVenta(Venta venta){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiVentas apiVentas = retrofit.create(ApiVentas.class);
        Call<ResponseBody> call = apiVentas.insertarDatos(venta);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Oferta.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == 200){
                    Toast.makeText(Oferta.this, "Usuario agregado exitosamente.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Oferta.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Oferta.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void agregarDetalle(Detalle detalle){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiDetalle apiDetalle = retrofit.create(ApiDetalle.class);
        Call<ResponseBody> call = apiDetalle.insertarDatos(detalle);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Oferta.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == 200){
                    Toast.makeText(Oferta.this, "Usuario agregado exitosamente.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Oferta.this, "Error.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Oferta.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}