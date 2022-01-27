package com.sparta.projectapi.controllers;

import com.sparta.projectapi.entities.Item;
import com.sparta.projectapi.entities.List;
import com.sparta.projectapi.entities.ListRow;
import com.sparta.projectapi.repositories.ItemRepository;
import com.sparta.projectapi.repositories.ItemTypeRepository;
import com.sparta.projectapi.repositories.ListRepository;
import com.sparta.projectapi.repositories.ListRowRepository;
import com.sparta.projectapi.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/list/create/newlist")
    public ResponseEntity<String> createNewList(@RequestBody List newList, @RequestHeader("Authorization") String usernameAndAuthToken){
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])){
            listRepository.save(newList);
            return new ResponseEntity<>("Blank List Created for User: " + headerParts[1] + ". Please send items to the correct endpoint to add them.", HttpStatus.CREATED);
        } else return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/list/create/newitems/{id}")
    public ResponseEntity<String> createNewListItems(@RequestBody java.util.List<Item> itemsToAdd, @RequestHeader("Authorization") String usernameAndAuthToken){
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])){
            for(Item item : itemsToAdd){
                if (itemTypeRepository.existsByTypeDescription(item.getItemType().getTypeDescription())){
                    itemRepository.save(item);
                } else return new ResponseEntity<>("The Type of Item was not valid in the case of: " + item.getItemName(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("All items successfully added to the list. ", HttpStatus.ACCEPTED);
        } else return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/list/view/{id}")
    public ResponseEntity<String> viewList(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable Integer id){
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            if (listRepository.existsById(id)){
                java.util.List<ListRow> rowsOfList = listRowRepository.getAllByList(listRepository.getById(id));
                return new ResponseEntity<>("All good so far", HttpStatus.I_AM_A_TEAPOT);
            } else return new ResponseEntity<>("The ID you supplied was not found. Please check the ID and try again", HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }
    
    @DeleteMapping("/list/delete")
    public ResponseEntity<String> deleteList(){
        return new ResponseEntity<>("Dummy Message", HttpStatus.I_AM_A_TEAPOT);
    }

}
