import pandas as pd
import numpy as np
from firebase import firebase
import json

firebase = firebase.FirebaseApplication('https://feedback-9e534.firebaseio.com', None);

studentFrame = pd.read_csv('reg_ason_13june.csv')
form_id = 0



if __name__ == '__main__':

    to_send = {}
    counter = 0

    studentFrame.dropna()

    columns = studentFrame.keys()
    print columns

    column_id = 0

    for i in studentFrame['Kaushalya ID'] :
        print i
        for j in studentFrame.loc[counter]:
            print columns[column_id],j
            try:
                firebase.put("Submissions/0/" + i + "/",columns[column_id],j)
            except:
                count = 0
            column_id+=1
        column_id=0