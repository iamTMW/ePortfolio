


package ePortfolio;

/**
 * This represents a general investment with attributes such as symbol, name, price, and quantity.
 * It provides methods to manage investments, including updating prices and managing book value.
 */
public abstract class Investment {
    // Investment attributes
    /**
    * The symbol of the investment, representing its unique identifier.
    */
    protected String symbol;
    /**
    * The name of the investment.
    */
    protected String name; 
    /**
    * The quantity of the investment owned.
    */
    protected int quantity;
    /**
    * The current price per unit of the investment.
    */
    protected double price; 
    /**
    * The total book value of the investment, representing the cost basis.
    */
    protected double bookValue;
    /**
    * The total realized gain from selling portions of the investment.
    */
    protected double realizedGain; // Track realized gain for each investment 
    

    /**
     * This is the constructor to initialize the investment with the given attributes.
     *
     * @param symbol   The investment's symbol.
     * @param name     The investment's name.
     * @param quantity The quantity owned.
     * @param price    The price per unit.
     */
    public Investment(String symbol, String name, int quantity, double price) {
        // Validate symbol and name are non-null and non-empty
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }

        // Validate quantity and price are non-negative
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be zero or a positive value.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be zero or a positive value.");
        }

        // This will initialize fields
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;

        // This will calculate initial book value
        this.bookValue = calculateInitialBookValue(quantity, price);

        // This will initialize realized gain to zero
        this.realizedGain = 0.0;
    }


    /**
     * This will xopy constructor to create a new Investment object from an existing one.
     *
     * @param other The existing investment object to copy.
     */
    // Copy constructor
    public Investment(Investment other) {
        this.symbol = other.symbol;
        this.name = other.name;
        this.quantity = other.quantity;
        this.price = other.price;
        this.bookValue = other.bookValue;
        this.realizedGain = other.realizedGain;
    }
    /**
     * This is the abstract method to calculate the initial book value.
     *
     * @param quantity The quantity of investment.
     * @param price    The price per unit.
     * @return The initial book value.
     */
    protected abstract double calculateInitialBookValue(int quantity, double price);

    /**
     * This is the Abstract method for selling investment.
     *
     * @param quantity The quantity to sell.
     * @param price    The sale price per unit.
     * @return The gain from the sale, or -1 if the sale is invalid.
     */
    public abstract double sell(int quantity, double price);

    // Getters and setters 

    /**
     * This gets the investment's symbol.
     *
     * @return The symbol of the investment.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This gets the investment's name.
     *
     * @return The name of the investment.
     */
    public String getName() {
        return name;
    }

    /**
     * This gets the investment's quantity.
     *
     * @return The quantity owned.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * This gets the investment's price per unit.
     *
     * @return The price per unit.
     */
    public double getPrice() {
        return price;
    }

     /**
     * Gets the investment's book value.
     *
     * @return The book value of the investment.
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * Gets the realized gain for the investment.
     *
     * @return The realized gain rounded to 2 decimal places.
     */
    public double getRealizedGain() {
        return Math.round(realizedGain * 100.0) / 100.0; // Return realized gain rounded to 2 decimals
    }

    /**
     * Sets the investment's price per unit.
     *
     * @param price The new price.
     * @throws IllegalArgumentException if the price is negative.
     */
    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        } else {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }

    /**
     * This will Set the investment's quantity.
     *
     * @param quantity The new quantity.
     * @throws IllegalArgumentException if the quantity is negative.
     */
    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        } else {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
    }

    /**
     * This will Sets the investment's book value.
     *
     * @param bookValue The new book value.
     */
    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * This will Update the realized gain for the investment.
     *
     * @param gain The gain from the sale to add to the realized gain.
     */
    public void updateRealizedGain(double gain) {
        this.realizedGain += gain;
    }

    /**
     * This will calculate the unrealized gain for the investment.
     *
     * @return The unrealized gain (current value - book value).
     */
    public double calculateUnrealizedGain() {
        double currentValue = this.price * this.quantity;
        return Math.round((currentValue - this.bookValue) * 100.0) / 100.0; // Rounded to 2 decimals
    }

    /**
     * This adjust the book value after a partial sale.
     *
     * @param quantitySold The quantity sold.
     */
    protected void reduceBookValue(int quantitySold) {
        if (this.quantity > 0) {
            this.bookValue *= (double) (this.quantity - quantitySold) / this.quantity;
        }
    }

    /**
     * This provides a string representation of the investment, including symbol, name, quantity, price, and book value.
     *
     * @return A string representation of the investment.
     */
    @Override
    public String toString() {
        return String.format("Investment [symbol=%s, name=%s, quantity=%d, price=%.2f, bookValue=%.2f, realizedGain=%.2f]",
                symbol, name, quantity, price, bookValue, realizedGain);
    }

     /**
     * This provides a user friendly string representation of the investment for display purposes.
     *
     * @return A display string representation of the investment.
     */
    public String toDisplayString() {
        return String.format("Symbol: %s, Name: %s, Quantity: %d, Price: %.2f, Book Value: %.2f",
                symbol, name, quantity, price, bookValue);
    } 

    /**
     * This gets the realized gain for the investment.
     *
     * @return The realized gain rounded to 2 decimal places.
     */
    public double getGain() {
        return Math.round(this.realizedGain * 100.0) / 100.0; // This is the only realized gain
    }

}



