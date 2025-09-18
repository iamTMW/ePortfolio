

package ePortfolio;

import java.io.*;
import java.util.*;  
import javax.swing.SwingUtilities;


/**
 * This is a portfolio class manages a unified list of investments, provides file I/O, and allows efficient searching.
 */
public class Portfolio {
    private ArrayList<Investment> investments;  // Unified list of investments
    private HashMap<String, ArrayList<Integer>> keywordIndex;  // Index for keywords

    /**
     * This initializes a new Portfolio with an empty list of investments and an empty keyword index.
     */
    public Portfolio() {
        investments = new ArrayList<>();
        keywordIndex = new HashMap<>();
    }  

    /**
    * This Retrieves the list of all investments in the portfolio.
    *
    * @return An ArrayList of all investments currently in the portfolio.
    */
    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    /**
    * This Retrieves a list of display strings for all investments in the portfolio.
    *
    * @return A list of strings, each representing an investment's display information.
    */
    public List<String> getInvestmentDisplayList() {
        List<String> displayList = new ArrayList<>();
        for (Investment investment : investments) {
            displayList.add(investment.toDisplayString());
        }
        return displayList;
    } 
    

    /**
     * This checks if the given symbol is already used by any investment.
     *
     * @param symbol The symbol to check.
     * @return True if the symbol exists, false otherwise.
     */
    public boolean isSymbolUnique(String symbol) {
        for (Investment investment : investments) {
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This buys an investment or adds more to an existing one.
     *
     * @param symbol   The investment's symbol.
     * @param name     The investment's name.
     * @param quantity The number of shares/units to buy.
     * @param price    The price per share/unit.
     * @param type     The type of investment ("stock" or "mutualfund").
     * @return A message indicating the success or failure of the operation.
     */
    public String buyInvestment(String symbol, String name, int quantity, double price, String type) { 
        StringBuilder message = new StringBuilder(); // Initialize the StringBuilder
        Investment existingInvestment = findInvestmentBySymbol(symbol);
        if (existingInvestment != null) {
            existingInvestment.setQuantity(existingInvestment.getQuantity() + quantity);
            existingInvestment.setPrice(price);
        
            // Below it will update the book value based on the type of investment
            if (existingInvestment instanceof Stock) {
                ((Stock) existingInvestment).updateBookValue(price, quantity);
            } else if (existingInvestment instanceof MutualFund) {
                ((MutualFund) existingInvestment).updateBookValue(price, quantity);
            }
             message.append("Added ").append(quantity).append(" more units of ").append(symbol)
               .append(" at $").append(price).append(" each.");
        } else {
            // It will ensure the symbol is unique across all investments
            if (!isSymbolUnique(symbol)) {
                return "Error: Symbol already used by another investment.";
            }

            Investment newInvestment;
            if (type.equalsIgnoreCase("stock")) {
                newInvestment = new Stock(symbol, name, quantity, price);
            } else if (type.equalsIgnoreCase("mutualfund")) {
                newInvestment = new MutualFund(symbol, name, quantity, price);
            } else {
                return "Unrecognized investment type.";
            }

            addInvestment(newInvestment);
             message.append("Bought ").append(quantity).append(" units of ").append(name)
               .append(" (").append(symbol).append(") at $").append(price).append(" each.");
        } 
         return message.toString();
    } 

    private double realizedGain = 0;

    /**
    * This sells a specified quantity of an investment and updates the price.
    *
    * @param symbol   The symbol of the investment to sell.
    * @param quantity The quantity to sell.
    * @param price    The selling price per unit. 
    * @return A message indicating the success or failure of the sale.
    */ 
    public String sellInvestment(String symbol, int quantity, double price) {
        Investment investment = findInvestmentBySymbol(symbol);
        if (investment == null) {
            return "Investment not found.";
        }
        StringBuilder message = new StringBuilder(); // This will initialize the StringBuilder
        double gain = investment.sell(quantity, price);  // This will perform the sale

        if (gain == -1) {
            return "Sale failed. Check quantity.";
        } else {
            investment.updateRealizedGain(gain); // This will add gain to realized gains for this investment
            investment.setPrice(price); // This will update the price to reflect the sale price
            gain = Math.round(gain * 100.0) / 100.0; // Round to 2 decimal places
            message.append("Success!! Sold ").append(quantity).append(" shares of ")
                .append(symbol).append(" at $").append(price).append(" per share.\n");
            message.append("Sold stock with gain: $").append(gain);
            realizedGain += gain; // This will add to the total portfolio gain
            if (investment.getQuantity() == 0) {
                investments.remove(investment);
                message.append("\nAll units of ").append(symbol).append(" sold. Investment removed from portfolio.");
            }
            return message.toString();
        }
    }



    /**
     * This adds an investment to the portfolio and indexes its keywords.
     *
     * @param investment The investment to be added.
     */
    public void addInvestment(Investment investment) {
        investments.add(investment);
        indexInvestmentKeywords(investment, investments.size() - 1);
    }

    /**
     * This indexes the keywords of an investment for optimized search.
     *
     * @param investment The investment to index.
     * @param position   The position of the investment in the list.
     */
    private void indexInvestmentKeywords(Investment investment, int position) {
        String[] keywords = investment.getName().toLowerCase().split("\\s+");
        for (String keyword : keywords) {
            keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(position);
        }
    }

    /**
    * This loads investments from a specified file.
    *
    * @param filename The name of the file to load investments from.
    */
    public void loadInvestmentsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String type = scanner.nextLine().split(" = ")[1].replace("\"", "");
                String symbol = scanner.nextLine().split(" = ")[1].replace("\"", "");
                String name = scanner.nextLine().split(" = ")[1].replace("\"", "");
                int quantity = Integer.parseInt(scanner.nextLine().split(" = ")[1].replace("\"", ""));
                double price = Double.parseDouble(scanner.nextLine().split(" = ")[1].replace("\"", ""));
                double bookValue = Double.parseDouble(scanner.nextLine().split(" = ")[1].replace("\"", ""));

                Investment investment;
                if (type.equalsIgnoreCase("stock")) {
                    investment = new Stock(symbol, name, quantity, price);
                } else if (type.equalsIgnoreCase("mutualfund")) {
                    investment = new MutualFund(symbol, name, quantity, price);
                } else {
                    continue;
                }

                investment.setBookValue(bookValue);  // This sets the book value as read from file
                addInvestment(investment);
                if (scanner.hasNextLine()) scanner.nextLine();  // This will skip blank line between entries
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. A new file will be created when saving.");
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
    * This saves all investments to a specified file in the required format.
    *
    * @param filename The name of the file to save investments to.
    */
    public void saveInvestmentsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Investment investment : investments) {
                writer.println("type = \"" + (investment instanceof Stock ? "stock" : "mutualfund") + "\"");
                writer.println("symbol = \"" + investment.getSymbol() + "\"");
                writer.println("name = \"" + investment.getName() + "\"");
                writer.println("quantity = \"" + investment.getQuantity() + "\"");
                writer.println("price = \"" + investment.getPrice() + "\"");
                writer.println("bookValue = \"" + investment.getBookValue() + "\"");
                writer.println();  // This is the blank line between entries
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }


    /**
     * This finds an investment by its symbol.
     *
     * @param symbol The symbol to search for.
     * @return The matching investment if found, otherwise null.
     */
    public Investment findInvestmentBySymbol(String symbol) {
        for (Investment investment : investments) {
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                return investment;
            }
        }
        return null;
    }

     /**
     * This searches for investments by symbol, keywords in name, and price range.
     *
     * @param symbol      The symbol to search for.
     * @param nameKeywords The keywords to search in the name.
     * @param minPrice    The minimum price.
     * @param maxPrice    The maximum price. 
     * @return A list of investments matching the criteria.
     */ 

    public List<Investment> searchInvestment(String symbol, String nameKeywords, double minPrice, double maxPrice) {
        Set<Integer> filteredPositions = new HashSet<>();

        // It will filter by keywords
        if (nameKeywords != null && !nameKeywords.isEmpty()) {
            String[] keywordArray = nameKeywords.toLowerCase().split("\\s+");

            for (String kw : keywordArray) {
                if (keywordIndex.containsKey(kw)) {
                    if (filteredPositions.isEmpty()) {
                        filteredPositions.addAll(keywordIndex.get(kw));
                    } else {
                        filteredPositions.retainAll(keywordIndex.get(kw)); // This will intersection to match all keywords
                    }
                } else {
                    filteredPositions.clear();
                    break; // If no matching keyword means no results
                }
            }
        } else {
            // If no keywords provided, include all positions
            for (int i = 0; i < investments.size(); i++) {
                filteredPositions.add(i);
            }   
        }

        // This will filter by symbol and price range
        List<Investment> results = new ArrayList<>();
        for (Integer pos : filteredPositions) {
            Investment investment = investments.get(pos);

            boolean matchesSymbol = symbol.isEmpty() || investment.getSymbol().equalsIgnoreCase(symbol);
            boolean matchesPrice = investment.getPrice() >= minPrice && investment.getPrice() <= maxPrice;
            if (matchesSymbol && matchesPrice) {
                results.add(investment);
            }
        }

        return results;
    }


    /**
     * This updates the prices of all investments based on user input.
     */
    public void updatePrices() {
        if (investments.isEmpty()) {
            System.out.println("No investments to update.");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        for (Investment investment : investments) {
            System.out.print("Enter new price for " + investment.getSymbol() + ": ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            investment.setPrice(price);
            System.out.println("Updated " + investment.getSymbol() + " to new price $" + price);
        }
    }

    /**
     * This calculates the total gain of the portfolio.
     *
     * @return The total gain.
     */
        
    public double getGain() {
        double totalGain = 0.0;

        // This will Iterate through all investments to calculate total realized gain
        for (Investment investment : investments) {
            totalGain += investment.getRealizedGain(); // This is Assuming realizedGain is tracked in each investment
        }

        return totalGain; // This will return the total realized gain
    }

    /**
    * This will retrieve the realized gains for each individual investment in the portfolio.
    *
    * @return A list of strings, where each string contains the symbol and realized gain 
    *         of an individual investment in the format: 
    *         "Symbol: [symbol], Gain: $[realized gain]".
    */
    public List<String> getIndividualGains() {
        List<String> individualGains = new ArrayList<>(); // This will Use a list to store gains for all investments

        for (Investment investment : investments) {
            //  Below it will fetch realized gain for each investment
            double individualGain = investment.getRealizedGain();
            // Below it will add investment details and its realized gain to the list
            individualGains.add("Symbol: " + investment.getSymbol() + ", Gain: $" + String.format("%.2f", individualGain));
        }

        return individualGains; // This will return the complete list of gains
    }

    /**
    * This will retrieves all investments in the portfolio.
    *
    * @return An ArrayList containing all Investment objects currently in the portfolio.
    */
    public ArrayList<Investment> getAllInvestments() {
        return investments;
    }
} 


