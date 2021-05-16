package com.acuster.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A POJO to hold all of the properties of the token returned from the AWS authorization endpoint.
 */
public class AwsTokenResponse{
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("id_token")
	private String idToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private int expiresIn;

	/**
	 * Get access token.
	 *
	 * @return the access token string
	 */
	public String getAccessToken(){
		return accessToken;
	}

	/**
	 * Get refresh token.
	 *
	 * @return the refresh token
	 */
	public String getRefreshToken(){
		return refreshToken;
	}

	/**
	 * Get id token.
	 *
	 * @return the id token string
	 */
	public String getIdToken(){
		return idToken;
	}

	/**
	 * Get token type string.
	 *
	 * @return the string
	 */
	public String getTokenType(){
		return tokenType;
	}

	/**
	 * Getwhen the token expires.
	 *
	 * @return when the token expires
	 */
	public int getExpiresIn(){
		return expiresIn;
	}
}
