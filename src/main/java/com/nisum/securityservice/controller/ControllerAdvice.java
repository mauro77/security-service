package com.nisum.securityservice.controller;

import com.nisum.securityservice.dto.shared.RestResponse;
import com.nisum.securityservice.exception.ResourceNotFoundException;
import com.nisum.securityservice.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * <p>
 * Class for custom response in exception management
 * </p>
 * 
 * @author Mauricio Hincapie Monsalve.
 *
 */
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

  /**
   *
   * @param exception
   * @return
   * @author Mauricio Hincapie Monsalve.
   *
   */
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Throwable.class)
  @ResponseBody
  public RestResponse handleException(Throwable exception) {
    return getResponse(exception.getMessage());
  }
  

  /**
   * 
   * @param resourceNotFoundException
   * @return
   * 
   * @author Mauricio Hincapie Monsalve.
   * @version 1.0
   */
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(value = ResourceNotFoundException.class)
  @ResponseBody
  public RestResponse userBadRequestRuntimeException(ResourceNotFoundException resourceNotFoundException) {
    return getResponse(resourceNotFoundException.getMessage());
  }

  /**
   *
   * @param unprocessableEntityException
   * @return
   *
   * @author Mauricio Hincapie Monsalve.
   * @version 1.0
   */
  @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(value = UnprocessableEntityException.class)
  @ResponseBody
  public RestResponse userBadRequestRuntimeException(UnprocessableEntityException unprocessableEntityException) {
    return getResponse(unprocessableEntityException.getMessage());
  }

  /**
   * 
   * @return @RestResponse
   * 
   * @author Mauricio Hincapie Monsalve.
   * 
   */
  private RestResponse getResponse(String message) {
    RestResponse response = new RestResponse();
    response.setMessage(message);
    return response;
  }

}
