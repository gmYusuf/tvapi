# tvapi

TV SCHEDULE API
  First application retrieving TV schedule one week(start today) in Great Brittain with tvmaze.


Run Command:
	java -jar target/tvschedule-1.0-SNAPSHOT.jar server config.yml

GET    
	http://localhost:8800/tvprograms
POST    
	pust http://localhost:8800/tvprograms
		json example
			{
			    "name": "xxx",
			    "channel": "BBC",
			    "aired": "2020-01-26",
			    "startTime": "09:35",
			    "endTime": "09:45",
			    "airTime": "10",
			}
PUT    
	put http://localhost:8800/tvprograms
		json example (id is dynamic randomly generated, after getting list, we can copy program id then update)
			{
			    "name": "Breakfast",
			    "channel": "BBC",
			    "aired": "2020-01-26",
			    "startTime": "07:35",
			    "endTime": "07:45",
			    "airTime": "10",
			    "id": "2e460ad1-b4a6-42a4-bef3-77dfa3d463a0"
			}
GET    
	http://localhost:8800/tvprograms/date/2020-01-26
GET    
	http://localhost:8800/tvprograms/name/bbc
DELETE   (id is dynamic randomly generated, after getting list, we can copy program id then update)
	http://localhost:8800/tvprograms/862eda27-49cd-4e30-97ba-855b5a9361b8
