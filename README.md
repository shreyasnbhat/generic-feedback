# generic-feedback
The main goal of the project was to enable professors to take feedback from the students for any sort of Program/Course. 
The project consists of two major components an android app and an analyzer script.
The analyzer script is responsible to make graphs and fact-sheets of the feedback that has been obtained which could be later on used in program reports.

The app is built mainly on Android Studio Platform and have used Java and XML.
The online Database used was Firebase by Google and the database is stored in the JSON format.
The Analyzer script is mainly written in Python and the data-analysis is done mainly using libraries such as numpy, pandas, matplotlib etc.

## Setup
For the analyzer script to get working we need to setup the development environment on a PC/Laptop. 
Required Software:
-	Anaconda for Python 2.7
-	Any IDE for Python(Optional)
-	python-firebase package ( https://pypi.python.org/pypi/python-firebase/1.2 )

After the installation of Anaconda for Python 2.7 we can access the conda environment in our command line or the linux terminal.
If you are using Windows/Linux:
1.	Just type `Windows key + “R”` for windows or  `Ctrl + Alt + “T”` for linux
2.	Type `cmd` on the run window(Windows only)
3.	The command prompt of windows will start
4.	For test, type `conda --version` 
5.	You should see something like: `conda 4.2.9`
6.	It is always good if you are starting to use Anaconda to update all the bundle with: `conda update conda` or `conda update --all` or `conda update anaconda` (I prefer the last one because it keeps your packages versions synced with the Anaconda official release)

To install `python-firebase` we need to open cmd in Windows 10 or the terminal in ubuntu and type 
`pip install python-firebase`.
For the python IDE I would recommend using PyCharm by JetBrains. 

Before we move on to running the program we need to ensure all proxy settings have been configured as the python script requests data from the cloud.
After all this is set -up we just need to run the file `FirebaseRetreiver.py`. To run this file, open the terminal and navigate to the directory where this file exists. Now type 
`python FirebaseRetreiver.py` in the command line.
