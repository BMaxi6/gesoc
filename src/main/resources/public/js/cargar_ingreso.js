let ids = [];
let idsEgresos=[];
var map= new Map();

function asociarEgreso(){

    let atributos = [];
    document.querySelectorAll('#egreso_seleccionado thead th').forEach(elemento => {
    atributos.push(elemento.innerText);
});

    let datos = [];
    document.querySelectorAll('#egreso_seleccionado tbody tr').forEach(fila => {
    let dato = {};
    atributos.forEach(campo => { dato[campo] = ''; });

    fila.querySelectorAll('td').forEach((elemento, n) => {
    let input = elemento.querySelector('input');

    if (input !== null && input.checked && !ids.includes(input.name)) {
        dato[atributos[n]] = input.checked;
        dato.id = input.id;
        dato.name = input.name;
        datos.push(dato);
        ids.push(input.name);
                //--------------------para la lista de ids egresos
        map.set(input.name, input.value);
        var objetoId=new Object();//creo un objeto para deserializar como con Gson del otro lado
                objetoId.valor=input.value;
                objetoId.otro=null;
                idsEgresos.push(objetoId);
                console.log("pusheo el id del egreso");
                console.log(input.value);
                console.log("la key es");
                console.log(input.name);
            }
            });

            });

            var select = document.getElementById("egresos_asociados");

            for (value in datos) {
                var option = document.createElement("option");
                option.text = datos[value].name;
                select.add(option);
             }
        }

function mostrarEgresos(){
    var x = document.getElementById('ListaEgresos');
    if(x.style.display = 'none'){
        x.style.display = 'inline';
    }else{
        x.style.display = 'none'
    }
}

function eliminarEgreso(){
	        var select = document.getElementById("egresos_asociados");
	        var i;
	        for(i=select.options.length-1;i>=0;i--)
	        {
	            if(select.options[i].selected){
                    var index=ids.indexOf(select.options[i]);
                    ids.splice(index,1);

                    var idEgresoEliminar=map.get(select.options[i].text);
                    console.log("saco el id del egreso");
                    console.log(idEgresoEliminar);
                    console.log("la key es");
                    console.log(select.options[i].text);
                    var indexEgreso=idsEgresos.indexOf(idEgresoEliminar);
                    idsEgresos.splice(indexEgreso,1);

                    //console.log(select.options[i]);
	                select.options[i] = null;
	                break;
	            }
	        }
        }

function cargarIngreso(){
    document.getElementById("lectura_egresos").value = JSON.stringify(idsEgresos);
}

