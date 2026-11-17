package com.lorenzo.tienda.relojes.serviciosImpl;

import com.lorenzo.tienda.relojes.constantesSQL.ConstantesSQL;
import com.lorenzo.tienda.relojes.model.Categoria;
import com.lorenzo.tienda.relojes.model.Usuario;
import com.lorenzo.tienda.relojes.servicios.ServicioCategorias;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.hibernate.query.sql.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service //Con esto marcas que la clase actua como servicio de datos
@Transactional //Los metodos de la clase se ejecutan como una unica transaction
// Transaccion:
public class ServicioCategoriaImplJPA implements ServicioCategorias {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void registrarCategoria(Categoria c){
        //el videojuego ahora mismo tiene la foto subida, pero
        //en imagenPortada no tiene nada
//        try {
//            u.setImagenPortada(u.getFotoSubida().getBytes());
//        } catch (IOException e) {
//            System.out.println("NO PUEDE PROCESAR LA FOTO SUBIDA");
//            throw new RuntimeException(e);
//        }
//        //de la idCtaegoria recibida, hay que asignar la categoria de la base de datos
//        //idCategoria es la id seleccionada en el desplegable de categorias
//        Categoria c = entityManager.find(Categoria.class, u.getIdCategoria());

        entityManager.persist(c);
    }

    @Override
    public List<Categoria> obtenerCategorias() {
        //Lanzar JPQL
        //La consulta equivale al sql: SELECT * FROM tabla_videojuegos as v
        return entityManager.createQuery("SELECT c FROM Categoria c").getResultList();
    }

    @Override
    public Categoria obtenerCategoriaPorId(long id) {
        return entityManager.find(Categoria.class, id);
    }

    @Override
    public void borrarCategoria(long id) {
        //En JPA la forma mas comun de borrar un registro seria pedirlo comprobar que existe y luego borrar
        Categoria categoriaAborrar = entityManager.find(Categoria.class, id);
        if( categoriaAborrar != null){
            entityManager.remove(categoriaAborrar);
        }else{
            System.out.println("id no encontrado");
        }
    }

    @Override
    public void guardarCambiosCategoria(Categoria categoriaEditar) throws Exception {
        //USANDO LA ID SELECCIONADA EN EL DESPLEGABLE DE LA CATEGORIA
        //TENGO QUE ASOCIAR LA CATEGORIA CORRESPONDIENTE AL VIDEOJUEGO
        //Categoria c = entityManager.find(Categoria.class, usuarioEditar.getIdCategoria());
        //usuarioEditar.setCategoria(c);


        //para no perder la foto hay que detectar si el admin le ha asignado una nueva foto, en caso contrario le vamos a cargar la foto anterior qu tuviese
//        if(usuarioEditar.getFotoSubida().getSize()==0){
//            Usuario original =
//                    entityManager.find(Usuario.class,usuarioEditar.getId());
//
//            usuarioEditar.setImagenPortada(original.getImagenPortada());
//        }else{
//            usuarioEditar.setImagenPortada(
//                    usuarioEditar.getFotoSubida().getBytes()
//            );
//        }
        //merge actualiza el registro si ya existe
        //si no existe lo crea
        entityManager.merge(categoriaEditar);

    }

//    @Override
//    public List<Map<String, Object>> obtenerJSONusuarios() {
//        //con JPA hemos asociado categoria a producto
//        //el problema el json recibido en portada no es correcto
//        //una posible solucion: dejar claro en una consulta a base de datos
//        // que informacion va formar el json que necesitamos en portada
//
//        //un map de string object es un conjunto de elementos
//        //donde cada elemento tiene un string y un object
//        //un object en java es cualquier tipo de datos
//        //Object es la clase padre de tosas las clases java
//        //Map<String,Object> json = new HashMap<>();
//        //json.put("modelo","5600");
//        //json.put("precio",45);
//        //json.put(45,55);//da error porque el primero tiene que ser un String
//        //para tener contro total, vamos a usar sql
//        //aqui preparo un sql para JPA
//        Query consulta =
//                entityManager.createNativeQuery(
//                        ConstantesSQL.SQL_OBTENER_USUARIOS_JSON
//                );
//        //usar la consulta directamente es mucho mas lioso que lo siguiente
//        NativeQueryImpl nativeQuery = (NativeQueryImpl)  consulta;
//        nativeQuery.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);
//
//
//        return nativeQuery.getResultList();
//    }

//    @Override
//    public Map<String, Object> obtenerJSONUsuarioPorId(Long id) {
//        return (Map<String, Object>) ((NativeQueryImpl)entityManager.createNativeQuery(ConstantesSQL.SQL_OBTENER_USUARIO_JSON)).setParameter("id",id).setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE).getResultList().get(0);
//    }
}

