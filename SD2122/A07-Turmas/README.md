# Turmas

Distributed Systems Project 2021/2022

## Authors

**Group A07**

### Team Members


| Number | Name              | User                                    | Email                                          |
|--------|-------------------|-----------------------------------------|------------------------------------------------|
| 95579  | Francisco Sousa   | <https://github.com/franciscomcsousa>   | <mailto:franciscomcsousa@tecnico.ulisboa.pt>   |
| 95595  | Jaime Costa       | <https://github.com/Jaime-Costa>        | <mailto:jaime.costa@tecnico.ulisboa.pt>        |
| 95674  | Sara Aguincha     | <https://github.com/SaraAguincha>       | <mailto:sara.aguincha@tecnico.ulisboa.pt>      |

## Getting Started

The overall system is made up of several modules. The main server is the _ClassServer_. The clients are the _Student_,
the _Professor_ and the _Admin_. The definition of messages and services is in the _Contract_. The future naming server
is the _NamingServer_.

See the [Project Statement](https://github.com/tecnico-distsys/Turmas) or a complete domain and system description.

### Prerequisites

The Project is configured with Java 17 (which is only compatible with Maven >= 3.8), but if you want to use Java 11 you
can too, just downgrade the version in the POMs.

To confirm that you have them installed and which versions they are, run in the terminal:

```s
javac -version
mvn -version
```

### Installation

To compile and install all modules run the following command in the root:

```s
mvn clean install
```

### Running
`The flag -debug is optional.`

#### To run the NamingServer:
```s
cd NamingServer
mvn exec:java -Dexec.args="-debug"
```

#### To run the ClassServer:

All clients have been hardcoded with the port 5000 and the host <localhost>.

```s
cd ClassServer
mvn exec:java -Dexec.args="-debug <turmas> <host> <port> <qualifiers>"
```

#### To run the Student:

```s
cd Student
mvn exec:java -Dexec.args="-debug alunoxxxx <student_name>"
```

#### To run the Professor:

```s
cd Professor
mvn exec:java -Dexec.args="-debug"
```

#### To run the Admin:

```s
cd Admin
mvn exec:java -Dexec.args="-debug"
```

## Built With

* [Maven](https://maven.apache.org/) - Build and dependency management tool;
* [gRPC](https://grpc.io/) - RPC framework.
