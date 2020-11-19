package GuiProject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * The purpose of this class is to set up the model where data for the program will be stored
 */
public class Inventory {

    //lists containing all current Parts/Products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    //Id will be used for Parts and Products so no parts or products can have the same id
    private static Integer uniqueId = 1;

    /**
     * This method will add new part to the allParts list
     * @param newPart - new part to be added
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * This method will add a new product to the allProducts list
     * @param newProduct - new product to be added
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * This method will find a part in the allParts list by searching with part Id
     * I may need an exists method*******
     * @param partId - id of the part to be found
     * @return - return the part if found
     */
    public static Part lookupPart(int partId){
        for (Part Part : allParts){
            if (Part.getId() == partId){
                return Part;
            }
        }
        return null;
    }

    /**
     * This method will find a product in the allProducts list by searching with product Id
     * @param ProductId - id of the product to be found
     * @return - return the product if found
     */
    public static Product lookupProduct(int ProductId){
        for (Product Product : allProducts){
            if (Product.getId() == ProductId){
                return Product;
            }
        }
        return null;
    }

    /**
     * This method will return the list if the part is found by using the part name used by the search tool
     * @param partName - name of the part to be found
     * @return - return the list the part is contained in
     * GET ALL FILTERED PARTS
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> tempList = FXCollections.observableArrayList();
        for(Part Part : allParts){
            if(Part.getName().contains(partName)){
                tempList.add(Part);
            }
        }
        return tempList;
    }

    /**
     * This method will return the list if the product is found by using the product name
     * @param productName - name of the product to be found
     * @return - return the list the product is contained in
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> createdProducts = FXCollections.observableArrayList();
        for(Product Product : allProducts){
            if(Product.getName().contains(productName)){
                createdProducts.add(Product);
            }
        }
        return createdProducts;
    }

    /**
     * This method is used to update/modify a part
     * @param index - where the part is located
     * @param selectedPart - the part to be updated
     */
    public static void updatePart(int index, Part selectedPart){
        Part tempObject = Inventory.lookupPart(index);
        Inventory.deletePart(tempObject);
        Inventory.addPart(selectedPart);
    }

    /**
     * This method is used to update/modify a product
     * @param index - where the product is located
     * @param newProduct - the product to be updated
     */
    public static void updateProduct(int index, Product newProduct){
        //assign the current product by index to a temp product
        Product temp = Inventory.lookupProduct(index);
        //delete the temp (old) product from the all products list
        Inventory.deleteProduct(temp);
        //add the new product to the all products list where the old product was
        Inventory.addProduct(newProduct);
    }

    /**
     * This method will delete a selected part
     * @param selectedPart - the part to be deleted
     * @return - return true if a part is deleted
     */
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }

    /**
     * This method will delete a selected product
     * @param selectedProduct - the product to be deleted
     * @return - return true if a product is deleted
     */
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }

    /**
     * This method is used to retrieve all of the parts in the system
     * @return - return a list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method is used to retrieve all of the products in the system
     * @return - return a list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method will retrieve the value of the unique Id variable
     * @return - unique Id value
     */
    public static Integer getUniqueId(){
        return uniqueId;
    }

    /**
     * This method is used to auto increment the unique Id value every time a part/product assigns a unique id
     */
    public static void setUniqueId(){
        uniqueId++;
    }

    /**
     * This method will ensure that the minimum value is below the maximum value
     * prevent minimum field from having value above max field
     * prevent maximum field from having value below min field
     * both done here
     * @param min - minimum entered parts
     * @param max - maximum entered parts
     * @return - true if min is lower than or equal to the maximum
     */
    public static boolean minMaxCheck(int min, int max){
        if(min > max || min < 0 ){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Min may not be higher than Max or a value below Zero.");
            alert.setTitle("High Minimum");
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    /**
     * This method will ensure that the provided inventory value is greater than the given minimum value and less than
     * the given maximum value
     * @param min - minimum allowed inventory
     * @param max - maximum allowed inventory
     * @param stock - amount of stock
     * @return - true if inventory within correct limits
     */
    public static boolean inventoryCheck(int min, int max, int stock){
        if(stock < min){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Inventory must be greater than min value.");
            alert.setTitle("Inventory Low");
            alert.showAndWait();
            return false;
        }
        else if(stock > max){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Inventory must be less than max value.");
            alert.setTitle("Inventory High");
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    /**
     * This method ensures that that entered price is greater than or equal to zero; no negative prices
     * @param price - given price
     * @return - true if price >= 0
     */
    public static boolean priceCheck(double price){
        if ( price < 0){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Price must be greater than or equal to Zero.");
            alert.setTitle("Price Check");
            alert.showAndWait();
            return false;
        }
        else return true;
    }

    /**
     * This method is used to see if a given string is a number or not
     * @param checkNum - value to be checked
     * @return - true if the value is a number
     */
    public static boolean isNum(String checkNum){
        try{
            Integer check = Integer.parseInt(checkNum);

        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
