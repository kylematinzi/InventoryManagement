package GuiProject;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This program is built and designed to create a basic inventory management GUI system that allows a user to
 * add/delete/modify parts or a grouping of parts called a product.
 *
 * From section J of the assignment I have:
 * Set 1:
 * - I have prevented the user from entering an Inventory that exceeds the Min/Max value for a product.
 * - I have prevented the Min field from being above the Max field and the reverse.
 * Set 2:
 * - I have included dialogue boxes for all "Delete" and "Cancel" buttons.
 * - I have made sure that both Parts & Products must have a name, price, and inventory level.
 *
 * @author Kyle Matinzi
 * @version 1.0
 * @date 2020-09-16
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane stackRoot = new StackPane();
        stackRoot.getChildren().add(FXMLLoader.load(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(stackRoot);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        //Test Parts
        InHouse part1 = new InHouse(1111,"Motor", 5000, 10,0,20,642);
        OutSourced part2 = new OutSourced(2222,"Gear", 50, 100, 0,600,"Mopar" );
        InHouse part3 = new InHouse(3333,"Crankshaft", 50.99, 15,0, 40, 243);
        OutSourced part4 = new OutSourced(4444, "Tire", 99.67, 100, 0, 600, "Goodyear");
        InHouse part5 = new InHouse(5555, "Rocket Engine", 21, 50, 0, 100, 767);
        InHouse part6 = new InHouse(6666, "Window", 250, 43, 0, 200, 805);
        InHouse part7 = new InHouse(7777, "Seat", 300, 56, 0, 500, 789);
        InHouse part8 = new InHouse(8888, "Fuel Tank", 1999, 10, 0, 80, 223);
        InHouse part9 = new InHouse(9999, "Wing", 100, 100, 0, 200, 545);
        InHouse part10 = new InHouse(1000, "Propeller", 1000, 20, 0, 70, 187);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
        Inventory.addPart(part9);
        Inventory.addPart(part10);
        //Test Products
        ObservableList<Part> testParts = FXCollections.observableArrayList();
        testParts.add(part1);
        testParts.add(part2);
        testParts.add(part3);
        Product product1 = new Product(5100, "Airplane", 100, 20, 0, 99, testParts);
        Product product2 = new Product(5200, "Car", 700, 90, 0, 99, testParts);
        Product product3 = new Product(5300, "Rocket Ship", 999, 85, 0, 99,testParts);
        Product product4 = new Product(5400, "Sail Boat", 7000, 5, 0, 99, testParts);
        Product product5 = new Product(5500, "Helicopter", 6000, 10, 0, 99, testParts);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);
        Inventory.addProduct(product5);
        launch(args);
    }
}
