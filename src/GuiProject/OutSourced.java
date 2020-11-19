package GuiProject;

/**
 * The purpose of this class is to create an outsourced part
 */
public class OutSourced extends Part {

    private String companyName;

    /**
     * This method will create a part that has been outsourced
     * @param id - part Id
     * @param name - part name
     * @param price - part price
     * @param stock - quantity in inventory
     * @param min - minimum quantity allowed in inventory
     * @param max - maximum quantity allowed in inventory
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id,name,price,stock,min,max);
        this.companyName = companyName;
    }

    /**
     * This method will set the company name of an out sourced part
     * @param companyName - name of company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * This method will return the name of the company who provided the part
     * @return - name of company
     */
    public String getCompanyName() {
        return companyName;
    }
}
