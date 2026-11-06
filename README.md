# Investment Portfolio Manager

A **Java desktop application** for tracking stock and mutual-fund holdings, transactions, searches, and gains.

**Stack:** Java · Swing GUI · File I/O

## Features

- Buy and sell stocks or mutual funds.
- Update investment prices and quantities.
- Search holdings by symbol, name, or price range.
- Calculate investment gains.
- Save and load portfolio records from a local file.

The application is designed for manually entered information; it does **not** fetch live market prices or connect to a brokerage.

## Run

From the repository root:

```bash
javac ePortfolio/*.java
java ePortfolio.ePortfolio investment.txt
```

You can replace `investment.txt` with another local data file path.

## Design

The repository separates stock/mutual-fund concepts, the underlying portfolio operations, and the GUI. Its documentation also contains generated Java API reference material in `docs/`.

## Limitations

- Prices are entered manually; no market-data API.
- No currency conversion, tax calculations, or brokerage integration.
- Local file storage only.

## Ideas for further development

Automated tests, configurable currencies, optional market-data integration, and clearer portfolio summaries.
