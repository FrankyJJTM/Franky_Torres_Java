package vallegrande.edu.pe.agrofrutoslambayeque.model;

public class Customer {
    private int customerId;
    private String name;
    private String lastName;
    private String documentNumber;
    private String phone;
    private String mail;
    private String address;
    private Integer documentTypeId;

    public Customer() {}

    public Customer(int customerId, String name, String lastName, String documentNumber, String phone, String mail, String address, Integer documentTypeId) {
        this.customerId = customerId;
        this.name = name;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phone = phone;
        this.mail = mail;
        this.address = address;
        this.documentTypeId = documentTypeId;
    }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDocumentNumber() { return documentNumber; }
    public void setDocumentNumber(String documentNumber) { this.documentNumber = documentNumber; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Integer getDocumentTypeId() { return documentTypeId; }
    public void setDocumentTypeId(Integer documentTypeId) { this.documentTypeId = documentTypeId; }
}