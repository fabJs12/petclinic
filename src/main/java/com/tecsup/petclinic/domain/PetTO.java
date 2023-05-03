package com.tecsup.petclinic.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * 
 * @author jgomezm
 *
 */
@Data
public class PetTO {

	private long id;
	
	private String name;
	
	private int typeId;

	private int ownerId;

	private String birthDate;

}
