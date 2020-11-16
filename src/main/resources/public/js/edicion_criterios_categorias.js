var http_request = false;

var idUsuario;

var urlCriterios='http://gesoc-spark.herokuapp.com/obtener_criterios_edicion';

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
        http_request.onreadystatechange = alertContentsCriterios;
        http_request.open('GET', url, true);
        http_request.send();
    }

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

let idsCategorias = [];
let criterios_pedidos = [];
var criterio_select;
function manejarCriterios(jsonCriterios){

    criterios_pedidos = JSON.parse(jsonCriterios);

    criterio_select = document.getElementById('criterioSelect');

    for(var i = 0; i<criterios_pedidos.length; i++){
        var opt = document.createElement("option");
        opt.label = criterios_pedidos[i].nombre;
        opt.value = criterios_pedidos[i].id;
        criterio_select.appendChild(opt);
    }
        criterio_select2 = document.getElementById('criterioSelect2');
        for(var i = 0; i<criterios_pedidos.length; i++){
            var opt = document.createElement("option");
            opt.label = criterios_pedidos[i].nombre;
            opt.value = criterios_pedidos[i].id;
            criterio_select2.appendChild(opt);
        }
}

let categorias = [];
var select_categorias
function actualizarCategorias(){
    select_categorias = document.getElementById('select_categoria');
    select_categorias.innerHTML = "";

    var idCriterio = criterio_select.value;
    var criterio;
    for(var i = 0; i<criterios_pedidos.length; i++){
        if(criterios_pedidos[i].id == idCriterio){
            criterio = criterios_pedidos[i];
        }
    }
    categorias = criterio.categorias;

    for(var j=0; j<categorias.length; j++){
      if(!idsCategorias.includes(categorias[j].id)){
        var opt = document.createElement("option");
        opt.label = categorias[j].nombre;
        opt.value = categorias[j].id;
        select_categorias.appendChild(opt);
      }
    }
}

/*function eliminarCategoria(){
    select_categorias = document.getElementById('select_categoria');

    criterio_select = document.getElementById('criterioSelect');
    var indiceCriterio = criterio_select.text;
    var criterioEditar = criterios_pedidos[indiceCriterio];

    for(var i=0; i<select_categorias.options.length;i++){
        var opcion = select_categorias.options[i];
        if(opcion.selected){
            for(var j = 0; j<categorias.length; j++){
                if(categorias[j].id == opcion.value){
                    categorias.splice(j,1);
                }
            }
            select_categorias.options[i] = null;
        }
    }
}*/

