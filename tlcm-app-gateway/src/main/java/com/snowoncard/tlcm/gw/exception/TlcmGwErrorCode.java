package com.snowoncard.tlcm.gw.exception;

public enum TlcmGwErrorCode {
  
    //Success
    SUCCESS("1000"),

    // Json Parsing error
    JSON_PROCESSING_ERROR("2001"),
    
    // API Parameter Error
    MANDATORY_PARAMETER_MISSING("2004"),
    INVALID_PARAMETER("2005"),
    
    // Cryptography Error
    CRYPTO_PROCESSING_ERROR("2008"),
    
    AUTHENTICATION_ERROR("4000"),
    PUSH_NOTIFICATION_ERROR("7001"),
    
    // Internal Error
    UNKNOWN_INTERNAL_ERROR("9000"),
    
    ;
  
    private String code;


    TlcmGwErrorCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean equals(String code) {
        return this.code.equals(code);
    }

    public static TlcmGwErrorCode find(String code) {
        for (TlcmGwErrorCode errorCode : TlcmGwErrorCode.values()) {
            if (errorCode.equals(code)) {
                return errorCode;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name() + " (" + code + ")";
    }

}
