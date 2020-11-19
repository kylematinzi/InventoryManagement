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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to create a window that allows the user to modify an already existing product.
 */
public class ModifyProductScreenController implements Initializable {
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

    //Product to be modified
    private static Product modifyingProduct = null;

    /**
     * This method will close the Modify Product screen without saving any changes
     * @param actionEvent - close window
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
            partSelectTable.refresh();
        }
    }

    /**
     * This method will save a product based on the information provided by the user in given text fields
     * @param actionEvent - save product
     */
    public void saveProductButtonPressed(ActionEvent actionEvent) {
        try{
            int id = modifyingProduct.getId();
            String name = nameTextBox.getText();
            int inv = Integer.parseInt(invTextBox.getText());
            Double price = Double.parseDouble(priceTextBox.getText());
            int min = Integer.parseInt(minTextBox.getText());
            int max = Integer.parseInt(maxTextBox.getText());
            ObservableList<Part> partsInProduct = productPartTable.getItems();
        //Check fields and modify product
            if(Inventory.minMaxCheck(min,max) && Inventory.inventoryCheck(min,max,inv) && Inventory.priceCheck(price)) {
                Product modifiedProduct = new Product(id,name,price,inv,min,max, partsInProduct);
                Inventory.deleteProduct(modifyingProduct);
                Inventory.addProduct(modifiedProduct);
            ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
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
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You are about to remove this part from the product.");
            alert.setTitle("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                productPartTable.getItems().remove(deletingPart);
            }
        }
    }

    /**
     * The purpose of this method is to fill the a table with parts in the currently selected product
     * @return - list of parts
     */
    private List<Part> fillPartTable(){
        ArrayList<Part> partList = new ArrayList<Part>();
        for(int i = 0; i < Inventory.getAllParts().size(); i++){
            Part identifiedPart = Inventory.getAllParts().get(i);
            if(modifyingProduct.getAllAssociatedParts().contains(identifiedPart)==true){
                partList.add(identifiedPart);
            }
        }
        return partList;
    }

    /**
     * The purpose of this method is to be able to set the product being modified from the mainscreen controller
     * @param modifyingProduct - product being modified
     */
    public static void modifyingProduct(Product modifyingProduct){
        ModifyProductScreenController.modifyingProduct = modifyingProduct;
    }

    /**
     * The purpose of this method is to add a part to a table; The available parts to the parts in the product
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
     * This method sets up the initial product modification screen
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
        //Set up text field default values
        productPartTable.getItems().setAll(fillPartTable());
        partIdTextBox.setText(String.valueOf(modifyingProduct.getId()));
        nameTextBox.setText(modifyingProduct.getName());
        invTextBox.setText(String.valueOf(modifyingProduct.getStock()));
        maxTextBox.setText(String.valueOf(modifyingProduct.getMax()));
        minTextBox.setText(String.valueOf(modifyingProduct.getMin()));
        priceTextBox.setText(String.valueOf(modifyingProduct.getPrice()));
    }
}