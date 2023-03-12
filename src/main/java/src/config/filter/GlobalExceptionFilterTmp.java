package src.config.filter;


import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
public class GlobalExceptionFilterTmp extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(value = {Exception.class, UnauthorizedException.class})
//    protected ResponseEntity<Object> handleExceptions(
//            RuntimeException ex,
//            HttpServletRequest request) {
//        ExceptionResponse errorResponse = new ExceptionResponse();
//        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        errorResponse.setPath(request.getRequestURL().toString() + "?" + request.getQueryString());
//        errorResponse.setMessage(ex.getLocalizedMessage());
//        errorResponse.setError(ex.getCause() == null ? "Unknown" : ex.getCause().toString());
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}