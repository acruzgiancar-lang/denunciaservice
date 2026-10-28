package com.ayasta.denunciaservice.repository;

import com.ayasta.denunciaservice.entity.Denuncia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia, Integer> {

    Page<Denuncia> findAllByOrderByIdDesc(Pageable pageable);

    List<Denuncia> findByDniOrderByIdDesc(String dni);
}
