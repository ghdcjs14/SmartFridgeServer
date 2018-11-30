package ac.kaist.smartfridge.ui.itemlist.vo;

import org.springframework.data.annotation.Id;

public class ItemListVO {
	@Id
    private String id;
    private String fullCode;			// GS1 full code
    private String indicator;			// 물류코
    private String memberOrganization;	// 국가코드 
    private String companyNumber;		// 업체코드 
    private String itemReference;		// 상품식별번호 
    private String manufacturedDate;	// 제조일자 
    private String expirationDate;		// 만료일자 
    private String itemName;			// 상품명 
    private Double count;				// 개수
    private String remark;				// 비고
    private String verificationNumber;	// 체크 디지트
    private String remainingDate; 		// 남은 날짜
    
    // GS1 Source Data
    private String productName;					// 상품명
	private String brandName;					// 브랜드명 
	private String productInformationLinkURL;	// 상품 구매 URL
	private String imageLinkURL;				// 상품 이미지 URL
	
	// DISTRIBUTOR
	private String partyContactRoleCode;		// DISTRIBUTOR 역할 코드
	private String partyContactName;			// DISTRIBUTOR 명 
	private String partyContactAddress;			// DISTRIBUTOR 주소
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullCode() {
		return fullCode;
	}
	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getMemberOrganization() {
		return memberOrganization;
	}
	public void setMemberOrganization(String memberOrganization) {
		this.memberOrganization = memberOrganization;
	}
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	public String getItemReference() {
		return itemReference;
	}
	public void setItemReference(String itemReference) {
		this.itemReference = itemReference;
	}
	public String getManufacturedDate() {
		return manufacturedDate;
	}
	public void setManufacturedDate(String manufacturedDate) {
		this.manufacturedDate = manufacturedDate;
	}
	public String getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getCount() {
		return count;
	}
	public void setCount(Double count) {
		this.count = count;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVerificationNumber() {
		return verificationNumber;
	}
	public void setVerificationNumber(String verificationNumber) {
		this.verificationNumber = verificationNumber;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getProductInformationLinkURL() {
		return productInformationLinkURL;
	}
	public void setProductInformationLinkURL(String productInformationLinkURL) {
		this.productInformationLinkURL = productInformationLinkURL;
	}
	public String getImageLinkURL() {
		return imageLinkURL;
	}
	public void setImageLinkURL(String imageLinkURL) {
		this.imageLinkURL = imageLinkURL;
	}
	public String getPartyContactRoleCode() {
		return partyContactRoleCode;
	}
	public void setPartyContactRoleCode(String partyContactRoleCode) {
		this.partyContactRoleCode = partyContactRoleCode;
	}
	public String getPartyContactName() {
		return partyContactName;
	}
	public void setPartyContactName(String partyContactName) {
		this.partyContactName = partyContactName;
	}
	public String getPartyContactAddress() {
		return partyContactAddress;
	}
	public void setPartyContactAddress(String partyContactAddress) {
		this.partyContactAddress = partyContactAddress;
	}
	public String getRemainingDate() {
		return remainingDate;
	}
	public void setRemainingDate(String remainingDate) {
		this.remainingDate = remainingDate;
	}
	@Override
	public String toString() {
		return "ItemListVO [id=" + id + ", fullCode=" + fullCode + ", indicator=" + indicator + ", memberOrganization="
				+ memberOrganization + ", companyNumber=" + companyNumber + ", itemReference=" + itemReference
				+ ", manufacturedDate=" + manufacturedDate + ", expirationDate=" + expirationDate + ", itemName="
				+ itemName + ", count=" + count + ", remark=" + remark + ", verificationNumber=" + verificationNumber
				+ ", productName=" + productName + ", brandName=" + brandName + ", productInformationLinkURL="
				+ productInformationLinkURL + ", imageLinkURL=" + imageLinkURL + ", partyContactRoleCode="
				+ partyContactRoleCode + ", partyContactName=" + partyContactName + ", partyContactAddress="
				+ partyContactAddress + ", getId()=" + getId() + ", getFullCode()=" + getFullCode()
				+ ", getIndicator()=" + getIndicator() + ", getMemberOrganization()=" + getMemberOrganization()
				+ ", getCompanyNumber()=" + getCompanyNumber() + ", getItemReference()=" + getItemReference()
				+ ", getManufacturedDate()=" + getManufacturedDate() + ", getExpirationDate()=" + getExpirationDate()
				+ ", getItemName()=" + getItemName() + ", getCount()=" + getCount() + ", getRemark()=" + getRemark()
				+ ", getVerificationNumber()=" + getVerificationNumber() + ", getProductName()=" + getProductName()
				+ ", getBrandName()=" + getBrandName() + ", getProductInformationLinkURL()="
				+ getProductInformationLinkURL() + ", getImageLinkURL()=" + getImageLinkURL()
				+ ", getPartyContactRoleCode()=" + getPartyContactRoleCode() + ", getPartyContactName()="
				+ getPartyContactName() + ", getPartyContactAddress()=" + getPartyContactAddress() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
    
}
