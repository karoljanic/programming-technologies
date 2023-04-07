import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Class overriding Dialog Pane to display instruction of program.
 */
public class ProgramInstruction extends Dialog<Pane> {
    /**
     * Class constructor initializes and displays dialog with program instruction.
     */
    ProgramInstruction() {
        final String informationString = "Aby stworzyć okrąg: \n" +
                "-> kliknij w odpowiedni przycisk na górnym pasku; \n" +
                "-> kliknij w płótno aby wybrać środek okręgu; \n" +
                "-> przesuń myszką aby ustawić promień i kliknij aby go zatwierdzić; \n \n" +
                "Aby stworzyć prostokąt: \n" +
                "-> kliknij w odpowiedni przycisk na górnym pasku; \n" +
                "-> kliknij w płótno aby wybrać jeden z wierzchołków prostokąta; \n" +
                "-> przesuń myszką aby ustawić drugi z nich i kliknij aby go zatwierdzić; \n \n" +
                "Aby stworzyć trójkąt: \n" +
                "-> kliknij w odpowiedni przycisk na górnym pasku; \n" +
                "-> kliknij w płótno aby wybrać jeden z wierzchołków trójkąta; \n" +
                "-> przesuń myszką aby ustawić drugi z nich i kliknij aby go zatwierdzić; \n" +
                "-> przesuń myszką aby ustawić trzeci z nich i kliknij aby go zatwierdzić; \n \n" +
                "Aby przesunać figurę: \n" +
                "-> kliknij w nią aby ustawić ją jako aktywną; \n" +
                "-> złap ją i przesuń w docelowe miejsce; \n \n" +
                "Aby przeskalować figurę: \n" +
                "-> kliknij w nią aby ustawić ją jako aktywną;" +
                "-> najedź na nią scrollem i przesuwając nim ustaw odpowiedni rozmiar; \n \n" +
                "Aby obrócić figurę: \n" +
                "-> kliknij w nią aby ustawić ją jako aktywną; \n" +
                "-> przytrzymaj klawisz SHIFT; \n" +
                "-> najedź na nią scrollem i przesuwając nim ustaw odpowiednią orientację; \n \n" +
                "Aby ustawić wypełnienie figury: \n" +
                "-> kliknij w nią prawym przyciskiem myszy; \n" +
                "-> otwórz Wybieracz Kolorów, który sie pojawi; \n" +
                "-> wybierz odpowiedni kolor;";

        setTitle("MyGraphics Instruction");
        setHeaderText("Witaj w instrukcji programu MyGraphics!");

        ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(type);

        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        TextArea area = new TextArea(informationString);
        area.setWrapText(true);
        area.setEditable(false);
        area.setBackground(
                new Background(new BackgroundFill(Appearance.secondaryAppColor, new CornerRadii(3), Insets.EMPTY)));

        getDialogPane().setContent(area);
        setResizable(true);
    }
}
