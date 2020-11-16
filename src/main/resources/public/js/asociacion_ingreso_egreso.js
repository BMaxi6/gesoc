var http_request = false;


const urlEgreso= 'http://localhost:9187/obtener_egreso';
const urlIngreso= 'http://localhost:9187/obtener_ingreso';

function makeRequestEgreso(url) {

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

    function alertContents() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                agregarEgresoATabla(http_request.responseText);
                //mostrarCategorias(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
            }
        }

    }

var idIngresoElegido;
var idsEgresos=[];
var mapEgresoFila= new Map();
var mapEgresoValor= new Map();
var mapIngresoValor=new Map();
var rowIndex=0;
var totalRestante=0;
var totalEgresos=0;
var totalIngresos=0;


function pedirEgreso(id){
    
    var bool=true;
    for(var i=0; i<idsEgresos.length;i++){
        if(idsEgresos[i].valor==id)
            bool=false;
    }

    if(bool){
        var idObjeto= new Object();
        idObjeto.valor=id;
    
        idsEgresos.push(idObjeto);
        makeRequestEgreso(urlEgreso + '?id_egreso=' + id);
        
    }
        
}

function asociarEgreso(){
  
    var idEgreso;
    var select_egreso=document.getElementById("select_egresos");
    

    for(i=0;i<select_egreso.options.length;i++){
        if(select_egreso.options[i].selected){
            
            idEgreso=select_egreso.options[i].value;
        }
    }

    
    console.log(JSON.stringify(idsEgresos));
    pedirEgreso(idEgreso);
}

function agregarEgresoATabla(jsonEgreso){
    rowIndex++;
    var egreso= new Object();
    egreso=JSON.parse(jsonEgreso);
    agregarAMapEgresoValor(egreso);
    var botonEliminar = document.createElement('button');
    botonEliminar.innerHTML = 'Eliminar';
    botonEliminar.id="boton_tabla";
    botonEliminar.type="button";
    botonEliminar.onclick = function(){
      eliminarEgreso(egreso.id);return false;
    };


    var tabla= document.getElementById("tabla_egresos");
    var fila= tabla.insertRow(rowIndex);
    mapEgresoFila.set(egreso.id, rowIndex);
    var celda0= fila.insertCell(0);
    var celda1= fila.insertCell(1);
    var celda2= fila.insertCell(2);
    var celda3= fila.insertCell(3);
    var celda4= fila.insertCell(4);

    celda0.innerHTML=egreso.numeroOp;
    celda1.innerHTML=egreso.valorTotal;
    celda2.innerHTML=egreso.proveedor;
    celda3.innerHTML=egreso.fecha.year + '-' + egreso.fecha.month + '-' + egreso.fecha.day;
    celda4.appendChild(botonEliminar);

    actualizarTotalRestanteEgreso(totalEgresos+egreso.valorTotal);



}

function eliminarEgreso(id){
    var indexEgreso=mapEgresoFila.get(id);
    document.getElementById("tabla_egresos").deleteRow(indexEgreso);
    rowIndex--;

    for(var i=0; i<idsEgresos.length;i++){
        if(idsEgresos[i].valor==id)
            idsEgresos.splice(i,1);
    }
    var valorTotal=mapEgresoValor.get(id);
    actualizarTotalRestanteEgreso(totalEgresos-valorTotal);
    
}


function makeRequestIngreso(url) {

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
    http_request.onreadystatechange = alertContentsIngreso;
    http_request.open('GET', url, true);
    http_request.send();
   

}

function alertContentsIngreso() {

    if (http_request.readyState == 4) {
        if (http_request.status == 200) {
            
            ponerIngresoEnTabla(http_request.responseText);
        } else {
            console.log('Hubo problemas con la petición.');
        }
    }

}


function pedirIngreso(id){
    
        
        makeRequestIngreso(urlIngreso + '?id_ingreso=' + id);
      
}

function elegirIngreso(){
    
    
    var select_ingreso=document.getElementById("select_ingresos");
    
    

    var opt=new Object();

    for(i=0;i<select_ingreso.options.length;i++){
        if(select_ingreso.options[i].selected){
            
            idIngresoElegido=select_ingreso.options[i].value;
        }
    }
    pedirIngreso(idIngresoElegido);
   
}

function ponerIngresoEnTabla(jsonIngreso){
    var ingreso= new Object();
    ingreso=JSON.parse(jsonIngreso);

    agregarAMapIngresoValor(ingreso);
    var tabla= document.getElementById("tabla_ingresos");
    if(tabla.rows.length>1)
        tabla.deleteRow(1);

    var fila= tabla.insertRow(1);
    var celda0= fila.insertCell(0);
    var celda1= fila.insertCell(1);
    var celda2= fila.insertCell(2);
    var celda3= fila.insertCell(3);
    var celda4= fila.insertCell(4);

    celda0.innerHTML=ingreso.descripcion;
    celda1.innerHTML=ingreso.valorTotal;
    celda2.innerHTML=ingreso.valorRestante;
    celda3.innerHTML=ingreso.fecha.year + '-' + ingreso.fecha.month + '-' + ingreso.fecha.day;
    celda4.innerHTML=ingreso.fechaAceptabilidad.year + '-' + ingreso.fechaAceptabilidad.month + '-' + ingreso.fechaAceptabilidad.day;

    actualizarTotalRestanteIngreso(ingreso.valorRestante);

}

function actualizarTotalRestanteEgreso(valor){
    totalEgresos=valor;
    var p_total=document.getElementById("total_actual");
    p_total.innerHTML='$' + (totalIngresos - totalEgresos);
}

function actualizarTotalRestanteIngreso(valor){
    totalIngresos=valor;
    var p_total=document.getElementById("total_actual");
    p_total.innerHTML='$' + (totalIngresos - totalEgresos);
}

function agregarAMapEgresoValor(egreso){
    if(!mapEgresoValor.has(egreso.id))
        mapEgresoValor.set(egreso.id, egreso.valorTotal);
}

function agregarAMapIngresoValor(ingreso){
    if(!mapIngresoValor.has(ingreso.id))
    mapIngresoValor.set(ingreso.id, ingreso.valorRestante);
}

function cargarVinculacion (){
    document.getElementById("ingreso_elegido").value = idIngresoElegido;
    document.getElementById("egresos_elegidos").value = JSON.stringify(idsEgresos);

    console.log("termine de cargar");

}