function verificarCamposTarjeta(){
    var selMedioPago=document.getElementById("select_medio_pago");
    var medioPago=selMedioPago.value;
    var inputNumTarjeta=document.getElementById("col_numero_tarjeta");
    var selPagosTarjeta=document.getElementById("col_pagos_tarjeta");
    var selTipoPagosTarjeta=document.getElementById("col_tipo_pagos_tarjeta");

    if(medioPago=="Tarjeta de crédito"){
        inputNumTarjeta.style["display"]="inline";
        selPagosTarjeta.style["display"]="inline";
        selTipoPagosTarjeta.style["display"]="inline";
    }else if(medioPago=="Tarjeta de débito"){
        inputNumTarjeta.style["display"]="inline";
        selPagosTarjeta.style["display"]="none";
        selTipoPagosTarjeta.style["display"]="none";
    }else{
        inputNumTarjeta.style["display"]="none";
        selPagosTarjeta.style["display"]="none";
        selTipoPagosTarjeta.style["display"]="none";
    }

}