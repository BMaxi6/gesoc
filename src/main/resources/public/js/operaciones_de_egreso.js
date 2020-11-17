var http_request = false;

const url= 'http://gesoc-spark.herokuapp.com/';

//----------------------------------------------PRESUPUESTOS
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

let presupuestos_pedidos = [];
            
function manejarPresupuestos(jsonPresupuestos){
    
    presupuestos_pedidos = JSON.parse(jsonPresupuestos);
    
    if(presupuestos_pedidos.length == 0){
        document.getElementById('tituloPresupuestos').innerHTML = 'Este egreso no tiene ningún presupuesto asociado';
        document.getElementById('rowBoton').style.display = 'inline';
        document.getElementById('rowTabla').style.display = 'none';
    }
    else{
        var tbodyRef = document.getElementById('tablaPresupuestosEgreso').getElementsByTagName('tbody')[0];
        tbodyRef.innerHTML = "";
        document.getElementById('tituloPresupuestos').innerHTML = 'Presupuesto';
        document.getElementById('rowTabla').style.display = 'inline';
        document.getElementById('rowBoton').style.display = 'none';

        var i;
        for(i=0; i<presupuestos_pedidos.length; i++){

            var newRow = tbodyRef.insertRow(); //agrega fila
        
            var proveedor = newRow.insertCell(); //agrega columna
            var proveedor_nombre = document.createTextNode(presupuestos_pedidos[i].proveedor);
            proveedor.appendChild(proveedor_nombre);
    
            var elegido = newRow.insertCell();
            var elegido_p = document.createTextNode(presupuestos_pedidos[i].elegido);
            elegido.appendChild(elegido_p);
        
            var valor_total = newRow.insertCell();
            var valor_total_presu = document.createTextNode(presupuestos_pedidos[i].valorTotal);
            valor_total.appendChild(valor_total_presu);

            var fecha = newRow.insertCell();
            var fecha_presu = document.createTextNode(presupuestos_pedidos[i].fecha);
            fecha.appendChild(fecha_presu);
        }
    }
}

function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarPresupuestos(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }

}
    
function pedirPresupuestos(id){
        makeRequest(url+'egreso_presupuestos'+'?id_egreso='+id);
}

//----------------------------------------DEMAS MODALES  Y BUSCADOR
function mostrarPago(medio, dato, numero, monto, moneda){

    document.getElementById('medioPago').innerHTML = 'Medio de pago: ' + medio;
    document.getElementById('dato').innerHTML ='Descripción: ' + dato;
    document.getElementById('numeroPago').innerHTML = 'Número de pago: ' + numero;
    document.getElementById('montoPago').innerHTML = 'Monto: ' + monto;
    document.getElementById('moneda').innerHTML = 'Moneda: ' + moneda;
}

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

function mostrarIngreso(descripcion, valorTotal, fecha, moneda, fechaDeAceptabilidadEgreso){
            document.getElementById('descripcionIngreso').innerHTML = 'Descripción: ' + descripcion;
            document.getElementById('valorTotalIngreso').innerHTML ='Valor total: ' + valorTotal;
            document.getElementById('fechaIngreso').innerHTML = 'Fecha: ' + fecha;
            document.getElementById('monedaIngreso').innerHTML = 'Moneda: ' + moneda;
            document.getElementById('fechaAceptabilidadIngreso').innerHTML = 'Fecha de aceptabilidad: ' + fechaDeAceptabilidadEgreso;
}

$(document).ready(function(){
  $("#inputEgreso").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#tablaEgresos tbody tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

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
    var tbodyRef = document.getElementById('tablaItemsEgreso').getElementsByTagName('tbody')[0];
    tbodyRef.innerHTML = "";

    var i;
     for(i=0; i<items_pedidos.length; i++){

            var newRow = tbodyRef.insertRow(); //agrega fila

            var tipo = newRow.insertCell(); //agrega columna
            var item_tipo = document.createTextNode(items_pedidos[i].tipo);
            tipo.appendChild(item_tipo);

            var descripcion = newRow.insertCell();
            var item_descripcion = document.createTextNode(items_pedidos[i].descripcion);
            descripcion.appendChild(item_descripcion);

            var cantidad = newRow.insertCell();
            var item_cantidad = document.createTextNode(items_pedidos[i].cantidad);
            cantidad.appendChild(item_cantidad);

            var valor_unitario = newRow.insertCell();
            var item_valor_unitario = document.createTextNode(items_pedidos[i].valorUnitario);
            valor_unitario.appendChild(item_valor_unitario);
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
        makeRequestItems(url+'egreso_items'+'?id_egreso='+id);
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
        document.getElementById('tituloCategorias').innerHTML = 'Este egreso no tiene ninguna categoría asociada';
        document.getElementById('rowBotonCategorias').style.display = 'inline';
        document.getElementById('rowTablaCategorias').style.display = 'none';
    }
    else{
        var tbodyRef = document.getElementById('tablaCategoriasEgreso').getElementsByTagName('tbody')[0];
        tbodyRef.innerHTML = "";
        document.getElementById('tituloCategorias').innerHTML = 'Categorías';
        document.getElementById('rowTablaCategorias').style.display = 'inline';
        document.getElementById('rowBotonCategorias').style.display = 'none';

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
        makeRequestCategorias(url+'egreso_categorias'+'?id_egreso='+id);
}

//-----------------------------------------------REVISORES

function makeRequestRevisores(url) {

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
        http_request.onreadystatechange = alertContentsRevisores;
        http_request.open('GET', url, true);
        http_request.send();
    }

let revisores_pedidas = [];

function manejarRevisores(jsonRevisores){

    revisores_pedidas = JSON.parse(jsonRevisores);
    var tbodyRef = document.getElementById('tablaRevisoresEgreso').getElementsByTagName('tbody')[0];
    tbodyRef.innerHTML = "";

    if(revisores_pedidas.length == 0){
        document.getElementById('tituloRevisores').innerHTML = 'Este egreso no tiene ningun usuario revisor';
        document.getElementById('rowTablaRevisores').style.display = 'none';
    }
    else{
        document.getElementById('tituloRevisores').innerHTML = 'Usuarios revisores';
        document.getElementById('rowTablaRevisores').style.display = 'inline';

        var i;
        for(i=0; i<revisores_pedidas.length; i++){
            var newRow = tbodyRef.insertRow(); //agrega fila

            var usuario = newRow.insertCell(); //agrega columna
            var revisor_egreso = document.createTextNode(revisores_pedidas[i].nombre + revisores_pedidas[i].apellido);
            usuario.appendChild(revisor_egreso);
        }
    }
}

function alertContentsRevisores() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                manejarRevisores(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
        }
    }
}

function pedirRevisores(id){
        makeRequestRevisores(url+'egreso_revisores'+'?id_egreso='+id);
}




