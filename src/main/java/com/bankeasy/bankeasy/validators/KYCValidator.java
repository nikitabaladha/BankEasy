//package com.bankeasy.bankeasy.validators;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
//
//
//public class KYCValidator {
//
//    @NotBlank(message = "Document type is required.")
//    @Size(max = 50, message = "Document type must not exceed 50 characters.")
//    private String documentType;
//
//    @NotBlank(message = "Document number is required.")
//    @Size(max = 50, message = "Document number must not exceed 50 characters.")
//    private String documentNumber;
//
//    public String getDocumentType() {
//        return documentType;
//    }
//
//    public void setDocumentType(String documentType) {
//        this.documentType = documentType;
//    }
//
//    public String getDocumentNumber() {
//        return documentNumber;
//    }
//
//    public void setDocumentNumber(String documentNumber) {
//        this.documentNumber = documentNumber;
//    }
//
//}


package com.bankeasy.bankeasy.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class KYCValidator {

    @NotBlank(message = "Document type is required.")
    @Size(max = 50, message = "Document type must not exceed 50 characters.")
    private String documentType;

    @NotBlank(message = "Document number is required.")
    @Size(max = 50, message = "Document number must not exceed 50 characters.")
    private String documentNumber;
    
//    @NotBlank(message = "Document image is required.")
    private String documentUrl;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

	public String getDocumentUrl() {
		return documentUrl;
	}

	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

}
