# Third Meeting

## Date and location
- Tuesday, December 17, 2019 @ 10:00
- A2527 @ Luleå University of Technology

## Purpose
- Example overview of Neo4J  
- Updated Github workflow
- Updated UML
- New user interface for admin
- About the interview with Jonny


## Participants
- Jan van Deventer
- Jesper Nilsson 
- Robin Danielsson
- Tommy Andersson
- Wilma Krutrök
- Johan Rodahl Holmgren
- Marcus Eriksson
- Steffi Knorn
- Damianov Varagnolo

## Summary
We presented the design for user interfaces together with the node structure for the database. The feedback from Steffi and Damianov was to include Intented learning outcome (ilo) to get a better overview of what is needed to learn to get the degree. Ilo would also make it easier for teachers to add the knowledge components because the ilo inlcude knowledge components. Program board members don't talk about knowledge components, instead they make the programs from skills or ilos. Ilos for a program will always be the same, therefore it doesn't matter if the knowledge components is changed as long as the ilos isn't affected. 

They also thought that the drag and drop function should have an alternative to just search for knowledge component and click to add. It should be efficient because teachers won't use the system if it takes too long time. A tree structure for the knowledge components should be good to know how they relate to each other. It would also be good if you could filter on different topics to easier find the knowledge components. They also wanted to know if knowledge components are connected inside a course. 

Before next meeting a git respository with all the information about the project done before will be shared to avoid replicating.

## Meeting notes
Johan explains what we are doing. Well done.
Dami: No drag and drop? No, I don't want to use the mouse.
Steffi: Another thing i could imagine is a list of things like you have there, and the things I have selected will be greyed out.
Dami: Teachers do not want to loose time. The system must be very efficient. 
Steffi: Some sort of tree structure with sub categories and super categories. 
Dami: linear algebra i a KC, but there are many things INSIDE that KC. I would like to see that.
Steffi: Instead of one long list, there should be categories.
Johan continues to explain things about KCs and stuff.
Dami: How are you planning to implement the database?
### Database
Wilma explains what the nodes are.
More on Neo4J
Dami: here i dont see how it scatter. One KC may be required for other KCs. Its a course that produces three KCs, but two of those are required for the third. 
Steffi: I would like to zoom in on the course and see how the KCs relate to each other inside the course. Like, how is LaTeX related to the other things in the same course?
Johan: So like KCs have connections to other KCs?
Steffi: Basically, yes. It might be difficult for students to understand why complex numbers are important. If a KC is impllicitly important for some KC in another course. 
Wilma. We have not decided yet. I dont think it will be clear though, that is not so easy to program. 
Steffi: From the programming side, i woiuld sugest you have a look at how it should be set up in the database.
Johan: Eash KC can relate to another KC. The relationships are just lines between nodes.
### New concept
Dami: I think you are thinking from a wrong perspective. You are forcing relationships between KC and course.
The group gets confused.
Dami: Continues to talk about layers.
Johan: How do you mean upscalable?
Dami: How in this picture would you do it?
Johan: ...
Steffi: there is a difference between the intended learing outcome and what you learn in a ourse. Sometimes you develop something but it is not needed later, so from the program view there are some things the students need to achieve. Sometimes you develop things in a KC that are intended, but some things are not. Do student reach the intended program outcomes. 
Dami: As teachers we talk about KC, as program board member we dont talk about KC, the talk about the skills. The tools must help different people to discuss. 
Steffi: What we need to do is add other layers and other kinds of information. E.g. a set of nodes that are intended learning outcomes and are connected to the knowledge components. 
Johan: example?
Steffi: in compueter sciense the program goal is to identify how you need to set up a program from a real world example. Now this is one quiet important thing. [I lost track after this, sorry]. Simply otherwise we would have too many unintended learning outcomes... There are lots of things you learn, but there are some things that are more important.
Wilma: What is important?
Steffi: I think the swedish covernment decide that. It is not up to you. Someody else have already decided it.
Dami: One for you program is [a lot of words], this is your pgram learning outcome.
Johan: So a program should also have a learning outcome.
Steffi: The learing outcome could be connected to some important KCs. Some things are program learning outcomes, so if I change on of those things it will have a big effect. But if I change something that is not connected to the program learning outcome it wont affect the outcome.

The only thing that should remain the same is the learning outcome of the program. I cannot change that, but I can change the internal structure of some course, as long as the students learn what they need to learn.

Johan and Jesper: one new super node which is the goal of the program. How abut that?
Wilma: I understand you need some things to complete the program, but even if we dont have those things we still complete the program. Who would decide?
Dami: Teachers.
Tommy: What is actually the goal? We thought thta this proram hould be used to motivate students, and easier for teachers to see how their courses affect other courses.
Dami: No, we are discussing representations. It cannot scale ot consider different relations. 
Steffi: It is perfeclty fine, but youi can go further.
Johan: Do I understand it correctly?
Dami: Somebody might want to see if changing a Kc will affect the program outcome.
Johan: for now , this is the structure as we have intended.
Dami: Let me make a proposal, do not replicate things we already have. They should focus on inserting data and working with the database. We already have the visual stuff. The important is how to keep the data in series and make people insert data that explains the relationships.
Jan: What do you have available now?
Dami: We have a github repository. We are much more ahead of [a lot of words],
Jan: ok but if you make that available so we can see what we can do with that.

Dami: We send you the information that we have, then we have a talk in january and the we have time to think about things. Whe n do you have to deliver your project?
Dami: My proposal is to talk the first week in january.

## Action points
- Build some small example of a course in JSON.

## Next meeting
- Wednesday, January 8, 2020 @ 13:00

