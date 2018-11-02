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
    
    
}
