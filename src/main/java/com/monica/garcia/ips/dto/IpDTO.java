package com.monica.garcia.ips.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class IpDTO implements Serializable {
	
	private static final long serialVersionUID = 8355188283516962340L;
	private String countryCode;
	private String region;
	private String city;
	private String timezone;	
}
