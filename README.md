# Customer Report Generator
This generator takes a multiline string containing comma separated data as input and generates below reports:
 - The number of unique customerId for each contractId.
 - The number of unique customerId for each geozone.
 - The average buildduration for each geozone.
 - The list of unique customerId for each geozone.

## Compile, Run and Test
Compile the application:
* `./gradlew assemble`

Run the application:
* `./gradlew run`

 - Provide input string and press enter twice to see the report.

Test the application
* `./gradlew test`

### Sample Input

2343225,2345,us_east,RedTeam,ProjectApple,3445s
1223456,2345,us_west,BlueTeam,ProjectBanana,2211s
3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s
1233456,2345,us_west,BlueTeam,ProjectDate,2221s
3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s

### Sample Output
```
The number of unique customerId for each contractId
-----------------------------------------------------------
    CONTRACT_ID          | UNIQUE_CUSTOMER_IDS_COUNT 
-----------------------------------------------------------
           2346          |                         2 
           2345          |                         3 
-----------------------------------------------------------


The number of unique customerId for each geozone
-----------------------------------------------------------
    CONTRACT_ID          | UNIQUE_CUSTOMER_IDS_COUNT 
-----------------------------------------------------------
        eu_west          |                         2 
        us_west          |                         2 
        us_east          |                         1 
-----------------------------------------------------------


The average buildduration for each geozone
-----------------------------------------------------------
        GEOZONE          |        AVG_BUILD_DURATION 
-----------------------------------------------------------
        eu_west          |                   4222.0s 
        us_west          |                   2216.0s 
        us_east          |                   3445.0s 
-----------------------------------------------------------


The list of unique customerId for each geozone
-----------------------------------------------------------
        GEOZONE          | UNIQUE_CUSTOMER_IDS_COUNT 
-----------------------------------------------------------
        eu_west          |        [3244332, 3244132] 
        us_west          |        [1223456, 1233456] 
        us_east          |                 [2343225] 
-----------------------------------------------------------
```