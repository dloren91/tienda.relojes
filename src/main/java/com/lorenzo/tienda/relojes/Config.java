package com.lorenzo.tienda.relojes;

//aqui vamos a configurar varias cosas entre ellas el interceptor

import com.lorenzo.tienda.relojes.interceptores.InterceptorAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Component
public class Config implements WebMvcConfigurer {

    @Autowired
    private InterceptorAdmin interceptorAdmin;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptorAdmin);
        registry.addInterceptor(localeChangeInterceptor());
    }
    //indicamos que el siguiente metodo devuelve una bean de spring
    @Bean
    public LocaleResolver localeResolver(){
        // al tener @Bean en este metodo, spring va a meter lo que devuelva
        //en el retun en el contenedor de spring

        // Para que queremos un localeResolver?
        //sirve para en que idioma mostrar la app web
        //vamos a decir que el cambio de idioma se mantenga en sesion
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.getDefault());
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setIgnoreInvalidLocale(true);
        localeChangeInterceptor.setParamName("idioma");
        return  localeChangeInterceptor;
    }

}
