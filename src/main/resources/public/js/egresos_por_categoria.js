var http_request = false;

const url= 'http://gesoc-spark.herokuapp.com/';
var idUsuario;

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

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarCriterios(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }

}

function obtenerIdUsuario(){
    idUsuario=document.getElementById("input_id_usuario").value;
}

function pedirCriterios(){
        obtenerIdUsuario();
        makeRequest(url+'obtener_criterios'+ '?id_usuario=' + idUsuario);
}

let categorias = [];
var select_categorias
function actualizarCategorias(){
    select_categorias = document.getElementById('categoriasSeleccionables');
    select_categorias.innerHTML = "";

    var indiceCriterio = criterio_select.value;
    let criterio = criterios_pedidos[indiceCriterio];
    categorias = criterio.categorias;

    for(var j=0; j<categorias.length; j++){
        var opt = document.createElement("option");
        opt.text = categorias[j].nombre;
        opt.value = categorias[j].id;
        select_categorias.appendChild(opt);
    }
}

function mostrarEgresos(){
    select_categorias = document.getElementById('categoriasSeleccionables');
    var idCategoria = select_categorias.value;
    pedirEgresos(idCategoria);
}

function makeRequestEgresos(url) {

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
        http_request.onreadystatechange = alertContentsEgresos;
        http_request.open('GET', url, true);
        http_request.send();
    }

function alertContentsEgresos() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarEgresos(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
}

function pedirEgresos(id){
    obtenerIdUsuario();
    makeRequestEgresos(url+'categoria_egresos'+'?id_categoria='+id+'&'+'id_usuario='+idUsuario);
}

var egresos_pedidos = [];
function manejarEgresos(jsonEgresos){
        document.getElementById('ListaDeEgresos').style.display = 'inline';
        egresos_pedidos = JSON.parse(jsonEgresos);

        if(egresos_pedidos.length == 0){
            document.getElementById('titulo_tabla_egresos').innerHTML = 'No hay egresos con esta categoría';
            document.getElementById('tabla_egresos').style.display = 'none';
            document.getElementById('ListaDeEgresos').style.display = 'inline';
        }

        else{
            var tbodyRef = document.getElementById('egresos_categoria').getElementsByTagName('tbody')[0];
            tbodyRef.innerHTML = "";
            document.getElementById('titulo_tabla_egresos').innerHTML = 'Egresos';
            document.getElementById('tabla_egresos').style.display = 'inline';
            document.getElementById('ListaDeEgresos').style.display = 'inline';

            for(var i=0; i<egresos_pedidos.length; i++){

                var newRow = tbodyRef.insertRow(); //agrega fila

                var nro_egreso = newRow.insertCell(); //agrega columna
                var egreso_nro = document.createTextNode(egresos_pedidos[i].numeroOp);
                nro_egreso.appendChild(egreso_nro);

                var fecha = newRow.insertCell();
                var fecha_egreso = document.createTextNode(egresos_pedidos[i].fecha);
                fecha.appendChild(fecha_egreso);

                var valor_total = newRow.insertCell();
                var valor_total_egreso = document.createTextNode(egresos_pedidos[i].valorTotal);
                valor_total.appendChild(valor_total_egreso);
            }
        }
}

function cerrar(){
    document.getElementById('ListaDeEgresos').style.display = 'none';
}