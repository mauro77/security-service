package com.nisum.securityservice.shared;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>
 * Generic utilities for security microservice
 * </p>
 *
 * @author Mauricio Hincapié
 * @version 1.0
 * @since 2023-07-31
 */
public class SecurityUtils {

  /**
   * generates the value sha256 for the given password
   *
   * @param password
   * @return
   *
   * @author Mauricio Hincapié
   * @version 1.0
   * @since 2023-07-31
   */
  public static String encryptPassword(String password) {
    String sha256hex = DigestUtils.sha256Hex(password);
    return sha256hex;
  }

}
