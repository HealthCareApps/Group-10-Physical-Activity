# Group-10-Physical-Activity
Team members: Leanna, Felipe, Quintin, Arielle, and Vinnie. Topic: App for Physical Activity


The app will be a patient/health professional interface, to guide and log the patient at home physical therapy activities. On the server database would be a list of activities/exercises and patients, different activities will be assigned to each patient, and a log of each patient.

On the app side of things, the patient will see directions and videos for the exercise he/she needs to perform, a start and stop button will record when and for how long each exercise was performed and logged on the vista server.

The health worker can follow up when the patient comes for an office visit if the patient has been performing the prescribed exercises, and check on expected progress.

So our only sensor would be a time record of performed exercise.

The story board will be updated changing the term doctor to health professional.


To be added in the app:
- Upload some preset exercise sets to be selected by provider
- Include "Physical" in the name
- Create animations or record videos
- Include provider name and contact info
- Add submit activity done button for each activity
- Track missed exercise "appointments" 

Added 04/04/16	

We aer moving away from storing the information on the VistA database, it apparently is not easily extensible, and although it probably has useable tables, documentation on it is sparse, and we need to speed up development.
We will be using MongoDB on the same server as the VistA install, allowing us to use the new database, but with the possibility of using web services to exchage relevant data with the VistA platform. 

Plans are to also have a simple web interface to allow health care providers to manage the data available to the user.
This approach, also allows for stand alone operation, while allowing for future integration with VistA.


