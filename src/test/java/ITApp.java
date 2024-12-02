import org.junit.jupiter.api.*;
import org.Homework.*;

@DisplayName("Tests d'intégration: ensemble des composants")
public class ITApp {
    @Test
    @DisplayName("Validation du meilleur scénario")
    public void happyPath (){
        Projet projet = new Projet();
        projet.setPrixHabitation(310000);
        projet.setRevenuCadastral(710);
        projet.setFraisNotaireAchat(5200);
        projet.setFraisTransformation(1100);
        double apportPersonnel = projet.calculApportMinimal();
        double montantEmprunt = projet.calculResteAEmprunter();

        Pret pret = new Pret();
        pret.setFraisDossierBancaire(1000);
        pret.setFraisNotaireCredit(1200);
        pret.setNombreAnnees(5);
        pret.setTauxAnnuel(0.05);
        Assertions.assertEquals(true, true);
        double apportBancaire = pret.calculRestantDu(montantEmprunt);

        Assertions.assertEquals(54716.6 ,apportBancaire + apportPersonnel,0.001 );
    }


}
