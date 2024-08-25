# Group A07 Mini Report


## Objective


In this delivery, we had the objective of being able to enroll students in the secondary server. As this command
requires a writing operation to the server, we had to find a solution to synchronize the primary and secondary server
with one another.


## Our solution to the problem


### Vector clocks


Firstly, we decided to implement vector clocks as a way to guarantee time coherence.


Each client has its own `prev`. Everytime a client reaches to a server, the server sends its own vector clock and
the client replaces his own `prev` for this clock.


If a client reaches a server for a read operation and the server's vector clock is behind the client's, the server will wait for the duration
of a gossip cycle before sending the response. This is done in order to try and make the server receive a more recent
state. If the server doesn't receive a more recent state in this time period, it will just send his own state to the
client.


### Timestamps

Secondly, we decided to implement timestamps for the last time a student had its state modified.


Each student has a new field called `lastModified`, every time a student is enrolled or cancelled, the value of
`lastModified` is updated.


If while merging both server's enrolled students, the end result of the merge has more students than the maximum
capacity, we use these timestamps to guarantee time consistency, this means that only the first N students to enroll
will be enrolled in the class' N slots.


### Merging both servers' states


Thirdly, everytime a propagated server's vector is ahead of the one receiving the state, we have to merge both states.


If a client enters in conflict in this merge (this means it has a different state - `enrolled` or `discarded` - in both
the servers), we check the `lastModified` value and the most recent will be the one to prevail in the merge.


For the `enrolled` merged list, we fill it with the most recent students first, in case the capacity of this class is
exceded, only the more recent ones to enroll will be discarded.


## Conclusion


We are happy with our implementation, as it doesn't cause much strain to the servers in case of many client requests (in 
contrast to forcing a gossip everytime a server is outdated) and also resolves the maximum capacity conflict in a fair
way (choosing the older enrolled student instead of, for example, having a server enrolled list prevail over the one).