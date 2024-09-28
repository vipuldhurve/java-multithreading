# Multi Threading
Why we need multi threading?
- <b><i>Performance(Parellelism)</i></b> : We can create an illusion of multiple tasks executing in parallel using just a single core.
- <b><i>Responsiveness(Concurrency)</i></b>  : Applications with user interface like watching movie & increasing volume simultaneously.

### Concurrency - Multitasking
Concurrency allows different parts of progam to make progress independently,
often leading to better resource utilization and improved performance.

<div align="center">
  <img src="https://github.com/vipuldhurve/java-multithreading/blob/main/assets/process%20vs%20thread.jpg" alt="Image" style="display:block; width:60%; height:auto; margin:auto;">
  <p style="position:absolute; bottom:0; text-align:center; width:100%;">Process v/s Thread</p>
</div>

## Process/Context - Instance of application
- A process consists of
  - Metadata like id, priority etc.
  - Files
  - Heap
  - Code
  - Atleast one thread called <b>Main Thread</b>
- A process is a unit of execution or instance of an application.
- Each process is completely isolated from other process that runs on system.
- A process has a heap memory.
- A process can have multiple threads

## Thread
- A thread consists of
  - thread stack : where local variables are stored and functions are executed
  - instruction pointer : Address of the next instruction to execute
  
## Context Switch
- Stop thread 1
- Schedule thread 1 out
- Schedule thread 2 in
- Start thread 2

### Thrashing
Spending more time in management(context switches) than real productive work

### Thread Shceduling
- First Come First Serve
  - if a long thread arrives first it can make UI threads unresposive leading to <b>Starvation</b>.
- Shortest Job First 
  -  If we keep scheduling shorter tasks(like UI threads) first the longer threads that has computations will never be executed.
- OS divides the time into moderately sized pieces called <b>Epoch</b>.
- Dynamic Priority = Static Priority + Bonus
- Using Dynamic priority OS will give preference to interactive threads and will give preference to threads that did not complete in the last epoch preventing Starvation.

### When to prefer Multi Threading
- Prefer if tasks share a lot of data.
- Threads are much faster to create and destroy
- Switching b/w threads in same process is faster(short context switches).

### When to prefer Multi Process
- Security and stability of higher priority as thread share data and a thread can bring down entire app.
- Tasks are unrrelated to each other

### Hyper-Threading
- A single physical core running two threads in parallel is achieved by  having some hardware units duplicated(so that threads can run in parallel) and some harware units are shared.
- We can never run all threads 100 percent in parallel.

### Inherent cost of task parellelization and aggregation
- (1. Dividing task into sub tasks)
- (2. Thread creation and passing subtasks to thread)
- (3. Time between Thread.start() to thread getting scheduled)
- (4. Time until last thread finishes and signals)
- (5. Time until aggregating thread runs)
- (6. Aggregation of the subresults into a single artefact)
<div align="center">
  <img src="https://github.com/vipuldhurve/java-multithreading/blob/main/assets/single%20threaded%20vs%20multithreaded%20latency.png" alt="Image" style="display:block; margin:auto;">
  <p style="position:absolute; bottom:0; text-align:center; width:100%;">This diagram illustrates the single-threaded app latency vs multi-threaded app latency.</p>
</div>
