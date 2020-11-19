package GuiProject;

/**
 * The purpose of this class is to create a new part object
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * This method will create a part that has been made in house
     * @param id - part id
     * @param name - part name
     * @param price - part price
     * @param stock - quantity in inventory
     * @param min - minimum quantity allowed in inventory
     * @param max - maximum quantity allowed in inventory
     * @param machineId - machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id,name,price,stock,min,max);
        this.machineId = machineId;
    }

    /**
     * This method set the machine Id
     * @param machineId - new machine Id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * This method will return the machine Id
     * @return - machine Id
     */
    public int getMachineId() {
        return machineId;
    }
}
