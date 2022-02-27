import com.google.gson.Gson;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Trade;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;

public class NseTradesParser {

  private List<Trade> trades;
  private Trade lastTrade;

  public NseTradesParser(InputStream inputStream, String password) throws IOException {
    try (PDDocument doc = Loader.loadPDF(inputStream, password)) {
      getTradesList(doc);
    }
  }

  public NseTradesParser(InputStream inputStream) throws IOException {
    try (PDDocument doc = Loader.loadPDF(inputStream)) {
      getTradesList(doc);
    }
  }

  public NseTradesParser(byte[] input, String password) throws IOException {
    try (PDDocument doc = Loader.loadPDF(input, password)) {
      getTradesList(doc);
    }
  }

  public NseTradesParser(byte[] input) throws IOException {
    try (PDDocument doc = Loader.loadPDF(input)) {
      getTradesList(doc);
    }
  }

  public NseTradesParser(File file, String password) throws IOException {
    try (PDDocument doc = Loader.loadPDF(file, password)) {
      getTradesList(doc);
    }
  }

  public NseTradesParser(File file) throws IOException {
      try (PDDocument doc = Loader.loadPDF(file)) {
        getTradesList(doc);
      }
  }

  /**
   * @return Returns List of Trades
   */
  public List<Trade> getTrades(){
    return this.trades;
  }

  /**
   * @return Return trades as JSON
   */
  public String getTradesJson(){
    Gson gson = new Gson();
    return gson.toJson(this.trades);
  }

  /**
   * Process multiple page pdf file
   * @param doc needs PDDocument instance
   * @throws IOException
   */
  private void getTradesList(PDDocument doc) throws IOException {
    this.trades = new ArrayList<>();
    for (PDPage pdPage : doc.getPages()) {
      getTradesForSinglePage(pdPage);
    }
  }

  /**
   * Process single page of the pdf
   * @param page
   * @throws IOException
   */
  private void getTradesForSinglePage(PDPage page) throws IOException {
    float pageHeight = page.getMediaBox().getHeight();
    for (int y =116; y < pageHeight ; y=y+7) {
      PDFTextStripperByArea stripper = new PDFTextStripperByArea();
      stripper.setSortByPosition(true);
      setRegionsToTextStripper(stripper, y);
      stripper.extractRegions(page);
      addTradeToList(stripper);
    }
  }

  /**
   * Adds a single trade to the provided list. Auxiliary method.
   * @param stripper
   */
  private void addTradeToList(PDFTextStripperByArea stripper) {
    List<String> regions = stripper.getRegions();
    Optional<Integer> srNo = Optional.ofNullable(stripper.getTextForRegion(regions.get(0)).strip()).filter(s -> !s.isBlank()).map(Integer::valueOf);
    String tmName = stripper.getTextForRegion(regions.get(1)).strip();
    Long clientCode = Optional.ofNullable(stripper.getTextForRegion(regions.get(2)).strip()).filter(s -> !s.isBlank()).map(Long::valueOf).orElse(null);
    String buySell = stripper.getTextForRegion(regions.get(3)).strip();
    String nameOfSecurity = stripper.getTextForRegion(regions.get(4)).strip();
    String symbol = stripper.getTextForRegion(regions.get(5)).strip();
    String series = stripper.getTextForRegion(regions.get(6)).strip();
    Long tradeNo = Optional.ofNullable(stripper.getTextForRegion(regions.get(7)).strip()).filter(s -> !s.isBlank()).map(Long::valueOf).orElse(null);
    String tradeTime = stripper.getTextForRegion(regions.get(8)).strip();
    Long quantity = Optional.ofNullable(stripper.getTextForRegion(regions.get(9)).strip()).filter(s -> !s.isBlank()).map(Long::valueOf).orElse(null);
    Double price = Optional.ofNullable(stripper.getTextForRegion(regions.get(10).strip())).filter(s -> !s.isBlank()).map(Double::valueOf).orElse(null);
    Double tradedValue = Optional.ofNullable(stripper.getTextForRegion(regions.get(11).strip())).filter(s -> !s.isBlank()).map(Double::valueOf).orElse(null);

    // Data for new trade or existing trade
    if(srNo.isPresent()) {
      this.lastTrade = new Trade(srNo.get(), tmName, clientCode, buySell, nameOfSecurity, symbol, series, tradeNo, tradeTime, quantity, price, tradedValue);
      this.trades.add(this.lastTrade);
    } else {
      if(!tmName.isEmpty())
        this.lastTrade.setTmName(" "+tmName);
      if(!nameOfSecurity.isEmpty())
        this.lastTrade.setNameOfSecurity(" "+nameOfSecurity);
    }
  }

  /**
   * Sets regions to search in the pdf
   * @param stripper
   * @param y the y coordinate for the region from where region starts
   */
  private void setRegionsToTextStripper(PDFTextStripperByArea stripper, int y){

    Rectangle srNo = new Rectangle(12,y,36,7);
    stripper.addRegion("srNo",srNo);

    Rectangle tmName = new Rectangle(48,y,144,7);
    stripper.addRegion("tmName",tmName);

    Rectangle clientCode = new Rectangle(192,y,72,7);
    stripper.addRegion("clientCode",clientCode);

    Rectangle buySell = new Rectangle(264,y,36,7);
    stripper.addRegion("buySell",buySell);

    Rectangle nameOfSecurity = new Rectangle(300,y,216,7);
    stripper.addRegion("nameOfSecurity",nameOfSecurity);

    Rectangle symbol = new Rectangle(516,y,108,7);
    stripper.addRegion("symbol",symbol);

    Rectangle series = new Rectangle(624,y,54,7);
    stripper.addRegion("series",series);

    Rectangle tradeNo = new Rectangle(678,y,126,7);
    stripper.addRegion("tradeNo",tradeNo);

    Rectangle tradeTime = new Rectangle(804,y,108,7);
    stripper.addRegion("tradeTime",tradeTime);

    Rectangle quantity = new Rectangle(912,y,72,7);
    stripper.addRegion("quantity",quantity);

    Rectangle price = new Rectangle(984,y,90,7);
    stripper.addRegion("price",price);

    Rectangle tradedValue = new Rectangle(1074,y,105,7);
    stripper.addRegion("tradedValue",tradedValue);
  }

}
