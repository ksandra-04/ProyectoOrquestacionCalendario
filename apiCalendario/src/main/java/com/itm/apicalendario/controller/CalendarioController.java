package com.itm.apicalendario.controller;

import com.itm.apicalendario.model.Calendario;
import com.itm.apicalendario.service.CalendarioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    private final CalendarioService calendarioService;

    public CalendarioController(CalendarioService calendarioService) {
        this.calendarioService = calendarioService;
    }

    @GetMapping("/generar/{year}")
    public boolean generar(@PathVariable int year) {
        return calendarioService.generarCalendario(year);
    }

    @GetMapping("/listar/{year}")
    public List<Calendario> listar(@PathVariable int year) {
        return calendarioService.listarCalendario(year);
    }
}
