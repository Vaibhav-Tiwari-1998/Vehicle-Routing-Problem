# Vehicle-Routing-Problem

Given: A set of loads and an unbounded number of drivers

Drive time between (x1, y1) and (x2, y2) = Euclidean Distance = sqrt((x2 - x1)^2 + (y2 - y1)^2)

Optimization objective:

Total cost = 500 * Number of drivers + total drive minutes

Constraints:
Driver start/end location: (0, 0) \n
Driver hours <= 12

Input Format: ID PICKUP DROPOFF \n
Output Format: List of Load IDs per driver

Class Diagram:

![VRP Class Diagram](https://github.com/Vaibhav-Tiwari-1998/Vehicle-Routing-Problem/assets/76246451/5da59803-fb31-4010-b5e3-b369c5394a2c)

Approach:

1) Create a List of Loads and sort this list as per the distance between Driver start (0, 0) and Load Pickup position
2) While the loads list is not empty pick the first load and check whether we can complete this load if yes remove this load from the list and add its ID to the driver.schedule
3) Now sort the loads list as per the distance between current Load dropoff position and next Loads Pickup position this will give us the nearest point
4) Continue until the load list is empty

Steps to build:

1) Download VRPSolver.java, input1.txt, input2.txt
2) Compile the Java file using the command: javac VRPSolver.java
3) Run the Java file using the command: java VRPSolver {input filename}


