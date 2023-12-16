# Parking-System

# Title
Parking system app for booking place with cash transfering

# Description

Introducing our cutting-edge Parking System App, a seamless solution designed to revolutionize the way you reserve and pay for parking spaces. This innovative application simplifies the entire parking experience, offering users the convenience of reserving a spot in advance and facilitating secure cash transfers for a hassle-free transaction.

Key Features:

1. **Effortless Booking:**
   Enjoy the convenience of reserving your parking space with just a few taps on your smartphone. Our user-friendly interface ensures a smooth booking process, allowing you to plan ahead and secure a spot at your desired location.

2. **Real-Time Availability:**
   Stay informed with real-time updates on parking space availability. Our app provides live information on vacant spots, helping you make informed decisions and avoid unnecessary delays.

3. **Secure Cash Transfers:**
   We understand the importance of secure transactions. Our Parking System App incorporates a robust payment gateway, allowing users to transfer cash seamlessly and confidently. Your financial information is protected, ensuring a safe and secure payment process.

4. **Notification Alerts:**
   Receive timely notifications, including booking confirmations and reminders, to enhance your overall parking experience. Stay informed and in control throughout the entire process.

5. **Customizable Preferences:**
   Tailor your parking experience to suit your preferences. Whether it's selecting a specific parking zone, choosing covered or open spaces, or setting duration limits, our app puts you in control of your parking arrangements.

6. **History and Receipts:**
   Keep track of your parking history and access digital receipts for easy expense tracking. The app provides a comprehensive record of your parking transactions, simplifying reimbursement processes for business users.

7. **User-Friendly Navigation:**
   Benefit from integrated maps and navigation features that guide you effortlessly to your reserved parking space. Say goodbye to circling around for an available spot; our app streamlines the entire process for a stress-free experience.

8. **Feedback and Ratings:**
   Share your experiences and contribute to the community by providing feedback and ratings for parking locations. Help fellow users make informed decisions and contribute to the continual improvement of our app.

Embrace the future of parking with our Parking System App – where reservation meets simplicity, and secure cash transfers pave the way for a more convenient and efficient parking experience. Download the app today and enjoy a smarter, more connected way to park.


# Team Members List:
Aruuke
Amira
Raheba

# Roles of Group Members:
Aruuke: Project Manager
        Task Coordination: Aruuke is responsible for overseeing the entire project and ensuring that each team member understands their roles and responsibilities.
        Requirements Analysis: Work closely with stakeholders to gather and document the requirements for the parking system app.
        Timeline and Milestones: Develop a project timeline with milestones to track progress and ensure that the team meets deadlines.
        Communication: Facilitate effective communication within the team and with stakeholders. Provide regular updates on project status.

Amira: Backend Developer (JDBC, Java)
        Database Design: Design the database schema for the parking system, considering factors such as parking spot details, user information, and transaction records.
        JDBC Implementation: Develop the backend logic using Java Database Connectivity (JDBC) to interact with the database, handle data retrieval, and manage transactions.
        Data Validation: Implement robust data validation mechanisms to ensure the integrity and security of the data stored in the database.
        Backend Testing: Conduct thorough testing of the backend functionality to identify and resolve any issues or bugs.

Raheba: Frontend Developer (JavaFX GUI, IntelliJ IDEA)
        JavaFX GUI Design: Design the graphical user interface (GUI) for the parking system app using JavaFX, ensuring an intuitive and user-friendly experience.
        IntelliJ IDEA Development: Utilize IntelliJ IDEA as the integrated development environment (IDE) for coding and designing the frontend components.
        User Interaction: Implement features such as parking space selection, reservation confirmation, and transaction history display within the JavaFX GUI.
        Frontend Testing: Conduct thorough testing of the frontend to ensure that the GUI functions correctly and provides a positive user experience.

Collaborative Tasks: Aruuke, Amira, Raheba
        Integration Testing: Collaborate to perform integration testing to ensure seamless communication between the frontend and backend components.
        User Acceptance Testing (UAT): Coordinate with stakeholders to conduct UAT, gathering feedback and making necessary adjustments to meet user expectations.
        Documentation: Work together to document the codebase, including comments in the code, user manuals, and system documentation.
        Bug Fixes and Maintenance: Collaborate on addressing any bugs or issues identified during testing and provide ongoing maintenance and support.




# Functions in project 
initialize()
updateChoiceParkingPlace()
loadCarModels()
setUserInfo(String username)
updateWelcomeMessage()
updateBalance()
getInitialBalance(String username)
addBalanceButtonClicked()
updateInitialBalance(
subtractFromInitialBalance(
showNotEnoughMoneyAlert()
Add()
checkAndDeleteExpiredRecords(
Delete(
Update(
Connect()

And its all in one file

Overall is 50+ functions





# How to Connect all project
Download the Newparking folder and open with Intellij Idea
add in Project Structures .jar library

Open in pgAdmin 4 db (f.e. name Db "parking-db")
and with .sql file run in order all script

in java.class file find JDBC connection and change the url and password to yours
(by default is jdbc:postgresql:parking-systemdb and password a)

Thats All


# Links for Presentation
https://www.canva.com/design/DAF3BjSvZEE/leuBDKOjRZ8M34FeGJ4NDQ/edit?utm_content=DAF3BjSvZEE&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton
https://t.me/suranovasua







If there would be problem with Jar and Dependies
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>



