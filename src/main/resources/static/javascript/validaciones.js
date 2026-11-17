//validaciones por expresiones regulares
// en js todo lo que se ponga entra / es
//expresiones regular
// ^ es para decir que debe empezar
// $ debe terminar
// la i al final es para case insersitive,
// considerer mayus y minus

regexp_nombre = /^[a-z áéíóúñ]{2,20}$/i
function validarNombre(nombre){
    if(regexp_nombre.test(nombre)){
        return true;
    }else {
        alert("Nombre debe ser letras y espacios de 2 a 20 caracteres")
        return false;
    }
}