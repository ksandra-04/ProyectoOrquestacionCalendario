package com.itm.apicalendario.service;

import com.itm.apicalendario.dto.FestivoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FestivoClientService {

    private final RestTemplate restTemplate;

    @Value("${apifestivos.baseurl}")
    private String apiFestivosBaseUrl;

    public FestivoClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FestivoDTO> obtenerFestivos(int year) {
        String url = apiFestivosBaseUrl + "/api/festivos/obtener/" + year;
        FestivoDTO[] festivos = restTemplate.getForObject(url, FestivoDTO[].class);
        return festivos != null ? Arrays.asList(festivos) : List.of();
    }
}
