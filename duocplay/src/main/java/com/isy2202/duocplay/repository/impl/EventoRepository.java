package com.isy2202.duocplay.repository.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;
import org.springframework.stereotype.Repository;

import com.isy2202.duocplay.model.EventoModel;
import com.isy2202.duocplay.repository.interfaces.IEventoRepository;

@Repository 
public class EventoRepository  implements IEventoRepository {

    private final Jdbi _jdbi;

    public EventoRepository(Jdbi jdbi) {
        _jdbi = jdbi;
    }


    @Override
    public List<EventoModel> getAll() {
        String sql = """
        SELECT * FROM DUOCPLAY.EVENTO
        ORDER BY FECHA_INICIO DESC
        """;

        return _jdbi.withHandle(handle ->
            handle.createQuery(sql)
            .mapToBean(EventoModel.class)
            .list()
        );
    }

    @Override
    public Optional<EventoModel> getById(int id_evento) {
        
        String sql = """
        SELECT * FROM DUOCPLAY.EVENTO
        WHERE ID_EVENTO = :id_evento 
        """;

        return _jdbi.withHandle(handle -> 
            handle.createQuery(sql)
            .bind("id_evento", id_evento)
            .mapToBean(EventoModel.class)
            .findOne()
        );
    }

    @Override
    public int create(EventoModel evento) {
        String sql = """
        INSERT INTO DUOCPLAY.EVENTO (
                    NOMBRE,
                    DESCRIPCION,
                    VIDEOJUEGO,
                    CATEGORIA,
                    MODALIDAD,
                    UBICACION,
                    URL_EVENTO,
                    FECHA_INICIO,
                    FECHA_FIN,
                    ESTADO,
                    USUARIO_CREADOR
                ) VALUES (
                    :nombre,
                    :descripcion,
                    :videojuego,
                    :categoria,
                    :modalidad,
                    :ubicacion,
                    :url_evento,
                    COALESCE(:fecha_inicio, SYSTIMESTAMP),
                    :fecha_fin,
                    :estado,
                    :usuario_creador
        """;

        return _jdbi.withHandle(handle -> 
            {
                Update query = handle.createUpdate(sql)
                .bind("nombre", evento.getNombre())
                .bind("videojuego", evento.getVideojuego())
                .bind("categoria", evento.getCategoria())
                .bind("modalidad", evento.getModalidad())
                .bind("estado", evento.getEstado())
                .bind("usuario_creador", evento.getUsuario_creador());

                bindNullable(query, "descripcion", evento.getDescripcion(), Types.VARCHAR);
                bindNullable(query, "ubicacion", evento.getUbicacion(), Types.VARCHAR);
                bindNullable(query, "url_evento", evento.getUrl_evento(), Types.VARCHAR);

                if (evento.getFecha_inicio() != null) {
                    query.bind("fecha_inicio", Timestamp.valueOf(evento.getFecha_inicio()));
                } else {
                    query.bindNull("fecha_inicio", Types.TIMESTAMP);
                }

                if (evento.getFecha_fin() != null) {
                    query.bind("fecha_fin", Timestamp.valueOf(evento.getFecha_fin()));
                } else {
                    query.bindNull("fecha_fin", Types.TIMESTAMP);
                }

                return query
                    .executeAndReturnGeneratedKeys("id_evento")
                    .mapTo(int.class)
                    .one();
            }
        );
    }

    @Override
    public int update(EventoModel evento) {
        return _jdbi.withHandle(handle -> {
            Update query = handle.createUpdate("""
                UPDATE DUOCPLAY.EVENTO
                SET NOMBRE = :nombre,
                DESCRIPCION = :descripcion,
                VIDEOJUEGO = :videojuego,
                CATEGORIA = :categoria,
                MODALIDAD = :modalidad,
                UBICACION = :ubicacion,
                URL_EVENTO = :url_evento,
                FECHA_INICIO = :fecha_inicio,
                FECHA_FIN = :fecha_fin
                WHERE ID_EVENTO = :id_evento
            """)
            .bind("nombre", evento.getNombre())
            .bind("videojuego", evento.getVideojuego())
            .bind("categoria", evento.getCategoria())
            .bind("modalidad", evento.getId_evento());

            bindNullable(query,"descripcion",evento.getDescripcion(),Types.VARCHAR);
            bindNullable(query,"ubicacion",evento.getUbicacion(),Types.VARCHAR);
            bindNullable(query,"url_evento",evento.getUrl_evento(),Types.VARCHAR);

            query.bind("fecha_inicio",Timestamp.valueOf(evento.getFecha_inicio()));

            if (evento.getFecha_fin() != null) {
                query.bind("fecha_fin", Timestamp.valueOf(evento.getFecha_fin()));
            } else {
                query.bindNull("fecha_fin", Types.TIMESTAMP);
            }

            return query.execute();
        });
    }

    @Override
    public int desactivate(int id_evento) {
        String sql = """
        UPDATE DUOCPLAY.EVENTO
        SET ESTADO = :estado
        WHERE ID_EVENTO = :id_evento
        """;

        return _jdbi.withHandle(handle ->
            handle.createUpdate(sql)
            .bind("estado", EventoModel.EstadoEvento.INACTIVO)
            .bind("id_evento", id_evento)
            .execute()
        );
    }
    
    private void bindNullable(
            Update query,
            String parameter,
            String value,
            int sqlType) {

        if (value != null) {
            query.bind(parameter, value);
        } else {
            query.bindNull(parameter, sqlType);
        }
    }

}
