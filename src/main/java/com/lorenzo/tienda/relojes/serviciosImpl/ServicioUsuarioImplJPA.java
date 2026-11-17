package com.lorenzo.tienda.relojes.serviciosImpl;


import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //Con esto marcas que la clase actua como servicio de datos
@Transactional //Los metodos de la clase se ejecutan como una unica transaction
// Transaccion:
public class ServicioUsuarioImplJPA implements ServicioUsuario {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarUsuario(Usuario u){
        entityManager.persist(u);
    }

    @Override
    public List<Usuario> obtenerUsuario() {
        //Lanzar JPQL
        //La consulta equivale al sql: SELECT * FROM tabla_videojuegos as v
        return entityManager.createQuery("SELECT u FROM Usuario u").getResultList();
    }

    @Override
    public Usuario obtenerUsuarioPorEmailYpass(String email, String pass) {

        Query query = entityManager.createQuery("select u from Usuario u where u.email = :email and u.pass = :pass");
        //la anterior consulta de jpa seria como la sql select * from usuario where email = ? and pass = ?
        query.setParameter("email", email);//asi asigno el :email
        query.setParameter("pass", pass);//asi asigno el :pass
        List<Usuario> res = query.getResultList();


        if(res.size()==0){
            return null;
        }
        return res.get(0);
    }

    @Override
    public Usuario obtenerUsuarioPorId(long id) {
        return entityManager.find(Usuario.class, id);
    }

    @Override
    public void borrarUsuario(long id) {
        //En JPA la forma mas comun de borrar un registro seria pedirlo comprobar que existe y luego borrar
        Usuario usuarioAborrar = entityManager.find(Usuario.class, id);
        if( usuarioAborrar != null){
            entityManager.remove(usuarioAborrar);
        }else{
            System.out.println("id no encontrado");
        }
    }

    @Override
    public void guardarCambiosUsuario(Usuario usuarioEditar) throws Exception {
        entityManager.merge(usuarioEditar);

    }

}

