package com.example.ubigeo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ubigeo.entidad.Pedido;
import com.example.ubigeo.service.ServiceRest;
import com.example.ubigeo.util.ConnectionRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class MainActivity extends AppCompatActivity {

        Spinner spnReg, spnEnt,spnLug;
        ServiceRest servicio;
        String selReg, selEnt;
        int idPedido = -1;
        ArrayAdapter<String> adapterReg,adapterEnt;
        ArrayAdapter<Pedido> adapterLug;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            adapterReg =  new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item);
            adapterReg.add("[ Seleccione Fecha Registro ]");
            spnReg =  findViewById(R.id.spnFechaRegistro);
            spnReg.setAdapter(adapterReg);

            adapterEnt =  new ArrayAdapter<String>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item);
            adapterEnt.add("[ Seleccione Fecha Entrega ]");
            spnEnt =  findViewById(R.id.spnFechaEntrega);
            spnEnt.setAdapter(adapterEnt);

            adapterLug =  new ArrayAdapter<Pedido>(MainActivity.this,R.layout.support_simple_spinner_dropdown_item);
            adapterLug.add(new Pedido(0,"","","[ Selecciona Lugar Entrega ]"));
            spnLug =  findViewById(R.id.spnLugarEntrega);
            spnLug.setAdapter(adapterLug);

            servicio = ConnectionRest.getConnection().create(ServiceRest.class);
            cargaFechaRegistro();

            spnReg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){ //Si no es el primer elemento
                        selReg = spnReg.getSelectedItem().toString();
                        cargaFechaEntrega(selReg);
                    }else{
                        adapterEnt.clear();
                        adapterEnt.add("[ Selecciona Fecha Entrega ]");
                        adapterEnt.notifyDataSetChanged();
                        adapterLug.clear();
                        adapterLug.add(new Pedido(0,"","","[ Selecciona Lugar entrega ]"));
                        adapterLug.notifyDataSetChanged();
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            spnEnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){ //Si no es el primer elemento
                        selEnt = spnEnt.getSelectedItem().toString();
                        carga(selReg, selEnt);
                    }else{
                        adapterLug.clear();
                        adapterLug.add(new Pedido(0,"","","[ Selecciona Lugar Entrega ]"));
                        adapterLug.notifyDataSetChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            spnLug.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position != 0){
                        idPedido = adapterLug.getItem(position).getIdPedido();
                        String mensaje = "Pedido : " + idPedido ;
                        mostrarMensaje("Selección de Datos", mensaje);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        public void cargaFechaRegistro(){
            Call<List<String>> call = servicio.listarFechaRegisto();
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    List<String> lista = response.body();
                    if(lista != null){
                        adapterReg.addAll(lista);
                        adapterReg.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("ERROR", "onFailure: ", t);
                }
            });
        }


        public void cargaFechaEntrega(String dep){
            Log.e("INFO", "->"+  dep +"<-");
            Call<List<String>> call = servicio.listarFechaEntrega(dep);
            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    List<String> lista = response.body();
                    if(lista != null){
                        adapterEnt.clear();
                        adapterEnt.add("[ Selecciona Fecha Entrega ]");
                        adapterEnt.addAll(lista);
                        adapterEnt.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("ERROR", "onFailure: ", t);
                }
            });
        }


        public void carga(String dep, String dis){
            Log.e("INFO", "->"+  dep +"<-");
            Log.e("INFO", "->"+  dis +"<-");
            Call<List<Pedido>> call = servicio.listarLugarEntrega(dep, dis);
            call.enqueue(new Callback<List<Pedido>>() {
                @Override
                public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                    List<Pedido> lista = response.body();
                    if(lista != null){
                        adapterLug.clear();
                        adapterLug.add(new Pedido(0,"","","[ Selecciona Lugar entrega ]"));
                        adapterLug.addAll(lista);
                        adapterLug.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<Pedido>> call, Throwable t) {
                    Log.e("ERROR", "onFailure: ", t);
                }
            });
        }


        public void mostrarMensaje(String titulo, String msg){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(msg);
            alertDialog.setTitle(titulo);
            alertDialog.setCancelable(true);
            alertDialog.show();
        }

    }

