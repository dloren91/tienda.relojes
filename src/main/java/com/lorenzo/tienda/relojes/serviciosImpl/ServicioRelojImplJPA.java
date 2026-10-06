package com.lorenzo.tienda.relojes.serviciosImpl;

import com.lorenzo.tienda.relojes.model.Reloj;
import com.lorenzo.tienda.relojes.servicios.ServicioReloj;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service //Con esto marcas que la clase actua como servicio de datos
@Transactional //Los metodos de la clase se ejecutan como una unica transaction
// Transaccion:
public class ServicioRelojImplJPA implements ServicioReloj {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarReloj(Reloj r){
        //el videojuego ahora mismo tiene la foto subida, pero
        //en imagenPortada no tiene nada
        try {
            r.setImagenPortada(r.getFotoSubida().getBytes());
        } catch (IOException e) {
            System.out.println("NO PUEDE PROCESAR LA FOTO SUBIDA");
            throw new RuntimeException(e);
        }
        entityManager.persist(r);
    }

    @Override
    public List<Reloj> obtenerReloj() {
        //Lanzar JPQL
        //La consulta lanada equivale al sql: SELECT * FROM tabla_videojuegos as v
        return entityManager.createQuery("SELECT r FROM Reloj r").getResultList();
    }

    @Override
    public Reloj obtenerRelojPorId(long id) {
        return entityManager.find(Reloj.class, id);
    }

    @Override
    public void borrarReloj(long id) {
        //En JPA la forma mas comun de borrar un registro seria pedirlo comprobar que existe y luego borrar
        Reloj relojAborrar = entityManager.find(Reloj.class, id);
        if( relojAborrar != null){
            entityManager.remove(relojAborrar);
        }else{
            System.out.println("id no encontrado");
        }
    }

    @Override
    public void guardarCambiosReloj(Reloj relojEditar) throws Exception {
        //para no perder la foto hay que detectar si el admin le ha asignado una nueva foto, en caso contrario le vamos a cargar la foto anterior qu tuviese
        if(relojEditar.getFotoSubida().getSize()==0){
            Reloj original =
                    entityManager.find(Reloj.class,relojEditar.getId());

            relojEditar.setImagenPortada(original.getImagenPortada());
        }else{
            relojEditar.setImagenPortada(
                    relojEditar.getFotoSubida().getBytes()
            );
        }
        //merge actualiza el registro si ya existe
        //si no existe lo crea
        entityManager.merge(relojEditar);

    }
}

