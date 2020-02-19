# Cat Person User Guide
![Screenshot](/docs/Ui.png)
How to raise a boring cat person :cat2:

## Features 

### Adding Tasks 
The cat person can handle record tasks and help keep track of them for you in case you forget them yourself. 
### Marking Tasks as done
When you feel motivated and got something done, you can ask the cat person to mark it as done for some extra dopamine.
### Deleting Tasks
When you realise a task is too impossible to be done or just do not feel like doing it anymore, you can tell the cat person to delete it to stop it from cluttering your screen.
### Scheduling
If you are having a hard time visualizing how your day is going to go, you can ask the cat person to help you visualize schedule of a specific date. The events will be presented in chronological order. Note this feature only supports events with durations.
### Showing Conflicts
When asked to show the schedule of a specific date, if one event starts before the previous event ends, it will be shown in red.

## Usage

### `todo` - Adds a task with description and state only.

Format: `todo TASK_DESCRIPTION`

Example of usage: 

`todo nothing`

Expected outcome:

`Got it ^UωU^ I've added this task:`<br/>
`[T][✘] nothing`<br/>
`Nyow you have 5 tasks in the wist.`

### `deadline` - Adds a task with description, date, state and optional time.

Format: `deadline TASK_DESCRIPTION /by DATE [TIME]`<br/>
Where DATE needs to be in d/M/yyyy format and TIME needs to be in H:mm format.

Example of usage: 

`deadline finish user guide /by 19/2/2020 23:59`

Expected outcome:

`Got it ^UωU^ I've added this task:`<br/>
`[D][✘] finish user guide (by: Wednesday 19 Feb 2020 23:59)`<br/>
`Nyow you have 6 tasks in the wist.`

### `event` - Adds a task with description, date, state, optional time and duration.

Format: `event TASK_DESCRIPTION /by DATE [TIME] [/for DURATION]`

Where DATE needs to be in d/M/yyyy format and TIME needs to be in H:mm format and DURATION needs to be in integer number of hours.<br/>
When DURATION is specified, TIME must have been specified also.

Example of usage: 

`event tea party /at 25/2/2020 13:00 /for 2`

Expected outcome:

`Got it ^UωU^ I've added this task:`<br/>
`[E][✘] tea party (at: Tuesday 25 Feb 2020 13:00 for PT2H)`<br/>
`Nyow you have 7 tasks in the wist.`

### `delete` - Removes a task.

Format: `delete INDEX`

INDEX starts from 1, must not exceed the current number of tasks.

Example of usage: 

`delete 7`

Expected outcome:

`nyoted (^・'ω'・^)  I've removed this task:`<br/>
`[E][✘] tea party (at: Tuesday 25 Feb 2020 13:00 for PT2H)`<br/>
`Nyow you have 6 tasks in the wist.`

### `list` - Lists all current tasks.

Example of usage: 

`list`

Expected outcome:

![List](/docs/List.png)

### `find` - Find tasks with matching substring.
Format: `find SUBSTRING`<br/>

Example of usage: 

`find party`

Expected outcome:

![Find](/docs/Find.png)

### `schedule` - Show schedule of the specified date.
Format: `schedule DATE`

Where DATE needs to be in d/M/yyyy format.

Example of usage: 

`schedule 30/3/2020`

Expected outcome:

![Schedule](/docs/Schedule.png)

### `bye` - Quits the program
Expected outcome:

![Goodbye](/docs/Goodbye.png)

### `no you dont` or `no you don't` - Shows some surprises