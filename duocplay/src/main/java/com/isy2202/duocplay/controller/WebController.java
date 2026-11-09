package com.isy2202.duocplay.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.isy2202.duocplay.model.EventoModel;
import com.isy2202.duocplay.service.interfaces.IEventoService;
import com.isy2202.duocplay.dto.response.EventoResponseDTO;

@Controller 
public class WebController {
    
    private final IEventoService _service;

    public WebController(IEventoService service) {
        _service = service;
    }

    @GetMapping("/")
    public String inicio(Model model, Principal principal) {

        model.addAttribute("usuarioAutenticado",principal !=null);

        List<EventoResponseDTO> eventosRecientes =
            _service.obtenerEventos().stream()

                .filter(evento ->
                    evento.getEstado() == EventoModel.EstadoEvento.ACTIVO
                )

                .sorted(Comparator.comparing(
                    EventoResponseDTO::getFecha_inicio,
                    Comparator.nullsLast(
                        Comparator.<LocalDateTime>reverseOrder()
                    )
                ))

                .limit(3)
                .toList();

        model.addAttribute("eventosRecientes", eventosRecientes);

        return "index";
    }

    @GetMapping("/eventos")
    public String eventos(

            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String videojuego,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String modalidad,
            @RequestParam(required = false) String ubicacion,

            Model model, Principal principal) {

        model.addAttribute("usuarioAutenticado",principal !=null);

        List<EventoResponseDTO> eventos = _service.obtenerEventos().stream()

            // Mostrar solamente eventos activos
            .filter(evento ->
                evento.getEstado() == EventoModel.EstadoEvento.ACTIVO
            )

            // Aplicar filtros
            .filter(evento ->
                contiene(evento.getNombre(), nombre)
            )
            .filter(evento ->
                contiene(evento.getVideojuego(), videojuego)
            )
            .filter(evento ->
                contiene(evento.getCategoria(), categoria)
            )
            .filter(evento ->
                contiene(evento.getModalidad(), modalidad)
            )
            .filter(evento ->
                contiene(evento.getUbicacion(), ubicacion)
            )

            .sorted(Comparator.comparing(
                EventoResponseDTO::getFecha_inicio,
                Comparator.nullsLast(
                    Comparator.<LocalDateTime>reverseOrder()
                )
            ))

            .toList();

        // Enviar resultados y filtros a Thymeleaf
        model.addAttribute("eventos", eventos);

        model.addAttribute("nombre", nombre);
        model.addAttribute("videojuego", videojuego);
        model.addAttribute("categoria", categoria);
        model.addAttribute("modalidad", modalidad);
        model.addAttribute("ubicacion", ubicacion);

        return "eventos";
    }


    // DETALLE DE EVENTO
    @GetMapping("/eventos/{id_evento}")
    public String detalleEvento(@PathVariable int id_evento, Model model, Principal principal) {

        model.addAttribute("usuarioAutenticado",principal !=null);

        EventoResponseDTO evento = _service.obtenerEventoId(id_evento);

        // No mostrar eventos inactivos
        if (evento.getEstado() != EventoModel.EstadoEvento.ACTIVO) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "El evento no está disponible"
            );
        }

        model.addAttribute("evento", evento);

        return "detalle-evento";
    }


    // PAGINA DE LOGIN
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    // METODO AUXILIAR PARA LOS FILTROS
    private boolean contiene(String valor, String filtro) {

        if (filtro == null || filtro.isBlank()) {
            return true;
        }

        if (valor == null) {
            return false;
        }

        return valor.toLowerCase(Locale.ROOT)
            .contains(filtro.trim().toLowerCase(Locale.ROOT));
    }


}
