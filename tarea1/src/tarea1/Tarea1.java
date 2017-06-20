package tarea1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tarea1 {

    public static void main(String args[] ) throws IOException {

    	int port = 8081;
        ServerSocket server = new ServerSocket(port);
        System.out.println("Listening for connection on port "+port+" ....");
        
        
        while (true) {
            try (Socket socket = server.accept()) {
            	/*
            	Map<String, Object> parameters = new HashMap<String, Object>();
                InputStreamReader isr =  new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(isr);
                String line = reader.readLine();
                parseQuery(line, parameters);
                System.out.println("\n\n\n AASDASD \n\n\n");
                System.out.println("\n\n\n"+line+"\n\n\n");
                for (Map.Entry entry: parameters.entrySet()){
                	System.out.println(entry.getKey()+ "  "+ entry.getValue());
                }
                int algo = 0; String asd = "";
                while (!line.isEmpty()) {
                	if(algo == 0) asd = line;
                	System.out.println(line);
                    line = reader.readLine();
                    algo++;
                }
                String[] parts = asd.split(" ");
                //System.out.println(parts[1]);
*/                
                InputStream stream = socket.getInputStream();
                byte[] b = new byte[1024];
                byte[] c = new byte[1024];
                int algo = 0; String asd = "";
                //while(stream.read(b) > 0 && !socket.isInputShutdown()) {
                while(stream.read(b) > 0 && !socket.isInputShutdown()) {
                    System.out.println(new String(b));
                	//if(algo == 0){ System.out.println(new String(c)); c = b.clone();}
                    String[] parts = new String(b).split(" ");
                    asd = parts[1];
                    
                    b = null;
                    b = new byte[1024];
                    System.out.println(asd);
                    algo++;
                    System.out.println(algo);
                }
                //String[] parts = new String(b).split(" ");
                System.out.println("sali del loop");
            	
                if(asd.equals("/home_old")){
                	String httpResponse = "HTTP/1.1 301 Moved Permanently\r\nLocation: /\r\n\r\n";
                	System.out.println("301");
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/secret")){
                	
                	String aux = "403";
                	String httpResponse = "HTTP/1.1 403 Forbidden\r\n\r\n" + renderHtml(aux);
                	System.out.println("403");
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/")){
                	String aux = "index";
                	String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + renderHtml(aux);
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else if(asd.equals("/login")){
                	String aux = "login";
                	String httpResponse = "HTTP/1.1 200 OK\r\nVary: Upgrade-Insecure-Requests\r\n\r\n" + renderHtml(aux);
                	socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                }
                else{
                	
                }
            	socket.close();   

            }
        }
    }
    

    
    public static String renderHtml(String route){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String s = ""; 
        try {
            archivo = new File (route+".html");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            while((linea=br.readLine())!=null)
            		s += linea;
         } 
         catch(Exception e){
             e.printStackTrace();
          }finally{
             try{                    
                if( null != fr ){   
                   fr.close();     
                }                  
             }catch (Exception e2){ 
                e2.printStackTrace();
             }
          }
    	
    	return s;
    	
    }
    
    public static void parseQuery(String query, Map<String, 
    		  Object> parameters) throws UnsupportedEncodingException {

    		         if (query != null) {
    		                 String pairs[] = query.split("[&]");
    		                 for (String pair : pairs) {
    		                          String param[] = pair.split("[=]");
    		                          String key = null;
    		                          String value = null;
    		                          if (param.length > 0) {
    		                          key = URLDecoder.decode(param[0], 
    		                            System.getProperty("file.encoding"));
    		                          }

    		                          if (param.length > 1) {
    		                                   value = URLDecoder.decode(param[1], 
    		                                   System.getProperty("file.encoding"));
    		                          }

    		                          if (parameters.containsKey(key)) {
    		                                   Object obj = parameters.get(key);
    		                                   if (obj instanceof List<?>) {
    		                                            List<String> values = (List<String>) obj;
    		                                            values.add(value);

    		                                   } else if (obj instanceof String) {
    		                                            List<String> values = new ArrayList<String>();
    		                                            values.add((String) obj);
    		                                            values.add(value);
    		                                            parameters.put(key, values);
    		                                   }
    		                          } else {
    		                                   parameters.put(key, value);
    		                          }
    		                 }
    		         }
    		}

}









