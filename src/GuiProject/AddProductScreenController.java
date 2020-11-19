package GuiProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to create a window that allows the user to add a new product
 */
public class AddProductScreenController implements Initializable {
    //Variables to collect data from a user
    @FXML
    private TextField searchTextBox;
    @FXML
    private TextField partIdTextBox;
    @FXML
    private TextField invTextBox;
    @FXML
    private TextField nameTextBox;
    @FXML
    private TextField priceTextBox;
    @FXML
    private TextField maxTextBox;
    @FXML
    private TextField minTextBox;
    //Variables for the parts to select from table
    @FXML
    private TableView<Part> partSelectTable;
    @FXML
    private TableColumn<Part,Integer> partId;
    @FXML
    private TableColumn<Part,String> partName;
    @FXML
    private TableColumn<Part,Integer> partInventory;
    @FXML
    private TableColumn<Part,Integer> partPrice;
    //Variables for the parts included in a product table
    @FXML
    private TableView<Part> productPartTable;
    @FXML
    private TableColumn<Part,Integer> partIdInProduct;
    @FXML
    private TableColumn<Part,String> partNameInProduct;
    @FXML
    private TableColumn<Part,Integer> partInventoryInProduct;
    @FXML
    private TableColumn<Part,Integer> partPriceInProduct;

    /**
     * This method will close the current screen without making any changes
     * @param actionEvent - close screen
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
     * This method will search for a part by name based on the text given in the associated text field
     * @param actionEvent - search for part
     */
    public void partSearchButtonPressed(ActionEvent actionEvent) {
        //if the search box is empty
        if(searchTextBox.getText().isEmpty()){

            partSelectTable.setItems(Inventory.getAllParts());
        }
        //if we're getting an part id
        if (Inventory.isNum(searchTextBox.getText())){
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            searchedParts.add(Inventory.lookupPart(Integer.parseInt(searchTextBox.getText())));
            partSelectTable.setItems(searchedParts);
        }
        //if we're getting a part name
        else{
            partSelectTable.setItems(Inventory.lookupPart(searchTextBox.getText()));
        }
    }

    /**
     * This method will add a new product when the save button is pressed on the add product screen
     * @param actionEvent - save new product
     */
    public void saveButtonPressed(ActionEvent actionEvent) {
       try{
           int id = Inventory.getUniqueId();
           Inventory.setUniqueId();
           String name = nameTextBox.getText();
           int inv = Integer.parseInt(invTextBox.getText());
           Double price = Double.parseDouble(priceTextBox.getText());
           int min = Integer.parseInt(minTextBox.getText());
           int max = Integer.parseInt(maxTextBox.getText());
           ObservableList<Part> partsInProduct = productPartTable.getItems();
        //Check fields and add product
           if(Inventory.minMaxCheck(min,max) && Inventory.inventoryCheck(min,max,inv) && Inventory.priceCheck(price)){
               Product newProduct = new Product(id,name,price,inv,min,max,partsInProduct);
               Inventory.addProduct(newProduct);
            ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
        }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Data must be entered in proper format and may not be left blank:" +
                    " \n\n Inventory: Numbers only " +
                    "\n Price: Numbers only \n Max: Numbers only \n Min: Numbers only");
            alert.setTitle("Incorrect Entry");
            alert.showAndWait();
        }
    }

    /**
     * This method will add a part from the part selection table to the product selection table and also add it to
     * the correct associated parts list
     * @param actionEvent - add selected part
     */
    public void addPartButtonPressed(ActionEvent actionEvent) {
        Part addingPart = partSelectTable.getSelectionModel().getSelectedItem();
        if(addingPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No part selected.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else{
            addPartToTable(addingPart);
            productPartTable.refresh();
        }
    }

    /**
     * This method will delete the selected part from the given product
     * @param actionEvent - delete part
     */
    public void deletePartButtonPressed(ActionEvent actionEvent) {
        Part deletingPart = productPartTable.getSelectionModel().getSelectedItem();
        if(deletingPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No part selected for removal.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to remove this part from the product.");
            alert.setTitle("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                productPartTable.getItems().remove(deletingPart);
            }
        }
    }

    /**
     * The purpose of this method is to add a part to a table; Available parts to parts in current product
     * @param part - part to be added
     */
    private void addPartToTable(Part part){
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productPartTable.getItems().add(part);
    }

    /**
     * This method sets up the initial screen for adding a product
     * @param location - url location
     * @param resources - resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partIdTextBox.setStyle("-fx-control-inner-background: #d1cfcf");
        partIdTextBox.setStyle("-fx-border-color: Grey");
        partIdTextBox.setDisable(true);
        //Part selection table; Top
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        partSelectTable.setItems(Inventory.getAllParts());
        //Parts included in current product table; Bottom
        partIdInProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameInProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryInProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceInProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
