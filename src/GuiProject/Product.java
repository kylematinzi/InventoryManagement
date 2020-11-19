package GuiProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The purpose of this class is create a product object; I have modified the class from the UML but only because for
 * me it makes more sense conceptually.
 */
public class Product {

    //I'm adding a list to the product class because it makes more sense to me for each product object to just contain
    //the parts included in it for each instantiation of the object. The list of parts is just as much a part of a
    //product object as is its id and name. Doing this will make it simpler to directly access the parts associated
    //with each product created. Doing this will change the way I need to add and modify products.
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This method will create a product. A product in this program is a list of parts combined
     * @param id - product Id
     * @param name - product name
     * @param price - product price
     * @param stock - quantity of product in inventory
     * @param min - minimum quantity allowed in inventory
     * @param max - maximum quantity allowed in inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> parts){
        this.id = id;
        this.name= name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = parts;
    }

    /**
     * This method will set the Id of the product
     * @param id - Product Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * This method will set the Name of the product
     * @param name - product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method will set the price of the product
     * @param price - product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method will set the quantity of products in stock
     * @param stock - amount of products in inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method will set the minimum allowed quantity allowed
     * @param min - minimum quantity allowed
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method will set the maximum allowed quantity allowed
     * @param max - maximum quantity allowed
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method will get the product Id
     * @return - product Id
     */
    public int getId() {
        return id;
    }

    /**
     * This method will get the name of the product
     * @return - product name
     */
    public String getName() {
        return name;
    }

    /**
     * This method will get the price of the product
     * @return - product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method will get the quantity of products in stock
     * @return - quantity in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method will get the minimum quantity of a product allowed in inventory
     * @return - minimum quantity allowed
     */
    public int getMin() {
        return min;
    }

    /**
     * This method will get the maximum quantity of a product allowed in inventory
     * @return - maximum quantity allowed
     */
    public int getMax() {
        return max;
    }

    /**
     * This method will add a part to the associated parts list, which is then considered the "product"
     * @param part - part to be added to the product list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method will deleted a part from a product list
     * @param selectedAssociatedPart - The part that is to be deleted
     * @return - returns True if a part is deleted from the list (if false no part found/deleted?)
     */
    public void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * This method will return the list of parts that creates a product
     * @return - list of parts in a product
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}
