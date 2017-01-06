import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class Controller {
    @FXML
    private TextField inputField;

    public void runPalindrome(ActionEvent event) {
        PalindromeTester tester = new PalindromeTester();

        if (tester.isAPalindrome((inputField.getCharacters().toString()))){
            inputField.setText("IS a palindrome");
        }
        else{
            inputField.setText("is NOT a palindrome");
        }

    }
}


