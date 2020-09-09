package org.easy.secure.exception;


import org.easy.tool.exception.CustomException;
import org.easy.tool.web.IResultCode;
import org.easy.tool.web.ResultCode;

/**
 */
public class UserNotFoundException extends CustomException {

    public UserNotFoundException(IResultCode code, String message) {
        super(code);
    }

    public UserNotFoundException(IResultCode code) {
        super(code);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
