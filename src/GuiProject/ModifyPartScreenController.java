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
 * The purpose of this class is to create a window that allows the user to modify an already existing part.
 */
public class ModifyPartScreenController implements Initializable {
    @FXML
    private Label machLbl;
    @FXML
    private TextField machTextBox;
    @FXML
    private TextField partIdTextBox;
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
    private RadioButton outSourceRadio;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private ToggleGroup inOutToggleGroup;

    /**
     * This method will close the current screen without making any changes to the program
     * @param actionEvent - close current window
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
     * This method will save any changes to an already existing part
     * @param actionEvent - save updates
     */
    public void saveButtonPressed(ActionEvent actionEvent) {
        try {
            //if inhouse == true then create new part and update list
            if (inHouseRadio.isSelected()) {
                int id = Integer.parseInt(partIdTextBox.getText());
                String name = partNameTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                int stock = Integer.parseInt(invTextBox.getText());
                int min = Integer.parseInt(minTextBox.getText());
                int max = Integer.parseInt(maxTextBox.getText());
                int machineId = Integer.parseInt(machTextBox.getText());
                //check fields and create new part
                if (Inventory.minMaxCheck(min, max) && Inventory.inventoryCheck(min, max, stock) && Inventory.priceCheck(price)) {
                    InHouse newModifyPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(MainScreenController.getPartIndex(), newModifyPart);
                    //next line deletes the old part
                    Inventory.deletePart(Inventory.getAllParts().get(MainScreenController.getPartIndex()));
                    ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
                }
            }
            //if outsource == true then create new part and update list
            else if (outSourceRadio.isSelected()) {
                int id = Integer.parseInt(partIdTextBox.getText());
                String name = partNameTextBox.getText();
                double price = Double.parseDouble(priceTextBox.getText());
                int stock = Integer.parseInt(invTextBox.getText());
                int min = Integer.parseInt(minTextBox.getText());
                int max = Integer.parseInt(maxTextBox.getText());
                String companyName = machTextBox.getText();
                //check fields and create new part
                if (Inventory.minMaxCheck(min, max) && Inventory.inventoryCheck(min, max, stock) && Inventory.priceCheck(price)) {
                    OutSourced newModifyPart = new OutSourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(MainScreenController.getPartIndex(), newModifyPart);
                    //next line deletes the old part
                    Inventory.deletePart(Inventory.getAllParts().get(MainScreenController.getPartIndex()));
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
     * This method sets up the initial modify part screen
     * @param location - url location
     * @param resources - resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partIdTextBox.setStyle("-fx-control-inner-background: #d1cfcf");
        partIdTextBox.setStyle("-fx-border-color: Grey");
        partIdTextBox.setDisable(true);
        //Radio buttons
        inOutToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (outSourceRadio.isSelected()) {
                machLbl.setText("Company");
                machTextBox.setPromptText("Company");
            } else {
                machLbl.setText("Machine ID");
                machTextBox.setPromptText("Machine ID");
            }
            machTextBox.setFont(Font.font(null, FontPosture.ITALIC, 14));
        });
        Part modifiedPart = Inventory.getAllParts().get(MainScreenController.getPartIndex());
        partIdTextBox.setText(String.valueOf(modifiedPart.getId()));
        partNameTextBox.setText(modifiedPart.getName());
        invTextBox.setText(String.valueOf(modifiedPart.getStock()));
        maxTextBox.setText(String.valueOf(modifiedPart.getMax()));
        minTextBox.setText(String.valueOf(modifiedPart.getMin()));
        priceTextBox.setText(String.valueOf(modifiedPart.getPrice()));
        //label must be based on type of part
        if (modifiedPart instanceof InHouse) {
            machTextBox.setText(String.valueOf(((InHouse) modifiedPart).getMachineId()));
        }
        if (modifiedPart instanceof OutSourced) {
            machTextBox.setText(((OutSourced) modifiedPart).getCompanyName());
            outSourceRadio.setSelected(true);
        }
    }
}