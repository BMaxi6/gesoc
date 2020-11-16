$(document).ready(function(){
  $("#inputPresupuesto").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaPresupuestos tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

var http_request = false;

const url= 'http://localhost:9187/';


//----------------------------------------DEMAS MODALES  Y BUSCADOR
function mostrarProveedor(nombre, cuit, codigoPostal, calle, altura, piso, ciudad, provincia, pais){

    document.getElementById('nombreProveedor').innerHTML = 'Nombre: ' + nombre;
    document.getElementById('cuitProveedor').innerHTML ='CUIT: ' + cuit;
    document.getElementById('codigoPostalProveedor').innerHTML = 'Código postal: ' + codigoPostal;
    document.getElementById('calleProveedor').innerHTML = 'Calle: ' + calle;
    document.getElementById('alturaProveedor').innerHTML = 'Altura: ' + altura;
    document.getElementById('pisoProveedor').innerHTML = 'Piso: ' + piso;
    document.getElementById('ciudadProveedor').innerHTML = 'Ciudad: ' + ciudad;
    document.getElementById('provinciaProveedor').innerHTML = 'Provincia: ' + provincia;
    document.getElementById('paisProveedor').innerHTML = 'País: ' + pais;
}

function mostrarDocComercial(numero, tipo, fecha, path){

    document.getElementById('numeroDoc').innerHTML = 'Número: ' + numero;
    document.getElementById('tipoDoc').innerHTML = 'Tipo: ' + tipo;
    document.getElementById('fechaDoc').innerHTML = 'Fecha: ' + fecha;
    document.getElementById('pathDoc').innerHTML = 'Documento: ' + path;
}

//---------------------------------------------ITEMS------------------------------------------------------------

function makeRequestItems(url) {

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
        http_request.onreadystatechange = alertContentsItems;
        http_request.open('GET', url, true);
        http_request.send();
    }

let items_pedidos = [];

function manejarItems(jsonItems){

    items_pedidos = JSON.parse(jsonItems);

    var tbodyRef = document.getElementById('tablaItemsPresupuesto').getElementsByTagName('tbody')[0];

    var i;
     for(i=0; i<items_pedidos.length; i++){

            var newRow = tbodyRef.insertRow(); //agrega fila

            var tipo = newRow.insertCell(); //agrega columna
            var item_tipo = document.createTextNode(items_pedidos[i].tipo);
            tipo.appendChild(item_tipo);

            var cantidad = newRow.insertCell();
            var item_cantidad = document.createTextNode(items_pedidos[i].cantidad);
            cantidad.appendChild(item_cantidad);

            var valor_unitario = newRow.insertCell();
            var item_valor_unitario = document.createTextNode(items_pedidos[i].valorUnitario);
            valor_unitario.appendChild(item_valor_unitario);

            var descripcion = newRow.insertCell();
            var item_descripcion = document.createTextNode(items_pedidos[i].itemAsociado.descripcion);
            descripcion.appendChild(item_descripcion);
        }
}

function alertContentsItems() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarItems(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
}

function pedirItems(id){
        makeRequestItems(url+'presupuesto_items'+'?id_presupuesto='+id);
}


//---------------------------------------------CATEGORIAS------------------------------------------------------------

function makeRequestCategorias(url) {

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
        http_request.onreadystatechange = alertContentsCategorias;
        http_request.open('GET', url, true);
        http_request.send();
    }

let categorias_pedidas = [];

function manejarCategorias(jsonCategorias){

    categorias_pedidas = JSON.parse(jsonCategorias);

    if(categorias_pedidas.length == 0){
        document.getElementById('tituloCategorias').innerHTML = 'Este presupuesto no tiene ninguna categoría asociada';
        document.getElementById('rowBotonCategorias').style.display = 'inline';
        document.getElementById('rowTablaCategorias').style.display = 'none';
    }
    else{
        var tbodyRef = document.getElementById('tablaCategoriasPresupuesto').getElementsByTagName('tbody')[0];
        tbodyRef.innerHTML = "";
        document.getElementById('tituloCategorias').innerHTML = 'Categorías';
        document.getElementById('rowTablaCategorias').style.display = 'inline';

        var i;
        for(i=0; i<categorias_pedidas.length; i++){
            var newRow = tbodyRef.insertRow(); //agrega fila

            var categoria = newRow.insertCell(); //agrega columna
            var categoria_egreso = document.createTextNode(categorias_pedidas[i].nombre);
            categoria.appendChild(categoria_egreso);
        }
    }
}

function alertContentsCategorias() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarCategorias(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
}

function pedirCategorias(id){
        makeRequestCategorias(url+'presupuesto_categorias'+'?id_presupuesto='+id);
}