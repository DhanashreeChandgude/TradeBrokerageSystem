# Trade System - README
## Overview
This project demonstrates a trade system that handles Buy and Sell trade requests, validating and processing them through various services like TradeService, WalletService, and ShareService. The flow ensures that trades are executed reliably and securely while maintaining consistent communication between services.

## Use Case: Trade Request Handling (Buy/Sell)
### 1. User Initiates a Trade Request:
   The trader initiates a trade request via a frontend interface (web app, mobile app, or phone call). The trade request contains the following information:

ShareName: Name of the share being traded.
ShareQuantity: Number of shares to be bought or sold.
SharePrice: Price per share for the trade.
TraderId: Unique identifier for the trader.
BuyOrSell: Flag indicating whether it’s a buy or sell order.
TradeId: (Optional) Used for updating or canceling existing trades.
### 2. Trade Request Sent to TradeService:
   The TradeService acts as the main orchestrator. It first receives the trade request and validates the input data:

Ensures required fields are present.
Checks that values are within valid limits (e.g., ShareQuantity > 0).
### 3. Validation Based on Trade Type:
   3.1 Buy Trade:
   TradeService -> WalletService:
   The TradeService interacts with WalletService to check if the trader has enough funds to cover the purchase (SharePrice * ShareQuantity).
   WalletService:
   Retrieves trader's wallet balance.
   Blocks the required amount for the buy order.
   Updates the wallet with the blocked amount.
   If the funds are successfully blocked, the TradeService proceeds to book the trade. Otherwise, it rejects the request.
   3.2 Sell Trade:
   TradeService -> ShareService:
   For a sell trade, the TradeService communicates with ShareService to ensure the trader has enough shares to sell.
   ShareService:
   Retrieves the trader's share holdings.
   Blocks the necessary number of shares.
   Updates the database to reflect the blocked shares.
   If the shares are blocked successfully, the TradeService books the trade; otherwise, the trade is rejected. 

### 4. Booking the Trade:
   Once validation is complete (funds or shares are blocked), TradeService proceeds to book the trade:

TradeService -> Trade Database:
The trade details are stored in the TradeService database with a generated TradeId (for new trades) and status.
TradeService -> Share Exchange:
Trade details are sent to the Share Exchange for execution via a message queue (e.g., Kafka, RabbitMQ). The Share Exchange executes the trade and returns the result asynchronously.
### 5. Post-Execution: Updating Wallet and Share Balances
   5.1 Buy Trade:
   TradeService -> WalletService:
   Once the trade is executed, TradeService instructs WalletService to finalize the wallet transaction by deducting the blocked funds and releasing any excess.
   TradeService -> ShareService:
   ShareService is updated to add the purchased shares to the trader’s holdings.
   5.2 Sell Trade:
   TradeService -> ShareService:
   The blocked shares are deducted from the trader's holdings.
   TradeService -> WalletService:
   The sale proceeds are credited to the trader’s wallet.
### 6. Trade Status Reporting:
   TradeService -> User:
   The trader is notified of the trade’s status (success, failure, partial execution) via a response. Additionally, users can query the trade status anytime using the TradeId. 
### 7. Monitoring and Logging:
   Each service logs its actions and errors to a centralized monitoring system (e.g., Prometheus, Grafana, or ELK Stack). Logs capture:
         1. Trade requests.
         2. Wallet and share actions.
         3. Database updates.
         4. Communication with the Share Exchange.

## Detailed Flow Summary:
   1.User submits a trade request (Buy/Sell). 
   2.TradeService validates the request.
      For Buy: Calls WalletService to block funds.
      For Sell: Calls ShareService to block shares.
   3.TradeService books the trade in the database and sends it to the Share Exchange.
   4.Share Exchange executes the trade and returns a response.
   5.TradeService updates the WalletService and ShareService based on trade execution.
   6.TradeService reports the trade status to the user.
   7.Monitoring captures logs for auditing and system health checks.
   
![img](https://github.com/user-attachments/assets/5f9a4a5f-208a-4b0a-b08d-0f1485c95681)



Miro Link: https://miro.com/app/board/uXjVKgZDR_s=/?moveToWidget=3458764599732233473&cot=14
