# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAIICuYAFug8ARGjr5CxYKQBKKAOZIAzmCh8kAitUIohDUaQDiwALYpVVdZpFMYAET7AWIEClmzMAExsAjYLJQwX7zJhSUBBs2DAAxGjAVACeMJIy8or8aBEA7pxIYBqIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0bN5QADpoAN6dlFGGADQwuE5p0C5jKPrASAgAvpjGlDBF8dJyCkoqpeqVUNvylAAUg1DDKGMTslNQMzBzCwgAlJgJO8nKaMbqG2K1jAtnsjlklSkKDAAFUuhcutcPsDQQ4nIDfO5KmQAKIAGRxcEaMEu1xgADNgvoSV1MCi7GjZBtNl8kns-gcHJU0GwEAhPiddil-g4MfSwU5KiBjnwUHDzqSDDdxl57tNkTYGeCMX5KgBJAByuPExMVoxVk2ms3mixghsadRptFZQt+Ip8m3FjKlMuy7C4CKGSo1IK16M2urtRpxJqdVyVY2AHE4jQgAGt0FGHTAk1w6ZqJUz8izBT99mouTnk6mM4IXWWORWPZs1hUq1wa+hVqV1sXirkeJUAEwABhHvT6uZT6fQK3QLgCQRCYXCQRQmbxECkIWY4QyWRyyB4GNblSqlnxOMaONabW8Tl+vSnncErYx-u4GEI7MqCC3O7OZ8ZzQD4Px4b8UjFAtvV8FAEGhFAPxfQDq2AkNUW1CMsSsS9rxgAAxcQ6gAWXbTgYAAdQACRjHEyJfGAAF56OA-NQ0LZlijAr8QB-GBZDYQtMG43hIL7EpqEoSoBKEt8+wHDBhzHGAzhkxkPnnRdglCCJjhcGA8R2GA9EMJk90ybJMAU5gWx7Nsqh0K9bykJUn1Q2tu0k2BNhMoxORQX8dl82QUI7NDMF890oPYmCoTAYKznQsMi2KSNcQJIkYAAKgIojSJc0y2Iw8MfKVd1ISVZxIv8ziJLKCrCrkzZrKU8d+gKxw5zQBdAm0ldsDYKBsHg+BfR8Xz0gsw88hs4pT2qeommc1yonc9AJw6g0lRWOTimqpsfRQWVfNC6dazGTbgwisqas9aDwUO46lVOl8LqVLbDCSjisP1aNY3tR0OqZM5Ls+mAADUWDxGEcTIFTQZQL7GVq-aTErDq9Usa7DCi8T5oxrGmv7I9FJgUc2r6Amup6pcdPCGI4L-NIYAAKQgJBUgm8J3AQUA0ysknZrqqSFphFp2g6tywtrCdrLgCA-ygN7DExna7IxVHDhgAArdm0BOoDzvgQX5cV5WUExj5NdFO6YoenW9YNta0DGOWFegc3LaK5KdWwmEaksFg8KB8gr3GXmGagU3oCY42Zuj2BqNomACdjgnve+0qcf86TBMZbG-KbWr5rU8FPLKWqWrJscJ1Lpxqa05cIiCYA4kQI7YGAbBhog35JoPAWZpPOyzwvAlr1vPxy97VLsJAeC8ES1x3Gi4qIRgeeO6Xr1MNnyox6vOjCJIuMAG4cPHo+8rI8+D7w4-8sq0+M+RvGR-4vPwWfomq-JlS69kBpbqQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

### Sequence Diagram

[](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAIICuYAFug8ARGjr5CxYKQBKKAOZIAzmCh8kAitUIohDUaQDiwALYpVVdZpFMYAET7AWIEClmzMAExsAjYLJQwX7zJhSUBBs2DAAxGjAVACeMJIy8or8aBEA7pxIYBqIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0bN5QADpoAN6dlFGGADQwuE5p0C5jKPrASAgAvpjGlDBF8dJyCkoqpeqVUNvylAAUg1DDKGMTslNQMzBzCwgAlJgJO8nKaMbqG2K1jAtnsjlklSkKDAAFUuhcutcPsDQQ4nIDfO5KmQAKIAGRxcEaMEu1xgADNgvoSV1MCi7GjZBtNl8kns-gcHJU0GwEAhPiddil-g4MfSwU5KiBjnwUHDzqSDDdxl57tNkTYGeCMX5KgBJAByuPExMVoxVk2ms3mixghsadRptFZQt+Ip8m3FjKlMuy7C4CKGSo1IK16M2urtRpxJqdVyVY2AHE4jQgAGt0FGHTAk1w6ZqJUz8izBT99mouTnk6mM4IXWWORWPZs1hUq1wa+hVqV1sXirkeJUAEwABhHvT6uZT6fQK3QLgCQRCYXCQRQmbxECkIWY4QyWRyyB4GNblSqlnxOMaONabW8Tl+vSnncErYx-u4GEI7MqCC3O7OZ8ZzQD4Px4b8UjFAtvV8FAEGhFAPxfQDq2AkNUW1CMsSsS9rxgAAxcQ6gAWXbTgYAAdQACRjHEyJfGAAF56OA-NQ0LZlijAr8QB-GBZDYQtMG43hIL7EpqEoSoBKEt8+wHDBhzHGAzhkxkPnnRdglCCJjhcGA8R2GA9EMJk90ybJMAU5gWx7Nsqh0K9bykJUn1Q2tu0k2BNhMoxORQX8dl82QUI7NDMF890oPYmCoTAYKznQsMi2KSNcQJIkYAAKgIojSJc0y2Iw8MfKVd1ISVZxIv8ziJLKCrCrkzZrKU8d+gKxw5zQBdAm0ldsDYKBsHg+BfR8Xz0gsw88hs4pT2qeommc1yonc9AJw6g0lRWOTimqpsfRQWVfNC6dazGTbgwisqas9aDwUO46lVOl8LqVLbDCSjisP1aNY3tR0OqZM5Ls+mAADUWDxGEcTIFTQZQL7GVq-aTErDq9Usa7DCi8T5oxrGmv7I9FJgUc2r6Amup6pcdPCGI4L-NIYAAKQgJBUgm8J3AQUA0ysknZrqqSFphFp2g6tywtrCdrLgCA-ygN7DExna7IxVHDhgAArdm0BOoDzvgQX5cV5WUExj5NdFO6YoenW9YNta0DGOWFegc3LaK5KdWwmEaksFg8KB8gr3GXmGagU3oCY42Zuj2BqNomACdjgnve+0qcf86TBMZbG-KbWr5rU8FPLKWqWrJscJ1Lpxqa05cIiCYA4kQI7YGAbBhog35JoPAWZpPOyzwvAlr1vPxy97VLsJAeC8ES1x3Gi4qIRgeeO6Xr1MNnyox6vOjCJIuMAG4cPHo+8rI8+D7w4-8sq0+M+RvGR-4vPwWfomq-JlS69kBpbqQA)
