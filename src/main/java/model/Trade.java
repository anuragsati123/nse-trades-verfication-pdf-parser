package model;

public class Trade {

  private Integer srNo;
  private StringBuffer tmName;
  private Long clientCode;
  private String buySell;
  private StringBuffer nameOfSecurity;
  private String symbol;
  private String series;
  private Long tradeNo;
  private String tradeTime;
  private Long quantity;
  private Double price;
  private Double tradedValue;

  public Trade(Integer srNo, String tmName, Long clientCode, String buySell,
      String nameOfSecurity, String symbol, String series, Long tradeNo,
      String tradeTime, Long quantity, Double price, Double tradedValue) {
    this.srNo = srNo;
    this.tmName = new StringBuffer(tmName);
    this.clientCode = clientCode;
    this.buySell = buySell;
    this.nameOfSecurity = new StringBuffer(nameOfSecurity);
    this.symbol = symbol;
    this.series = series;
    this.tradeNo = tradeNo;
    this.tradeTime = tradeTime;
    this.quantity = quantity;
    this.price = price;
    this.tradedValue = tradedValue;
  }

  public Integer getSrNo() {
    return srNo;
  }

  public void setSrNo(Integer srNo) {
    this.srNo = srNo;
  }

  public String getTmName() {
    return tmName.toString();
  }

  public void setTmName(String tmName) {
    if(this.tmName == null)
      this.tmName = new StringBuffer();
    this.tmName.append(tmName);
  }

  public Long getClientCode() {
    return clientCode;
  }

  public void setClientCode(Long clientCode) {
    this.clientCode = clientCode;
  }

  public String getBuySell() {
    return buySell;
  }

  public void setBuySell(String buySell) {
    this.buySell = buySell;
  }

  public String getNameOfSecurity() {
    return nameOfSecurity.toString();
  }

  public void setNameOfSecurity(String nameOfSecurity) {
    if(this.nameOfSecurity == null)
      this.nameOfSecurity = new StringBuffer();
    this.nameOfSecurity.append(nameOfSecurity);
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getSeries() {
    return series;
  }

  public void setSeries(String series) {
    this.series = series;
  }

  public Long getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(Long tradeNo) {
    this.tradeNo = tradeNo;
  }

  public String getTradeTime() {
    return tradeTime;
  }

  public void setTradeTime(String tradeTime) {
    this.tradeTime = tradeTime;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Double getTradedValue() {
    return tradedValue;
  }

  public void setTradedValue(Double tradedValue) {
    this.tradedValue = tradedValue;
  }

  @Override
  public String toString() {
    return "Trade{" +
        "srNo=" + srNo +
        ", tmName=" + tmName +
        ", clientCode=" + clientCode +
        ", buySell='" + buySell + '\'' +
        ", nameOfSecurity=" + nameOfSecurity +
        ", symbol='" + symbol + '\'' +
        ", series='" + series + '\'' +
        ", tradeNo=" + tradeNo +
        ", tradeTime='" + tradeTime + '\'' +
        ", quantity=" + quantity +
        ", price=" + price +
        ", tradedValue=" + tradedValue +
        '}';
  }
}
