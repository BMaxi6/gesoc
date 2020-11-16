var urlDoc= 'http://localhost:9187/informacion_doc_comercial';

function makeRequestDoc(url) {

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
    http_request.onreadystatechange = alertContentsDoc;
    http_request.open('GET', url, true);
    http_request.send();
}


function alertContentsDoc() {

    if (http_request.readyState == 4) {
        if (http_request.status == 200) {
            manejarEgreso(http_request.responseText);
        } else {
            console.log('Hubo problemas con la petición.');
    }
}

}

function pedirDoc(){
    makeRequestDoc(urlDoc)
}