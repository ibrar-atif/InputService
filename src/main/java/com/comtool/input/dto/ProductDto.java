package com.comtool.input.dto;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class ProductDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@CsvBindByName(column = "UUID")
	private String id;
	
	@CsvBindByName(column = "Description")
	private String description;
	
	@CsvBindByName(column = "Name")
	private String name;
	
	@CsvBindByName(column = "provider")
	private String provider;
	
	@CsvBindByName(column = "available")
	private Boolean available;
	
	@CsvBindByName(column = "MeasurementUnits")
	private String measurementUnits;
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getMeasurementUnits() {
		return measurementUnits;
	}

	public void setMeasurementUnits(String measurementUnits) {
		this.measurementUnits = measurementUnits;
	}
	
	
}
