public interface BrokerService {
    boolean validateTrade(Trade trade);
    String bookTrade(Trade trade);
    void updateTrade(Trade trade);
    void reportTradeStatus(String tradeId);
}