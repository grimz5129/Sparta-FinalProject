package com.sparta.projectapi;

import com.sparta.projectapi.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class ListControllerTests {
    private String newListResponse;
    private String noAuthResponse;
    private String addItemResponse;
    private String viewListResponse;

    @Test
    @DisplayName("POST check list create response")
    public void checkCreateListResponse() throws IOException, URISyntaxException, InterruptedException {
        String username = "Yefri51";
        newListResponse = RequestFactory.listRequest("/list/create/newlist",username, "BqwkosRkCpavC3cvgUpweXLsYHUWVC1pi1yP6zrn","POST","NewList.json");
        Assertions.assertTrue(newListResponse.equals("Blank List Created for User: " + username + ". Please send items to the correct endpoint to add them."));
    }

    @Test
    @DisplayName("POST no auth list response")
    public void checkNotAuthResponse() throws IOException, URISyntaxException, InterruptedException {
        String username = "Yefri51";
        noAuthResponse = RequestFactory.listRequest("/list/create/newlist",username, "BqwkosRkCpavC3cvgUadwadwaWVC1pi1yP6zrn","POST","NewList.json");
        Assertions.assertTrue(noAuthResponse.equals("You are not authorized for this page, Check your username or token and try again."));
    }

    @Test
    @DisplayName("POST add item response")
    public void addItemToListResponse() throws IOException, URISyntaxException, InterruptedException {
        String username = "Yefri51";
        int id = 1;
        addItemResponse = RequestFactory.listRequest("/list/create/newitems/" + id, username, "BqwkosRkCpavC3cvgUpweXLsYHUWVC1pi1yP6zrn","POST","AddItemToList.json");
        System.out.println(addItemResponse);
        Assertions.assertTrue(addItemResponse.equals("All items successfully added to the list. "));
    }

    @Test
    @DisplayName("GET view login response")
    public void viewListResponse() throws IOException, URISyntaxException, InterruptedException {
        String username = "Yefri51";
        int id = 1;
        viewListResponse = RequestFactory.listRequest("/list/view/" + id, username, "BqwkosRkCpavC3cvgUpweXLsYHUWVC1pi1yP6zrn","GET",null);
        Assertions.assertTrue(viewListResponse.equals("[{\"itemName\":\"Docker\",\"itemType\":\"Application\",\"id\":\"1\"},{\"itemName\":\"Ruby\",\"itemType\":\"Package\",\"id\":\"2\"}]"));
    }

}
