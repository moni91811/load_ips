package com.monica.garcia.ips.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

import com.monica.garcia.ips.exception.ClientErrorException;
import com.monica.garcia.ips.service.LoadIpsService;
import com.monica.garcia.ips.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoadIpsServiceTest {
	
	private static String IP = "201.184.37.54";
	private static long DECIMAL_IP = 3384288566L;

	private static final String BASE_URL_API = "/ip";
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private LoadIpsService loadIpsService;
	
	private MockMvc mockMvcClient;
	
	@BeforeEach
	public void setUp() throws Exception {
		mockMvcClient = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void getLoadIps() throws Exception {
		this.mockMvcClient.perform(get(BASE_URL_API + "/{ip}",IP).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	@Test
	public void getDecimalIp() {
		assertEquals(DECIMAL_IP,Util.getDecimalIp(IP));
	}
	
	@Test
	public void getLoadIpsService() {
		HttpStatus statusCode = null;
		try {
			MockMultipartFile file = new MockMultipartFile("file", "prueba.csv", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
			loadIpsService.loadIps(file);
		} catch (ClientErrorException ex) {
			statusCode = ex.getStatusCode();
		}
		assertEquals(HttpStatus.BAD_REQUEST, statusCode);
	}
	
}
