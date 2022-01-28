# Sparta-FinalProject

This is the final Sparta project


## Authentication

Endpoints that require authentication are expecting the HTTP ```Authorization``` header to be included with the request. It should have the value ```Basic <username> <auth_token>``` where ```<username>``` is the username used for logging in the current user and ```<auth_token>``` is the corresponding 40 character alphanumeric access token for this account.

## Users

There are two endpoints related to user management, with a base URL of ```/user```:

### /create
- Request Type: ```POST```
- Authentication Required: NO
- Request Body: YES    
  - Body Content should consist of JSON with the following format
    ```json
       {
         "name" : "Test User 1"
       }
    ```
- Parameters: NONE

### /delete
- Request Type: ```DELETE```
- Authentication Required: YES
- Request Body: NO
- Parameters
  - URL variable representing the ID of the user that you want to delete (e.g. 1)

## Login

There are three endpoints relates to login, with a base URL of ```/login```

### /check
- Request Type: ```POST```
- Authentication Required: NO
- Request Body: YES    
  - Body Content should consist of JSON with the following format
  ```json
    {
        "id": 1,
        "username" : "ATestUser",
        "password" : "Password123"
    }
  ```
- Parameters: NONE

### /register
- Request Type: ```POST```
- Authentication Required: NO
- Request Body: YES    
  - Body Content should consist of JSON with the following format
  ```json
    {
        "id": 1,
        "username" : "ATestUser",
        "password" : "Password123"
    }
  ```
- Parameters: NONE

### /delete
- Request Type: ```DELETE```
- Authentication Required: YES
- Request Body: YES
- Body Content should consist of JSON with the following format
  ```json
    {
        "id": 1,
        "username" : "ATestUser",
        "password" : "Password123"
    }
  ```
- Parameters: NONE

## List

There are 5 endpoints with the base URL of ```/list```

### /create/newlist
- Request Type: ```POST```
- Authentication Required: YES
- Request Body: YES    
  - Body Content should consist of JSON with the following format
    ```json
      {
          "list_title" : "Example List",
          "list_description" : "The Test List"
      }
    ```
- Parameters: NONE

### create/newitems
- Request Type: ```POST```
- Authentication Required: YES
- Request Body: YES    
  - Body Content should consist of an array of JSON objects with the following format
    ```json
      {
          "item_name" : "Docker",
          "item_type" : "Application"
      }
    ```
    Supported values for ```item_type``` are ```"Application", "Package" and "Component"```
- Parameters: URL Parameter of the id of the List that you are trying to add these items to.

### /view
- Request Type: ```GET```
- Authentication Required: YES
- Request Body: NO
- Parameters: URL Parameter of the id of the List that you are trying to view.

### delete/wholelist
- Request Type: ```DELETE```
- Authentication Required: YES
- Request Body: NO
- Parameters: URL Parameter of the id of the List that you are trying to delete.

### delete/fromlist
- Request Type: ```DELETE```
- Authentication Required: YES
- Request Body: YES    
  - Body Content should consist of an array of JSON objects with the following format
    ```json
      {
          "item_name" : "Docker"
      }
    ```
- Parameters: URL Parameter of the id of the List that you are trying to delete these items from.
