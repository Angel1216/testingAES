package mx.com.beo.util;

public class Sanitizacion {
	
	public String sanitizacion(String url){
//		StringBuilder sbResponse = new StringBuilder();
//		StringBuilder sbAux = new StringBuilder();
		
		
		
		
//		sbResponse.append(url.replace("=", "%3D"));
//		sbAux.append(sbResponse.toString());
//		
//		sbResponse.setLength(0);
//		sbResponse.append(sbAux.toString().replace("/", "%2F"));
//		
//		sbAux.setLength(0);
//		sbAux.append(sbResponse.toString());
//		
//		sbResponse.setLength(0);
//		sbResponse.append(sbAux.toString().replace("+", "%2B"));
		
//		return sbResponse.toString();
		
		return url.replace("=", "%3D").replace("/", "%2F").replace("+", "%2B");
		
		/*
		 
		 {
	"fechaOperacion":20171201,
	"criterioConsulta":"T",
	"valorConsulta":"CLAVE DE RASTREO",
	"claveSpeiBancoEmisorPago":23242,
	"claveSpeiBancoReceptorPago":43532,
	"NumeroCuentaBeneficiario":1928374650192837,
	"montoOperacion":2500.50
}
		 
		 */
	}

}
