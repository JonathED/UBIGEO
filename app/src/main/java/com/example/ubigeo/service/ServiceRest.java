package com.example.ubigeo.service;

import com.example.ubigeo.entidad.Pedido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceRest {

     //   @GET("departamentos")
       // Call<List<String>> listaDepartamentos();

        //@GET("provincias/{dep}")
        //Call<List<String>> listaProvincias(@Path("dep")String idDep);

        //@GET("distritos/{dep}/{pro}")
        //Call<List<Ubigeo>> listaDistritos(@Path("dep")String idDep,@Path("pro")String idPro);


        @GET("fechaRegistro")
        Call<List<String>> listarFechaRegisto();

        @GET("fechaEntrega/{reg}")
        Call<List<String>> listarFechaEntrega(@Path("reg")String idReg);

        @GET("lugarEntrega/{reg}/{lug}")
        Call<List<Pedido>> listarLugarEntrega(@Path("reg")String idReg, @Path("lug")String idEnt);

    }
