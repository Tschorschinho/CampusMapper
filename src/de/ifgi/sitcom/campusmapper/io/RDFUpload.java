package de.ifgi.sitcom.campusmapper.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.xerces.impl.dv.util.Base64;

import de.ifgi.sitcom.campusmapper.exceptions.UploadException;

import android.util.Log;

/*
 * upload of sparql/ rdf data to lodum triple store
 */
public class RDFUpload {

	/************* server path ****************/
	private static final String UPLOAD_SERVER_URI = "http://data.uni-muenster.de:8080/openrdf-workbench/repositories/indoormapping/update";
	
	private static final String CONTEXT_URI = "<http://data.uni-muenster.de/context/indoormapping>";

	public void uploadRDF(String rdfString) throws UploadException{

		String updateScript = "INSERT DATA { GRAPH " + CONTEXT_URI + " { " + rdfString +"} }";
		Log.v("updateScript", updateScript);

		// Construct data
		String data = null;
		try {
			data = URLEncoder.encode("update", "UTF-8") + "="
					+ URLEncoder.encode(updateScript, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new UploadException();
		}

		
		// Send data
		try {
			URL url = new URL(UPLOAD_SERVER_URI);
			URLConnection conn = url.openConnection();
			
			String userpass = "username:password"; // username:password
					String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
					conn.setRequestProperty ("Authorization", basicAuth);

			// Allow Outputs
			 conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				Log.v("response", line);
			}
			
			// close streams
			wr.close();
			rd.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new UploadException();
		}


	}


}
