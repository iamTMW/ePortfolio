

package ePortfolio;

import java.util.Scanner;

/**
 * This is main class for the ePortfolio application, providing a command line interface
 * to manage investments, including stocks and mutual funds.
 */
public class ePortfolio { 

    /**
     * Main entry point for the ePortfolio application. Loads investments from a file
     * if specified and allows users to manage investments interactively.
     *
     * @param args Command-line arguments where the first argument should be the filename to load/save investments.
     */
    public static void main(String[] args) {
        // Below it will check if a filename argument is provided to load/save investments
        if (args.length != 1) {
            System.out.println("Usage: java ePortfolio.ePortfolio <filename>");
            return;
        }

        String filename = args[0];  // This is the filename for loading and saving investments
        Portfolio portfolio = new Portfolio();  // Here I Created Portfolio instance
        portfolio.loadInvestmentsFromFile(filename);  // This will load investments from file

         // Launch the GUI and pass the portfolio and filename
        javax.swing.SwingUtilities.invokeLater(() -> {
            PortfolioGUI gui = new PortfolioGUI(portfolio, filename);
            gui.setVisible(true);
        });

        Scanner scanner = new Scanner(System.in);
        String command = "";  // This will initialize command to avoid uninitialized variable error

        // Below is the main command loop
        while (!command.equalsIgnoreCase("quit")) {
            System.out.print("\nEnter command (buy, sell, update, getGain, search, quit): ");
            command = scanner.nextLine().toLowerCase().trim();  // It will read user input and trim spaces

            // Below it will handle ambiguous input for s and se
            if (command.equals("s") || command.equals("se")) {
                command = resolveAmbiguousCommand(scanner);  // This resolve between search and sell
            }

            // Below is the process the user's command using flexible matching logic
            if (matchesCommand(command, "buy")) {
                handlingBuy(scanner, portfolio);  // This will handle buying stocks or mutual funds
            } else if (matchesCommand(command, "sell")) {
                handlingSell(scanner, portfolio);  // This will handle selling stocks or mutual funds
            } else if (matchesCommand(command, "update")) {
                portfolio.updatePrices();  // This will update the prices of investments
            } else if (matchesCommand(command, "getgain")) {
                portfolio.getGain();  // This will calculate and display the total gain of the portfolio
            } else if (matchesCommand(command, "search")) {
                handlingSearch(scanner, portfolio);  // This will search for investments based on criteria
            } else if (matchesCommand(command, "quit")) { 
                portfolio.saveInvestmentsToFile(filename);  // This will save investments to file on exit
                System.out.println("Exiting the program.");  // This will confirm program exit
                break;  // This will exit the loop
            } else {
                System.out.println("Invalid command.");  // This will print error for invalid command
            }
        }
    }

    /**
     * This handles ambiguous input such as 's' or 'se' to determine if the user meant search or sell.
     *
     * @param scanner The scanner object to read user input.
     * @return The resolved command (search or sell).
     */
    private static String resolveAmbiguousCommand(Scanner scanner) {
        while (true) {
            System.out.print("Enter 'search' or 'sell': ");
            String choice = scanner.nextLine().toLowerCase().trim();  // This will read the user's input
            if (choice.equals("search") || choice.equals("sell")) {
                return choice;  // This will return the valid choice
            } else {
                System.out.println("Invalid input. Please enter 'search' or 'sell'.");  // This will handle invalid input
            }
        }
    }

    /**
     * This matches the user's input with available commands.
     *
     * @param input   The input provided by the user.
     * @param command The expected command to match.
     * @return True if the input matches the command, false otherwise.
     */
    private static boolean matchesCommand(String input, String command) {
        return command.startsWith(input) && input.length() <= command.length();  // This will match input with command
    }

    /**
     * This handles the buying of a new investment.
     *
     * @param scanner   The scanner for user input.
     * @param portfolio The portfolio instance to add the investment to.
     */
    private static void handlingBuy(Scanner scanner, Portfolio portfolio) {
        String type = getValidInvestmentType(scanner);
        System.out.print("Enter symbol: ");
        String symbol = scanner.nextLine().toUpperCase().trim();

        // This will check if the investment already exists in the portfolio
        Investment existingInvestment = portfolio.findInvestmentBySymbol(symbol);

        if (existingInvestment != null) {
            // Below it will check if the existing investment type matches the current buy request type
            if ((existingInvestment instanceof Stock && type.equals("stock")) || (existingInvestment instanceof MutualFund && type.equals("mutualfund"))) {
                // If types match, proceed to add more quantity and update the price
                System.out.println("Investment already exists: " + existingInvestment.getName());  
                int additionalQuantity;
                while (true) {
                    System.out.print("Enter quantity to add: ");
                    additionalQuantity = Integer.parseInt(scanner.nextLine().trim());  
                    if (additionalQuantity > 0) break;  // This is to check for the valid quantity
                        //System.out.println("Quantity must be greater than zero. Please re-enter."); 
                        throw new IllegalArgumentException("Quantity must be greater than zero. Please re-enter.");
                    } 
                    double newPrice;
                    while (true) {
                        System.out.print("Enter new price: ");
                        newPrice = Double.parseDouble(scanner.nextLine().trim()); 
                        if (newPrice > 0) break;  // This is to check for the valid price
                        //System.out.println("Price must be greater than zero. Please re-enter."); 
                        throw new IllegalArgumentException("Price must be greater than zero. Please re-enter.");
                    }

                    // This will update the investment
                    existingInvestment.setQuantity(existingInvestment.getQuantity() + additionalQuantity);
                    existingInvestment.setPrice(newPrice);
                    if (existingInvestment instanceof Stock) {
                        ((Stock) existingInvestment).updateBookValue(newPrice, additionalQuantity);
                    System.out.println("Success!! " + additionalQuantity + " shares added to " + symbol + " at $" + newPrice + " per share.");
                    } else if (existingInvestment instanceof MutualFund) {
                        ((MutualFund) existingInvestment).updateBookValue(newPrice, additionalQuantity);
                        System.out.println("Success!! " + additionalQuantity + " units added to " + symbol + " at $" + newPrice + " per unit.");
                    }
            } else {
                // This will check if types do not match, print an error
                //System.out.println("Error: Symbol already used for a different investment type."); 
                throw new IllegalArgumentException("Error: Symbol already used for a different investment type."); 
            }
        } else {
            // This will check if investment doesn't exist, create a new one
            System.out.print("Enter name: ");
            String name = scanner.nextLine().trim(); 
            int quantity;
            while (true) {
                System.out.print("Enter quantity: ");
                quantity = Integer.parseInt(scanner.nextLine().trim());
                if (quantity > 0) break;  // This will check for valid quantity
                    System.out.println("Quantity must be greater than zero. Please re-enter.");
                }
                double price;
            while (true) {
                System.out.print("Enter price: ");
                price = Double.parseDouble(scanner.nextLine().trim());
                if (price > 0) break;  // This will check for valid price
                System.out.println("Price must be greater than zero. Please re-enter.");
            }

            Investment newInvestment;
            if (type.equals("stock")) {
                newInvestment = new Stock(symbol, name, quantity, price);
            } else {
                newInvestment = new MutualFund(symbol, name, quantity, price);
            }
            portfolio.addInvestment(newInvestment);
            System.out.println("Success!! Bought " + quantity + " of " + name + " (" + symbol + ") at $" + price + " each.");
        }
    }

    /**
     * This handles the selling of an investment.
     *
     * @param scanner   The scanner for user input.
     * @param portfolio The portfolio instance to sell the investment from.
     */
    private static void handlingSell(Scanner scanner, Portfolio portfolio) {
        System.out.print("Enter symbol: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter quantity to sell: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter sale price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        portfolio.sellInvestment(symbol, quantity, price);  // This will sell investment and update portfolio
    }

    /**
     * This handles searching for investments based on user-provided criteria.
     *
     * @param scanner   The scanner for user input.
     * @param portfolio The portfolio instance to search within.
     */
    private static void handlingSearch(Scanner scanner, Portfolio portfolio) {
        System.out.print("Enter symbol: ");
        String symbol = scanner.nextLine().toUpperCase();  // This will read and ensure uppercase input

        System.out.print("Enter name keyword: ");
        String keywords = scanner.nextLine();  // This will read the keyword for searching

        System.out.print("Enter price range: ");
        String priceRange = scanner.nextLine();  // This will read the price range

        double minPrice = Double.NEGATIVE_INFINITY;  // This is the default minimum price
        double maxPrice = Double.POSITIVE_INFINITY;  // This is the default maximum price

        // The following below will parse the price range if provided
        if (!priceRange.isEmpty()) {
            if (priceRange.endsWith("-")) {
                minPrice = Double.parseDouble(priceRange.replace("-", "").trim());
            } else if (priceRange.startsWith("-")) {
                maxPrice = Double.parseDouble(priceRange.replace("-", "").trim());
            } else if (priceRange.contains("-")) {
                String[] prices = priceRange.split("-");
                minPrice = Double.parseDouble(prices[0].trim());
                maxPrice = Double.parseDouble(prices[1].trim());
            }
        }

        portfolio.searchInvestment(symbol, keywords, minPrice, maxPrice);
    }

    /**
     * This prompts for and validates the investment type from the user.
     *
     * @param scanner The scanner for user input.
     * @return The valid investment type ("stock" or "mutualfund").
     */
    private static String getValidInvestmentType(Scanner scanner) {
        String type;
        while (true) {
            System.out.print("Enter type (stock/mutualfund): ");
            type = scanner.nextLine().toLowerCase().trim();
        
            // This will check if the input matches stock or s
            if ("stock".startsWith(type) && type.length() <= "stock".length()) {
                return "stock";  // This will return valid type for stock
            } 
            // This will check if the input matches mutualfund or m
            else if ("mutualfund".startsWith(type) && type.length() <= "mutualfund".length()) {
                return "mutualfund";  // This will return valid type for mutual fund
            } 
            else {
                System.out.println("Invalid type. Please enter 'stock' or 'mutualfund'.");
            }
        }
    }
}

