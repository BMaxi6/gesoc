var http_request = false;

var urlCategoriasListado = 'http://gesoc-spark.herokuapp.com/criterio_categorias';

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

let categorias_pedidas = [];
            
function mostrarCategorias(jsonCategorias){
    
    categorias_pedidas = JSON.parse(jsonCategorias);

    if(categorias_pedidas.length==0){
        document.getElementById('tituloCategorias').innerHTML = 'Este criterio no tiene categorías';
        document.getElementById('rowTablaCategorias').style.display = 'none';
    }
    else{
        var tbodyRef = document.getElementById('tablaCategorias').getElementsByTagName('tbody')[0];
        tbodyRef.innerHTML = "";

        document.getElementById('tituloCategorias').innerHTML = 'Categorías';
        document.getElementById('rowTablaCategorias').style.display = 'inline';

        var i;
        for(i=0; i<categorias_pedidas.length; i++){
            var newRow = tbodyRef.insertRow(); //agrega fila

            var nombreCategoria = newRow.insertCell(); //agrega columna
            var categoria_nombre = document.createTextNode(categorias_pedidas[i].nombre);
            nombreCategoria.appendChild(categoria_nombre);

        }
    }

} 

function Eliminar (i) {
    document.getElementById("tablaCategorias").deleteRow(i);
    }

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                mostrarCategorias(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
            }
        }

    }
    
function pedirCategorias(id){
        makeRequest(urlCategoriasListado+'?id_criterio='+id);
}

//----------------------------------------------------------SUB CRITERIOS-----------------------------------------------

var urlSubCriterios = 'http://localhost:9187/criterio_subcriterios';

function makeRequestSubCriterios(url) {

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
        http_request.onreadystatechange = alertContentsSubCriterios;
        http_request.open('GET', url, true);
        http_request.send();


    }

let subCriterios_pedidos = [];

function mostrarSubCriterios(jsonSubCriterios){

    subCriterios_pedidos = JSON.parse(jsonSubCriterios);

    if(subCriterios_pedidos.length == 0){
        document.getElementById('tituloSubCriterios').innerHTML = 'Este criterio no tiene sub-criterios';
        document.getElementById('rowTabla').style.display = 'none';
    }
    else{
        document.getElementById('tituloSubCriterios').innerHTML = 'Sub-criterios';
        document.getElementById('rowTabla').style.display = 'inline';
        var tabla = document.createElement("table");
        var tblBody = document.createElement("tbody");

        var tbodyRef = document.getElementById('tablaSubCriterios').getElementsByTagName('tbody')[0];

        tbodyRef.innerHTML = "";

            var i;
            for(i=0; i<subCriterios_pedidos.length; i++){
                var newRow = tbodyRef.insertRow(); //agrega fila

                var nombreCriterio = newRow.insertCell(); //agrega columna
                var criterio_nombre = document.createTextNode(subCriterios_pedidos[i].nombre);
                nombreCriterio.appendChild(criterio_nombre);
            }
    }
}

function alertContentsSubCriterios() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                mostrarSubCriterios(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
            }
        }

    }

function pedirSubCriterios(id){
        makeRequestSubCriterios(urlSubCriterios+'?id_criterio='+id);
}


$(document).ready(function(){
  $("#inputCriterio").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaCriterios tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});