CLOUDSHOT 

==========================================================================================================
SET-UP
==========================================================================================================
Our game, Cloudshot, uses libGDX, an external project that uses Gradle to manage dependencies and build itself.

We have provided a step-by-step list to build Cloudshot on your own computer. 

Instruction sets are for Windows, Mac, and the ECS environments.


	---------------------------
	-------INSTRUCTIONS--------
	---------------------------

+++++++++++++++++++++++++
For WINDOWS, MAC, AND ECS (**for specialised ECS setup, follow steps 1-3 here and look at end)
+++++++++++++++++++++++++

1) Go to our repo: https://gitlab.ecs.vuw.ac.nz/swen222-2017-p1-t14/Cloudshot

	Where it says SSH, switch to HTTPS and copy the link.

2) Now go to wherever you want to save it on your computer. Inside the folder, shift-right-click and select
	"Open Command Prompt here" or "Open terminal here". If on ECS, you can navigate to the directory and open terminal with F7, or cd there.

3) type git clone <our HTTPS repo link you just copied>      
	This is assuming you have git installed. If not, install git, just search git on google.

	
4) Now open up your favourite IDE:

**IntelliJ**
	Follow the instructions at: https://www.jetbrains.com/help/idea/gradle.html#gradle_import
	Close any active projects, and choose Import Project option -> go to step 2 in the tutorial link

	Now this might not work...but I think this is the correct way to do it. What you should do is in step 4 of the
	tutorial in the link, you need to select:
		"Use local Gradle distribution"
	and in the file space right underneath, choose your cloudshot folder and select the .gradle folder
	
	If this.gradle folder does not exist, try using the bundled Gradle option.


	This will take a min or two to set up even on fast internet, and be sure to let any Java or whatever prompts
	permission to act through Windows Firewall (preferably on a private connection haha)


**Eclipse**
	-You're lucky, because Gradle was built to work with Eclipse easier.

	@Main tutorial: https://libgdx.badlogicgames.com/documentation/gettingstarted/Importing%20into%20IDE.html#eclipse
		 and if it doesn't work, use the instructions below in conjunction with this main tutorial
	
	-Install this plugin: https://marketplace.eclipse.org/content/buildship-gradle-integration

		using this tutorial: https://www.tutorialspoint.com/eclipse/eclipse_install_plugins.htm

	-Then follow this answer:

		https://stackoverflow.com/a/32089444

	This will take a min or two to set up even on fast internet, and be sure to let any Java or whatever prompts
	permission to act through Windows Firewall (preferably on a private connection haha)

5) To run, use these instructions:

**INTELLIJ

	https://libgdx.badlogicgames.com/documentation/gettingstarted/Running%20and%20Debugging.html#running-desktop-project-in-intellij-android-studio

**ECLIPSE

	https://libgdx.badlogicgames.com/documentation/gettingstarted/Running%20and%20Debugging.html#running-desktop-project-in-eclipse

-------------------------------------------------------------------------------------------------------------------------------
	*******
	**ECS**
	*******
-ECS requires some specialised set-up first. First things: upon cloning the file, a .gradle file will be created at $HOME.

1) Please first delete your just-cloned Cloudshot folder, then navigate to $HOME and open this .gradle directory.
2) Delete all files and folders inside.
3) Create a file named: gradle.properties, and put these lines inside:


---START DO NOT COPY THIS LINE-----------------

org.gradle.daemon=true
org.gradle.jvmargs=-Xms128m -Xmx512m -XX\:MaxPermSize/=512m
org.gradle.configureondemand=true

#HTTP proxy
systemProp.http.proxyHost=www-cache.ecs.vuw.ac.nz
systemProp.http.proxyPort=8080
systemProp.http.proxyUser="username"
systemProp.http.proxyPassword="password"
#HTTPS proxy
systemProp.https.proxyHost=www-cache.ecs.vuw.ac.nz
systemProp.https.proxyPort=8080
systemProp.https.proxyUser="username"
systemProp.https.proxyPassword="password"

----END DO NOT COPY THIS LINE---------------

except replace "username" with your ECS username and password with...you guess.
(in case you cannot find these lines, they will be included on our Cloudshot repo, inside the main directory's gradle.properties)


4) Set up a proxy for your fav IDE: 

In eclipse try setting the proxy settings.
Window -> Preferences -> General -> Network Connections
Set Active Provider: to Manual then edit http
Add Host: www-cache.ecs.vuw.ac.nz port: 8080
Tick Requires Authentication and enter your username & password

In Intellij you also have to set the proxy settings

Settings -> Appearance & Behavior > System Settings -> HTTP Proxy
Then same as Eclipse.

5) Finally, reclone Cloudshot into your desired directory and return and start following the main ReadMe from step 4 above (before ECS).


-------------------------------------------------------------------------------------------------------------------------------





