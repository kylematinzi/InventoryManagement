package GuiProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to create the main screen window for the entire inventory management system program
 */
public class MainScreenController implements Initializable {
    //Parts table variables
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part,Integer> partId;
    @FXML
    private TableColumn<Part,String> partName;
    @FXML
    private TableColumn<Part,Integer> partInventory;
    @FXML
    private TableColumn<Part,Double> partPrice;
    @FXML
    private Button partSearchButton;
    @FXML
    private TextField partSearchBox;
    //Product table variables
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableColumn<Product,Integer> productId;
    @FXML
    private TableColumn<Product,String> productName;
    @FXML
    private TableColumn<Product,Integer> productInventory;
    @FXML
    private TableColumn<Product,Double> productPrice;
    @FXML
    private Button productSearchButton;
    @FXML
    private TextField productSearchBox;
    // Variables to help locate part/product location
    private static int partIndex;
    private static int productIndex;

    /**
     * This method closes the window when the exit button is pressed
     * @param actionEvent - close window
     */
    public void exitButtonPressed(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * This method opens the add part window when a button is pressed
     * @param actionEvent - open part window
     */
    public void addPartButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane addPartWinParent = new StackPane();
        addPartWinParent.getChildren().add(FXMLLoader.load(getClass().getResource("AddPartScreen.fxml")));
        Scene scene = new Scene(addPartWinParent);
        Stage addPartWinScene = new Stage();
        addPartWinScene.setScene(scene);
        addPartWinScene.initModality(Modality.WINDOW_MODAL);
        addPartWinScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        addPartWinScene.sizeToScene();
        addPartWinScene.setResizable(false);
        addPartWinScene.show();
        //This loop is for after a search is run and should always run
        if(1==1){
            partTableView.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method opens the modify part window when modify button is pressed
     * @param actionEvent - open modify part window
     */
    public void modifyPartButtonPressed(ActionEvent actionEvent) throws IOException {
        //get part information
        Part modifyPart = partTableView.getSelectionModel().getSelectedItem();
        partIndex = Inventory.getAllParts().indexOf(modifyPart);
        if(modifyPart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please select a part to modify.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else {
            //load window
            StackPane modifyPartWinParent = new StackPane();
            modifyPartWinParent.getChildren().add(FXMLLoader.load(getClass().getResource("ModifyPartScreen.fxml")));
            Scene scene = new Scene(modifyPartWinParent);
            Stage modifyPartWinScene = new Stage();
            modifyPartWinScene.setScene(scene);
            modifyPartWinScene.initModality(Modality.WINDOW_MODAL);
            modifyPartWinScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            modifyPartWinScene.sizeToScene();
            modifyPartWinScene.setResizable(false);
            modifyPartWinScene.show();
        }
        partTableView.setItems(Inventory.getAllParts());
    }

    /**
     * This method will open the add product screen when add button is pressed
     * @param actionEvent - open add product screen
     * @throws IOException
     */
    public void addProductButtonPressed(ActionEvent actionEvent) throws IOException {
        StackPane addProductWinParent = new StackPane();
        addProductWinParent.getChildren().add(FXMLLoader.load(getClass().getResource("AddProductScreen.fxml")));
        Scene scene = new Scene(addProductWinParent);
        Stage addProductWinScene = new Stage();
        addProductWinScene.setScene(scene);
        addProductWinScene.initModality(Modality.WINDOW_MODAL);
        addProductWinScene.initOwner(((((Button)actionEvent.getSource()).getScene().getWindow())));
        addProductWinScene.sizeToScene();
        addProductWinScene.setResizable(false);
        addProductWinScene.show();
        //loop should run every time to fill table
        if(1==1){
            productTableView.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * This method will open the modify product screen when modify button is pressed
     * @param actionEvent - open the modify product screen
     * @throws IOException
     */
    public void modifyProductButtonPressed(ActionEvent actionEvent) throws IOException {
        //get product information
        Product modifyProduct = productTableView.getSelectionModel().getSelectedItem();
        productIndex = Inventory.getAllProducts().indexOf(modifyProduct);
        ModifyProductScreenController.modifyingProduct(productTableView.getSelectionModel().getSelectedItem());
        if(modifyProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please select a product to modify.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else {
            StackPane modifyProductWinParent = new StackPane();
            modifyProductWinParent.getChildren().add(FXMLLoader.load(getClass().getResource("ModifyProductScreen.fxml")));
            Scene scene = new Scene(modifyProductWinParent);
            Stage modifyProductWinScene = new Stage();
            modifyProductWinScene.setScene(scene);
            modifyProductWinScene.initModality(Modality.WINDOW_MODAL);
            modifyProductWinScene.initOwner(((((Button) actionEvent.getSource()).getScene().getWindow())));
            modifyProductWinScene.sizeToScene();
            modifyProductWinScene.setResizable(false);
            modifyProductWinScene.show();
        }
        productTableView.setItems(Inventory.getAllProducts());
    }

    /**
     * This method will delete a selected part but first inquire if the user is sure about their decision
     * @param actionEvent - delete selected part
     * @throws IOException
     */
    public void deletePartButtonPressed(ActionEvent actionEvent) throws IOException {
        Part deletePart = partTableView.getSelectionModel().getSelectedItem();
        if(deletePart == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No part selected for removal.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this part?");
            alert.setTitle("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
            }
        }
        partTableView.setItems(Inventory.getAllParts());
    }

    /**
     * This method will delete a selected product but first inquire if the user is sure about their decision
     * @param actionEvent - delete selected product
     * @throws IOException
     */
    public void deleteProductButtonPressed(ActionEvent actionEvent) throws IOException {
        Product deleteProduct = productTableView.getSelectionModel().getSelectedItem();
        if(deleteProduct == null){
            Alert alert = new Alert(Alert.AlertType.WARNING,"No product selected for removal.");
            alert.setTitle("Empty Selection");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to delete this product?");
            alert.setTitle("Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            }
        }
        productTableView.setItems(Inventory.getAllProducts());
    }

    /**
     * This method will use the text provided in a text field to find any parts that match the given text; If the
     * field is empty it will return all available parts
     * @param actionEvent - search for given text matches
     */
    public void partSearchButtonPressed(ActionEvent actionEvent) {
        //if the search box is empty
        if(partSearchBox.getText().isEmpty()){
            partTableView.setItems(Inventory.getAllParts());
        }
        //if we're getting an part id
        if (Inventory.isNum(partSearchBox.getText())){
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            searchedParts.add(Inventory.lookupPart(Integer.parseInt(partSearchBox.getText())));
            partTableView.setItems(searchedParts);
        }
        // if we're getting a part name
        else{
            partTableView.setItems(Inventory.lookupPart(partSearchBox.getText()));
        }
    }

    /**
     * This method will use the text provided in a text field to find any products that match the given text; If the
     * field is empty it will return all available parts
     * @param actionEvent - search for given text matches
     */
    public void productSearchButtonPressed(ActionEvent actionEvent) {
        //Product Search box empty
        if(productSearchBox.getText().isEmpty()){
            productTableView.setItems(Inventory.getAllProducts());
        }
        //if we're getting a product id
        if (Inventory.isNum(productSearchBox.getText())){
            ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
            searchedProducts.add(Inventory.lookupProduct(Integer.parseInt(productSearchBox.getText())));
            productTableView.setItems(searchedProducts);
        }
        //if we're getting a product name
        else{
            productTableView.setItems(Inventory.lookupProduct(productSearchBox.getText()));
        }
    }

    /**
     * This method will get a part index
     * @return - current part index
     */
    public static int getPartIndex(){
        return partIndex;
    }

    /**
     * This method will get a product index
     * @return - current product index
     */
    public static int getProductIndex() {
        return productIndex;
    }

    /**
     * This method sets up the initial inventory management main screen
     * @param location - url location
     * @param resources - resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Part table; Left
        partTableView.setItems(Inventory.getAllParts());
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //Product table; Right
        productTableView.setItems(Inventory.getAllProducts());
        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}