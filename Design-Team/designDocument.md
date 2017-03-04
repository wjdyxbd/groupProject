# Design Document

**Author**: Yihe Chen

## 1 Design Considerations

### 1.1 Assumptions

* If the dataBase is unreachable for any reason, any instruction to alternate the shopping lists will fail and halt. The system "voids" the instuction, and logs the instruction as failed.

* The ID of earch user is an integer unique for each user.

* The dataBase holds all the data of GroceryLists, Listitem, items, and itemTypes. Every time the system need the data of these objects, it needs to query the dataBase for the information. On the other hand, if the system needs to make any alternation to the dataBase, it will modify the objects in the dataBase by requesting the modification.

### 1.2 Constraints

* The system must interact with dataBase that requires Internet capabilities and will not function properly without Internet communication.

### 1.3 System Environment

The system will operate on any device capable of supporting Android API Level 15 (Ice Cream Sandwich 4.0.3 or above). This application is intended to run on mobile devices, i.e. cell phones or tablets. However, as the system utilizes the Android platform, it should be capable of running on any device running the Android OS.

The device running shopping list manager System must be be able to communicate with dataBase systems through the APIs provided by the dataBase service provider.

## 2 Architectural Design

### 2.1 Component Diagram

The only user of the system will be the customer. All other users in the shop (i.e. shop manager) have no engagement with the system, as all information that the customer needs to create and modify the shooping list is in the dataBase. 

### 2.2 Deployment Diagram

The Shopping List Management System is installed on any device running any Android package installer. As a private application, it would be to install in two ways:

1. Downloading the APK package, and installing it on the device with Android's Package Manager.

2. Plugging in the device to a computer. Running `adb install` to install APK.

## 3 Low-Level Design

### 3.1 Class Diagram

![class-diagram](./design-team.png "Class Diagram") 

#### 3.1.1 Shopping List Management System

The Shopping List Management System is the main tool for shop customers to editing and maintaining the stuff they are going to purchase. It also provides all the functionality necessary for customer to search, locate items by name and type.

#### 3.1.2 DataBase

The Shopping List Management System communicates with the dataBase in order to retrive and modify the informations concerning the shopping list. The Shopping List Management System must provide the dataBase with data in a format that following the API supported by the dataBase.

Communications trail:
* Query (Shopping List Management System -> DataBase): There are two kinds of queries. 1. Shopping List Management System queries the dataBase for item by name. 2. Shopping List Management System queries dataBase with hierarchical query
* Output (DataBase -> Shopping List Management System): DataBase return the query result to Shopping List Management System.
* Modify (Shopping List Management System -> DataBase): The Shopping List Management System send modification instructions to dataBase. The Shopping List Management System must provide the exact object it is going to modify and the field it is going to modify.
* Output (DataBase -> Shopping List Management System): The dataBase return the result of modification.

## Classes

### System

#### 1.  UserManager

This class is responsible for creating a user in the system. This class will be automatically created once the system starts. 

#### 2. User

The class User is a representation of a customer in the shop. It stores:
	1. UserID
	2. UserName
	3. password
	4. status

#### 3. ListManager

This class is the main class that receives instructions from user and manage the shopping lists.

#### 3. DBManager

This class is used to manage the dataBase.

### Data

#### 1. GoceryList

This class corresponds to an shopping list. It stors the name and items that are in the shopping list. It support following instructions of modifying the list:
	1. addItem
	2. deleteItem
	3. checkOffItem
	4. chekcOffAllItem
	5. lookupItem

#### 2. ListItem

This class corresponds to the item that is added to the shopping list. This class will store the GroceryList in order to know to which the item belongs

#### 3. item

This class corresponds to the items that could be add to the list.

#### 3. itemType

This class corresponds to the itemType.

### Enumerations

#### 1. Unit

Used to determin the ammount of item.

## 5 User Interface Design

|Create User|List Manager|List Modification|
|---|---|---|
|![create-user](./mockups/createUser.png "Create-User")|![list-manager](./mockups/selectList.png "List-Manager")|![list-modification](./mockups/modify.png "List-Modification")|

