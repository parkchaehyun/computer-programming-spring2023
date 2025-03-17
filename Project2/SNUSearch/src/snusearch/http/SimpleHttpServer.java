package snusearch.http;

import snusearch.io.SearchFileDataModule;
import snusearch.search.SearchModule;
import snusearch.utility.UtilityModule;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleHttpServer {
    public static void main(String[] args) {
        int port = 8080; // If the 8080 port isn't available, try to use another port number.
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Create a new thread to handle the client request
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                System.out.println("Received request: " + request);

                // Extract the path from the request
                String[] requestParts = request.split(" ");
                String path = requestParts[1];

                // Read the HTML file and send it as the response
                if (path.equals("/")) {  // CASE 1: It sends index.html page to the client.
                    String filePath = "src/index.html";  // Default file to serve
                    File file = new File(filePath);  // Replace with the actual path to your HTML files
                    if (file.exists() && file.isFile()) {
                        System.out.println(file.getAbsolutePath());
                        String contentType = Files.probeContentType(Paths.get(filePath));
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: " + contentType);
                        out.println("Content-Length: " + content.length());
                        out.println();
                        out.println(content);
                    } else {
                        // File not found
                        out.println("HTTP/1.1 404 Not Found");
                        out.println();
                    }
                } else {  // CASE 2: It sends a specific data to the client as an HTTP Response.
                    String content = "TBD";

                    if (path.startsWith("/user/")) { // CASE 2-1: It handles user-related requests.
                        // extract id and passwd from request
                        String id = "";
                        String passwd = "";
                        String[] queryParts = path.split("\\?")[1].split("&");
                        for (String queryPart : queryParts) {
                            String[] keyValue = queryPart.split("=");
                            if (keyValue.length == 2) {
                                if (keyValue[0].equals("id")) {
                                    id = URLDecoder.decode(keyValue[1], "UTF-8");
                                } else if (keyValue[0].equals("passwd")) {
                                    passwd = URLDecoder.decode(keyValue[1], "UTF-8");
                                }
                            }
                        }

                        saveRequest(id, path); // saves the request in file

                        boolean operationSuccessful = false;

                        SearchFileDataModule dataModule = new SearchFileDataModule();

                        if (path.startsWith("/user/join")) { // handles user join requests
                            operationSuccessful = dataModule.joinUser(id, passwd);
                            if (operationSuccessful) {
                                content = id + " joined successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "id already exists ";
                                out.println("HTTP/1.1 409 Conflict");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/user/login")) { // handles user login requests
                            operationSuccessful = dataModule.loginUser(id, passwd);
                            if (operationSuccessful) {
                                content = id + " logged in successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "id doesn't exist or wrong password ";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/user/logout")) { // handles user logout requests
                            operationSuccessful = dataModule.logoutUser(id);
                            if (operationSuccessful) {
                                content = id + " logged out successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "You are not logged in or id is wrong";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/user/leave")) { // handles user leave requests
                            operationSuccessful = dataModule.leaveUser(id, passwd);
                            if (operationSuccessful) {
                                content = id + " left successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = id + " doesn't exist or wrong password ";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/user/recover")) { // handles user recover requests
                            operationSuccessful = dataModule.recoverUser(id, passwd);
                            if (operationSuccessful) {
                                content = id + " recovered successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "id doesn't exist or wrong password ";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            }
                        } else { // undefined requests
                            out.println("HTTP/1.1 404 Not Found");
                        }
                    } else if (path.startsWith("/data/")) { // CASE 2-2: It handles data-related requests.
                        String username = "";
                        String query = "";
                        int queryIndex = path.indexOf("?q=");
                        if (queryIndex != -1) {
                            String substring = path.substring(queryIndex + 3);
                            String[] queryParts = substring.split("&user=");
                            if (queryParts.length == 2) {
                                query = queryParts[0];
                                query = query.replace("%20?", "&"); // replaces " ?" with & to fit the query format
                                username = URLDecoder.decode(queryParts[1], "UTF-8");
                            }
                        }

                        saveRequest(username, path); // saves the request in file

                        SearchFileDataModule dataModule = new SearchFileDataModule();
                        content = "";
                        SearchModule searchModule = new SearchModule();
                        if (path.startsWith("/data/search")) { // handles search requests
                            content = searchModule.performSearch(query);
                            if (content != "") {
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "Search failed";
                                out.println("HTTP/1.1 500 Internal Server Error");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/data/save_data")) { // handles data saving requests
                            query = UtilityModule.decodeURLToQuery(query);
                            boolean operationSuccessful = dataModule.saveData(username, query);

                            if (operationSuccessful) {
                                content = query + " saved successfully";
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "Data saving failed";
                                out.println("HTTP/1.1 500 Internal Server Error");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/data/load_data")) { // handles data loading requests
                            content = dataModule.loadData(username);
                            if (content != "") {
                                content = username + "'s data:\n" + content;
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "Data loading failed";
                                out.println("HTTP/1.1 500 Internal Server Error");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/data/load_fri")) { // handles friend's data loading requests
                            content = dataModule.loadData(query);
                            if (content != "") {
                                content = query + "'s data:\n" + content;
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "Friend content loading failed";
                                out.println("HTTP/1.1 500 Internal Server Error");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/data/load_hot")) { // handles hot data loading requests
                            content = dataModule.loadHotData();
                            if (content != "") {
                                content = "Hot data:\n" + content;
                                out.println("HTTP/1.1 200 OK");
                                sendResponse(content, out);
                            } else {
                                content = "Hot content loading failed";
                                out.println("HTTP/1.1 500 Internal Server Error");
                                sendResponse(content, out);
                            }
                        } else if (path.startsWith("/data/load_acc")) { // handles accounts loading requests
                            if (!username.equals("admin")) { // only admin can load accounts
                                content = "You are not admin";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            } else {
                                content = dataModule.loadUsers();
                                if (content != "") {
                                    out.println("HTTP/1.1 200 OK");
                                    sendResponse(content, out);
                                } else {
                                    content = "Accounts loading failed";
                                    out.println("HTTP/1.1 500 Internal Server Error");
                                    sendResponse(content, out);
                                }
                            }
                        } else if (path.startsWith("/data/load_log")) { // handles logs loading requests
                            if (!username.equals("admin")) { // only admin can load logs
                                content = "You are not admin";
                                out.println("HTTP/1.1 401 Unauthorized");
                                sendResponse(content, out);
                            } else {
                                content = dataModule.loadLog();
                                if (content != "") {
                                    out.println("HTTP/1.1 200 OK");
                                    sendResponse(content, out);
                                } else {
                                    content = "Logs loading failed";
                                    out.println("HTTP/1.1 500 Internal Server Error");
                                    sendResponse(content, out);
                                }
                            }
                        } else { // undefined requests
                            out.println("HTTP/1.1 404 Not Found");
                        }
                    } else { // CASE 2-3: It handles undefined requests.
                        // extract username and query from request
                        String username = "";
                        String[] queryParts = path.split("\\?")[1].split("&");
                        for (String queryPart : queryParts) {
                            String[] keyValue = queryPart.split("=");
                            if (keyValue.length == 2) {
                                if (keyValue[0].equals("user")) {
                                    username = URLDecoder.decode(keyValue[1], "UTF-8");
                                }
                            }
                        }

                        saveRequest(username, path);

                        out.println("This is an undefined request");
                        out.println("HTTP/1.1 404 Not Found");
                        sendResponse(content, out);
                    }

                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void saveRequest(String id, String request) {
            SearchFileDataModule dataModule = new SearchFileDataModule();
            String requestString = "[" + id + "] " + "http://localhost:8080" + request;
            dataModule.saveLog(requestString);
        }

        private void sendResponse(String content, PrintWriter out) {
            out.println("Access-Control-Allow-Origin: *");  // Allow requests from any origin
            out.println("Content-Type: application/json");  // application/json would be OK!
            out.println("Content-Length: " + content.length());
            out.println();
            out.println(content);
        }
    }
}
