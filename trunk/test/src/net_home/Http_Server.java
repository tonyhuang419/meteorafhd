package net_home;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class Http_Server {
	public static void main(String[] args) throws Exception{
		HttpServerProvider httpServerProvider = HttpServerProvider.provider();
		InetSocketAddress addr = new InetSocketAddress(7778);
		HttpServer httpServer = httpServerProvider.createHttpServer(addr, 1);
		httpServer.createContext("/myapp/", new MyHttpHandler());
		httpServer.setExecutor(null);
		httpServer.start();
		System.out.println("started");
	}

	static class MyHttpHandler implements HttpHandler{
		public void handle(HttpExchange httpExchange) throws IOException {          
			String response = "Hello world!";
			httpExchange.sendResponseHeaders(200, response.length());
			OutputStream out = httpExchange.getResponseBody();
			out.write(response.getBytes());
			out.close();
		}  
	}
}
