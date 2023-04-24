package com.msys.esm.api;

import com.msys.esm.core.util.Messages.MessageAndStatusCode;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<MessageAndStatusCode> handleError(HttpServletRequest request) {
        var status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            if (status == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>(new MessageAndStatusCode(
                        "Endpoint not found or deprecated"
                        , 404), HttpStatus.NOT_FOUND);
            } else if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>(new MessageAndStatusCode(
                        "Internal server error"
                        , 500), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(new MessageAndStatusCode(
                "Unknown error"
                , 500), HttpStatus.NOT_FOUND);
    }


}
