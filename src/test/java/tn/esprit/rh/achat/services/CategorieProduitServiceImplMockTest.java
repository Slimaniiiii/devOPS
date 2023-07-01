package tn.esprit.rh.achat.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CategorieProduitServiceImplMockTest {

    @InjectMocks
    CategorieProduitServiceImpl cps;
    CategorieProduitRepository categorieProduitRepository = Mockito.mock(CategorieProduitRepository.class);

    CategorieProduit categorieProduit;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categorieProduit = new CategorieProduit(1L, "Test", "Lib", null);
        categorieProduits.add(categorieProduit);
        categorieProduits.add(new CategorieProduit(2L, "Test2", "Lib2", null));
    }

    List<CategorieProduit> categorieProduits = new ArrayList<>();


    @Test
    void retrieveCategorieProduit() {
        Mockito.when(categorieProduitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categorieProduit));
        CategorieProduit cp = cps.retrieveCategorieProduit(1L);
        Assertions.assertNotNull(cp);
    }

    @Test
    void retrieveAllCategorieProduits() {
        Assertions.assertNotNull(cps.retrieveAllCategorieProduits());
    }

    @Test
    void addCategorieProduit() {
        Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(categorieProduit);
        cps.addCategorieProduit(categorieProduit);
        Mockito.when(categorieProduitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categorieProduit));
        CategorieProduit cp = cps.retrieveCategorieProduit(1L);
        Assertions.assertEquals(cp.getIdCategorieProduit(), categorieProduit.getIdCategorieProduit());
        Assertions.assertEquals(cp.getLibelleCategorie(), categorieProduit.getLibelleCategorie());
        Assertions.assertEquals(cp.getCodeCategorie(), categorieProduit.getCodeCategorie());
    }

    @Test
    void updateCategorieProduit() {
        Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(categorieProduit);
        cps.addCategorieProduit(categorieProduit);
        Mockito.when(categorieProduitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(categorieProduit));
        CategorieProduit cp = cps.retrieveCategorieProduit(1L);
        Assertions.assertEquals(cp.getIdCategorieProduit(), categorieProduit.getIdCategorieProduit());
        Assertions.assertEquals(cp.getLibelleCategorie(), categorieProduit.getLibelleCategorie());
        Assertions.assertEquals(cp.getCodeCategorie(), categorieProduit.getCodeCategorie());
    }

    @Test
    void deleteCategorieProduit() {
        long id=42;

        cps.deleteCategorieProduit(id);

        Mockito.verify(categorieProduitRepository, Mockito.times(1)).deleteById(id);
    }
}
