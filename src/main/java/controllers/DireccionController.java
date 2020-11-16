package controllers;

import DTOs.PaisJson;
import DTOs.PresupuestoCategoriasDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Egresos.Presupuesto;
import domain.Items.Item;
import domain.ubicaciones.Pais;
import repositorios.FactoryRepositorio;
import spark.Request;
import spark.Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DireccionController {
    public String obtenerPaises (Request request, Response response){
        List<Pais> paises= FactoryRepositorio.instancia().obtenerRepositorio(Pais.class).buscarTodos();
        List<PaisJson> paisesJson=paises.stream().map(p->PaisJson.toPaisJson(p)).collect(Collectors.toList());

        Type listType = new TypeToken<ArrayList<PaisJson>>(){}.getType();
        Gson gson = new Gson();
        String jsonPaises = gson.toJson(paisesJson, listType);
        response.type("application/json");
        System.out.println("json" + jsonPaises);
        return jsonPaises;
        //return "";
    }

}
