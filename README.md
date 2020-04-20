# term-proj-client
ENSF 409 Term Project Client

## Group
- Alexa Calkhoven, alexa.calkhoven1@ucalgary.ca
- Radu Schirliu, radu.schirliu1@ucalgary.ca
- Jordan Kwan, jordan.kwan2@ucalgary.ca

## Extra Features
The extra features include:
- A login system so that multiple students may log in, and each see their own separate registrations
- A separate admin system so that admins can create courses, course offerings, students, and pre-requisites
- JUnit tests for the network controllers on the server
- Running the server and client on computers that are not on the same network
- A view of all the students on the admin panel
- Using reflection and method annotations on the server to handle routing of network requests
- MVC architecture for both the client and server

## Building
The project requires no external libraries to build.

## Running
First, the server must be running in order to start the client.  

To run the project, run the `Client` class with no arguments.
This will start a client that tries to connect to a server at `localhost:4200`
  
To specify the address of the server, run the client with two command line arguments,
the host and the port, in that order.