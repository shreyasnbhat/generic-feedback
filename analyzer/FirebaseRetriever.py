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
    counter = 0
    feature_array = np.array([[]])
    columns = []

    '''Store data in DataFrame'''
    for element in result:
        for key in result[element]:
            columns.append(key)
        break

    dataframe = pd.DataFrame(columns=columns)
    counter = 0
    for element in result:
        row_val = []
        for key in result[element]:

            row_val.append(result[element][key])
        dataframe.loc[counter] = row_val
        counter+=1

    print dataframe

    '''Student Distribution'''
    sns.countplot(y='Session Location',data=dataframe)
    plt.ylabel("No of Students")
    plt.show()



