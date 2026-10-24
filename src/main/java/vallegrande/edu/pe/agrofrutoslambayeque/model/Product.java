package vallegrande.edu.pe.agrofrutoslambayeque.model;

public class Product {
    private int productId;
    private String productName;
    private String variety;
    private String certificationType;

    public Product() {}

    public Product(int productId, String productName, String variety, String certificationType) {
        this.productId = productId;
        this.productName = productName;
        this.variety = variety;
        this.certificationType = certificationType;
    }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getVariety() { return variety; }
    public void setVariety(String variety) { this.variety = variety; }

    public String getCertificationType() { return certificationType; }
    public void setCertificationType(String certificationType) { this.certificationType = certificationType; }
}