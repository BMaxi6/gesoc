var http_request = false;

var idUsuario;

var url= 'http://localhost:9187/informacion_egreso';

var urlCriterios='http://localhost:9187/obtener_criterios';

function obtenerIdUsuario(){
    idUsuario = document.getElementById("input_id_usuario").value;
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

let egreso;
let idsCategorias = [];
function manejarEgreso(jsonEgreso){

    egreso = JSON.parse(jsonEgreso);

    var x = document.getElementById('InfoEgreso');
    x.style.display = 'inline';

    document.getElementById("idEgreso").value = egreso.id;
    document.getElementById("numeroOperacion").innerHTML = 'Número: ' + egreso.numeroOp;
    document.getElementById("fechaOperacion").innerHTML = 'Fecha: ' + egreso.fecha.year + '-' + egreso.fecha.month + '-' + egreso.fecha.day;
    document.getElementById("valorTotalOperacion").innerHTML = 'Valor total: ' + egreso.valorTotal;
    document.getElementById("proveedorOperacion").innerHTML = 'Proveedor: ' + egreso.proveedor;
    document.getElementById("categoriasAsociadasOperacion").innerHTML = 'Categorías asociadas:';
    var select = document.getElementById("categorias_asociadas");

    select.innerHTML = "";
    idsCategorias=[];

    var selectCategorias = document.getElementById('select_categoria');

    for(var i = 0; i<egreso.categorias.length; i++){
        var opt = document.createElement("option");
        opt.text = egreso.categorias[i].nombre;
        select.appendChild(opt);
        idsCategorias.push(egreso.categorias[i].id);
    }
}

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarEgreso(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }

}

function pedirInfoEgreso(id){
        makeRequest(url+'?id_egreso='+id);
        document.getElementById('botonEliminar').disabled = true;
}

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
        console.log(idCategoria);

        select.options[i] = null;
    }
}
}

function eliminarCategoria(){
    console.log(idsCategorias);
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

$(document).ready(function(){
  $("#inputEgresoCat").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaEgresosCat tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});






function pedirCriterios(){
        obtenerIdUsuario();
        makeRequestCriterios(urlCriterios + '?id_usuario=' + idUsuario);
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


function guardarCambios(){
    var idsEnviar=[];

    for(var i=0; i<idsCategorias.length;i++){
        var objActual= new Object();
        objActual.valor=idsCategorias[i];
        idsEnviar.push(objActual);
    }
    
    document.getElementById("lectura_categorias").value = JSON.stringify(idsEnviar);
}

function habilitarBotonEliminar (){
    var x = document.getElementById('botonEliminar');
    x.disabled = false;
}

function cerrarInfoEgreso(){
    document.getElementById('InfoEgreso').style.display = 'none';
}