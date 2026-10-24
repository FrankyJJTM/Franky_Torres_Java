package vallegrande.edu.pe.agrofrutoslambayeque.model;

public class Partner {
    private int memberId;
    private String dniRuc;
    private String memberCode;
    private String phone;
    private String address;
    private String firstNamesLastNames;
    private String certificationStatus;
    private Integer documentTypeId;

    public Partner() {}

    public Partner(int memberId, String dniRuc, String memberCode, String phone, String address, String firstNamesLastNames, String certificationStatus, Integer documentTypeId) {
        this.memberId = memberId;
        this.dniRuc = dniRuc;
        this.memberCode = memberCode;
        this.phone = phone;
        this.address = address;
        this.firstNamesLastNames = firstNamesLastNames;
        this.certificationStatus = certificationStatus;
        this.documentTypeId = documentTypeId;
    }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getDniRuc() { return dniRuc; }
    public void setDniRuc(String dniRuc) { this.dniRuc = dniRuc; }

    public String getMemberCode() { return memberCode; }
    public void setMemberCode(String memberCode) { this.memberCode = memberCode; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getFirstNamesLastNames() { return firstNamesLastNames; }
    public void setFirstNamesLastNames(String firstNamesLastNames) { this.firstNamesLastNames = firstNamesLastNames; }

    public String getCertificationStatus() { return certificationStatus; }
    public void setCertificationStatus(String certificationStatus) { this.certificationStatus = certificationStatus; }

    public Integer getDocumentTypeId() { return documentTypeId; }
    public void setDocumentTypeId(Integer documentTypeId) { this.documentTypeId = documentTypeId; }
}