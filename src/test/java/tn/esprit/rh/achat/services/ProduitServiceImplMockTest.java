package tn.esprit.rh.achat.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.repositories.ProduitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProduitServiceImplMockTest {

    @InjectMocks
    ProduitServiceImpl produitServiceImpl;
    ProduitRepository produitRepository = Mockito.mock(ProduitRepository.class);

    Produit produit;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produit = new Produit(); produit.setIdProduit(1L); produit.setLibelleProduit("produit1"); produit.setPrix(1000);
        produits.add(produit);
        produits.add(new Produit(1L, "produit1", "libelle1"));
    }

    List<Produit> produits = new ArrayList<>();


    @Test
    void retrieveProduit() {
        Mockito.when(produitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(produit));
        Produit cp = produitServiceImpl.retrieveProduit(1L);
        Assertions.assertNotNull(cp);
    }

    @Test
    void retrieveAllProduits() {
        Assertions.assertNotNull(produitServiceImpl.retrieveAllProduits());
    }

    @Test
    void addProduit() {
        Mockito.when(produitRepository.save(Mockito.any(Produit.class))).thenReturn(produit);
        produitServiceImpl.addProduit(produit);
        Mockito.when(produitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(produit));
        Produit cp = produitServiceImpl.retrieveProduit(1L);
        Assertions.assertEquals(cp.getIdProduit(), produit.getIdProduit());
        Assertions.assertEquals(cp.getLibelleProduit(), produit.getLibelleProduit());
        Assertions.assertEquals(cp.getCodeProduit(), produit.getCodeProduit());

    }

    @Test
    void updateProduit() {
        Mockito.when(produitRepository.save(Mockito.any(Produit.class))).thenReturn(produit);
        produitServiceImpl.addProduit(produit);
        Mockito.when(produitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(produit));
        Produit cp = produitServiceImpl.retrieveProduit(1L);
        Assertions.assertEquals(cp.getIdProduit(), produit.getIdProduit());
        Assertions.assertEquals(cp.getLibelleProduit(), produit.getLibelleProduit());
        Assertions.assertEquals(cp.getCodeProduit(), produit.getCodeProduit());
    }

    @Test
    void deleteProduit() {
        long id=1L;

        produitServiceImpl.deleteProduit(id);

        Mockito.verify(produitRepository, Mockito.times(1)).deleteById(id);
    }
}
