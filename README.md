
1. Title: Assignment3

2. General Problem:
To beigin, this project implements an ePortfolio application to manage all types of investments whether big or small, including stocks and mutual funds. Users can add, sell, update, search and load or save investments with specific criteria to a file. The program also calculates total gains from investments. All this is done by using GUI, so you ahve an app like interface.
 
3. Assumptions and Limitations: 
Lets start with Assumptions:
    -The program assumes all prices entered are positive numbers.
    -The program assumes symbols for both stocks and mutual funds are case insensitive but must be entered.
    -Investments can be removed when the quantity reaches zero after selling.
    -The same symbol cannot be reused across stock and mutual fund investments.
    -Commands are flexible e.g, bu works for buy, but for search and sell, the system will differentiate based on the prefix. 
    - All portfolio data is saved when the program is done e.g when the user exits/ quits. 
    - If there exists a file that has information for the stocks and mutual funds before the prgram is runned, then once it is runned it will laod that file
Limitations:
    -The program does not handle currency exchange rates or real time market data. 
    -The program requires manual entires for prices changes 
    - There are only two types of investments which are stocks and mutual funds 

4. User Guide 
:Here is how you can and I did my compiling and running for this prgram: 
Compile:
javac ePortfolio/*.java      

Run:
java ePortfolio.ePortfolio investment.txt 
this is a example you can use any file e.g java ePortfolio.ePortfolio <filename.txt>

Commands Overview:
buy: This will buy a stock or mutual fund. That said, it also supports adding more to existing investments.
sell: This will sell a stock or mutual fund.
update: This will update prices for all investments.
getGain: This will calculate the total gain of the portfolio.
search: This will search for investments by symbol, keyword, or price range. That said, and if nothing is entered in those fields it will dsiplay everything in the portfolio
quit: This will exit the program.

5. Test Plan 
Below for many of them I am using an example, but if you decide not to or do something else,you should still see something similar as the output, I hahve stated.
Test 1: (In order for Buying a Stock)
Scenario: In this Scenario user buys a new stock that is not in the portfolio. 
Input: 
                Buying an Investment
Type: Stock
Symbol: aapl
Name: apple
Quantity: 500
Price: 110
Expected Output,
Messages:
Success!! Bought 500 shares of (AAPL) at $110.0 each.

Test 2: (Buying the Same Stock again) 
Scenario: In this Scenario user buys the stock that they prevously have already bought again.
Input:
                Buying an Investment
Type: Stock
Symbol: aapl
Name: apple
Quantity: 500
Price: 110
Expected Output, 
Messages:
Added 100 more units of aapl at $110.0 each.

Test 3: (Buying Mutual with the symbol from stock)
Scenario: Attempt to Use the Same Symbol for a Mutual Fund
Input:
                Buying an Investment
Type: Mutual Fund
Symbol: aapl
Name: apple
Quantity: 500
Price: 110
Expected Output, 
Messages:
Error: Symbol already exists with a different investment type.

Test 4: 
Scenario: Searching for Investments: 
input:
                Searching Investments
Symbol: AAPL or aapl
keyword: apple
Price Range: 10- 
press search
Expected Output,
Search Results:  
Search Results: 
Symbol: aapl, Name: apple, Quantity: 600, Price: $110.00, Book Value: $66019.98
    -> Also you can leave all of these feilds blank and it will display whatever user has in his porfolio/ the stocks or mutual funds the user has it will display all of them  
Another Scenario: Searching with a symbol that does not exist.
input:
                Searching Investments
Symbol: tldr
keyword: 
Price Range: 
press search
Expected Output,
Search Results:  
No matching investments found.

Test 5: Selling stocks
Scenario: Selling part of a stock 
Input: 
                Selling an Investment
Symbol: AAPL
Quantity: 100
Price: 200 
Expected Output,
Messages:
Success!! Sold 100 shares of aapl at $200.0 per share.
Sold stock with gain: $8986.68
 -> what if we sell the whole stock leaving nothing behind,
 Success!! Sold 500 shares of AAPL at $140.0 per share.
 Sold stock with gain: $27097.222799999996
 All units of AAPL sold. Investment removed from portfolio.
   
Test 6: (Updating Prices)
Scenario: This will Update prices of all investments.
Input:  
                Updating Investments
Symbol: aapl
Name: apple
Price: 130
you can also go back and forth between investments when updating and whenever ou update to have to click save 
expected output:
Messages:
Price updated for aapl: $200.0

Test 7: (Using Get Gain) 
Scenario: GetGain of all investments.
input:  
            Getting Total Gain
Expected Output: 
Total gain: and whatever the total gain is based on the ouput 
Individul Gains:
whatever is ur investment
    
6. Possible Improvements 
the possible improvements can be, 
- we can implement real time stock price updates by using an API. 
- We can also ask user before anything wheather they will like to open an account and if so which one e.g TFSA or other accounts and then we can changre them accordingly  
- We can adddd support for different currencies and exchange rates.
- Lastly we can make another option that tells user about their cureent holdings and the amount of total money they have invested and we can put a limit on them like the TFSA account does, eg, with the withdrawinga and investing thing 
