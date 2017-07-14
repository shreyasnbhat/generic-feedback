from firebase import firebase
import json
import pandas as pd
import numpy as np
import seaborn as sns
import matplotlib.pyplot as plt
from utils import *
import os

firebase = firebase.FirebaseApplication('https://feedback-9e534.firebaseio.com', None);
form_id = 0

if not os.path.exists("Graphs"):
    os.makedirs("Graphs")

if not os.path.exists("Submissions"):
    os.makedirs("Submissions")

if __name__ == '__main__':

    result = firebase.get('/Submissions/'+str(form_id), None)

    '''Save result in a file'''
    with open('Submissions/submissions.json', 'w+') as outfile:
        json.dump(result, outfile)

    json_result = json.dumps(result)

    counter = 0
    columns = []

    '''Store data in DataFrame'''
    for element in result:
        for key in result[element]:
            for sub_keys in result[element][key]:
                columns.append(sub_keys)
            break

    columns = set(columns)
    columns = list(columns)
    dataframe = pd.DataFrame(columns=columns)
    counter = 0
    print columns

    print ""
    print "Printing Element Wise Result"
    print ""
    for element in result:
        print element
        print result[element]
        for submission_day in result[element] :
            dataframe.loc[counter] = result[element][submission_day]
            counter += 1

    print dataframe

    dataframe = IntegerConvertor(dataframe,['Faculty Quality','Course Relevance','Food Quality'])

    '''Seaborn Styling'''
    sns.set_style("darkgrid")
    sns.set(font_scale=1.4)  # crazy big

    writer = pd.ExcelWriter('Submissions/kaushalya-feedback.xlsx')
    dataframe.to_excel(writer,'Feedback')



    '''Student Distribution
    TODO
    plt.ylabel("No of Students")
    plt.savefig("Graphs/student_distribution.png")
    dataframe.to_excel(writer,'Feedback')
    '''''

    locations = ['Mahape','Vidyalankar','Madh Island']

    '''Get Location Wise Ratings'''
    FacultyRatingsByLocation = dict(dataframe.groupby(['Session Location'])['Faculty Quality'].mean())
    print "Faculty Rating by Location"
    print FacultyRatingsByLocation

    FacultyRatingsByLocation = fillZeroes(FacultyRatingsByLocation,locations)

    FoodQualityByLocation = dict(dataframe.groupby(['Session Location'])['Food Quality'].mean())
    print "Food Quality By Location"
    print FoodQualityByLocation

    FoodQualityByLocation = fillZeroes(FoodQualityByLocation,locations)

    CourseRelevanceByLocation = dict(dataframe.groupby(['Session Location'])['Course Relevance'].mean())
    print "Course Relevance By Location"
    print CourseRelevanceByLocation

    CourseRelevanceByLocation = fillZeroes(CourseRelevanceByLocation,locations)


    ratingFrame = pd.DataFrame(columns=['Faculty Rating','Food Quality','Course Relevance'])
    ratingFrame.loc[0] = FacultyRatingsByLocation.values()
    ratingFrame.loc[1] = FoodQualityByLocation.values()
    ratingFrame.loc[2] = CourseRelevanceByLocation.values()

    '''Save this data in Sheet 2'''
    ratingFrame.to_excel(writer, 'Location-Rating')

    x = np.arange(len(FacultyRatingsByLocation))

    y = FacultyRatingsByLocation.values()
    z = FoodQualityByLocation.values()
    k = CourseRelevanceByLocation.values()

    plt.clf()

    ax = plt.subplot(111)
    p1 = ax.bar(x - 0.2, y, width=0.2, color='b', align='center')
    p2 = ax.bar(x, z, width=0.2, color='g', align='center')
    p3 = ax.bar(x + 0.2, k, width=0.2, color='r', align='center')
    ax.xaxis_date()
    ax.set_xlabel("Location")
    ax.set_ylabel("Average Rating")
    ax.set_xticks([0,1,2])
    ax.set_xticklabels(['Mahape','Vidyalankar','Madh Island'])
    ax.legend([p1,p2,p3],['Faculty Quality','Food Quality','Course Relevance'])
    plt.savefig("Graphs/ratings_location_wise.png",dpi=500)

    '''Do same as above Session Wise'''
    sessions = set(dataframe['Session Name'])
    print sessions

    '''Get SessionWise Ratings'''
    FacultyRatingBySessionName = dict(dataframe.groupby(['Session Name'])['Faculty Quality'].mean())
    print "Faculty Rating by Session Name"
    print FacultyRatingBySessionName

    FoodQualityBySessionName = dict(dataframe.groupby(['Session Name'])['Food Quality'].mean())
    print "Food Quality by Session Name"
    print FoodQualityBySessionName

    '''Get SessionWise Ratings'''
    CourseRelevanceBySessionName = dict(dataframe.groupby(['Session Name'])['Course Relevance'].mean())
    print "Course Relevance by Session Name"
    print CourseRelevanceBySessionName

    values = [FacultyRatingBySessionName,FoodQualityBySessionName,CourseRelevanceBySessionName]

    SessionRatingFrame = pd.DataFrame(values)
    print SessionRatingFrame

    '''Save this data in Sheet 3'''
    SessionRatingFrame.to_excel(writer, 'Session-Rating')
    writer.save()

    x = np.arange(len(FacultyRatingBySessionName))

    y = FacultyRatingBySessionName.values()
    z = FoodQualityBySessionName.values()
    k = CourseRelevanceBySessionName.values()

    plt.clf()

    labels = [i for i in sessions]
    print labels

    ax = plt.subplot(111)
    p1 = ax.bar(x - 0.1, y, width=0.1, color='b', align='center')
    p2 = ax.bar(x, z, width=0.1, color='g', align='center')
    p3 = ax.bar(x + 0.1, k, width=0.1, color='r', align='center')
    ax.xaxis_date()
    plt.title("Session Wise Average Ratings")
    ax.set_xticks([i for i in range(len(sessions))])
    ax.set_xticklabels(labels,rotation=90,fontsize=10)
    plt.tight_layout()
    ax.set_xlabel("Sessions")
    ax.set_ylabel("Average Rating")
    ax.legend([p1, p2, p3], ['Faculty Quality', 'Food Quality', 'Course Relevance'],loc='upper center', bbox_to_anchor=(0.5, 1.05),
              ncol=3, fancybox=True, shadow=True)
    plt.savefig("Graphs/ratings_session_wise.png",dpi=500)

    '''Get location wise distribution for each Session'''
    FacultyQualityBySessionNameAndLocation = dict(
        dataframe.groupby(['Session Name', 'Session Location'])['Faculty Quality'].mean())

    FoodQualityBySessionNameAndLocation = dict(
        dataframe.groupby(['Session Name', 'Session Location'])['Food Quality'].mean())

    CourseRelevanceBySessionNameAndLocation = dict(dataframe.groupby(['Session Name','Session Location'])['Course Relevance'].mean())

    dataframe_list = [pd.DataFrame(columns=columns) for i in range(3)]

    RatingsBySessionNameAndLocation = [FacultyQualityBySessionNameAndLocation,FoodQualityBySessionNameAndLocation,CourseRelevanceBySessionNameAndLocation]

    label_list = [[] for i in range(3)]

    for i in range(3):
        print locations[i]
        sessionRatings = []
        for j in range(3):
            sessionRatings.append(generateFrame(RatingsBySessionNameAndLocation[j],locations[i]))
        dataframe_list[i] = pd.DataFrame(sessionRatings)
        print dataframe_list[i]
        label_list[i] = list(dataframe_list[i].columns.values)

    '''Generate Graphs according to the dataframe_list'''
    print label_list


    for i in range(0,3):

        x = np.arange(len(label_list[i]))

        list_of_labels = label_list[i]

        y = list(dataframe_list[i].loc[0])
        z = list(dataframe_list[i].loc[1])
        k = list(dataframe_list[i].loc[2])

        plt.clf()

        ax = plt.subplot(111)
        p1 = ax.bar(x - 0.1, y, width=0.1, color='b', align='center')
        p2 = ax.bar(x, z, width=0.1, color='g', align='center')
        p3 = ax.bar(x + 0.1, k, width=0.1, color='r', align='center')
        ax.xaxis_date()
        plt.title("Session Wise Average Ratings for " + locations[i])
        ax.set_xticks([j for j in range(len(list_of_labels))])
        print label_list[i]
        ax.set_xlabel("Sessions")
        ax.set_ylabel("Average Rating")
        ax.set_xticklabels(label_list[i], rotation=90, fontsize=10)
        plt.tight_layout()
        ax.legend([p1, p2, p3], ['Faculty Quality', 'Food Quality', 'Course Relevance'], loc='upper center',
                  bbox_to_anchor=(0.5, 1.05),
                  ncol=3, fancybox=True, shadow=True)
        plt.savefig("Graphs/session-wise-by-location-" + locations[i] + ".png",dpi=500)




