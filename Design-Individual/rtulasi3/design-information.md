Requirements Realization:

1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).
Realization: To realize this requirement, I added to the design a class "GroceryList" with attributes "itemName" and "itemQty". Class "GroceryList" has operations "addItem()", "deleteItem()" and "updateItem()" that perform operations to add items to the list, delete items from the list and change/update the quantitiy of items in the list. I have added a "User" class that handles user login functionality for the application. Attributes "userId", "userName", "password" and "status" have been added to the "User" class with operation "validateLogin()". Every user could have multiple grocery lists and this is represented by the aggregation relation. I have also added "GroceryStore" class with attributes "storeId" and "storeName" as a placeholder for any future requirements.

2. The application must contain a database (DB) of items and corresponding item types.
Realization: To realize this requirement, I have added to the design "ItemDao" data access object class that interacts with the database table "TBL_ITEM" to manage item related operations including fetching and saving items added to the list to the database using operation "manageItems()". "ItemDao" class has attributes "itemId","itemType", "itemName" and "itemStatus". "itemId" uniquely identifies the item and serves as the primary key for "TBL_ITEM". "itemStatus" flags the item as "Active" or "Inactive". This field could be used to expand on grocery list manager functionality in future. "ItemDao" class has an association to "GroceryList" class.

3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.
Realization: To realize this requirement, I have added attributes "itemName" and "itemType" to class "GroceryList". The items will be grouped by item type and the item will be available to pick based on item type selected. Attribute "itemQty" is used to store the quantity specified for the item. This is a string value considering different unit of measure that can be typed in.

4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type.
Realization: To realize this requirement, I have added operation "lookupItem()" to "GroceryList" class that uses "itemDao" class to lookup the item specified by the user in the database. Attribute "itemExists" is used to store the boolean result of "lookupItem()" operation that lets the user know if a match for the item specified is found or not. If a match is not found, the user types in name and type of the item that is stored in "itemName" and "itemType" attributes. Operation "addItem" is used to save this new item to the database via "itemDao" class.

5. Lists must be saved automatically and immediately after they are modified.
Realization: To realize this requirement, I have added "itemModified" boolean attribute and operation "saveList" to "GroceryList" class. For any list modifications, the attribute "itemModified" is updated triggering "saveList" operation.

6. Users must be able to check off items in a list (without deleting them).
Realization: To realize this requirement, I have added attribute "itemCheckedOff" boolean attribute to class "GroceryList". This will help keep track of items in the list that are checked off.

7. Users must also be able to clear all the check-off marks in a list at once.
Realization: To realize this requirement, I have added operation "clearAllCheckedOffItems" to class "GroceryList" that will reset all check-off marks in the list. 

8. Check-off marks for a list are persistent and must also be saved immediately.
Realization: To realize this requirement, I have added "itemCheckedOff" attribute in "GroceryList" class that tracks the check-off marks. This triggers the operation "saveList()" that saves the check-off marks to "TBL_GROCERYLIST" in the database for persistence.

9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).To realize this requirement
Realization: To realize this requirement, I have added operation "viewSortedList()" to class "GroceryList". This method groups the items in the list by type and presents the sorted result.

10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.
Realization: To realize this requirement, I have added to the design "GroceryListAdministrator" class with attributes "listId" and "listType". This class is used by "GroceryList" class. Atrribute "listType" keeps track of list type - weekly, monthly lists etc. "GroceryListAdministrator" class has operations "createList()", "renameList()", “selectList()” and "deleteList()" that are used to create, rename, select list for viewing and delete lists. 

11. The User Interface (UI) must be intuitive and responsive.
Realization: This requirement is not considered at this time as it does not affect the design directly. This will be considered as part of UI design and performance considerations for the application.

