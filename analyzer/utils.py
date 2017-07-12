def IntegerConvertor(dataframe,columns):
    for column in columns :
        dataframe[column] = dataframe[column].astype(int)

    return dataframe

def fillZeroes(var,dict_entries):
    for i in dict_entries :
        if i not in var.keys() :
            var[i] = 0

    return var

def generateFrame(var,place):

    dict_loc = {}
    for session, location in var:
        if location == place :
            dict_loc[session] = var[(session,location)]
    return dict_loc