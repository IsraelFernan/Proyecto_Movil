package fisei.uta.app_movil;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import Model.Clientes;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import Interfaces.ApiClientes;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {
    ImageView regresar;
    Button registrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        regresar = findViewById(R.id.btnRegresar);
        registrarse = findViewById(R.id.btnReRegistrar);
        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXf9JRh1mElsELSs2oBdJl0mp4FND2aUB1bA&usqp=CAU").into(regresar);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(Registro.this,MainActivity.class);
                startActivity(i);
            }
        });
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCliente();
            }
        });
    }

    public boolean verificarCedulaPAAV(String x) {
        int suma = 0;
        if (x.length() == 9) {
            return false;
        } else {
            int a[] = new int[x.length() / 2];
            int b[] = new int[(x.length() / 2)];
            int c = 0;
            int d = 1;
            for (int i = 0; i < x.length() / 2; i++) {
                a[i] = Integer.parseInt(String.valueOf(x.charAt(c)));
                c = c + 2;
                if (i < (x.length() / 2) - 1) {
                    b[i] = Integer.parseInt(String.valueOf(x.charAt(d)));
                    d = d + 2;
                }
            }
            for (int i = 0; i < a.length; i++) {
                a[i] = a[i] * 2;
                if (a[i] > 9) {
                    a[i] = a[i] - 9;
                }
                suma = suma + a[i] + b[i];
            }
            int aux = suma / 10;
            int dec = (aux + 1) * 10;
            if(x.equals("")) {
                return false;
            }else if ((dec - suma) == Integer.parseInt(String.valueOf(x.charAt(x.length() - 1)))) {
                return true;
            }else if (suma % 10 == 0 && x.charAt(x.length() - 1) == '0') {
                return true;
            } else {
                return false;
            }
        }
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
                if(!response.isSuccessful()){
                    Toast.makeText(Registro.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Clientes> datos = response.body();
                ArrayList<String> cedulas = new ArrayList<>();
                TextView Viewnombre = findViewById(R.id.TextReNombre);
                String nombre = String.valueOf(Viewnombre.getText());
                TextView Viewapellido = findViewById(R.id.TextReApellido);
                String apellido = String.valueOf(Viewapellido.getText());
                TextView Viewcedula = findViewById(R.id.TextReCedula);
                String cedula = String.valueOf(Viewcedula.getText());
                TextView Viewemail = findViewById(R.id.TextReEmail);
                String email = String.valueOf(Viewemail.getText());
                TextView ViewClave = findViewById(R.id.TextReClave);
                String clave = String.valueOf(ViewClave.getText());
                int j=0;
                for(Clientes post: datos){
                    cedulas.add(post.getCedula());
                    if(post.getCedula().equals(cedula) || post.getEmail().equals(email)){
                        j++;
                    }
                }
                boolean respuesta = verificarCedulaPAAV(cedula);
                if(nombre.equals("") || apellido.equals("") || cedula.equals("") || email.equals("") || clave.equals("")){
                    Toast.makeText(Registro.this, "Todos los datos son requeridos.", Toast.LENGTH_SHORT).show();
                } else if(respuesta == false){
                    Toast.makeText(Registro.this, "Cédula mal ingresada.", Toast.LENGTH_SHORT).show();
                }else if(j>0){
                    Toast.makeText(Registro.this, "Usuario ya registrado.", Toast.LENGTH_SHORT).show();
                }else{
                    Clientes clientes = new Clientes(0,nombre,apellido,cedula,email,clave);
                    agregarCliente(clientes);
                }

            }

            @Override
            public void onFailure(Call<List<Clientes>> call, Throwable t) {
                Toast.makeText(Registro.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void agregarCliente(Clientes clientes){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.israel.somee.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiClientes apiClientes = retrofit.create(ApiClientes.class);
        Call<ResponseBody> call = apiClientes.insertarDatos(clientes);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Registro.this,"Codigo: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == 200){
                    Toast.makeText(Registro.this, "Usuario agregado exitosamente.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Registro.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Registro.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}