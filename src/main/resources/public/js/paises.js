var paises= [];

const url_paises= 'http://localhost:9187/obtener_paises';
const select_pais="select_proveedor_pais";
const select_provincia="select_proveedor_provincia";
const select_ciudad="select_proveedor_ciudad";

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

function alertContents() {

    if (http_request.readyState == 4) {
        if (http_request.status == 200) {
            guardarPaises(http_request.responseText);
        } else {
            console.log('Hubo problemas con la petición.');
        }
    }

}

function guardarPaises(jsonPaises){
     paises=JSON.parse(jsonPaises);
     cargarPaisesAlSelect();
     
}

function obtenerPaisActual(){
    var idActual= document.getElementById(select_pais).value;
    for(var i=0; i<paises.length;i++){
        if(paises[i].id==idActual){
            return paises[i];
        }
    }
}

function obtenerProvinciaActual(){
    var idActual=document.getElementById(select_provincia).value;
    var paisActual= obtenerPaisActual();
    
    for(var i=0; i<paisActual.provincias.length;i++){
        if(paisActual.provincias[i].id==idActual){
            //console.log("entre al if");
            return paisActual.provincias[i];
        }
    }

}

function cargarPaisesAlSelect(){
    
    var sel= document.getElementById(select_pais);
    
    for(var i=0; i<paises.length; i++){
        var paisActual=new Object();
        paisActual= paises[i];
     
        var opt=document.createElement("option");
        opt.text=paisActual.nombre;
        opt.value= paisActual.id;
        sel.appendChild(opt);
    }
    sel.options[0].selected=true;

    var pais=obtenerPaisActual();
    var sel_provincia= document.getElementById(select_provincia);

    for(var i=0;i<pais.provincias.length;i++){
        var provinciaActual=new Object();
        provinciaActual= pais.provincias[i];
     
        var opt=document.createElement("option");
        opt.text=provinciaActual.nombre;
        opt.value= provinciaActual.id;
        sel_provincia.appendChild(opt);
    }
    sel_provincia.options[0].selected=true;

    
    var sel_ciudad= document.getElementById(select_ciudad);
    var ciudades=pais.provincias[0].ciudades;
    for(var i=0;i<ciudades.length;i++){
        var ciudadActual=new Object();
        ciudadActual= ciudades[i];
     
        var opt=document.createElement("option");
        opt.text=ciudadActual.nombre;
        opt.value= ciudadActual.id;
        sel_ciudad.appendChild(opt);
    }
    sel_ciudad.options[0].selected=true;
    
}

function cambiarProvinciasSelect(){
    var sel= document.getElementById(select_provincia);
    vaciarSelect(sel);
    var paisActual= obtenerPaisActual();
    var provincias= paisActual.provincias; 
    for(var i=0; i<provincias.length; i++){
        var provinciaActual=new Object();
        provinciaActual= provincias[i];
        var opt=document.createElement("option");
        opt.text=provinciaActual.nombre;
        opt.value= provinciaActual.id;
        sel.appendChild(opt);
    }
}

function cambiarCiudadesSelect(){
    var sel= document.getElementById(select_ciudad);
    vaciarSelect(sel);
    var provinciaActual= obtenerProvinciaActual();
    //console.log(provinciaActual);
    var ciudades= provinciaActual.ciudades; 
    for(var i=0; i<ciudades.length; i++){
        var ciudadActual=new Object();
        ciudadActual= ciudades[i];
        var opt=document.createElement("option");
        opt.text=ciudadActual.nombre;
        opt.value= ciudadActual.id;
        sel.appendChild(opt);
    }
}

function vaciarSelect(select){
    select.options.length=0;
}

makeRequest(url_paises);

