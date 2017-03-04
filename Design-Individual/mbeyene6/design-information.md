# Design Information
Requirements

1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).

  * The classes ListManager,  GroceryList, Item and ListItem and their operations are added to realize this requirement. The user can change the quantity of an existing list item by invoking the addItem and deleteItem operations with the respective quantity.
 
2. The application must contain a database (DB) of items and corresponding item types.

  * This requirement is realized by the entity classes Item, ItemType and Unit.

3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.

  * This requirement is realized by classes ItemType, Item and ListItem. The ListItem class is a bridge between a given list and the Item and it has attribute to represent the quantity of the selected Item.

4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.

  * This requirement is realized by the ItemDAO class. The addNewItem and findItem operations of this class are used to satisfy this requirement

5. Lists must be saved automatically and immediately after they are modified.

  * There is no explicit save operation, the save operation will be triggered based on a UI event which invokes the ListDAO saveList operatoin to save the list to the database. This save method will have a cascade effect by which it will automatically save any child object that are associated to a given list.

6. Users must be able to check off items in a list (without deleting them).

  * The checkoffItem operation in the GroceryList class realizes this requirement by updating the checkedOff boolean attribute of the ListItem Class. The ListItem class is composition of List and Item with a checkedOff attribute. 

7. Users must also be able to clear all the check-off marks in a list at once.

  * The checkOffAllItems operaton in the GroceryList class realizes this requirement.

8. Check-off marks for a list are persistent and must also be saved immediately.

  * All state changes on the entity objects of GroceryList, and ListItem are immediately saved. Therefore this requirement is not explicitly represented in the design.

9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).

  * This requirement is an implementation detail, therefore it has no impact on the design directly.

10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.

  * The ListManager class realizes this requirement. The createNewList, renameList and deleteList operations are used to satisfy this requirement.

11. The User Interface (UI) must be intuitive and responsive.

  * This requirement does not have impact on the high level design

