Test project for xDesign

Removed some junk at the end of the csv file because it couldn't be read - assuming this is intended.

This is an intellij project which you can import from github at https://github.com/harpermj66/takehome.git.

The project will run on port 8080. 

The API url is http://localhost:8080/api/munro and takes query parameters - category, sort, size, minHeight, maxHeight if required.s

The package naming may cause confusion because the main package ends with 'test' - not to be confused with src/test.

The MunroService class loads the data from the testdata folder and provides the find method for filtering sorting etc.

The HillTopController class provides the api call

SortValidator is a utility function to check field names and structure of the sort string.

HillTop is the main model for the project. I didn't need to import all the data but did anyway.

HillTopDTO is the return object which just holds only the required fields to reduce api contention.

Tests are in the standard place under src/test and include api and service layer tests.

I prepared, but left out the optional extra goal because I was short of time.


