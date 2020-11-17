Test project for xDesign

Removed some junk at the end of the csv file because it couldn't be read - assuming this is intended.

This is an intellij project which you can import from github at https://github.com/harpermj66/takehome.git.

The package naming may be confusion because the main package is 'test' - not to confused with src/main/test.

The MunroService class loads the data from the testdata folder and provides the find method for filtering sorting etc.

The HillTopController class provides the api call

SortValidator is a utility function to check field names and structure of sort string.

HillTop is the main model for the project. I didn't need to import all the data but did anyway.

HillTopDTO is the return object which just holds only the required fields to reduce api contention.

Tests are in the standard place under src/test and include api and service layer tests.

I prepared, but left out the optional extra goal because I was short of time.


