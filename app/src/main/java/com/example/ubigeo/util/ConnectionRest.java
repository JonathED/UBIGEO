package com.example.ubigeo.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionRest {


        private static Retrofit retrofit = null;
       // private static final String RUTA_API_REST ="http://env-9035382.j.layershift.co.uk/rest/servicios/";
       private static final String RUTA_API_REST = "http://node229017-env-0167439.j.layershift.co.uk/rest/servicios/";
        public static Retrofit getConnection() {
            if (retrofit==null) {
                retrofit = new Retrofit.Builder().baseUrl(RUTA_API_REST).addConverterFactory(GsonConverterFactory.create()).build();
            }
            return retrofit;
        }
    }