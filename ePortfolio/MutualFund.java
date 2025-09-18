

package ePortfolio;

/**
 * This Represents a mutual fund investment, extending the general Investment class.
 * Provides methods to manage mutual funds, including selling, updating book value,
 * and calculating gains with a redemption fee for each sale.
 */
public class MutualFund extends Investment {
    private static final double REDEMPTION_FEE = 45.00;  // Fixed redemption fee for mutual funds

    /**
     * This initializes a MutualFund instance with the given symbol, name, quantity, and price.
     * 
     * @param symbol The mutual fund's symbol.
     * @param name The mutual fund's name.
     * @param quantity The number of units held.
     * @param price The current price per unit.
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        this.bookValue = calculateInitialBookValue(quantity, price);  // Initial book value without additional fees
    }

    /**
     * This calculates the initial book value when buying mutual funds (no additional fees).
     * 
     * @param quantity The number of units bought.
     * @param price The price per unit.
     * @return The calculated book value without additional fees.
     */
    @Override
    protected double calculateInitialBookValue(int quantity, double price) {
        return quantity * price;  // No additional fee for mutual funds
    }

    /**
     * This sells a specified number of units at a given price and calculates the gain.
     * 
     * @param quantity The number of units to sell.
     * @param price The price per unit for the sale.
     * @return The gain from the sale as a double, or -1 if the sale is invalid.
     */
    @Override
    public double sell(int quantity, double price) {
        // Below it will check if there are enough units to sell
        if (quantity > this.quantity) {
            return -1;  // Invalid sale
        }

        double payment = quantity * price - REDEMPTION_FEE;  // This will calculate payment after redemption fee
        double gain = payment - (bookValue * ((double) quantity / this.quantity));  // This will calculate gain
        this.quantity -= quantity;  // This will reduce the quantity of units

        // This will check if all units are sold, if so reset the book value to 0
        if (this.quantity == 0) {
            this.bookValue = 0;
        } else {
            // This will adjust the book value proportionally if some units remain
            reduceBookValue(quantity);
        }

        return gain;  // This will return the gain from the sale
    }

    /**
    * This updates the book value of the investment by adding the cost of additional units
    * purchased at the specified price.
    *
    * @param price    The price per unit at which the additional quantity was bought.
    * @param quantity The quantity of additional units bought.
    */
    public void updateBookValue(double price, int quantity) {
        this.bookValue += quantity * price;
    }

    /**
     * This reduces the book value proportionally when units are sold.
     * 
     * @param quantity The quantity of units sold.
     */
    protected void reduceBookValue(int quantity) {
        this.bookValue *= ((double) (this.quantity - quantity) / this.quantity);
    }

    /**
     * This provides a string representation of the mutual fund with its attributes.
     * 
     * @return A string containing the mutual fund's symbol, name, quantity, price, and book value.
     */
   /* @Override
    public String toString() {
        return "MutualFund [symbol=" + symbol + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", bookValue=" + bookValue + "]";
    } 
    */ 

   @Override
    public String toDisplayString() {
        return "Symbol: " + symbol + 
               ", Name: " + name + 
               ", Quantity: " + quantity + 
               ", Price: $" + String.format("%.2f", price) + 
               ", Book Value: $" + String.format("%.2f", bookValue);
    } 
    /**
     * This will Calculate the gain of the mutual fund investment based on the current quantity and price,
     * subtracting the book value and the redemption fee.
     *
     * @return The gain for the mutual fund investment.
     */
    @Override
    public double getGain() {
        return (quantity * price) - bookValue - REDEMPTION_FEE;
    }
}

