# 🛒 Order Processing System (Spring Boot + PostgreSQL)

## 📘 Overview
This is a simple **E-commerce Order Processing backend** built using **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**.

It allows customers to:
- Place new orders with multiple items  
- Retrieve order details  
- Update order statuses (PENDING → PROCESSING → SHIPPED → DELIVERED)  
- Cancel pending orders  
- Automatically move PENDING orders to PROCESSING every 5 minutes  

---

## ⚙️ Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **Lombok** (optional)
- **Jackson** for JSON serialization

---

## 🧩 Features

| Feature | Description |
|----------|--------------|
| 🆕 Create Order | Customers can create orders with multiple items. |
| 🔍 Get Order | Retrieve an order by ID. |
| 📋 List Orders | List all orders or filter by status. |
| 🔄 Update Status | Update an order's status manually. |
| ❌ Cancel Order | Cancel an order (only if PENDING). |
| ⏱️ Auto Job | Background scheduler updates PENDING → PROCESSING every 5 minutes. |

---

**[Google Drive Snapshots Folder](https://drive.google.com/drive/folders/1vDJXoyEJti4BWLwxdV4KhHG2CHzd3n1t?usp=sharing)**
Postman collection is present in the project"Orders.postman_collection"
