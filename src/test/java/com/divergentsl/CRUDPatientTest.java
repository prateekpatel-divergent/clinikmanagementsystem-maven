package com.divergentsl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class CRUDPatientTest {

	@Test
	void test() {
		
		Map <String, String> map = new HashMap<>();
		 map.put("patient_name", "john");
         map.put("age", "55");
         map.put("weight", "55");
         map.put("gender", "male");
         map.put("contact_number", "453648756");
         map.put("address", "los");         
		assertEquals(map, CRUDPatient.getPatientData("101"));
		
	}

}
