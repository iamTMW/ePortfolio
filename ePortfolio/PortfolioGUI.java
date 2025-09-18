
package ePortfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener; 
import java.util.List;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The PortfolioGUI represents the Graphical User Interface GUI for managing an investment portfolio.
 * That said, it allows the user to perform various actions such as buying, selling, updating investments,
 * calculating portfolio gains, and searching for investments.
 */
public class PortfolioGUI extends JFrame {

    /** This is the backend portfolio instance used for managing investments. */
    private Portfolio portfolio; // This is the backend portfolio instance
    /** This is the filename used for saving and loading investments. */
    private String filename; // This is the file for saving and loading investments
    /** This is the panel displayed when the application starts (Welcome Panel). */
    private JPanel welcomePanel, buyPanel, sellPanel, updatePanel, gainPanel, searchPanel; // This is for the panels for each command
    /** This is the area for displaying messages or results to the user. */
    private JTextArea messageArea; // This is for displaying messages or results

    /**
    * The PortfolioGUI represents the Graphical user interface GUI for managing an investment portfolio.
    * Constructs the GUI for the portfolio application.
    * @param portfolio The backend portfolio object containing investment data.
    * @param filename  The name of the file used for saving and loading portfolio data.
    */
    public PortfolioGUI(Portfolio portfolio, String filename) {
        this.portfolio = portfolio;
        this.filename = filename;

        setTitle("ePortfolio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // This is the welcome Panel Below
        welcomePanel = new JPanel(new BorderLayout(15, 15)); // This is uses BorderLayout with spacing
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // This will add padding around the panel

        // This is the welcome Message Below
        JLabel welcomeLabel = new JLabel("Welcome to ePortfolio!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // This is set font for bold title
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // This is the center align the text

        // This is the instructions message
        JLabel instructionsLabel = new JLabel("<html><div style='text-align: center;'>"
            + "Choose a command from the 'Commands' menu to walk thorough the application.<br>"
            + "You can buy, sell investments, update prices, calculate portfolio gains,<br>"
            + "search for investments, or lastly quit the program."
            + "</div></html>", JLabel.CENTER);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // This is set font for instructions
        instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER); // This is the center align the text

        // This will add Components to Welcome Panel
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH); // This will add the title at the top
        welcomePanel.add(instructionsLabel, BorderLayout.CENTER); // This will add the instructions in the center

        // This will add Welcome Panel to the Frame
        add(welcomePanel, BorderLayout.CENTER);

        // This is the menu
        JMenuBar menuBar = new JMenuBar();
        JMenu commandsMenu = new JMenu("Commands");

        JMenuItem buyItem = new JMenuItem("Buy");
        JMenuItem sellItem = new JMenuItem("Sell");
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem getGainItem = new JMenuItem("Get Gain");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem quitItem = new JMenuItem("Quit");

        commandsMenu.add(buyItem);
        commandsMenu.add(sellItem);
        commandsMenu.add(updateItem);
        commandsMenu.add(getGainItem);
        commandsMenu.add(searchItem);
        commandsMenu.add(quitItem);
        menuBar.add(commandsMenu);
        setJMenuBar(menuBar);

        // These are the actions for each command
        buyItem.addActionListener(e -> showBuyPanel());
        sellItem.addActionListener(e -> showSellPanel());
        updateItem.addActionListener(e -> showUpdatePanel());
        getGainItem.addActionListener(e -> showGainPanel());
        searchItem.addActionListener(e -> showSearchPanel());
        quitItem.addActionListener(e -> {
            try {
                portfolio.saveInvestmentsToFile(filename);
                messageArea.setText("Portfolio saved successfully. Exiting...");
                System.exit(0);
            } catch (Exception ex) {
                messageArea.setText("Error saving file: " + ex.getMessage());
            }
        });

        // This will create common message area
        messageArea = new JTextArea(10, 50);
        messageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.SOUTH);
    }

    /**
     * The following displays the Buy panel, allowing users to input details for buying investments.
     */
    private void showBuyPanel() {
        if (buyPanel == null) {
            buyPanel = new JPanel(new BorderLayout(10, 10));
            buyPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // This is the Title
            JLabel titleLabel = new JLabel("Buying an Investment", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            buyPanel.add(titleLabel, BorderLayout.NORTH);

            // This is for the Form Panel
            JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            JLabel typeLabel = new JLabel("Type:");
            JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Stock", "Mutual Fund"});
            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbolField = new JTextField();
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField();
            JLabel quantityLabel = new JLabel("Quantity:");
            JTextField quantityField = new JTextField();
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();

            formPanel.add(typeLabel);
            formPanel.add(typeCombo);
            formPanel.add(symbolLabel);
            formPanel.add(symbolField);
            formPanel.add(nameLabel);
            formPanel.add(nameField);
            formPanel.add(quantityLabel);
            formPanel.add(quantityField);
            formPanel.add(priceLabel);
            formPanel.add(priceField);

            // This is the for the button Panel
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton resetButton = new JButton("Reset");
            JButton buyButton = new JButton("Buy");
            buttonPanel.add(resetButton);
            buttonPanel.add(buyButton);

            // This will combine Form and Button Panels
            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.add(formPanel, BorderLayout.CENTER);
            inputPanel.add(buttonPanel, BorderLayout.EAST);

            buyPanel.add(inputPanel, BorderLayout.CENTER);

            // This is the message Area
            JPanel messagePanel = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Messages:");
            JTextArea messageArea = new JTextArea(5, 40);
            messageArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(messageArea);
            messagePanel.add(messageLabel, BorderLayout.NORTH);
            messagePanel.add(scrollPane, BorderLayout.CENTER);

            buyPanel.add(messagePanel, BorderLayout.SOUTH);

            // This will reset the Button Action
            resetButton.addActionListener(e -> {
            typeCombo.setSelectedIndex(0);
            symbolField.setText("");
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            messageArea.setText("");
            });

            // This is the buy Button Action
            buyButton.addActionListener(e -> {
                String type = (String) typeCombo.getSelectedItem();
                String symbol = symbolField.getText().trim();
                String name = nameField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String priceText = priceField.getText().trim();

                // This will validate fields
                if (symbol.isEmpty() || name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                    messageArea.setText("Error: All fields must be filled.");
                    return;
                }

                // This will validate name contains only letters and spaces
                if (!name.matches("[a-zA-Z ]+")) {
                    messageArea.setText("Error: Name must contain only letters and spaces.");
                    return;
                }

                // This will validate quantity
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityText);
                    if (quantity <= 0) {
                        messageArea.setText("Error: Quantity must be a positive integer.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    messageArea.setText("Error: Quantity must be a valid integer.");
                    return;
                }

                // This will validate price
                double price;
                try {
                    price = Double.parseDouble(priceText);
                    if (price <= 0) {
                        messageArea.setText("Error: Price must be a positive number.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    messageArea.setText("Error: Price must be a valid number.");
                    return;
                }

                // The following will check for existing symbol
                Investment existingInvestment = portfolio.findInvestmentBySymbol(symbol);
                if (existingInvestment != null) {
                    if ((type.equalsIgnoreCase("Stock") && existingInvestment instanceof MutualFund) ||
                        (type.equalsIgnoreCase("Mutual Fund") && existingInvestment instanceof Stock)) {
                        messageArea.setText("Error: Symbol already exists with a different investment type.");
                        return;
                    }
                }

                // This is an attempt to buy
                try {
                    String result = portfolio.buyInvestment(symbol, name, quantity, price, type.toLowerCase());
                    messageArea.setText(result);

                    if (!result.startsWith("Error:")) {
                        typeCombo.setSelectedIndex(0);
                        symbolField.setText("");
                        nameField.setText("");
                        quantityField.setText("");
                        priceField.setText("");
                    }
                } catch (Exception ex) {
                    messageArea.setText("Error: " + ex.getMessage());
                }
            });
        }

        switchPanel(buyPanel);
    }

    /**
     * This displays the Sell panel, allowing users to input details for selling investments.
     */
    private void showSellPanel() {
        if (sellPanel == null) {
            sellPanel = new JPanel(new BorderLayout(10, 10));
            sellPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // This is the title
            JLabel titleLabel = new JLabel("Selling an Investment", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            sellPanel.add(titleLabel, BorderLayout.NORTH);

            // This is Form Panel
            JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbolField = new JTextField();
            JLabel quantityLabel = new JLabel("Quantity:");
            JTextField quantityField = new JTextField();
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField();

            formPanel.add(symbolLabel);
            formPanel.add(symbolField);
            formPanel.add(quantityLabel);
            formPanel.add(quantityField);
            formPanel.add(priceLabel);
            formPanel.add(priceField);

            // These are the buttons
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton sellButton = new JButton("Sell");
            JButton resetButton = new JButton("Reset");
            buttonPanel.add(sellButton);
            buttonPanel.add(resetButton);

            // This will combine Form and Buttons
            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.add(formPanel, BorderLayout.CENTER);
            inputPanel.add(buttonPanel, BorderLayout.SOUTH);

            sellPanel.add(inputPanel, BorderLayout.CENTER);

            // This is the message Area
            JPanel messagePanel = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Messages:");
            JTextArea messageArea = new JTextArea(5, 40);
            messageArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(messageArea);
            messagePanel.add(messageLabel, BorderLayout.NORTH);
            messagePanel.add(scrollPane, BorderLayout.CENTER);

            sellPanel.add(messagePanel, BorderLayout.SOUTH);

            // These are the button Actions
            resetButton.addActionListener(e -> {
                symbolField.setText("");
                quantityField.setText("");
                priceField.setText("");
                messageArea.setText("");
            });

            sellButton.addActionListener(e -> {
                String symbol = symbolField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String priceText = priceField.getText().trim();

                // This will validate inputs
                if (symbol.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                    messageArea.setText("Error: All fields must be filled.");
                    return;
                }

                try {
                    int quantity = Integer.parseInt(quantityText);
                    double price = Double.parseDouble(priceText);

                    if (quantity <= 0 || price <= 0) {
                        messageArea.setText("Error: Quantity and Price must be positive.");
                        return;
                    }

                    String result = portfolio.sellInvestment(symbol, quantity, price);
                    messageArea.setText(result);
                    symbolField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                } catch (NumberFormatException ex) {
                    messageArea.setText("Error: Quantity and Price must be valid numbers.");
                } catch (Exception ex) {
                    messageArea.setText("Error: " + ex.getMessage());
                }
            });
        }
        switchPanel(sellPanel);
    }

    /**
    * This will display the Update panel, allowing users to update the price of investments.
    */
    private void showUpdatePanel() {
       // The following below will initialize or reset the updatePanel
        if (updatePanel == null) {
            updatePanel = new JPanel(new BorderLayout());
        } else {
            updatePanel.removeAll();
        }
        JLabel instructionLabel = new JLabel("Updating investments");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 20)); // This will make the text bold and larger
        instructionLabel.setHorizontalAlignment(JLabel.CENTER);    // This will center align the text
        instructionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // This will add extra padding
        updatePanel.add(instructionLabel, BorderLayout.NORTH);


        // This is for the investment data navigation
        JPanel dataPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        dataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel symbolLabel = new JLabel("Symbol:");
        JTextField symbolField = new JTextField();
        symbolField.setEditable(false); // This is Non editable since Symbol cannot change

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        nameField.setEditable(false); // This is non editable since Name cannot change

        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        dataPanel.add(symbolLabel);
        dataPanel.add(symbolField);
        dataPanel.add(nameLabel);
        dataPanel.add(nameField);
        dataPanel.add(priceLabel);
        dataPanel.add(priceField);

        // This will add data panel to the center of updatePanel
        updatePanel.add(dataPanel, BorderLayout.CENTER);

        // Below are buttons for navigation and saving
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton prevButton = new JButton("Prev");
        JButton nextButton = new JButton("Next");
        JButton saveButton = new JButton("Save");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(saveButton);

        updatePanel.add(buttonPanel, BorderLayout.EAST);

        // Below is the message area for feedback
        JLabel messageLabel = new JLabel("Messages");
        JTextArea messageArea = new JTextArea(5, 30);
        messageArea.setEditable(false);
        JScrollPane messageScrollPane = new JScrollPane(messageArea);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.NORTH);
        messagePanel.add(messageScrollPane, BorderLayout.CENTER);

        updatePanel.add(messagePanel, BorderLayout.SOUTH);

        // Below is the state management for current investment
        int[] currentIndex = {0}; // This array to allow modification in lambda

        // This is the helper method to display current investment
        Runnable displayInvestment = () -> {
            if (portfolio.getInvestments().isEmpty()) {
                symbolField.setText("");
                nameField.setText("");
                priceField.setText("");
                messageArea.setText("No investments available to update.");
                return;
            }

            Investment currentInvestment = portfolio.getInvestments().get(currentIndex[0]);
            symbolField.setText(currentInvestment.getSymbol());
            nameField.setText(currentInvestment.getName());
            priceField.setText(String.valueOf(currentInvestment.getPrice()));
            messageArea.setText("Displaying investment " + (currentIndex[0] + 1) + " of " + portfolio.getInvestments().size());
        };

        // This will attach action listeners
        prevButton.addActionListener(e -> {
            if (portfolio.getInvestments().isEmpty()) return;
            currentIndex[0] = (currentIndex[0] - 1 + portfolio.getInvestments().size()) % portfolio.getInvestments().size();
            displayInvestment.run();
        });

        nextButton.addActionListener(e -> {
            if (portfolio.getInvestments().isEmpty()) return;
            currentIndex[0] = (currentIndex[0] + 1) % portfolio.getInvestments().size();
            displayInvestment.run();
        });

        saveButton.addActionListener(e -> {
            if (portfolio.getInvestments().isEmpty()) return;
            try {
                double newPrice = Double.parseDouble(priceField.getText());
                if (newPrice <= 0) throw new NumberFormatException("Price must be positive.");
                Investment currentInvestment = portfolio.getInvestments().get(currentIndex[0]);
                currentInvestment.setPrice(newPrice);
                messageArea.setText("Price updated for " + currentInvestment.getSymbol() + ": $" + newPrice);
            } catch (NumberFormatException ex) {
                messageArea.setText("Invalid input for price. Please enter a positive number.");
            }
        });

        // This will display the first investment on panel load
        displayInvestment.run();

        // This will switch to the update panel
        switchPanel(updatePanel);
    }

    /**
    * This displays the Gain panel, showing the total and individual gains for investments.
    */
    private void showGainPanel() {
        if (gainPanel == null) {
            gainPanel = new JPanel(new BorderLayout(10, 10));
            gainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // For the Title
            JLabel titleLabel = new JLabel("Getting Total Gain", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            gainPanel.add(titleLabel, BorderLayout.NORTH);

            // For the Form Panel
            JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            JLabel totalGainLabel = new JLabel("Total Gain:");
            JTextField totalGainField = new JTextField();
            totalGainField.setEditable(false); // This is non-editable field for total gain
            formPanel.add(totalGainLabel);
            formPanel.add(totalGainField);

            gainPanel.add(formPanel, BorderLayout.CENTER);

            // For the Message Area
            JPanel messagePanel = new JPanel(new BorderLayout());
            JLabel messageLabel = new JLabel("Individual Gains:");
            JTextArea messageArea = new JTextArea(10, 40);
            messageArea.setEditable(false); // Non-editable
            JScrollPane scrollPane = new JScrollPane(messageArea);
            messagePanel.add(messageLabel, BorderLayout.NORTH);
            messagePanel.add(scrollPane, BorderLayout.CENTER);

            gainPanel.add(messagePanel, BorderLayout.SOUTH);

            // This will populate Total Gain and Individual Gains
            populateGains(totalGainField, messageArea);
        }

        switchPanel(gainPanel);
    }

    /**
    * This will Populate the total Gain and Individual Gains in the Gain panel.
    *
    * @param totalGainField The text field to display the total gain.
    * @param messageArea    The text area to display individual gains.
    */
    private void populateGains(JTextField totalGainField, JTextArea messageArea) {
        double totalGain = 0; // This will start with zero for total realized gain
        StringBuilder individualGains = new StringBuilder();

        for (Investment investment : portfolio.getInvestments()) { // This will iterate over all investments
            double realizedGain = investment.getRealizedGain(); // This will fetch realized gain for this investment
            totalGain += realizedGain; // This will add to the total realized gain
            individualGains.append(String.format("Symbol: %s, Gain: %.2f\n", investment.getSymbol(), realizedGain));
        }

        // This will update the total gain field
        totalGainField.setText(String.format("%.2f", totalGain));
    
        // This will update the message area with individual gains
        messageArea.setText(individualGains.toString());
    }

    /**
    * This displays the Search panel, allowing users to search for investments by symbol, keywords, or price range.
    */
    private void showSearchPanel() {
        if (searchPanel == null) {
            searchPanel = new JPanel(new BorderLayout(10, 10));
            searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // This is for the title
            JLabel titleLabel = new JLabel("Searching Investments", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            searchPanel.add(titleLabel, BorderLayout.NORTH);

            // This is for the Form Panel
            JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            JLabel symbolLabel = new JLabel("Symbol:");
            JTextField symbolField = new JTextField();
            JLabel keywordsLabel = new JLabel("Keywords:");
            JTextField keywordsField = new JTextField();
            JLabel priceRangeLabel = new JLabel("Price Range (min-max):");
            JTextField priceRangeField = new JTextField();

            formPanel.add(symbolLabel);
            formPanel.add(symbolField);
            formPanel.add(keywordsLabel);
            formPanel.add(keywordsField);
            formPanel.add(priceRangeLabel);
            formPanel.add(priceRangeField);

            // This is for the Button Panel
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            JButton resetButton = new JButton("Reset");
            JButton searchButton = new JButton("Search");
            buttonPanel.add(resetButton);
            buttonPanel.add(searchButton);

            // This will combine Form and Button Panels
            JPanel inputPanel = new JPanel(new BorderLayout());
            inputPanel.add(formPanel, BorderLayout.CENTER);
            inputPanel.add(buttonPanel, BorderLayout.EAST);

            searchPanel.add(inputPanel, BorderLayout.CENTER);

            // This will search Results Area
            JPanel resultsPanel = new JPanel(new BorderLayout());
            JLabel resultsLabel = new JLabel("Search Results:");
            JTextArea resultsArea = new JTextArea(8, 40);
            resultsArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(resultsArea);
            resultsPanel.add(resultsLabel, BorderLayout.NORTH);
            resultsPanel.add(scrollPane, BorderLayout.CENTER);

            searchPanel.add(resultsPanel, BorderLayout.SOUTH);

            // This is for the button Actions
            resetButton.addActionListener(e -> {
                symbolField.setText("");
                keywordsField.setText("");
                priceRangeField.setText("");
                resultsArea.setText("");
            });

            searchButton.addActionListener(e -> {
                try {
                    String symbol = symbolField.getText().trim();
                    String keywords = keywordsField.getText().trim();
                    String[] prices = priceRangeField.getText().trim().split("-");
                    double minPrice = prices[0].isEmpty() ? 0 : Double.parseDouble(prices[0]);
                    double maxPrice = prices.length > 1 ? Double.parseDouble(prices[1]) : Double.MAX_VALUE;

                    // This is to perform search
                    List<Investment> results = portfolio.searchInvestment(symbol, keywords, minPrice, maxPrice);

                    if (results.isEmpty()) {
                        resultsArea.setText("No matching investments found.");
                    } else {
                        StringBuilder resultText = new StringBuilder("Search Results:\n");
                        for (Investment investment : results) {
                            resultText.append(investment.toDisplayString()).append("\n");
                        }
                        resultsArea.setText(resultText.toString());
                    }
                } catch (NumberFormatException ex) {
                    resultsArea.setText("Error: Invalid price range format.");
                } catch (Exception ex) {
                    resultsArea.setText("Error: " + ex.getMessage());
                }
            });
        }

        switchPanel(searchPanel);
    }

    /**
    * This will Switche to the specified panel, clearing any previous panels.
    *
    * @param panel The panel to display.
    */
    private void switchPanel(JPanel panel) {
       getContentPane().removeAll(); // This will clear existing components
        add(panel, BorderLayout.CENTER); // This will add the new panel to the center
        add(new JScrollPane(messageArea), BorderLayout.SOUTH); // This will re add the message area at the bottom

        revalidate(); // This will refresh the UI layout
        repaint();    // This will repaint the UI to show updates
    }

    /**
    * This the main method to launch the Portfolio GUI application.
    *
    * @param args Command-line arguments.
    */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Portfolio portfolio = new Portfolio();
            String filename = "portfolio.txt";

            try {
                portfolio.loadInvestmentsFromFile(filename);
            } catch (Exception ex) {
                System.out.println("Error loading portfolio: " + ex.getMessage());
            }

            PortfolioGUI frame = new PortfolioGUI(portfolio, filename);
            frame.setVisible(true);
        });
    } 

    /**
    * This forces the Gain panel to reinitialize, refreshing its content.
    */
    private void refreshGainPanel() {
        gainPanel = null; // This will force reinitialization
        showGainPanel(); // This is fot the rebuild the panel
    }
}


