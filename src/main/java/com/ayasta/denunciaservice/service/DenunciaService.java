package com.ayasta.denunciaservice.service;

import com.ayasta.denunciaservice.dto.CrearDenunciaRequest;
import com.ayasta.denunciaservice.dto.DenunciaResponse;
import com.ayasta.denunciaservice.dto.PaginaDenunciasResponse;
import com.ayasta.denunciaservice.entity.Denuncia;
import com.ayasta.denunciaservice.exception.RecursoNoEncontradoException;
import com.ayasta.denunciaservice.exception.SolicitudInvalidaException;
import com.ayasta.denunciaservice.repository.DenunciaRepository;
import com.ayasta.denunciaservice.repository.OffsetPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DenunciaService {

    private static final int LIMITE_MINIMO = 1;
    private static final int LIMITE_MAXIMO = 50;

    private final DenunciaRepository denunciaRepository;

    public DenunciaService(DenunciaRepository denunciaRepository) {
        this.denunciaRepository = denunciaRepository;
    }

    @Transactional
    public DenunciaResponse registrar(CrearDenunciaRequest request) {
        Denuncia denuncia = new Denuncia();
        denuncia.setDni(request.getDni().trim());
        denuncia.setTipo(request.getTipo().trim().toUpperCase());
        denuncia.setDireccion(request.getDireccion().trim());
        denuncia.setDescripcion(request.getDescripcion().trim());
        denuncia.setEstado(Denuncia.ESTADO_PENDIENTE);
        return DenunciaResponse.desde(denunciaRepository.save(denuncia));
    }

    @Transactional(readOnly = true)
    public PaginaDenunciasResponse listar(int offset, int limit) {
        if (offset < 0) {
            throw new SolicitudInvalidaException("El parámetro offset no puede ser negativo");
        }
        if (limit < LIMITE_MINIMO || limit > LIMITE_MAXIMO) {
            throw new SolicitudInvalidaException("El parámetro limit debe estar entre " + LIMITE_MINIMO + " y " + LIMITE_MAXIMO);
        }

        OffsetPageRequest pageable = new OffsetPageRequest(offset, limit, Sort.by(Sort.Direction.DESC, "id"));
        Page<Denuncia> resultado = denunciaRepository.findAllByOrderByIdDesc(pageable);
        List<DenunciaResponse> items = resultado.getContent().stream()
                .map(DenunciaResponse::desde)
                .collect(Collectors.toList());
        return new PaginaDenunciasResponse(items, offset, limit, resultado.getTotalElements());
    }

    @Transactional(readOnly = true)
    public DenunciaResponse obtenerPorId(Integer id) {
        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una denuncia con id " + id));
        return DenunciaResponse.desde(denuncia);
    }

    @Transactional(readOnly = true)
    public List<DenunciaResponse> listarPorDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new SolicitudInvalidaException("El DNI debe contener exactamente 8 dígitos");
        }
        return denunciaRepository.findByDniOrderByIdDesc(dni).stream()
                .map(DenunciaResponse::desde)
                .collect(Collectors.toList());
    }

    @Transactional
    public DenunciaResponse marcarAtendida(Integer id) {
        Denuncia denuncia = denunciaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una denuncia con id " + id));
        denuncia.setEstado(Denuncia.ESTADO_ATENDIDA);
        return DenunciaResponse.desde(denunciaRepository.save(denuncia));
    }
}
