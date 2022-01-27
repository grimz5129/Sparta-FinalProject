package com.sparta.projectapi.controllers;

import com.sparta.projectapi.entities.Item;
import com.sparta.projectapi.entities.List;
import com.sparta.projectapi.entities.ListRow;
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
                if (itemTypeRepository.existsByTypeName(values.get("item_type"))) {
                    // I tried a constructor. It had random null references. This works. I have no idea why.
                    Item newItem = new Item();
                    newItem.setItemName(values.get("item_name"));
                    newItem.setItemType(itemTypeRepository.getByTypeName(values.get("item_type")));
                    itemObjects.add(newItem);
                } else return new ResponseEntity<>("One or more items have invalid types", HttpStatus.BAD_REQUEST);
            }
            java.util.List<Item> newItems = new ArrayList<>();
            java.util.List<Item> itemObjectsForRows = new ArrayList<>();
            for (Item item: itemObjects){
                if (!itemRepository.existsByItemName(item.getItemName())){
                    newItems.add(item);
                    itemObjectsForRows.add(item);
                } else {
                    itemObjectsForRows.add(itemRepository.getByItemName(item.getItemName()));
                }
            }
            if (newItems.size() > 0) {
                itemRepository.saveAll(newItems);
            }
            java.util.List<ListRow> listRows = new ArrayList<>(itemObjects.size());
            List addingToList = listRepository.getById(id);
            for (Item item : itemObjectsForRows) {
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
                return new ResponseEntity<>("List and all Rows deleted. ", HttpStatus.ACCEPTED);
            } else
                return new ResponseEntity<>("The list with the specified ID: " + id + " was not found", HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/list/delete/fromlist/{id}")
    public ResponseEntity<String> deleteItemsFromList(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable Integer id, @RequestBody String itemsToDelete) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        String[] itemList = itemsToDelete.split("},");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            java.util.List<ListRow> listRowObjects = new ArrayList<>(itemList.length);
            for (String item : itemList) {
                Map<String, String> values = regexService.parseProperties(item);
                if (itemRepository.existsByItemName(values.get("item_name"))) {
                    if (listRowRepository.existsByItem(itemRepository.getByItemName(values.get("item_name")))){
                        listRowObjects.add(listRowRepository.getByItem(itemRepository.getByItemName(values.get("item_name"))));
                    } else
                        return new ResponseEntity<>("Item with name " + values.get("item_name") + " does not exist in list with ID: " + id, HttpStatus.NOT_FOUND);
                } else
                    return new ResponseEntity<>("One or more items specified do not exist", HttpStatus.BAD_REQUEST);
            }
            listRowRepository.deleteAll(listRowObjects);
            return new ResponseEntity<>("All objects were deleted successfully", HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }


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
        return outMap;
    }

}
