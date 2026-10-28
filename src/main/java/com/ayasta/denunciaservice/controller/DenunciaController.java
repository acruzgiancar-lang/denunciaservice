package com.ayasta.denunciaservice.controller;

import com.ayasta.denunciaservice.dto.CrearDenunciaRequest;
import com.ayasta.denunciaservice.dto.DenunciaResponse;
import com.ayasta.denunciaservice.dto.PaginaDenunciasResponse;
import com.ayasta.denunciaservice.service.DenunciaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping({"", "/"})
    public ResponseEntity<DenunciaResponse> registrar(@Valid @RequestBody CrearDenunciaRequest request) {
        DenunciaResponse creada = denunciaService.registrar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/denuncias/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping({"", "/"})
    public PaginaDenunciasResponse listar(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return denunciaService.listar(offset, limit);
    }

    @GetMapping("/ciudadano/{dni}")
    public List<DenunciaResponse> porDni(@PathVariable String dni) {
        return denunciaService.listarPorDni(dni);
    }

    @GetMapping("/{id}")
    public DenunciaResponse porId(@PathVariable Integer id) {
        return denunciaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public DenunciaResponse marcarAtendida(@PathVariable Integer id) {
        return denunciaService.marcarAtendida(id);
    }
}
