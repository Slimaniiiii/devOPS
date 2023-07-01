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
import tn.esprit.rh.achat.entities.CategorieFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.FournisseurRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class FournisseurServiceImplMockTest {

    @InjectMocks
    FournisseurServiceImpl fs;
    FournisseurRepository fournisseurRepository = Mockito.mock(FournisseurRepository.class);
    Fournisseur fournisseur;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        fournisseur = new Fournisseur(1L,"Test","lib", CategorieFournisseur.CONVENTIONNE,null,null,null);
        fournisseurs.add(fournisseur);
        fournisseurs.add(new Fournisseur(2L,"Test2","lib2", CategorieFournisseur.CONVENTIONNE,null,null,null));
    }

    List<Fournisseur> fournisseurs = new ArrayList<>();

    @Test
    void retrieveFournisseur(){
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Fournisseur f = fs.retrieveFournisseur(1L);
        Assertions.assertNotNull(f);
    }

    @Test
    void retrieveAllFournisseurs(){
        Assertions.assertNotNull(fs.retrieveAllFournisseurs());
    }

    @Test
    void addFournisseur(){
        Mockito.when(fournisseurRepository.save(Mockito.any(Fournisseur.class))).thenReturn(fournisseur);
        fs.addFournisseur(fournisseur);
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Fournisseur f = fs.retrieveFournisseur(1L);
        Assertions.assertEquals(f.getIdFournisseur(), fournisseur.getIdFournisseur());
        Assertions.assertEquals(f.getCode(), fournisseur.getCode());
        Assertions.assertEquals(f.getLibelle(), fournisseur.getLibelle());

    }

    @Test
    void updateFournisseur(){
        Mockito.when(fournisseurRepository.save(Mockito.any(Fournisseur.class))).thenReturn(fournisseur);
        fs.addFournisseur(fournisseur);
        Mockito.when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Fournisseur f = fs.retrieveFournisseur(1L);
        Assertions.assertEquals(f.getIdFournisseur(), fournisseur.getIdFournisseur());
        Assertions.assertEquals(f.getCode(), fournisseur.getCode());
        Assertions.assertEquals(f.getLibelle(), fournisseur.getLibelle());
    }

    @Test
    void deleteFournisseur(){
        long id = 20;
        fs.deleteFournisseur(id);
        Mockito.verify(fournisseurRepository, Mockito.times(1)).deleteById(id);
    }

}
