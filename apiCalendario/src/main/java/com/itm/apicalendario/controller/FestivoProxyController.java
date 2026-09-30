package com.itm.apicalendario.controller;

import com.itm.apicalendario.dto.FestivoDTO;
import com.itm.apicalendario.service.FestivoClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/festivos")
public class FestivoProxyController {

    private final FestivoClientService festivoClientService;

    public FestivoProxyController(FestivoClientService festivoClientService) {
        this.festivoClientService = festivoClientService;
    }

    @GetMapping("/obtener/{year}")
    public List<FestivoDTO> obtener(@PathVariable int year) {
        return festivoClientService.obtenerFestivos(year);
    }
}
