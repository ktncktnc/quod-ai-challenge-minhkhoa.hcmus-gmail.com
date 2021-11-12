# HealthScoreCalculator 
## Dependencies
While developing project, I used some external libraries, include:
- `gson`: used to convert object to json and from json to object.
- `org.json`: used to parse json from gharchive.org to object.
- `sqlite`: a simple database store data.

## Compile and run
- `git clone https://github.com/ktncktnc/quod-ai-challenge-minhkhoa.hcmus@gmail.com`
- `cd quod-ai-challenge-minhkhoa.hcmus@gmail.com`
- Check health score last hour: `gradle run`
- Check health score in a range: `gradle run --args="2021-11-12T00:00:00Z 2021-11-13T06:00:00Z"`

## Technical decisions
### What frameworks/libraries did you use? What are the benefits of those libraries?
- I choose `gson` and `org.json` because they simplify json handling and easy to use.
- Sqlite is chosen to store, insert and get data easily. Moreover, it can run immediately without external installation.
### How would you improve your code for performance?
- At the first sight, I stored all the data in a hash map instead of a database. The program ran fastly if the data isn't too large.
- When the data become larger, hash map became a bad choice and moreover, the data will be clean out when program is close. I decide to use gson library and convert all object to json and store each object in a file.
Quickly, I realise that there are too many files (more than 100000).
- I decide to use database last because I haven't developed any program in Java with sql database. At my last job, I store data using gson and an internal framework.
- Using SQLite, it's easier to manage data. But I store all my data in a table, which makes program too slow to run even for one-hour data. To improve performance, I would like to redesign my database with reasonable data structure.
- Google BigQuery is a great solution to improve speed because we mustn't download and unzip, parse data.
### What code would you refactor for readability and maintainability?
- To make my code readability and maintainability, I would like to split type of object (Event.Type, IssuePayload.Type,...) to a new folder. Moreover, I want to rearrange my class.