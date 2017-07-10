from firebase import firebase

firebase = firebase.FirebaseApplication('https://feedback-9e534.firebaseio.com', None)

if __name__ == '__main__':

    form_id = int(raw_input().strip())

    form_title = raw_input().strip()

    post_string = "Forms/" + str(form_id) + "/"

    print "This is the location in the database where the form is being generated"
    print post_string

    print "Choices available for Form Building"
    print "First Item in Form must always be ID i.e must be a Text Field"
    print "1) TEXT FIELD - type in 'EditText'"
    print "2) RATING FIELD - type in 'Rating'"
    print "3) DROPDOWN - type in 'Dropdown'"

    firebase.put(post_string,"formTitle",form_title)
    test = 0
    string_yes = "Yes"
    while string_yes=="Yes" or string_yes=="yes":
        print "Enter Type"
        type = raw_input().strip()
        firebase.put(post_string + str(test), "type", type)
        print "Enter field description"
        description = raw_input().strip()
        firebase.put(post_string+str(test),"description",description)

        if type == "Dropdown":
            print "Enter choices for the dropdown"
            choices = raw_input().strip().split()
            print choices
            firebase.put(post_string+str(test),"choices",choices)

        string_yes = raw_input().strip()
        test+=1
