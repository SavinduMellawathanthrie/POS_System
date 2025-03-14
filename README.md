Spring Boot POS System Backend 
This is a Point of Sale (POS) System Backend developed using Spring Boot. It manages inventory, employees, suppliers, customer invoices, and security authentication with JWT.

Features
 Authentication & Security
JWT-based authentication
Role-based access control (ROLE_USER, ROLE_ADMIN, ROLE_MANAGER)
Secure API endpoints
Inventory Management
Clothing and Accessory item management
Supplier management
Purchase stock management
Sales Processing
Customer invoice management
Stock deduction on purchase
Employee Management
Secure login system
Employee CRUD operations
Reports & Analytics (Planned)
Sales reports (daily, weekly, monthly)
Best-selling and slow-moving product tracking
Technology Stack
Spring Boot 3 (Java 21)
Spring Security (JWT-based authentication)
Spring Data JPA (H2 Database - Embedded for now)
Lombok (For reducing boilerplate code)
Setup & Installation
1. Clone the repository
bash
Copy
Edit
git clone https://github.com/your-repo/pos-system-backend.git
cd pos-system-backend
2. Configure Environment Variables
Set up your JWT Secret Key and database configurations:

bash
Copy
Edit
export JWT_SECRET=your-secret-key
3. Build & Run the Project
bash
Copy
Edit
mvn clean install
mvn spring-boot:run
API Endpoints
Authentication
Method	Endpoint	Description
POST	/api/auth/login	Logs in a user and returns a JWT token
Employee Management
Method	Endpoint	Description
GET	/api/employees	Fetch all employees
POST	/api/employees	Create a new employee
Inventory Management
Method	Endpoint	Description
GET	/api/clothing	Get all clothing items
POST	/api/clothing	Create a new clothing item
Customer Invoices
Method	Endpoint	Description
GET	/api/invoices	Fetch all invoices
POST	/api/invoices	Create a new invoice
To-Do List
 Implement Goods Receive Note (GRN)
 Add multi-payment methods (Cash, Card, Digital Wallets)
 Enhance sales reporting & analytics
 Implement customer management features (Loyalty programs, purchase history, etc.)
Contributing
Fork the repository
Create a new branch (git checkout -b feature-branch)
Commit your changes (git commit -m "Added new feature")
Push to your branch (git push origin feature-branch)
Create a Pull Request
License
This project is licensed under the MIT License.
