  
     var http_request = false;
    
     var documentoComercial= new Object();

     const urlItems= 'http://gesoc-spark.herokuapp.com/obtener_item';

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

    let items = [];
    let itemsAsociadosBorrados=[];
            
                    function agregarItem(jsonItemAsoc){

                        var itemAsoc=JSON.parse(jsonItemAsoc);

                        var itemNuevo = new Object();
                        //itemNuevo.cantidad = document.getElementById("cantidad").value;
                        itemNuevo.cantidad = itemAsoc.cantidad;
                        //document.getElementById("cantidad").value = "";
                        itemNuevo.valorUnitario = document.getElementById("valor_unitario").value;
    
                        itemNuevo.itemAsociado= new Object();
                        itemNuevo.itemAsociado=itemAsoc;
                        itemNuevo.id=undefined;
                        document.getElementById("valor_unitario").value = "";
                        /*if(document.getElementById("Producto").checked){
                            itemNuevo.tipo = "PRODUCTO";
                            document.getElementById("Producto").checked = false;
                        }
                        if(document.getElementById("Servicio").checked){
                            itemNuevo.tipo = "SERVICIO";
                            document.getElementById("Servicio").checked = false;
                        }*/
                        itemNuevo.tipo=itemAsoc.tipo;
                        //itemNuevo.descripcion = document.getElementById("descripcion").value;
                        itemNuevo.descripcion = itemAsoc.descripcion;
                        //document.getElementById("descripcion").value = "";

                        items.push(itemNuevo);

                        var sel=document.getElementById("lista_items_presupuesto");
                        var opt=document.createElement("option");
                        //opt.text=document.getElementById("descripcion").value;
                        opt.text=itemAsoc.descripcion;
                        opt.value=itemAsoc.id;
                        sel.appendChild(opt);
                        borrarItemAsociadoDelSelect();
                        
                    }


    function alertContentsItems() {

        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                agregarItem(http_request.responseText);
            } else {
                console.log('Hubo problemas con la petición.');
            }
        }

    }
    
function pedirItemAsociado(){
        var idItemAsociado= document.getElementById("select_item_asociado").value;
        makeRequestItems(urlItems+'?id_item='+idItemAsociado);
}

function cargarPresupuesto(){
    document.getElementById("lectura_items").value = JSON.stringify(items);
    document.getElementById("lectura_documento").value = JSON.stringify(documentoComercial);
}

function cargarDocumentoComercial(){
    documentoComercial.numeroDocumento=document.getElementById("numero_documento").value;
    documentoComercial.tipo=document.getElementById("tipo_documento").value;
    documentoComercial.fecha=document.getElementById("fecha_documento").value;
    documentoComercial.path=document.getElementById("archivo_documento").value;

    document.getElementById("texto_documento").innerHTML= "Num.: "+documentoComercial.numeroDocumento+ " - Tipo: "+ documentoComercial.tipo + " - Fecha: " + documentoComercial.fecha;
}

function eliminarItemPresupuesto(){
    restaurarItemAsociadoDelSelect();
    var select= document.getElementById("lista_items_presupuesto");
    var id=select.value;
    var i;
    for(i=0; i<items.length;i++){
        if(items[i].itemAsociado.id==id){
            items.splice(i,1);
        }
    }

    for(i=0;i<select.options.length;i++){
        if(select.options[i].selected){
            select.options[i]=null;
        }
    }
}

function borrarItemAsociadoDelSelect(){
    var select=document.getElementById("select_item_asociado");
    var idItemAsociado= document.getElementById("select_item_asociado").value;
    var infoItemAsociado;

    for(i=0;i<select.options.length;i++){
        if(select.options[i].selected){
            infoItemAsociado=select.options[i].text;
        }
        

    var itemAsociado=new Object();
    itemAsociado.id=idItemAsociado;
    itemAsociado.info=infoItemAsociado;

    for(i=0;i<select.options.length;i++){
        if(select.options[i].selected){
            select.options[i]=null;
        }
    }

    itemsAsociadosBorrados.push(itemAsociado);
}}

function restaurarItemAsociadoDelSelect(){
    var i;
    var select= document.getElementById("lista_items_presupuesto");
    var id=select.value;
    var selectItemsAsociados=document.getElementById("select_item_asociado");
    for(i=0;i<itemsAsociadosBorrados.length;i++){

        if(itemsAsociadosBorrados[i].id==id){
            
            
            var opt=document.createElement("option");
            
            opt.text=itemsAsociadosBorrados[i].info;
            opt.value=itemsAsociadosBorrados[i].id;
            selectItemsAsociados.appendChild(opt);
            itemsAsociadosBorrados.splice(i,1);
        }

    }
}