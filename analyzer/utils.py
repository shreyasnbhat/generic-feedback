def IntegerConvertor(dataframe,columns):
    for column in columns :
        dataframe[column] = dataframe[column].astype(int)

    return dataframe
