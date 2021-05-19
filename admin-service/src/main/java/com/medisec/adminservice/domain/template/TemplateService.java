package com.medisec.adminservice.domain.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    public void create() {

    }

    public List<TemplateDTO> findAll() {
        return templateRepository
                .findAll()
                .stream()
                .map(template -> new TemplateDTO(
                    template.getId(),
                    template.getName(),
                    template.getExtensions()
        )).collect(Collectors.toList());
    }

    public void delete(Long id) {
        templateRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        templateRepository.deleteById(id);
    }
}
