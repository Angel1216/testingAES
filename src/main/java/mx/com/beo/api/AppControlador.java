package mx.com.beo.api;

import java.util.Map;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.beo.util.AESCBC128bits;
import mx.com.beo.util.HeadersParser;


import mx.com.beo.util.PBKDF2AES;
import mx.com.beo.util.Sanitizacion;

/**
* Copyright (c)  2017 Nova Solution Systems S.A. de C.V.
* Mexico D.F.
* Todos los derechos reservados.
*
* @author Angel Martínez León
*
* ESTE SOFTWARE ES INFORMACIÓN CONFIDENCIAL. PROPIEDAD DE NOVA SOLUTION SYSTEMS.
* ESTA INFORMACIÓN NO DEBE SER DIVULGADA Y PUEDE SOLAMENTE SER UTILIZADA DE ACUERDO CON LOS TÉRMINOS DETERMINADOS POR LA EMPRESA SÍ MISMA.
*/

@RestController
public class AppControlador {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppControlador.class);

	/**
     * Servicio para encriptar la cadena con el estándar avanzanzado de encripción (AES).
     * @param numero-cliente es el número de cliente que sera utilizado para encriptar con el formato "fecha|cuenta|numero-cliente".
     * @param cuenta es el número de cuenta que sera utilizado para encriptar con el formato "fecha|cuenta|numero-cliente".
     * 
     * @return la cadena encriptada con AES en base64.
     * @throws si la encriptación falla.
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/obtenerURL", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> detalleFondos(RequestEntity<Object> request) {
		
		LOGGER.info("EndPoint obtenerURL");
		
		// Variables
		Map<String, Object> responseError = new HashMap<String, Object>();
		Map<String, Object> mapBody = (Map<String, Object>) request.getBody();
		Map<String, Object> responseEncryption = new HashMap<String, Object>();
		
		String cadenaCifrada = "";
		String cadenaSanitizada = "";
		StringBuilder cadenaOriginal = new StringBuilder();
		PBKDF2AES PBKDF2AES_128 = new PBKDF2AES();
		Sanitizacion sanitizacion = new Sanitizacion(); 

		
		try {
			
			// Arma cadena
//			cadenaOriginal.append(mapBody.get("fechaOperacion")).append("|").
//			append(mapBody.get("criterioConsulta")).append("|").
//			append(mapBody.get("valorConsulta")).append("|").
//			append(mapBody.get("claveSpeiBancoEmisorPago")).append("|").
//			append(mapBody.get("claveSpeiBancoReceptorPago")).append("|").
//			append(mapBody.get("numeroCuentaBeneficiario")).append("|").
//			append(mapBody.get("montoOperacion"));
			
			cadenaOriginal.append("20170912|A|4310163|40132|Angel|002528095301162365|23.50");
			
			//LOGGER.info("cadenaOriginal                      : " + cadenaOriginal);
			
//			// Encriptar
//			cadenaCifrada = PBKDF2AES_128.encrypt(cadenaOriginal.toString());
//			cadenaSanitizada = sanitizacion.sanitizacion(cadenaCifrada);
			
			//LOGGER.info("cadenaCifrada (Fondos)              : " + cadenaCifrada);
			//LOGGER.info("cadenaSanitizada (Fondos)           : " + cadenaSanitizada);
			
			
			// Encriptar AES CBC 128 bits
			String key = "1M4lYza2hlrEEhoQv2xGMQ5v+wyeGUhCfiQsIqqGSdc=";
			
			cadenaCifrada = AESCBC128bits.encrypt(key, cadenaOriginal.toString());
			cadenaSanitizada = sanitizacion.sanitizacion(cadenaCifrada);
//			String desEncriptado = AESCBC128bits.decrypt(key, encriptado.toString());

			LOGGER.info("Texto encriptado (AESCBC128bits)    : " + cadenaCifrada);
			LOGGER.info("Texto Sanitizada (AESCBC128bits)    : " + cadenaSanitizada);
//			LOGGER.info("Texto desencriptado (AESCBC128bits) : " + desEncriptado);
			
			
			
			responseEncryption.put("encryption", cadenaSanitizada);
			responseEncryption.put("responseStatus", 200);
			responseEncryption.put("responseError", "");
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
			exception.printStackTrace();
			responseError = new HashMap<String, Object>();
			responseError.put("responseStatus", 500);
			responseError.put("responseError", exception.getMessage());
			return new ResponseEntity<>(responseError,HttpStatus.OK);
		}
		
		return new ResponseEntity<>(responseEncryption,HttpStatus.OK);
		
	}
}
