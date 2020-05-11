package com.ivan.tfm.MainAppBackEnd.beans;

import org.web3j.tuples.generated.Tuple6;

import com.ivan.tfm.MainAppBackEnd.utils.Converter;


public class Client {
	private String endpoint;
	private String url_bs;
	private String id_bs;
	private String key_bs;
	private String url_s;
	private String id_s;
	private String key_s;
	
	public Client() {}
	
	public Client(String endpoint, Tuple6<byte[],byte[],byte[],byte[],byte[],byte[]> in) {
		this.endpoint = endpoint;
		this.url_bs = Converter.byteToAscii(in.component1());
		this.id_bs = Converter.byteToAscii(in.component2());
		this.key_bs = Converter.byteToAscii(in.component3());
		this.url_s = Converter.byteToAscii(in.component4());
		this.id_s = Converter.byteToAscii(in.component5());
		this.key_s = Converter.byteToAscii(in.component6());
	}
	
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getUrl_bs() {
		return url_bs;
	}
	public void setUrl_bs(String url_bs) {
		this.url_bs = url_bs;
	}
	public String getId_bs() {
		return id_bs;
	}
	public void setId_bs(String id_bs) {
		this.id_bs = id_bs;
	}
	public String getKey_bs() {
		return key_bs;
	}
	public void setKey_bs(String key_bs) {
		this.key_bs = key_bs;
	}
	public String getUrl_s() {
		return url_s;
	}
	public void setUrl_s(String url_s) {
		this.url_s = url_s;
	}
	public String getId_s() {
		return id_s;
	}
	public void setId_s(String id_s) {
		this.id_s = id_s;
	}
	public String getKey_s() {
		return key_s;
	}
	public void setKey_s(String key_s) {
		this.key_s = key_s;
	}
}
