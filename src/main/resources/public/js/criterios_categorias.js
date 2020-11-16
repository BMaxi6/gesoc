var http_request = false;

const url = 'http://localhost:9187/';

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

function pedirCriterios(){
        makeRequest(url+'criterios');
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
        var opt = document.createElement("option");
        opt.text = categorias[j].nombre;
        opt.value = categorias[j].id;
        select_categorias.appendChild(opt);
    }
}