public class Trade {
    private String shareName;
    private int shareQuantity;
    private double sharePrice;
    private String traderId;
    private TradeAction action; // Enum for BUY or SELL
    private String tradeId; // Optional, null for new trades

    // Constructors, Getters, Setters, and toString

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public boolean isValid() {
        // Basic validation logic
        if (shareName == null || shareQuantity <= 0 || sharePrice <= 0 || traderId == null || action == null) {
            return false;
        }
        return true;
    }
}

enum TradeAction {
    BUY,
    SELL
}