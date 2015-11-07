from flask import Flask
from flask import render_template, request, redirect, session, url_for, escape, make_response, flash, abort
from flask.ext.sqlalchemy import SQLAlchemy
import os
from api import api
from models import db
from models import User
import requests
import json

app = Flask(__name__)

app.secret_key = "bnNoqxXSgzoXSOezxpfdvadrMp5L0L4mJ4o8nRzn"

app.config['SQLALCHEMY_DATABASE_URI'] = "postgres://wigdnybtvbxjyl:VI5y4w1SgdVdoEDUyCFBmKyqVH@ec2-46-137-72-123.eu-west-1.compute.amazonaws.com:5432/dd5fh71aujkvoq"
db.init_app(app)
app.register_blueprint(api)

@app.route('/')
def index():
    email = None
    if 'email' in session:
        #loggedIn
        email = session['email']
        print('Logged in as {}'.format(email))

    return render_template('index.html', email=email)

@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == 'POST':
        email = request.form['email']
        password = request.form['password']
        # check credentials against database here
        r = requests.post(url="http://localhost:8080/authenticate",data=json.dumps({"email":email, "password":password}))
        auth = json.loads(r)["result"]
        if(auth):
            session['email'] = email
        else:
            print "Bad login"
    
    return redirect(url_for("index"))

@app.route('/register', methods=['GET', 'POST'])
def register():
    #handle new user registration here
    if request.method == 'POST':
        first_name = request.form['first_name']
        last_name = request.form['last_name']
        email = request.form['email']
        password = request.form['password']
        r = requests.post(url="http://localhost:8080/create_user",
            data=json.dumps({
                "first_name":first_name,
                "last_name":last_name,
                "email":email, 
                "password":password}))
        auth = json.loads(r)["result"]
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


    return render_template('admin.html', loggedIn=loggedIn)

@app.route('/admin/volunteer', methods=['GET', 'POST'])
def volunteer():
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


@app.route('/report')
def report():
    return render_template('report.html')

if __name__ == '__main__':
    app.run(port=8080, debug=True, threaded=True) 
