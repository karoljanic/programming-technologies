import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/**
 * Class overriding Alert to display basic information of program.
 */
public class ProgramInformation extends Alert {
    /**
     * Class constructor initializes and displays alert with program basic
     * information.
     */
    ProgramInformation() {
        super(AlertType.INFORMATION);

        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        setTitle("MyGraphics Information");
        setHeaderText("Witaj w programie MyGraphics!");

        String informationString = "Program służy do tworzenie prostych figur geometrycznych" +
                "oraz ich modyfikacji. Więcej informacji w instrukcji użytkownika. \n \n" +
                "Autor: Karol Janic \n \n Wersja: 1.0";

        setContentText(informationString);

        setGraphic(null);
    }
}
