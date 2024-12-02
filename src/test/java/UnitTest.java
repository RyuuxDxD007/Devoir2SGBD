import org.Homework.Projet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

public class UnitTest {

    private static Projet mockedProjet;
    private static Projet projet;


    @BeforeAll
    public static void setUp() {
        UnitTest.projet = new Projet();
        UnitTest.mockedProjet = Mockito.spy(projet);
    }

    @Nested
    @DisplayName("Unit tests for calculTotalProjetAchat")
    class testCTPA {
        @Test
        @DisplayName("Normal working value without mock (with dependance)")

        public void testCTPANormal() {
            projet.setPrixHabitation(200000);
            projet.setRevenuCadastral(1000);
            projet.setFraisNotaireAchat(5000);
            projet.setFraisTransformation(1000);
            Assertions.assertEquals(226060, projet.calculTotalProjetAchat(), 0.001);
        }

        @ParameterizedTest
        @CsvSource({"200000, 5000, 1000, 60, 20000, 226060",
                "500000, 3000, 2000, 120, 60000, 565120",
                "365000, 2000, 5000, 300, 40875, 413175"})
        @DisplayName("Normal working values with mock")

        public void testCTPANormalMock(double habitation, double notaire, double transformation, double frais, double droit, double total) {
            mockedProjet.setPrixHabitation(habitation);
            mockedProjet.setFraisNotaireAchat(notaire);
            mockedProjet.setFraisTransformation(transformation);
            Mockito.doReturn(frais).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(droit).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertEquals(total, mockedProjet.calculTotalProjetAchat(), 0.001);
        }

        @Test
        @DisplayName("Negative number")

        public void testCTPANegative() {
            mockedProjet.setPrixHabitation(-200000);
            mockedProjet.setFraisNotaireAchat(5000);
            mockedProjet.setFraisTransformation(1000);
            Mockito.doReturn(60.00).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(20000.00).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertTrue(mockedProjet.calculTotalProjetAchat() > 0, "Total project cost should not be negative!");
        }

        @Test
        @DisplayName("Detailed number (7 number after the 7 test)")

        public void testCTPADetailed() {
            mockedProjet.setPrixHabitation(200000);
            mockedProjet.setFraisNotaireAchat(500.2654789);
            mockedProjet.setFraisTransformation(1000);
            Mockito.doReturn(60.00).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(20000.00).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertEquals(221560.2654789, mockedProjet.calculTotalProjetAchat(), 0.001);
        }

        @Test
        @DisplayName("Max values overflow")

        public void testCTPAOverflow() {
            mockedProjet.setPrixHabitation(Double.MAX_VALUE);
            mockedProjet.setFraisNotaireAchat(Double.MAX_VALUE);
            mockedProjet.setFraisTransformation(Double.MAX_VALUE);
            Mockito.doReturn(Double.MAX_VALUE).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(Double.MAX_VALUE).when(mockedProjet).calculDroitEnregistrement();

            double result = mockedProjet.calculTotalProjetAchat();
            Assertions.assertFalse(Double.isInfinite(result), "Total should fail because result in positive infinity due to overflow");
            Assertions.assertFalse(Double.isNaN(result), "Total should fail because it's an undefined computation resulting in not a number");
        }

    }

    @Nested
    @DisplayName("Unit tests for calculApportMinimal")
    class testCAM {
        @Test
        @DisplayName("Normal working value without mock (with dependance)")

        public void testCAMNormal() {
            projet.setPrixHabitation(200000);
            projet.setRevenuCadastral(1000);
            projet.setFraisNotaireAchat(5000);
            projet.setFraisTransformation(1000);
            Assertions.assertEquals(45106, projet.calculApportMinimal(), 0.001);
        }

        @ParameterizedTest
        @CsvSource({"200000, 5000, 1000, 60, 20000, 45106",
                "500000, 3000, 2000, 120, 60000, 113212",
                "365000, 2000, 5000, 300, 40875, 79905",
                "365000, 2000, 5000, 300, 19620, 58650"})
        @DisplayName("Normal working values with mock")

        public void testCAMNormalMock(double habitation, double notaire, double transformation, double frais, double droit, double total) {
            mockedProjet.setPrixHabitation(habitation);
            mockedProjet.setFraisNotaireAchat(notaire);
            mockedProjet.setFraisTransformation(transformation);
            Mockito.doReturn(frais).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(droit).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertEquals(total, mockedProjet.calculApportMinimal(), 0.001);
        }

        @Test
        @DisplayName("Negative numbers")

        public void testCAMNegative() {
            mockedProjet.setPrixHabitation(-200000);
            mockedProjet.setFraisNotaireAchat(5000);
            mockedProjet.setFraisTransformation(1000);
            Mockito.doReturn(60.00).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(-20000.00).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertTrue(mockedProjet.calculApportMinimal() > 0, "Total should not be negative!");
        }

        @Test
        @DisplayName("Detailed number (7 number after the 7 test)")

        public void testCAMDetailed() {
            mockedProjet.setPrixHabitation(200000);
            mockedProjet.setFraisNotaireAchat(500.2654789);
            mockedProjet.setFraisTransformation(1000);
            Mockito.doReturn(60.00).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(20000.00).when(mockedProjet).calculDroitEnregistrement();
            Assertions.assertEquals(40606.2654789, mockedProjet.calculApportMinimal(), 0.001);
        }

        @Test
        @DisplayName("Max values overflow")

        public void testCAMOverflow() {
            mockedProjet.setPrixHabitation(Double.MAX_VALUE);
            mockedProjet.setFraisNotaireAchat(Double.MAX_VALUE);
            mockedProjet.setFraisTransformation(Double.MAX_VALUE);
            Mockito.doReturn(Double.MAX_VALUE).when(mockedProjet).calculTVAFraisTransformation();
            Mockito.doReturn(Double.MAX_VALUE).when(mockedProjet).calculDroitEnregistrement();

            double result = mockedProjet.calculApportMinimal();
            Assertions.assertFalse(Double.isInfinite(result), "Calculation should fail because of overflow");
            Assertions.assertFalse(Double.isNaN(result), "Calculation should fail because it's an undefined computation resulting in not a number");
        }
    }

}
