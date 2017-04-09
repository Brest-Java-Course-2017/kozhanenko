package by.eventcat.custom.exceptions;

/**
 * Custom exception class
 */
public class ServiceException extends Exception {

    private CustomErrorCodes customErrorCode;

    public ServiceException(CustomErrorCodes customErrorCode){
        this.customErrorCode = customErrorCode;
    }

    public CustomErrorCodes getCustomErrorCode() {
        return customErrorCode;
    }
}
