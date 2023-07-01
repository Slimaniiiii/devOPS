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
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.entities.Operateur;
import tn.esprit.rh.achat.repositories.OperateurRepository;

import java.util.HashSet;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OperateurServiceImplMockTest {
    @InjectMocks
    OperateurServiceImpl service;
    OperateurRepository repository = Mockito.mock(OperateurRepository.class);
    Operateur operateur;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operateur = new Operateur();

        operateur.setIdOperateur(1L);
        operateur.setNom("Operateur");
        operateur.setPrenom("Operateur");
        operateur.setPassword("password");

        HashSet<Facture> factures = new HashSet<>();
        factures.add(new Facture());
        operateur.setFactures(factures);
    }

    @Test
    void getOperateur() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(operateur));
        Operateur op = service.retrieveOperateur(1L);
        Assertions.assertNotNull(op);
    }

    @Test
    void getOperateurs() {
        Assertions.assertNotNull(service.retrieveAllOperateurs());
    }

    @Test
    void deleteCategorieProduit() {
        Long id = 1L;
        service.deleteOperateur(id);
        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void addOperateur() {
        Mockito.when(repository.save(Mockito.any(Operateur.class))).thenReturn(operateur);
        service.addOperateur(operateur);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(operateur));

        Operateur op = service.retrieveOperateur(1L);

        Assertions.assertEquals(op.getIdOperateur(), operateur.getIdOperateur());
        Assertions.assertEquals(op.getNom(), operateur.getNom());
        Assertions.assertEquals(op.getPrenom(), operateur.getPrenom());
        Assertions.assertEquals(op.getPassword(), operateur.getPassword());
    }
}
