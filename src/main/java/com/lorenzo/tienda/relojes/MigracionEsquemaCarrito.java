package com.lorenzo.tienda.relojes;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MigracionEsquemaCarrito implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public MigracionEsquemaCarrito(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<String> restricciones = jdbcTemplate.queryForList("""
                SELECT tc.CONSTRAINT_NAME
                FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc
                JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE kcu
                  ON tc.CONSTRAINT_CATALOG = kcu.CONSTRAINT_CATALOG
                 AND tc.CONSTRAINT_SCHEMA = kcu.CONSTRAINT_SCHEMA
                 AND tc.CONSTRAINT_NAME = kcu.CONSTRAINT_NAME
                WHERE tc.TABLE_SCHEMA = 'PUBLIC'
                  AND tc.TABLE_NAME = 'PRODUCTO_CARRITO'
                  AND tc.CONSTRAINT_TYPE = 'UNIQUE'
                  AND kcu.COLUMN_NAME = 'RELOJ_ID'
                """, String.class);

        for (String restriccion : restricciones) {
            jdbcTemplate.execute("ALTER TABLE PRODUCTO_CARRITO DROP CONSTRAINT \""
                    + restriccion.replace("\"", "\"\"") + "\"");
        }
    }
}
