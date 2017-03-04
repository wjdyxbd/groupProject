# GroceryListManager Design Document

## Classes

Grocery will use classes to realize both application data as well as interfacing among data structures.

### Application Classes

* User
* ShoppingList
* Item

### Utility/System Classes

* DataBaseList
* FirstLevelHierachicalList
* SecondLevelHierachicalList


## General Design

`User` is the main class that entails the general application. It is responsible for storing the list of ShoppingList and providing the general contracts between the two, such as `creatNewList`, `renameList`, `deleteList`

A `ShoppingList` realizes the concept of a shopping list. It can be uniquely identified by a system-generated ID on creation. The shopping list will have a name, a dictionary determine the quantity of each item, a dictionary determine weather the item is checked off, a dictionary determine what item of every type is in the shopping list.

A User has zero or more `ShoppingList`s.

A `Item` is a class that realizes all types of items that a customer can add to the shopping list. 

A `ShoppingList` has zero or more `Item`s.

A `ShoppingList` realizes the adding and removing between a `User` and one or more `Item`s. 

A `DataBaseList` realize the list in the database so that search item by name is feasible.

A `DataBaseList` has zero or more `Item`s.

When performing the adding an item  by specifying the name, the system utilizes the `DataBaseList` to determine the item and add the item to the `shoppingList`

When performing the adding an item  by hierachical list, the system utilizes the `firstHierachicalList` and `SecondtHierachicalList` to determine the item and add the item to the `shoppingList`