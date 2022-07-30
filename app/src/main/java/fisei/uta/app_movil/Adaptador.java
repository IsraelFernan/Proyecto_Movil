package fisei.uta.app_movil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import Model.ModeloAdaptador;

public class Adaptador extends ArrayAdapter<ModeloAdaptador> {

    private List<ModeloAdaptador> lista;
    private Context contexto;
    private int resourceLayout;
    public Adaptador(@NonNull Context context, int resource, List<ModeloAdaptador> objets) {
        super(context, resource,objets);
        this.lista = objets;
        this.contexto = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view==null) {
            view = LayoutInflater.from(contexto).inflate(resourceLayout, null);
        }
        ModeloAdaptador modeloAdaptador = lista.get(position);
        ImageView img = view.findViewById(R.id.imgItem);
        Picasso.get().load(modeloAdaptador.getImagen()).into(img);
        TextView texto = view.findViewById(R.id.textoItem);
        texto.setText(modeloAdaptador.getNombre());
        return view;
    }
}
