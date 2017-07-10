from firebase import firebase
import json
import pandas as pd
import numpy as np
import seaborn as sns
from collections import Counter
import matplotlib.pyplot as plt
from utils import IntegerConvertor
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
            columns.append(key)
        break

    dataframe = pd.DataFrame(columns=columns)
    counter = 0

    print ""
    print "Printing Element Wise Result"
    print ""
    for element in result:
        row_val = []
        print element
        print result[element]
        print ""
        dataframe.loc[counter] = result[element]
        counter+=1

    print dataframe

    dataframe = IntegerConvertor(dataframe,['Faculty Quality','Course Relevance','Food Quality'])

    '''Seaborn Styling'''
    sns.set_style("darkgrid")
    sns.set(font_scale=1.4)  # crazy big

    '''Student Distribution'''
    sns.countplot(x='Session Location',data=dataframe,palette='deep')
    plt.ylabel("No of Students")
    plt.savefig("Graphs/student_distribution.png")

    '''Save to Excel'''
    writer = pd.ExcelWriter('Submissions/kaushalya-feedback.xlsx')
    dataframe.to_excel(writer,'Sheet1')
    writer.save()

    '''Get Location Wise Ratings'''
    FacultyRatingsByLocation = dict(dataframe.groupby(['Session Location'])['Faculty Quality'].mean())
    print "Faculty Rating by Location"
    print FacultyRatingsByLocation

    FoodQualityByLocation = dict(dataframe.groupby(['Session Location'])['Food Quality'].mean())
    print "Food Quality By Location"
    print FoodQualityByLocation

    CourseRelevanceByLocation = dict(dataframe.groupby(['Session Location'])['Course Relevance'].mean())
    print "Course Relevance By Location"
    print CourseRelevanceByLocation

    ratingFrame = pd.DataFrame(columns=['Faculty Rating','Food Quality','Course Relevance'])
    ratingFrame.loc[0] = FacultyRatingsByLocation.values()
    ratingFrame.loc[1] = FoodQualityByLocation.values()
    ratingFrame.loc[2] = CourseRelevanceByLocation.values()

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
    ax.set_xticks([0,1,2])
    ax.set_xticklabels(['Mahape','Vidyalankar','Madh Island'])
    ax.legend([p1,p2,p3],['Faculty Quality','Food Quality','Course Relevance'])
    plt.savefig("Graphs/ratings_location_wise.png")

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
    x = np.arange(len(FacultyRatingBySessionName))

    y = FacultyRatingBySessionName.values()
    z = FoodQualityBySessionName.values()
    k = CourseRelevanceBySessionName.values()

    plt.clf()

    ax = plt.subplot(111)
    p1 = ax.bar(x - 0.2, y, width=0.2, color='b', align='center')
    p2 = ax.bar(x, z, width=0.2, color='g', align='center')
    p3 = ax.bar(x + 0.2, k, width=0.2, color='r', align='center')
    ax.xaxis_date()
    ax.set_xticks([0, 1, 2])
    ax.set_xticklabels(sessions)
    ax.legend([p1, p2, p3], ['Faculty Quality', 'Food Quality', 'Course Relevance'])
    plt.savefig("Graphs/ratings_session_wise.png")
