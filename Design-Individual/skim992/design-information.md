Design Information

* Assumption - The User is not considered as a class. Based on the requirements, there are no
  special characteristics for a "User" other than it is the entity using the application. 

1. To realize this requirement, I added to the design a List class, a List Item class, and an Item class.
   The list class has an attribute of a collection of ListItems which represents an item and some additional
   attributes. The List class has operations that will add items and delete items from the collection. The
   List Item has an attribute for quantity and metric which represents the "quantity" of an item in the list.
   The metric attribute is there to specify which unit of measurement to use and can be used in conjunction
   with the quantity attribute to create cases like "two pounds of apples". Basic getter and setter functions
   can be used to update these attributes.

2. To realize this requirement, I added to the design an object to represent the database and a database
   manager class which will communicate between the other classes and the database itself. The database will
   have a collection of lists, list items, items, and item types. The database manager supports CRUD functionality
   for these different types of objects.

3. To realize this requirement, the List class has an addListItem operation that will add items to the list.
   The hierarchical list can be created by getting all items from the database manager and filtering based on
   ItemType, an attribute of Item. Adding an item to the list can be done by creating a ListItem with a specified
   quantity.

4. To realize this requirement, the List class has a getItemsByName operation, which returns a list of items
   similar to the name provided. Adding a new item can be done through the database manager. A list of all
   item types can be retrieved by the database manager for selection. A new item can be created with a name and
   selected itemtype through the database manager.

5. To realize this requirement, the database manager can be used to save lists once modified.

6. To realize this requirement, the listitem class has an attribute named checked to indicated whether an item
   is checked or not. This attribute can be updated using a basic setter.

7. To realize this requirement, the list class has a clearAllChecked operation that can
   iterate through all its listitems and modified its checked attribute.

8. To realize this requirement, the list class and listitem class are persisted in the database.
   Once a listitem's checked attribute is updated, the database manager will update the database.

9. To realize this requirement, the list class has an attribute of items that is a map. The key
   is the itemtype and the value is an array of listitems. This collection is now able to group
   listitems by itemtype and can be displayed based on itemtype.

10. To realize this requirement, I added to the design a ListManager class that has an attribute
    of an array of List objects. This class manages the lifecycle of the Lists in its collection.
    It has operations to create, rename, select, and delete lists. The list class has an attribute
    of name to be used to identify the list.

11. Not considered because it does not affect the design directly. The design is however built in a way
    to provide persistence and fetching in a matter that will be responsive through the database manager
    class managing the communication between the other classes and database. An intuitive UI can also be
    built using the operations of the classes.
