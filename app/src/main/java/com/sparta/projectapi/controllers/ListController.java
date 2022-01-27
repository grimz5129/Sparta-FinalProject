package com.sparta.projectapi.controllers;

import com.sparta.projectapi.entities.Item;
import com.sparta.projectapi.entities.List;
import com.sparta.projectapi.entities.ListRow;
import com.sparta.projectapi.entities.User;
import com.sparta.projectapi.repositories.*;
import com.sparta.projectapi.services.AuthorizationService;
import com.sparta.projectapi.services.RegexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ListController {

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    ListRepository listRepository;
    @Autowired
    ListRowRepository listRowRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemTypeRepository itemTypeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    RegexService regexService;

    @PostMapping("/list/create/newlist")
    public ResponseEntity<String> createNewList(@RequestBody String newListString, @RequestHeader("Authorization") String usernameAndAuthToken) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            Map<String, String> values = regexService.parseProperties(newListString);
            List newList = new List(values.get("list_title"), values.get("list_description"), loginRepository.getByUsername(headerParts[1]).getUser());
            listRepository.save(newList);
            return new ResponseEntity<>("Blank List Created for User: " + headerParts[1] + ". Please send items to the correct endpoint to add them.", HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/list/create/newitems/{id}")
    public ResponseEntity<String> createNewListItems(@RequestBody String itemsToAdd, @RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable int id) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        String[] itemList = itemsToAdd.split("},");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            java.util.List<Item> itemObjects = new ArrayList<>(itemList.length);
            for (String item : itemList) {
                Map<String, String> values = regexService.parseProperties(item);
                System.out.println(values.get("item_type"));
                if (itemTypeRepository.existsByTypeName(values.get("item_type"))) {
                    if (userRepository.existsById(Integer.valueOf(values.get("user_id")))) {
                        itemObjects.add(new Item(values.get("item_name"), itemTypeRepository.getByTypeName(values.get("item_type")), userRepository.getById(Integer.valueOf(values.get("user_id")))));
                    } else
                        return new ResponseEntity<>("Cannot add items for the given user. Try again", HttpStatus.BAD_REQUEST);
                } else return new ResponseEntity<>("One or more items have invalid types", HttpStatus.BAD_REQUEST);
            }
            itemRepository.saveAll(itemObjects);
            java.util.List<ListRow> listRows = new ArrayList<>(itemObjects.size());
            List addingToList = listRepository.getById(id);
            for (Item item : itemObjects) {
                listRows.add(new ListRow(addingToList, item));
            }
            listRowRepository.saveAll(listRows);
            return new ResponseEntity<>("All items successfully added to the list. ", HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/list/view/{id}")
    public ResponseEntity<?> viewList(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable Integer id) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            if (listRepository.existsById(id)) {
                java.util.List<ListRow> rowsOfList = listRowRepository.getAllByList(listRepository.getById(id));
                return new ResponseEntity<>(buildOutputMapList(rowsOfList), HttpStatus.I_AM_A_TEAPOT);
            } else
                return new ResponseEntity<>("The ID you supplied was not found. Please check the ID and try again", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/list/delete/wholelist/{id}")
    public ResponseEntity<String> deleteList(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable Integer id) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            if(listRepository.existsById(id)){
                java.util.List<ListRow> rowsOfList = listRowRepository.getAllByList(listRepository.getById(id));
                listRowRepository.deleteAll(rowsOfList);
                listRepository.deleteById(id);
                // Potential change for assignment, to remove
                return new ResponseEntity<>("List and all Rows deleted. ", HttpStatus.ACCEPTED);
            } else
                return new ResponseEntity<>("The list with the specified ID: " + id + " was not found", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/list/delete/fromlist/{id}")
    public ResponseEntity<String> deleteItemsFromList(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable Integer id, @RequestBody String itemsToDelete){
        String[] headerParts = usernameAndAuthToken.split(" ");
        String[] itemList = itemsToDelete.split("},");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            java.util.List<ListRow> listRowObjects = new ArrayList<>(itemList.length);
            for (String item : itemList) {
                Map<String, String> values = regexService.parseProperties(item);
                if (listRowRepository.existsByItem(itemRepository.getById(Integer.valueOf(values.get("id"))))){
                    listRowObjects.add(listRowRepository.getByItem(itemRepository.getById(Integer.valueOf(values.get("id")))));
                } else
                    return new ResponseEntity<>("List row for item with id: " + id + " was notn ")
            }
        }
    } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);


    private java.util.List<Map<String, String>> buildOutputMapList(java.util.List<ListRow> rowList) {
        return rowList.stream()
                .map(k -> buildSingleOutputMap(k.getItem()))
                .toList();
    }

    private Map<String, String> buildSingleOutputMap(Item item) {
        Map<String, String> outMap = new HashMap<>();
        outMap.put("id", item.getId().toString());
        outMap.put("itemName", item.getItemName());
        outMap.put("itemType", item.getItemType().getTypeName());
        outMap.put("user", item.getUser().getName());
        return outMap;
    }

}
