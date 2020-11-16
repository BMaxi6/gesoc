var http_request = false;

const url= 'http://localhost:9187/';

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

let egresos_pedidos = [];

function manejarEgresos(jsonEgresos){

    egresos_pedidos = JSON.parse(jsonEgresos);

    var tbodyRef = document.getElementById('tablaEgresosIngreso').getElementsByTagName('tbody')[0];

    if(egresos_pedidos.length == 0){
        document.getElementById('tituloEgresos').innerHTML = 'Este ingreso no tiene ningún egreso asociado';
        document.getElementById('rowBoton').style.display = 'inline';
    }
    else{
        var i;
        for(i=0; i<egresos_pedidos.length; i++){

        document.getElementById('tituloEgresos').innerHTML = 'Operación de egreso';
        document.getElementById('rowTabla').style.display = 'inline';
            var newRow = tbodyRef.insertRow(); //agrega fila

            var egreso = newRow.insertCell(); //agrega columna
            var egreso_numero = document.createTextNode(egresos_pedidos[i].numeroOp);
            egreso.appendChild(egreso_numero);

            var fecha = newRow.insertCell();
            var egreso_fecha = document.createTextNode(egresos_pedidos[i].fecha);
            fecha.appendChild(egreso_fecha);

            var valorTotal = newRow.insertCell();
            var egreso_valorTotal = document.createTextNode(egresos_pedidos[i].valorTotal);
            valorTotal.appendChild(egreso_valorTotal);

            /*var celdaBoton = newRow.insertCell();
            var botonEgresos = document.createElement('button');
                botonEgresos.innerHTML = 'Ver más';
                botonEgresos.id = "boton_tabla";
                botonEgresos.type = "button";
                botonEgresos.onclick = function(){
                  mostrarEgreso(egreso.id);return false;
                };
                celdaBoton.appendChild(botonEgresos);*/
        }
    }
}

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarEgresos(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
}

function pedirEgresos(id){
        makeRequest(url+'ingreso_egresos'+'?id_ingreso='+id);
}

$(document).ready(function(){
  $("#inputIngreso").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaIngresos tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});