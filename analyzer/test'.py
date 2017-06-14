from firebase import firebase
import json
import pandas as pd
import numpy as np
import seaborn as sns
from collections import Counter
import matplotlib.pyplot as plt

firebase = firebase.FirebaseApplication('https://feedback-9e534.firebaseio.com', None);
form_id = 0

if __name__ == '__main__':

    result = firebase.get('/Submissions/'+str(form_id), None)

    '''Save result in a file'''
    with open('submissions.json', 'w+') as outfile:
        json.dump(result, outfile)

    json_result = json.dumps(result)
    print json_result

    dataframe = pd.DataFrame(result)

    print dataframe

    '''Student Distribution'''
    sns.countplot(y='Session Location',data=dataframe)
    plt.ylabel("No of Students")
    plt.show()

    '''Faculty Rating'''
    rating = dataframe['Faculty Quality']
    print "Average Faculty Rating is " , sum(rating)/len(rating)
    sns.countplot(rating)
    plt.show()



