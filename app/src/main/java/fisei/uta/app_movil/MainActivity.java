package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import Interfaces.ApiClientes;
import Model.Clientes;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button ingresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingresar = findViewById(R.id.ingresar);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCliente();
            }
        });
    }

    public void verificarCliente(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClientes apiClientes = retrofit.create(ApiClientes.class);
        Call<List<Clientes>> call = apiClientes.obtenerDatos();
        call.enqueue(new Callback<List<Clientes>>() {
            @Override
            public void onResponse(Call<List<Clientes>> call, Response<List<Clientes>> response) {
                TextView id;
                TextView clave;
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Clientes> datos = response.body();
                ArrayList<String> cedulas = new ArrayList<>();
                id = findViewById(R.id.id);
                clave = findViewById(R.id.clave);
                int j=0;
                int idCliente = 0;
                for(Clientes post: datos){
                    cedulas.add(post.getCedula());
                    if(post.getCedula().equals(id.getText().toString()) && post.getClave().equals(clave.getText().toString())){
                        idCliente += post.getId();
                        j++;
                    }
                }
                if(j>0){
                    Intent intent = new Intent(MainActivity.this, Imagenes.class);
                    intent.putExtra("idCliente",idCliente);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o Clave mal ingresados.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Clientes>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}