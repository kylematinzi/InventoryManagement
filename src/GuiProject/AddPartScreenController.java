package GuiProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to create a screen that allows a user to add a new part
 */
public class AddPartScreenController implements Initializable {

    @FXML
    private Label machLbl;
    @FXML
    private TextField machTextBox;
    @FXML
    private TextField partNameTextBox;
    @FXML
    private TextField invTextBox;
    @FXML
    private TextField priceTextBox;
    @FXML
    private TextField maxTextBox;
    @FXML
    private TextField minTextBox;
    @FXML
    private TextField partIdTextBox;
    @FXML
    private RadioButton outSourceRadio;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private ToggleGroup inOutToggleGroup;

    /**
     * This method will close the Add Part window without making any changes
     * @param actionEvent - cancel button pressed
     */
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"All unsaved changes will be lost. Click OK to continue.");
        alert.setTitle("Are you sure?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        }
    }

    /**
     * This method will save a new part based on the information given by a user in the given text fields
     * @param actionEvent - save new part
     */
    public void saveButtonPressed(ActionEvent actionEvent) {
        try {
            //InHouse part
            if (inHouseRadio.isSelected()) {
                //sets unique id in the inventory class
                int id = Inventory.getUniqueId();
                //tells inventory class to increment the unique id
                Inventory.setUniqueId();
                String name = partNameTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                int stock = Integer.parseInt(invTextBox.getText());
                int min = Integer.parseInt(minTextBox.getText());
                int max = Integer.parseInt(maxTextBox.getText());
                int machineId = Integer.parseInt(machTextBox.getText());
                // Check to see that min <= max, inventory within limits, price not negative
                if (Inventory.minMaxCheck(min, max) && Inventory.inventoryCheck(min, max, stock) && Inventory.priceCheck(price)) {
                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
                    ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                }
            }
            //OutSourced part
            if (outSourceRadio.isSelected()) {
                //sets unique id in the inventory class
                int id = Inventory.getUniqueId();
                //tells inventory class to increment the unique id
                Inventory.setUniqueId();
                String name = partNameTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                int stock = Integer.parseInt(invTextBox.getText());
                int min = Integer.parseInt(minTextBox.getText());
                int max = Integer.parseInt(maxTextBox.getText());
                String companyName = machTextBox.getText();
                //add the part
                if (Inventory.minMaxCheck(min, max) && Inventory.inventoryCheck(min, max, stock) && Inventory.priceCheck(price)) {
                    Inventory.addPart(new OutSourced(id, name, price, stock, min, max, companyName));
                    ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                }
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Data must be entered in proper format and may not be left blank:" +
                    " \n\n Inventory: Numbers only " +
                    "\n Price: Numbers only \n Max: Numbers only \n Min: Numbers only \n Machine ID: Numbers only");
            alert.setTitle("Incorrect Entry");
            alert.showAndWait();
        }
    }

    /**
     * This method sets up the initial screen for adding a part
     * @param location - url location
     * @param resources - resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partIdTextBox.setStyle("-fx-control-inner-background: #d1cfcf");
        partIdTextBox.setStyle("-fx-border-color: Grey");
        partIdTextBox.setDisable(true);
        inOutToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if(outSourceRadio.isSelected()){
                machLbl.setText("Company");
                machTextBox.setPromptText("Company");
            }
            else{
                machLbl.setText("Machine ID");
                machTextBox.setPromptText("Machine ID");
            }
            machTextBox.setFont(Font.font(null, FontPosture.ITALIC, 14));
        });
    }
}
