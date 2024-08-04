package client;

import helper.ServerFacade;
import model.AuthData;
import org.junit.jupiter.api.*;
import server.Server;


import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;

    private static ServerFacade serverFacade;

    @BeforeAll
    public static void init() {
        server = new Server();
        serverFacade = new ServerFacade("127.0.0.1", 8080);
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() throws Exception {
        serverFacade.clear();
        server.stop();
    }

    @Test
    public void goodRegister() throws Exception {
        AuthData returnData = serverFacade.register("User1", "Pw1", "email@email.com");

        assert returnData != null;
    }

    @Test
    public void badRegister() throws Exception {
        AuthData returnData = serverFacade.register("User1", "Pw2", "email@email.com");

        // Register the user twice
        assertThrows(Exception.class, () -> {serverFacade.register("User1", "Pw2", "email@email.com");});

    }

    @Test
    public void goodLogin() throws Exception {
        serverFacade.register("User1", "Pw1", "email@email.com");
        AuthData returnData = serverFacade.login("User1", "Pw1");

        assert returnData != null;
    }

    @Test
    public void badLogin() throws Exception {
        serverFacade.register("User1", "Pw1", "email@email.com");
        assertThrows(Exception.class, () -> {serverFacade.login("User1", "Pw2");});
    }

    @Test
    public void goodLogout() throws Exception {
        serverFacade.register("User1", "Pw1", "email@email.com");
        AuthData returnData = serverFacade.login("User1", "Pw1");

        assertDoesNotThrow(() -> {serverFacade.logout(returnData.authToken());});
    }

}
