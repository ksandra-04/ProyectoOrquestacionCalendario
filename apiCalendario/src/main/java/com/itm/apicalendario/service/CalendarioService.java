package com.itm.apicalendario.service;

import com.itm.apicalendario.dto.FestivoDTO;
import com.itm.apicalendario.model.Calendario;
import com.itm.apicalendario.model.Tipo;
import com.itm.apicalendario.repository.CalendarioRepository;
import com.itm.apicalendario.repository.TipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class CalendarioService {

    private static final String TIPO_LABORAL = "Dia laboral";
    private static final String TIPO_FIN_SEMANA = "Fin de Semana";
    private static final String TIPO_FESTIVO = "Dia festivo";

    private final CalendarioRepository calendarioRepository;
    private final TipoRepository tipoRepository;
    private final FestivoClientService festivoClientService;

    public CalendarioService(CalendarioRepository calendarioRepository,
                              TipoRepository tipoRepository,
                              FestivoClientService festivoClientService) {
        this.calendarioRepository = calendarioRepository;
        this.tipoRepository = tipoRepository;
        this.festivoClientService = festivoClientService;
    }

    @Transactional
    public boolean generarCalendario(int year) {
        try {
            Tipo tipoLaboral = obtenerOCrearTipo(TIPO_LABORAL);
            Tipo tipoFinSemana = obtenerOCrearTipo(TIPO_FIN_SEMANA);
            Tipo tipoFestivo = obtenerOCrearTipo(TIPO_FESTIVO);

            List<FestivoDTO> festivos = festivoClientService.obtenerFestivos(year);

            Map<LocalDate, String> mapaFestivos = new HashMap<>();
            for (FestivoDTO f : festivos) {
                mapaFestivos.put(LocalDate.parse(f.getFecha()), f.getNombre());
            }

            LocalDate inicio = LocalDate.of(year, 1, 1);
            LocalDate fin = LocalDate.of(year, 12, 31);

            calendarioRepository.deleteByFechaBetween(inicio, fin);

            for (LocalDate fecha = inicio; !fecha.isAfter(fin); fecha = fecha.plusDays(1)) {
                Tipo tipoDia;
                String descripcion;

                if (mapaFestivos.containsKey(fecha)) {
                    tipoDia = tipoFestivo;
                    descripcion = mapaFestivos.get(fecha);
                } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY
                        || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    tipoDia = tipoFinSemana;
                    descripcion = nombreDia(fecha);
                } else {
                    tipoDia = tipoLaboral;
                    descripcion = nombreDia(fecha);
                }

                calendarioRepository.save(new Calendario(fecha, tipoDia, descripcion));
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Calendario> listarCalendario(int year) {
        LocalDate inicio = LocalDate.of(year, 1, 1);
        LocalDate fin = LocalDate.of(year, 12, 31);
        return calendarioRepository.findByFechaBetweenOrderByFechaAsc(inicio, fin);
    }

    private Tipo obtenerOCrearTipo(String nombre) {
        return tipoRepository.findByTipo(nombre)
                .orElseGet(() -> tipoRepository.save(new Tipo(nombre)));
    }

    private String nombreDia(LocalDate fecha) {
        String dia = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
        return dia.substring(0, 1).toUpperCase() + dia.substring(1);
    }
}
