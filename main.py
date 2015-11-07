from flask import Flask
from flask import render_template, request, redirect, session, url_for, escape, make_response, flash, abort
from flask.ext.sqlalchemy import SQLAlchemy
import os
from api import api
from models import db
from models import User
import requests
import json
from create_map import create_map

app = Flask(__name__)

app.secret_key = "bnNoqxXSgzoXSOezxpfdvadrMp5L0L4mJ4o8nRzn"

app.config['SQLALCHEMY_DATABASE_URI'] = "postgres://wigdnybtvbxjyl:VI5y4w1SgdVdoEDUyCFBmKyqVH@ec2-46-137-72-123.eu-west-1.compute.amazonaws.com:5432/dd5fh71aujkvoq"
db.init_app(app)
app.register_blueprint(api)

@app.route('/')
def index():
    email = None
    all_projects = None
    my_projects = None
    if 'email' in session:
        #loggedIn
        email = session['email']
        my_projects = json.loads(requests.get(url="https://5812d998.ngrok.com/projects/"+email).text)["data"]
        all_projects = json.loads(requests.get(url="https://5812d998.ngrok.com/projects").text)["data"]
        all_projects_map = create_map("", [(i["lat"], i["lng"]) for i in all_projects], ["<p>"+i["name"]+"</p>" for i in all_projects])
        print(my_projects)
        print(all_projects)
        print('Logged in as {}'.format(email))

    return render_template('index.html', email=email, all_projects=all_projects, my_projects=my_projects)

@app.route('/login', methods=['POST'])
def login():
    email = request.form['email']
    password = request.form['password']
    # check credentials against database here
    r = requests.post(url="https://5812d998.ngrok.com/authenticate",data=json.dumps({"email":email, "password":password}))
    
    auth = json.loads(r.text)["result"]

    if(auth):
        session['email'] = email
    else:
        print "Bad login"
    
    return redirect(url_for("index"))

@app.route('/register', methods=['POST'])
def register():
    #handle new user registration here
    first_name = request.form['first_name']
    last_name = request.form['last_name']
    email = request.form['email']
    password = request.form['password']
    address = request.form['address']
    phone = request.form['phone']
    r = requests.post(url="https://5812d998.ngrok.com/create_user",
        data=json.dumps({
            "first_name":first_name,
            "last_name":last_name,
            "email":email, 
            "password":password,
            "address":address,
            "phone":phone}))
    auth = json.loads(r.text)["result"]
    if(auth):
        session['email'] = email
    else:
        print "Error during registration"
    return redirect(url_for("index"))

@app.route('/logout')
def logout():
    # close session and redirect to index
    session.pop('email', None)
    session.pop('admin', None)
    return redirect(url_for('index'))

@app.route('/admin', methods=['GET', 'POST'])
def admin():
    loggedIn = False
    all_projects = False
    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        if(username=='admin' and password == 'admin'):
            session['admin'] = True
        return redirect(url_for('admin'))
    else:
        if 'admin' in session:
            #logged in as admin
            loggedIn = True
            all_projects = json.loads(requests.get(url="https://5812d998.ngrok.com/projects").text)["data"]

<<<<<<< HEAD


    return render_template('admin.html', loggedIn=loggedIn)
=======
    return render_template('admin.html', loggedIn=loggedIn, all_projects=all_projects)
>>>>>>> f8ef7cd5a955ea0e8ef6d4601a17e14efc923c17

@app.route('/admin/volunteer', methods=['GET', 'POST'])
def volunteer():
    return render_template('volunteer.html')
    if 'admin' in session:
        if request.method == 'POST':
            firstname = request.form['firstname']
            lastname = request.form['lastname']
            email = request.form['email']
            postcode = request.form['postcode']
            interests = request.form['interests']
             
        return render_template('volunteer.html');
 
    else:
        return redirect(url_for('admin'))


@app.route('/admin/projects',methods=['POST'])
def projects():
    if 'admin' in session:
        if request.method == 'POST':
            projectname = request.form['projectname']
            description = request.form['description']
            
            address = request.form['address']
            num_people = request.form['num_people']
        if (projectname == '' or description == '' or address == '' or num_people == ''):
            print "Could not submit: empty field"
                   



@app.route('/report')
def report():
    return render_template('report.html')

if __name__ == '__main__':
    app.run(port=8080, debug=True, threaded=True) 
