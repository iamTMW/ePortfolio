

package ePortfolio;

/**
 * This represents a stock investment, extending the general Investment class.
 * This class provides methods to manage stocks, including buying, selling, 
 * updating book value, and calculating gains.
 */
public class Stock extends Investment {
    private static final double COMMISSION = 9.99;  // Fixed commission for stocks

    /**
     * This initializes a Stock instance with the given symbol, name, quantity, and price.
     * 
     * @param symbol The stock's symbol.
     * @param name The stock's name.
     * @param quantity The number of shares owned.
     * @param price The current price per share.
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price);
        this.bookValue = calculateInitialBookValue(quantity, price);  // Initial book value with commission
    }

    /**
     * This calculates the initial book value when buying stocks, including commission.
     * 
     * @param quantity The number of shares bought.
     * @param price The price per share.
     * @return The calculated book value with commission.
     */
    @Override
    protected double calculateInitialBookValue(int quantity, double price) {
        return quantity * price + COMMISSION;  // Calculate book value with commission
    }

    /**
     * This returns the fixed commission value for stock transactions.
     * 
     * @return The commission value as a double.
     */
    public static double getCommission() {
        return COMMISSION;  // Return the fixed commission value
    }

    /**
    * This will update the book value of the investment by adding the cost of additional units 
    * purchased at the specified price, including a fixed commission fee.
    *
    * @param price    The price per unit at which the additional quantity was bought.
    * @param quantity The quantity of additional units bought.
    */
    public void updateBookValue(double price, int quantity) {
        this.bookValue += (quantity * price) + COMMISSION;
    }


    /**
     * This sells a specified number of shares at a given price and calculates the gain.
     * 
     * @param quantity The number of shares to sell.
     * @param price The price per share for the sale.
     * @return The gain from the sale as a double, or -1 if the sale is invalid.
     */
    @Override
    public double sell(int quantity, double price) {
        // This will check if there are enough shares to sell
        if (quantity > this.quantity || this.quantity == 0) {
            return -1;  // Invalid sale
        }
        
        double payment = quantity * price - COMMISSION;  // Calculate payment after commission
        double gain = payment - (bookValue * ((double) quantity / this.quantity));  // Calculate gain
        this.quantity -= quantity;  // Reduce the quantity of shares

        // This will check if all shares are sold, if so reset the book value to 0
        if (this.quantity == 0) {
            this.bookValue = 0;
        } else {
            // Adjust the book value proportionally if some shares remain
            reduceBookValue(quantity);
        }

        return gain;  // This will Return the gain from the sale
    }

    /**
     * This reduces the book value proportionally when shares are sold.
     * 
     * @param quantity The quantity of shares sold.
     */
    protected void reduceBookValue(int quantity) {
        this.bookValue *= ((double) (this.quantity - quantity) / this.quantity);
    }

    /**
     * This provides a string representation of the stock with its attributes.
     * 
     * @return A string containing the stock's symbol, name, quantity, price, and book value.
     */
   /* @Override
    public String toString() {
        return "Stock [symbol=" + symbol + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", bookValue=" + bookValue + "]";
    }  */

    @Override
    public String toDisplayString() {
        return "Symbol: " + symbol + 
               ", Name: " + name + 
               ", Quantity: " + quantity + 
               ", Price: $" + String.format("%.2f", price) + 
               ", Book Value: $" + String.format("%.2f", bookValue);
    } 

    /** 
     * Calculates the gain of the stock investment based on the current quantity and price,
     * subtracting the book value and an additional commission fee.
     *
     * @return The gain for the stock investment.
     */
    @Override
    public double getGain() {
        return (quantity * price) - bookValue - COMMISSION;
    }

}




