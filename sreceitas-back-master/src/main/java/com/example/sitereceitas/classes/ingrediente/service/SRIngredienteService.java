package com.example.sitereceitas.classes.ingrediente.service;

import com.example.sitereceitas.classes.ingrediente.model.common.Ingrediente;
import com.example.sitereceitas.classes.ingrediente.repository.SRIngredienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SRIngredienteService {

    final private SRIngredienteRepository ingredienteRepository;

    @Transactional
    public Ingrediente cadastrarOuObterIngrediente(String nome) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findByNome(nome);
        if (ingrediente.isPresent())
            return ingrediente.get();
        else {
            Ingrediente i = new Ingrediente();
            i.setNome(nome);
            return ingredienteRepository.save(i);
        }
    }
}
