var http_request = false;


const url = 'http://gesoc-spark.herokuapp.com//criterio_subcriterios';

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

let subCriterios_pedidos = [];
            
function mostrarSubCriterios(jsonSubCriterios){
    
    subCriterios_pedidos = JSON.parse(jsonSubCriterios);
    
    var tabla = document.createElement("table");
    var tblBody = document.createElement("tbody");
    
    var tbodyRef = document.getElementById('tablaSubCriterios').getElementsByTagName('tbody')[0];

    tbodyRef.innerHTML = "";

    var i;
    for(i=0; i<subCriterios_pedidos.length; i++){
        var newRow = tbodyRef.insertRow(); //agrega fila    
        
        var nombreCriterio = newRow.insertCell(); //agrega columna
        var criterio_nombre = document.createTextNode(subCriterios_pedidos[i].nombre);
        nombreCategoria.appendChild(criterio_nombre);
        
    }
}   

function genera_tabla(jsonSubCriterios) {
    
    subCriterios_pedidos = JSON.parse(jsonSubCriterios);
 
    var tabla = document.createElement("table");
    var tblBody = document.createElement("tbody");
  
    // Crea las celdas
  for (var i = 0; i < 2; i++) {
    var hilera = document.createElement("tr");
 
    for (var j = 0; j < subCriterios_pedidos.length; j++) {
      var celda = document.createElement("td");
      var textoCelda = document.createTextNode(subCriterios_pedidos[i].nombre);
      celda.appendChild(textoCelda);
      hilera.appendChild(celda);
    }
 
    // agrega la hilera al final de la tabla (al final del elemento tblbody)
    tblBody.appendChild(hilera);
  }
 
  // posiciona el <tbody> debajo del elemento <table>
  tabla.appendChild(tblBody);
}

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                genera_tabla(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
            }
        }

    }
    
function pedirSubCriterios(id){
        makeRequest(url+'?id_criterio='+id);
}