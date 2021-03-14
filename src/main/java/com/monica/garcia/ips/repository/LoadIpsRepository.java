package com.monica.garcia.ips.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monica.garcia.ips.models.Ip;

@Repository
public interface LoadIpsRepository extends JpaRepository<Ip, Long> {
	
	
	/**
	 * Consultar información de una ip.
	 * @param decimalIp
	 * @return
	 */
	@Query(value = "SELECT * FROM ip WHERE ?1 BETWEEN  ip_from AND ip_to ", nativeQuery = true)
	public List<Ip> getIpByDecimalIp(double decimalIp);

}
