package com.monica.garcia.ips.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.monica.garcia.ips.dto.IpDTO;
import com.monica.garcia.ips.models.Ip;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IpsControllerTest {

	private static String COUNTRY_CODE = "AU";
	private static String REGION = "SOUTH AUSTRALIA";
	private static String CITY = "PROSPECT";
	private static String TIMEZONE = "?";
	private static final String BASE_URL_API = "/ip";
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvcClient;
	
	@Autowired
	private LoadIpController controller;
	
	@BeforeEach
	public void setUp() throws Exception {
		mockMvcClient = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void uploadIpsWithoutFile() throws Exception {
		MultipartFile file = null;
		this.mockMvcClient.perform(get(BASE_URL_API + "/upload", file).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void uploadIpsVerifyExtension() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
		this.mockMvcClient.perform(multipart(BASE_URL_API + "/upload").file(file)).andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}	
	
	@Test
	public void getLoadIpsIsEmpty() throws Exception {
		String ip = "";
		this.mockMvcClient.perform(get(BASE_URL_API + "/{ip}",ip).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void getLoadIpsValidationIp()  throws Exception {
		this.mockMvcClient.perform(get(BASE_URL_API + "/{ip}","1.1.1").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void getDtoController() throws Exception {
		assertEquals(makeIpDTO().getCountryCode(), controller.getDto(makeIp()).getCountryCode());
		assertEquals(makeIpDTO().getCity(), controller.getDto(makeIp()).getCity());
	}
	
	
	/**
	 * Armar entity ip.
	 * @return
	 */
	public Ip makeIp(){
		Ip ip = new Ip();
		ip.setCountryCode(COUNTRY_CODE);
		ip.setRegion(REGION);
		ip.setCity(CITY);
		ip.setTimezone(TIMEZONE);
		return ip;
	}
	
	
	/**
	 * Armar entity IpDTO.
	 * @return
	 */
	public IpDTO makeIpDTO(){
		IpDTO ip = new IpDTO();
		ip.setCountryCode(COUNTRY_CODE);
		ip.setRegion(REGION);
		ip.setCity(CITY);
		ip.setTimezone(TIMEZONE);
		return ip;
	}
	
}
