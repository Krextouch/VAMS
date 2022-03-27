package edu.dhbw.stuttgart.tinf20b.vamsBE.core.error;

import edu.dhbw.stuttgart.tinf20b.vamsBE.core.error.model.ErrorJsonResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
public class ErrorPage implements ErrorController {


    //Returns JSON
    @RequestMapping("/error")
    @ResponseBody
    public ErrorJsonResponse handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        //Deactivated on prod for security reasons. The end user should not see which exception are thrown if something goes wrong
        //Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        String path = (String) request.getAttribute("javax.servlet.error.request_uri");

        return ErrorJsonResponse.builder()
                .timestamp(LocalDateTime.now(ZoneOffset.UTC))
                .status(statusCode)
                .error(HttpStatus.resolve(statusCode).getReasonPhrase())
                .message(message)
                .path(path)
                .build();
    }

    /*

    //Returns HTML
    @RequestMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        //Deactivated for security reasons. The end user should not see which exception are thrown if something goes wrong
        //Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        return String.format("<html><body><h2>Error Page</h2><div>Status code: <b>%s</b></div>"
                        + "<div>Message: <b>%s</b></div><body></html>",
                statusCode, message.length() <= 0? "N/A": message);
    }
    */

}
