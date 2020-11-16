var http_request = false;

var idUsuario;

var url= 'http://localhost:9187/informacion_presupuesto';

var urlCriterios='http://localhost:9187/obtener_criterios';

function pedirInfoPresupuesto(id){
        makeRequest(url+'?id_presupuesto='+id);
        document.getElementById('botonEliminar').disabled = true;
}

function makeRequest(url) {

        http_request = false;

        if (window.XMLHttpRequest) { // Mozilla, Safari,...
            http_request = new XMLHttpRequest();
            if (http_request.overrideMimeType) {
                http_request.overrideMimeType('text/xml');
                // Ver nota sobre esta linea al final
            }
        } else if (window.ActiveXObject) { // IE
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }

        if (!http_request) {
            alert('Falla :( No es posible crear una instancia XMLHTTP');
            return false;
        }
        http_request.onreadystatechange = alertContents;
        http_request.open('GET', url, true);
        http_request.send();


    }

let presupuesto;

function manejarPresupuesto(jsonPresupuesto){

    presupuesto = JSON.parse(jsonPresupuesto);

    var x = document.getElementById('InfoPresupuesto');
    x.style.display = 'inline';

    document.getElementById("idPresupuesto").value = presupuesto.id;
    document.getElementById("egresoPresupuesto").innerHTML = 'Número de egreso asociado: ' + presupuesto.numeroEgreso;
    document.getElementById("fechaPresupuesto").innerHTML = 'Fecha: ' + presupuesto.fecha;
    document.getElementById("valorTotalPresupuesto").innerHTML = 'Valor total: ' + presupuesto.valorTotal;
    document.getElementById("proveedorPresupuesto").innerHTML = 'Proveedor: ' + presupuesto.proveedor;
    if(presupuesto.elegido){
        document.getElementById("elegidoPresupuesto").innerHTML = 'Elegido: Si';
    }else{
        document.getElementById("elegidoPresupuesto").innerHTML = 'Elegido: No';
    }

    document.getElementById("categoriasPresupuesto").innerHTML = 'Categorías asociadas al presupuesto:';
    var select = document.getElementById("categorias_asociadas");

    select.innerHTML = "";
    idsCategorias=[];

    for(var i = 0; i<presupuesto.categorias.length; i++){
        var opt = document.createElement("option");
        opt.text = presupuesto.categorias[i].nombre;
        select.appendChild(opt);
    }

    document.getElementById("categoriasEgreso").innerHTML = 'Categorías asociadas al egreso:';
    var select_egreso = document.getElementById("categorias_egreso");

    for(var i = 0; i<presupuesto.categoriasEgresos.length; i++){
        var opt = document.createElement("option");
        opt.text = presupuesto.categoriasEgresos[i].nombre;
        select_egreso.appendChild(opt);
       }
}

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarPresupuesto(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }

}

var idsCategorias = [];
function agregarCategoriaAlSelect(){
    var select = document.getElementById('select_categoria');
    var selectAsociadas = document.getElementById("categorias_asociadas");
    var i;

    for(i=0;i<select.options.length;i++){
      if(select.options[i].selected){
        var idCategoria = select.options[i].value;
        var categoria = select.options[i].label;
        var opt = document.createElement("option");
        opt.text = categoria;
        opt.value = idCategoria;
        selectAsociadas.appendChild(opt);

        idsCategorias.push(idCategoria);

        select.options[i] = null;
    }
}
}

function eliminarCategoria(){
    var selectAsociadas = document.getElementById("categorias_asociadas");
    var select = document.getElementById('select_categoria');
    var i;
       for(i=selectAsociadas.options.length-1;i>=0;i--)
        {
           if(selectAsociadas.options[i].selected){
              var index = idsCategorias.indexOf(selectAsociadas.options[i].value);
              idsCategorias.splice(index,1);

              var idCategoria = selectAsociadas.options[i].value;
              var categoria = selectAsociadas.options[i].label;

              var opt = document.createElement("option");
              opt.text = categoria;
              opt.value = idCategoria;
              select.appendChild(opt);
              selectAsociadas.options[i] = null;
              break;
            }
        }
}


function pedirCriterios(){
        obtenerIdUsuario();
        makeRequestCriterios(urlCriterios + '?id_usuario=' + idUsuario);
}

function obtenerIdUsuario(){
    idUsuario=document.getElementById("input_id_usuario").value;
}

function makeRequestCriterios(url) {

        http_request = false;

        if (window.XMLHttpRequest) { // Mozilla, Safari,...
            http_request = new XMLHttpRequest();
            if (http_request.overrideMimeType) {
                http_request.overrideMimeType('text/xml');
                // Ver nota sobre esta linea al final
            }
        } else if (window.ActiveXObject) { // IE
            try {
                http_request = new ActiveXObject("Msxml2.XMLHTTP");
            } catch (e) {
                try {
                    http_request = new ActiveXObject("Microsoft.XMLHTTP");
                } catch (e) {}
            }
        }

        if (!http_request) {
            alert('Falla :( No es posible crear una instancia XMLHTTP');
            return false;
        }
        http_request.onreadystatechange = alertContentsCriterios;
        http_request.open('GET', url, true);
        http_request.send();
}

function alertContentsCriterios() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarCriterios(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
    }

let criterios_pedidos = [];
var criterio_select;
function manejarCriterios(jsonCriterios){

    criterios_pedidos = JSON.parse(jsonCriterios);

    criterio_select = document.getElementById('criterioSelect');

    for(var i = 0; i<criterios_pedidos.length; i++){
        var opt = document.createElement("option");
        opt.text = criterios_pedidos[i].nombre;
        opt.value = i;
        criterio_select.appendChild(opt);
    }
}

let categorias = [];
var select_categorias
function actualizarCategorias(){
    select_categorias = document.getElementById('select_categoria');
    select_categorias.innerHTML = "";

    var indiceCriterio = criterio_select.value;
    let criterio = criterios_pedidos[indiceCriterio];
    categorias = criterio.categorias;

    for(var j=0; j<categorias.length; j++){
      if(!idsCategorias.includes(categorias[j].id)){
        var opt = document.createElement("option");
        opt.text = categorias[j].nombre;
        opt.value = categorias[j].id;
        select_categorias.appendChild(opt);
      }
    }
}

$(document).ready(function(){
  $("#inputPresupuestoCat").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaPresupuestosCat tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

function habilitarBotonEliminar (){
    var x = document.getElementById('botonEliminar');
    x.disabled = false;
}

function guardarCambios(){
    var idsEnviar=[];

    for(var i=0; i<idsCategorias.length;i++){
        var objActual= new Object();
        objActual.valor=idsCategorias[i];
        idsEnviar.push(objActual);
    }

    document.getElementById("lectura_categorias").value = JSON.stringify(idsEnviar);
}

function cerrarInfoPresupuesto(){
    document.getElementById("InfoPresupuesto").style.display = 'none';
}
