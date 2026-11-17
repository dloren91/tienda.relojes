//variables globales
nombre_login = "" //variable que indica el nombre del usuario si se ha identificado

// 1. Descarga de plantillas HTML
$.get(`plantillas_html_${codigo_idioma}/inicio.html`).done(function(res){
    plantilla_inicio = res;
});
$.get(`plantillas_html_${codigo_idioma}/listado_productos.html`).done(function(res){
    plantilla_productos = res;
});
$.get(`plantillas_html_${codigo_idioma}/detalles_producto.html`).done(function(res){
    plantilla_detalles_producto = res;
});
$.get(`plantillas_html_${codigo_idioma}/registro_usuarios.html`).done(function(res){
    plantilla_registro_usuarios = res;
});
$.get(`plantillas_html_${codigo_idioma}/identificar_usuario.html`).done(function(res){
    plantilla_login_usuarios = res;
});
$.get(`plantillas_html_${codigo_idioma}/carrito.html`).done(function(res){
    plantilla_carrito = res;
});
$.get(`plantillas_html_${codigo_idioma}/check_out_1.html`).done(function(res){
    plantilla_check_out_1 = res;
});
$.get(`plantillas_html_${codigo_idioma}/check_out_2.html`).done(function(res){
    plantilla_check_out_2 = res;
});
$.get(`plantillas_html_${codigo_idioma}/check_out_preguntas.html`).done(function(res){
    plantilla_check_out_preguntas = res;
});
$.get(`plantillas_html_${codigo_idioma}/check_out_3.html`).done(function(res){
    plantilla_check_out_3 = res;
});

modelo_a_buscar = ""//modelo en el buscador
comienzo_resultados = 0
// 2. Funciones principales
function mostrar_relojes(){
    $.getJSON("RESTrelojes/listar", { modelo: modelo_a_buscar, comienzo: comienzo_resultados}).done(function(json){
        console.log(json);
        resultadoMustache = Mustache.render(plantilla_productos, json.relojes);
        totalRelojes = json.totalRelojes
        $("#contenedor").html(resultadoMustache);
        $("#resultados").html(totalRelojes)

        //vamos a ocultar el anterior y siguiente cuando no proceda
        if( comienzo_resultados <= 0){
            // en este caso ocultamos el anterior
            $("#enlace_anterior").hide()
        }else{
            $("#enlace_anterior").show()
        }

        if(( comienzo_resultados + 10 < totalRelojes)){
            //en este caso si quieres el enlace siguiente
            $("#enlace_siguiente").show()
        }else{
            $("#enlace_siguiente").hide()
        }


        $("#caratula_principal").removeClass("hidden");

        //para no perder el modelo
        $("#modelo_buscador").val(modelo_a_buscar)
        $("#modelo_buscador").focus()

        $("#modelo_buscador").keyup(
            function (){
                //segun el usuario escriba en el buscador,
                //lo que escriba lo guardo en la variable titulo a buscar
                //e invoco de nuevo el mostrar_relojes
                modelo_a_buscar = $(this).val()
                comienzo_resultados = 0
                mostrar_relojes()
            }
        )//end keyup
        $("#enlace_anterior").click(
            function (){
                comienzo_resultados -= 10
                mostrar_relojes()
            }
        )//end click enlace_anterior
        $("#enlace_siguiente").click(
            function (){
                comienzo_resultados += 10
                mostrar_relojes()
            }
        )//end click enlace_siguiente
    });
}

function mostrar_detalles(id){
    $.getJSON("RESTrelojes/obtener-reloj", { id: id }).done(function(json){
        console.log(json);
        var resultadoHtml = Mustache.render(plantilla_detalles_producto, json);
        $("#contenedor").html(resultadoHtml);
        $("#caratula_principal").addClass("hidden");
    });
}

function mostrar_formulario_registro_usuario(){
    $("#contenedor").html(plantilla_registro_usuarios)
    //una vez mostrado el form de registro, decimos qué pasa al hacer click en registrar
    $("#form_registro_usuario").submit(function(e){
        e.preventDefault() //evita que el form refresque la página
        formulario = document.forms[0]
        formData = new FormData(formulario) //recoge todo lo insertado en el form
        console.log(Object.fromEntries(formData))
        $.ajax("RESTusuarios/registrarUsuario",{
            type: "POST",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function(res){
                alert(res)
                confetti();
            }
        })
    })
}

function mostrar_login(){
    $("#contenedor").html(plantilla_login_usuarios)
    //vamos a comprobar si hay alguna cookie con la info de identificacion y la cargamos
    //, mucho cuidado con guardar contraseñas o informacion personal sin encriptar
    if(typeof(Cookies.get("email")) != "underfined" ){
        $("#email").val(Cookies.get("email"))
    }
    if( typeof(Cookies.get("pass")) != "undefined"){
        $("#pass").val(Cookies.get("pass"))
    }
    $("#form_login").submit(function(e){
        e.preventDefault()
        $.post("RESTusuarios/identificarUsuario",
            {
                email: $("#email").val(),
                pass: $("#pass").val()
            }
        ).done(function(res){
            if(res == "email o pass incorrectos"){
                alert(res)
                return
            }
            nombre_login = res.split(",")[1]
            alert("identificado correctamente, ya puedes agregar productos al carrito")
            if($("#recordar_pass").prop("checked"))
                //guardar los datos d acceso en una cookie
                //cookie no es mas q un archivo q guarda el navegador
                //para cada sitio
                Cookies.set("email",$("#email").val(), {expires : 100})
                Cookies.set("pass", $("#pass").val(), {expires: 100})

            mostrar_relojes()
            $("#usuarioActual").html(nombre_login)
            //ocultar los enlaces para usuarios NO identificados
            $(".enlace_registrarme").css("display","none")
            $(".enlace_identificarme").css("display","none")
            //mostrar los enlaces para usuarios identificados
            $(".enlace_carrito").css("display","block")
            $(".enlace_pedidos").css("display","block")
            $(".enlace_mis_datos").css("display","block")
            $(".enlace_cerrar_sesion").css("display","block")


        })
    })
}

function agregarProductoCarrito(idProducto){
    if (nombre_login == ""){
        alert("Identificate antes")
        return
    }
    $.post("RESTcarrito/agregarProducto",{
        id: idProducto,
        cantidad: parseInt($("#cantidad-" + idProducto).val()) || 1
    }).done(function(res){
        alert(res)
    }).fail(function(xhr){
        console.error("Error al agregar el producto al carrito:", xhr.responseText)
        alert("No se pudo agregar el producto al carrito. Inténtalo de nuevo.")
    })
}

function mostrar_productos_carrito(){
    $.getJSON("RESTcarrito/obtenerProductos").done(function(res){
        console.log("json recibido:")
        console.log(res)
        //usar la plantilla carrito para mostrar el json recibido
        var html = Mustache.render(plantilla_carrito, res)
        $("#contenedor").html(html)
        //al pulsar Realizar pedido, empezamos el checkout
        $("#realizar_pedido").click(function(e){
            e.preventDefault()
            mostrar_check_out_paso_1()
        })
    })
}

function mostrar_check_out_paso_1(){
    $("#contenedor").html(plantilla_check_out_1)
    $("#aceptar_paso_1").click(
        enviar_datos_paso1
    )


}
function enviar_datos_paso1(){
    //obtener los valores introducidos
    //mandarlos a PedidosREST
    nombreCompleto = $("#campo_nombre").val()
    direccion = $("#campo_direccion").val()
    codigoPostal = $("#campo_codigoPostal").val()
    //lo siguiente obtendria el value
    provincia = $("#campo_provincia").val()
    telefono = $("#campo_telefono").val()
    observaciones = $("#campo_observaciones").val()



    $.post("RESTpedido/procesarPaso1",
        {
            nombre: nombreCompleto,
            direccion: direccion,
            codigoPostal: codigoPostal,
            provincia: provincia,
            telefono: telefono,
            observaciones: observaciones
        }
    ).done(function(res){
        //alert("el servidor responde: " + res)
        if(res=="ok"){
            $("#contenedor").html(plantilla_check_out_2);
            $("#aceptar_paso_2").click(
                enviar_datos_paso2
            )
        }else{
            alert(res)
        }
    })
}//end enviar_datos_paso1

function enviar_datos_paso2(){
    //recoger datos introducidos
    tipo_tarjeta = $("#tipo_tarjeta").val()
    numero_tarjeta = $("#numero_tarjeta").val()
    titular_tarjeta = $("#titular_tarjeta").val()
    a_plazos = $("#a_plazos").prop("checked") //true o false según la casilla

    $.post("RESTpedido/procesarPaso2",
        {
            tarjeta : tipo_tarjeta,
            numero : numero_tarjeta,
            titular : titular_tarjeta,
            aPlazos : a_plazos
        }
    ).done(function (res){
        if(res == "ok"){
            //después del pago van las preguntas
            mostrar_check_out_preguntas()
        }else{
            alert(res)
        }
    })
}//end enviar_datos_paso2

function mostrar_check_out_preguntas(){
    $("#contenedor").html(plantilla_check_out_preguntas)
    $("#aceptar_preguntas").click(enviar_datos_preguntas)
}

function enviar_datos_preguntas(){
    $.post("RESTpedido/procesarPreguntas",
        {
            resPregunta1 : $("#resPregunta1").val(),
            resPregunta2 : $("#resPregunta2").val()
        }
    ).done(function (res){
        //el servidor devuelve el resumen completo para mostrar el paso 3
        $("#contenedor").html(Mustache.render(plantilla_check_out_3, res))
        $("#boton_confirmar_pedido").click(enviar_datos_paso3)
    })
}//end enviar_datos_preguntas

function enviar_datos_paso3(){
    $.post("RESTpedido/procesarPaso3").done(
        function (res){
            if(res == "ok"){
                confetti()
                alert("Pedido Completado! Puedes seguir comprando")
                mostrar_relojes()
            }else{
                alert(res)
            }
        }
    )
}//end enviar_datos_paso3


// 3. Eventos de clics (todos a nivel global, no dentro del login)
$("#inicio").click(function(e){
    e.preventDefault();
    $("#contenedor").html(plantilla_inicio);
});

$("#relojes").click(function(e){
    e.preventDefault();
    mostrar_relojes();
});

$("#registrarme").click(function(e){
    e.preventDefault();
    mostrar_formulario_registro_usuario();
});

$("#identificarme").click(function(e){
    e.preventDefault();
    mostrar_login();
});

$("#carrito").click(function(e){
    e.preventDefault();
    mostrar_productos_carrito();
});

$("#pedidos").click(function(e){
    e.preventDefault();
    alert("Ver mis pedidos");
});

$("#mis_datos").click(function(e){
    e.preventDefault();
    alert("Mostrar mis datos");
});

$("#cerrar_sesion").click(function(e){
    e.preventDefault();
    alert("Cerrar la sesión");
});

$("#cerrar_sesion").click(cerrar_sesion)

function cerrar_sesion(){
    alert("cerrar sesion")
    $.post("RESTusuarios/cerrarSesion").done(
        function (res){
            if( res == "ok"){
                location.reload()
            }
        }

    )
}

// Carga inicial
mostrar_relojes();