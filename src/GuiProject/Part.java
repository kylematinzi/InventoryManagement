package GuiProject;

/**
 * The purpose of this class is to set up a parent part class that the inhouse and outsourced classes will use when
 * creating new parts
 */
public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * This method will create a "Part"
     * @param id - the parts id number
     * @param name - name of the part
     * @param price - price of the part
     * @param stock - quantity of given part in stock
     * @param min - minimum quantity of part allowed in stock
     * @param max - maximum quantity of part allowed in stock
     */
    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This method will set the id of a given part
     * @param id - Part id
     */
    public void setId(int id){
       this.id = id;
    }

    /**
     * This method will set the name of a given part
     * @param name - part name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method will set the price of a given part
     * @param price - part price
     */
    public void setPrice(double price) {
        if(price >= 0.0) {
            this.price = price;
        }
    }

    /**
     * This method will set the quantity of part in inventory
     * @param stock - how many parts in inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * This method will set the minimum quantity of part allowed in inventory
     * @param min - minimum level allowed in inventory
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * This method will set the maximum quantity of part allowed in inventory
     * @param max - maximum level allowed in inventory
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method will return a parts id
     * @return - part id
     */
    public int getId() {
        return id;
    }

    /**
     * This method will return a parts name
     * @return - part name
     */
    public String getName() {
        return name;
    }

    /**
     * This method will return a parts price
     * @return - part price
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method will return the quantity of parts in inventory
     * @return - amount in inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * This method will return the minimum quantity of a part allowed in inventory
     * @return - minimum level allowed
     */
    public int getMin() {
        return min;
    }

    /**
     * This method will return the maximum quantity of a part allowed in inventory
     * @return - maximum level allowed
     */
    public int getMax() {
        return max;
    }
}
