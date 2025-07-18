package com.apicollabdev.odk.collabdev.controller;

import com.apicollabdev.odk.collabdev.dto.DomaineRequestDto;
import com.apicollabdev.odk.collabdev.entity.Administrateur;
import com.apicollabdev.odk.collabdev.entity.Domaine;
import com.apicollabdev.odk.collabdev.repository.AdministrateurRepository;
import com.apicollabdev.odk.collabdev.service.Impl.DomaineServiceImpl;
import com.apicollabdev.odk.collabdev.service.Interfaces.AdministrateurService;
import com.apicollabdev.odk.collabdev.service.Interfaces.DomaineService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RequestMapping("/api/domaines")
@RestController
public class DomaineController {

    @Autowired
    private DomaineServiceImpl domaineServiceImpl;
    @Autowired
    private  AdministrateurService administrateurService;
    @Autowired
    private  AdministrateurRepository administrateurRepository;

    @PostMapping(value = "/administrateur/{idAdmin}", consumes = "application/json")
    //@PostMapping("/administrateur/{idAdmin}")
    public ResponseEntity<Domaine> create(@RequestBody DomaineRequestDto domaine, @PathVariable("idAdmin") long idAdmin) {
        Administrateur a = administrateurRepository.findById(idAdmin).orElseThrow( ()-> new RuntimeException("Administrateur non trouvé"));
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        domaineServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/administrateur/{idAdmin}/{idDomaine}")
    public ResponseEntity<Domaine> updateDomaine(
            @PathVariable("idAdmin") Long idAdmin,
            @PathVariable("idDomaine") Long idDomaine,
            @RequestBody Domaine domaine) {

        Administrateur admin = administrateurRepository.findById(idAdmin)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));

        Domaine updated = domaineServiceImpl.updateDomaine(idDomaine, domaine, admin);
        return ResponseEntity.ok(updated);
    }

}
