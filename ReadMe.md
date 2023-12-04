# Assignment 2 Submission - Distributed Algorithm (CSL7920)

## Causal Order Broadcast Simulation

This repository contains the code for the Assignment 2 submission of the Distributed Algorithm course (CSL7920). The assignment involves developing and simulating a causal order broadcast for a distributed system. Additionally, you are required to use this broadcast protocol to calculate the sum of numbers into a single shared variable. The numbers will be read by the agents from files, and each agent will have a different set of numbers in their local files.

## Code Overview

The code provided in this repository is written in Java and utilizes the JBotSim library for simulating distributed systems. The main class of interest is `Counter`, which represents the behavior of agents in the distributed system. Below is an overview of the key components of the code:

- `State` Enumeration: Defines the possible states of an agent, including IDLE, PENDING, PROCESSING, and DONE.

- `counter`: A counter used to track the progress of the agent.

- `LocalArray`: A list of integers representing the local set of numbers for the agent.

- `pendingQueue`: A queue used to store pending messages.

- `SharedSum`: A variable that represents the shared sum of numbers, which agents aim to calculate.

- `localsum`: The sum of numbers in the local set for the agent.

- `NodeID`: The unique ID of the agent.

The code also includes various methods and functions for message handling, processing, and ensuring causal order broadcast, such as `processMessage`, `processWrite`, and `processRead`.

## Getting Started

To run and test the code, follow these steps:

1\. Ensure you have the JBotSim library and dependencies set up in your development environment.
- Declaring your dependencies with Maven.
```
<dependency>
    <groupId>io.jbotsim</groupId>
    <artifactId>jbotsim-all</artifactId>
    <version>1.2.0</version>
</dependency>
```
2\. Clone this repository to your local machine:

```bash

git clone https://github.com/your-username/your-repo-name.git

```

3\. Compile and run the code using your preferred Java development environment or IDE.

4\. Observe the behavior of agents in the simulated distributed system as they perform causal order broadcast and calculate the sum of numbers into a shared variable.

## Usage

The code simulates the behavior of multiple agents, each with a unique set of numbers, working together to calculate the sum of these numbers into a shared variable. You can use this code as a basis for understanding causal order broadcast in distributed systems and for further experimentation and exploration.

## Acknowledgments

This code was developed as part of the Distributed Algorithm course (CSL7920) and is intended for educational purposes.

## Author

- [Gaurav Sangwan](https://github.com/gauravsangwan)

If you have any questions or need further assistance, feel free to reach out to me.

Thank you for reviewing my assignment submission!
