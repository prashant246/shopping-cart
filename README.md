# shopping-cart
The following functionality are embedded in this service
- Ability to create account with two roles (admin, user) and log in
- Admin should be able to
  - Add items
  - Suspend user
- User should be able to
  - List available items
  - Add items to a cart (if there are items in stock)
  - Remove items from their cart
- Restrict the access to APIs through RBAC mechanism

#### RBAC Implementation
The role based access control is implemented using basic session management.
Everytime user login, a session-id is generated that is used to get the user details and validate its access controler via ROLE it is assigned.

Sessions have an expiry TTL defined after that user has to login again.

### How to run
#### Tech Stack
- Java 17
- Spring boot - 3.1.2
- Maven - 3.9.2
- Database - In Memory
#### Steps to run
- first build the project using `mvn clean build`
- goto /target
- run  `java -jar cart-0.0.1-SNAPSHOT.jar`
#### API contracts
- import the `cartService.postman_collection.json` in postman
- the following APIs are there
  - `createUser` - to create user
  - `updateUser` - to update user details
  - `login` - to login and generate session id
  - `logout` - to logout the user
  - `getUser` - to get the basic user details
  - `suspend-user` - to suspend the user by admin role
  - `add-product` - to add item to the datasource
  - `update-product` - to update the item details
  - `get-cart` - to get cart of the logged in user
  - `add-to-cart` - to add product to the cart of user
  - `remove-from-cart` - to remove product from cart