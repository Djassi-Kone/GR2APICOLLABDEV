package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.Exception.RessourceNotFoundException;
import com.apicollabdev.odk.collabdev.dto.DomaineRequestDto;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Domaine;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.service.Impl.DomaineServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.AdministrateurService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/domaines")
@RestController
public class DomaineController {

    @Autowired
    private DomaineServiceImpl domaineServiceImpl;

    @Autowired
    private AdministrateurService administrateurService;

    @Autowired
    private AdministrateurRepository administrateurRepository;

    // POST sans 'consumes'
    @PostMapping("/administrateur/{idAdmin}")
    public ResponseEntity<Domaine> create(@RequestBody DomaineRequestDto domaine, @PathVariable("idAdmin") long idAdmin) {
        Administrateur a = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));
        return ResponseEntity.ok(domaineServiceImpl.createDomaine(domaine, idAdmin));
    }

    @GetMapping
    public List<Domaine> getAll() {
        return domaineServiceImpl.getAllDomaine();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domaine> getById(@PathVariable Long id) {
        return ResponseEntity.ok(domaineServiceImpl.getById(id));
    }

    @DeleteMapping("/domaine/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        domaineServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // PUT sans 'consumes' ni 'produces'
    @PutMapping("/{idDomaine}/admin/{idAdmin}")
    public ResponseEntity<Domaine> updateDomaine(
            @PathVariable Long idDomaine,
            @PathVariable Long idAdmin,
            @RequestBody DomaineRequestDto domaineDto) {  // <-- DTO ici

        Administrateur admin = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RessourceNotFoundException("Administrateur non trouvé"));

        Domaine updated = domaineServiceImpl.updateDomaineFromDto(idDomaine, domaineDto, admin);
        return ResponseEntity.ok(updated);
    }

}
